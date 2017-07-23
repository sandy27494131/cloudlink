package com.winit.robot.scheduler;

import com.google.common.collect.Maps;
import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.common.AppID;
import com.winit.robot.sms.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

import static com.winit.robot.utils.DateTimes.diffSeconds;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.split;

/**
 * Created by stvli on 2017/4/5.
 */
@Component
public class CommandAlertScheduler {
    @Autowired
    private SmsSender smsSender;

    @Autowired
    private Cloudlink cloudlink;

    public static final Map<String, String> zoneDisplayNames = Maps.newLinkedHashMap();

    static {
        zoneDisplayNames.put("CNR", "中国");
        zoneDisplayNames.put("USR", "美国");
        zoneDisplayNames.put("AUR", "澳洲");
        zoneDisplayNames.put("EUR", "欧洲");
    }

    /**
     * 告警阈值，以秒为单位，默认600秒，即10分钟末收到指令就告警，
     */
    @Value("${spring.cloudlink.robot.alarmThreshold:600}")
    private Long alarmThreshold;

    @Value("${spring.cloudlink.robot.alerted:false}")
    private boolean alerted;

    @Value("${spring.cloudlink.robot.alert.zone:}")
    private String alertZone;

    @Value("${spring.cloudlink.robot.alert.sms.to:}")
    private String smsAlertTo;
    private String[] smsAlertToList;

    private Map<String, Date> lastTimes = Maps.newLinkedHashMap();


    @PostConstruct
    public void init() {
        if (isNotBlank(alertZone)) {
            String[] alertZones = split(alertZone, ",");
            Date now = new Date();
            for (String zone : alertZones) {
                lastTimes.put(zone, now);
            }
        }
        if (isNotBlank(smsAlertTo)) {
            smsAlertToList = split(smsAlertTo, ",");
        }
    }

    public void onReceive(Command<?> command) {
        String fromZone = new AppID(command.getHeaders().getFromAppId()).getArea();
        if (lastTimes.containsKey(fromZone)) {
            lastTimes.put(fromZone, new Date());
        }
    }


    @Scheduled(fixedRate = 1000 * 60 * 10)//每10分钟执行一次
    public void alert() {
        if (alerted) {
            Date now = new Date();
            for (Map.Entry<String, Date> entry : lastTimes.entrySet()) {
                long diffSeconds = diffSeconds(entry.getValue(), now);
                if (diffSeconds >= alarmThreshold) {
                    alert(entry.getKey(), diffSeconds);
                }
            }
        }
    }

    private void alert(String fromZone, long diffSeconds) {
        String currentZone = cloudlink.getMetadata().getCurrentZone().getName();
        String content = String.format("%s机器人已超过%s秒没有收到从%s发过来的指令了",
                getZoneDisplayName(currentZone),
                diffSeconds,
                getZoneDisplayName(fromZone)
        );
        if (null != smsAlertToList && smsAlertToList.length > 0) {
            smsSender.sendSms(content, smsAlertToList);
        }
    }

    private String getZoneDisplayName(String zone) {
        return zoneDisplayNames.get(zone);
    }
}