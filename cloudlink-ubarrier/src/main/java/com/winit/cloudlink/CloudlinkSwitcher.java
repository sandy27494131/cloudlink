package com.winit.cloudlink;

import com.winit.cloudlink.config.MqServerOptions;
import com.winit.ubarrier.common.Constants;
import com.winit.ubarrier.common.SwitchCommand;
import com.winit.ubarrier.common.status.Health;
import com.winit.ubarrier.common.util.Nets;
import com.winit.ubarrier.common.util.Threads;
import com.winit.ubarrier.jmx.agent.JmxAbstractSwitcher;
import com.winit.ubarrier.jmx.agent.UbarrierAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stvli on 2017/6/20.
 */
public class CloudlinkSwitcher extends JmxAbstractSwitcher {
    private static final Logger logger = LoggerFactory.getLogger(CloudlinkSwitcher.class);
    public static final String NAME = "cloudlink";
    public static final String DEFAULT_UBARRIER_VIRTUAL_HOST = "ubarrier";
    private Cloudlink cloudlink;
    private MqServerOptions mqServerOptions;
    private MqServerOptions ubarrierMqServerOptions;

    public CloudlinkSwitcher(Cloudlink cloudlink) {
        this.cloudlink = cloudlink;
        this.mqServerOptions = cloudlink.getMetadata().getCurrentZone().getMqServerOptions();
        this.ubarrierMqServerOptions = MqServerOptions.build(
                mqServerOptions.getHost(),
                mqServerOptions.getPort(),
                mqServerOptions.getUsername(),
                mqServerOptions.getPassword(),
                DEFAULT_UBARRIER_VIRTUAL_HOST
        );
        UbarrierAgent.getInstance().registerSwitcher(this);
    }

    @Override
    public Health health() {
        return Health.status(grayMode.name()).build();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    protected void executeInternel(final SwitchCommand command) {
        if (!needToSwitchCloudlink(command)) {
            logger.info("Cloudlink无需切换.");
            return;
        }
        MqServerOptions switchMqServerOptions = isGray(command) ? ubarrierMqServerOptions : mqServerOptions;
        if (switchMqServerOptions.equals(cloudlink.getMetadata().getCurrentZone().getMqServerOptions())) {
            logger.info("Cloudlink无需切换,原因是当前MQ配置与待切换MQ配置一致.");
            return;
        }
        logger.info("Cloudlink当前的MQ配置:{}", cloudlink.getMetadata().getCurrentZone().getMqServerOptions());
        logger.info("Cloudlink待切换MQ配置:{}", switchMqServerOptions);
        logger.info("Cloudlink开始切换到{}模式...", command.getGrayMode().name());
        cloudlink.getMetadata().getCurrentZone().setMqServerOptions(switchMqServerOptions);
        cloudlink.deactive();
        logger.info("Cloudlink已停止消费消息");
        final int cloudlinkActiveDeloys = command.getConfig() != null ? command.getConfig().getCloudlinkActiveDelays()
                : Constants.DEFAULT_CLOUDLINK_ACTIVE_DELAYS;
        if (cloudlinkActiveDeloys > 0) {
            logger.info("Cloudlink将延时{}毫秒后激活.", cloudlinkActiveDeloys);
            Thread activeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Threads.sleep(cloudlinkActiveDeloys);
                    cloudlink.active();
                    logger.info("Cloudlink已切换到{}模式.", command.getGrayMode().name());
                }
            });
            activeThread.setDaemon(true);
            activeThread.start();
        } else {
            cloudlink.active();
            logger.info("Cloudlink已切换到{}模式.", command.getGrayMode().name());
        }
    }

    protected boolean needToSwitchCloudlink(final SwitchCommand command) {
        if (command == null || command.getGrayMode() == null || command.getGrayMode().equals(this.grayMode)) {
            return false;
        }
        String host = Nets.getLocalHost();
        switch (command.getGrayMode()) {
            case GNONE:
                return true;
            case GA:
                return command.getAgroupHosts().contains(host);
            case GB:
                return command.getBgroupHosts().contains(host);
            default:
                return false;
        }
    }

}
