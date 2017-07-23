package com.winit.cloudlink.console.task;

import java.util.*;

import com.winit.cloudlink.console.sms.SmsSender;
import com.winit.cloudlink.console.task.check.QueueAlarmCheckMaker;
import com.winit.cloudlink.storage.api.manager.QueueAlarmManager;
import com.winit.cloudlink.storage.api.vo.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.console.task.check.ExchangeCheckMaker;
import com.winit.cloudlink.console.task.check.QueueCheckMaker;
import com.winit.cloudlink.console.task.check.ShovelCheckMaker;
import com.winit.cloudlink.console.task.model.ExchangeCheck;
import com.winit.cloudlink.console.task.model.QueueCheck;
import com.winit.cloudlink.console.task.model.ShovelCheck;
import com.winit.cloudlink.console.utils.Constants;
import com.winit.cloudlink.console.utils.MailHelper;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.console.utils.SystemConfigs;
import com.winit.cloudlink.console.utils.VelocityTemplate;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Aliveness;
import com.winit.cloudlink.rabbitmq.mgmt.model.Node;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelLink;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelStatus;
import com.winit.cloudlink.storage.api.constants.AlarmSettingsKey;
import com.winit.cloudlink.storage.api.constants.AlarmType;
import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;
import com.winit.cloudlink.storage.api.manager.AppIdManager;
import com.winit.cloudlink.storage.api.manager.AppQueueManager;

public class QuartzTaskFactory implements Job {

    private static final Logger logger = LoggerFactory.getLogger(QuartzTaskFactory.class);

    private static final Locale LOCALE = Locale.CHINA;

    private ApplicationContext  applicationContext;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("任务运行...");
            AlarmConfigVo task = (AlarmConfigVo) context.getMergedJobDataMap().get("scheduleJob");
            applicationContext = (ApplicationContext) context.getMergedJobDataMap().get("applicationContext");
            AlarmConfigManager alarmConfigManager = applicationContext.getBean(AlarmConfigManager.class);
            task = alarmConfigManager.findByAlarmType(task.getAlarmType());
            logger.info("任务名称: [" + task.getAlarmType() + "]");

            if (null != task && task.getArea() != null) {
                Object object = null;
                if (AlarmType.CLUSTER_NODE_STATUS.name().equals(task.getAlarmType())) {
                    object = executeCusterNodeStatusAlarm(task);
                } else if (AlarmType.NODE_WORKER_STATUS.name().equals(task.getAlarmType())) {
                    object = executeNodeWorkerStatusAlarm(task);
                } else if (AlarmType.CONNECTIONS.name().equals(task.getAlarmType())) {
                    object = executeConnectionsAlarm(task);
                } else if (AlarmType.EXCHANGE_STATUS.name().equals(task.getAlarmType())) {
                    object = executeExchangeAlarm(task);
                } else if (AlarmType.QUEUE_STATUS.name().equals(task.getAlarmType())) {
                    object = executeQueueAlarm(task);
                } else if (AlarmType.SHOVEL_STATUS.name().equals(task.getAlarmType())) {
                    object = executeShovelAlarm(task);
                }

                if (null != object) {
                    Map<String, Object> content = new HashMap<String, Object>();
                    content.put("info", object);
                    sendAlarmMail(AlarmType.valueOf(task.getAlarmType()), task, content);
                }
            }

            // 在这里执行你的任务...
        } catch (Throwable e) {
            logger.error("Alarm task execution failed", e);
        }

    }

    /**
     * 工作状态告警
     * 
     * @param task
     */
    private Map<String, Object> executeNodeWorkerStatusAlarm(AlarmConfigVo task) {
        boolean flag = false;
        Map<String, Object> model = new HashMap<String, Object>();
        if (null != task && task.getArea() != null) {
            for (String areaCode : task.getArea()) {
                try {
                    RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
                    if (service == null) {
                        continue;
                    }
                    Aliveness aliveness = service.aliveness().test(service.getVhost()).get();
                    if (!aliveness.isOk()) {
                        flag = true;
                        AreaVo areaVo = RabbitMgmtServiceHelper.getAreaVoByAreaCode(areaCode);
                        URL url = URL.valueOf(areaVo.getMqMgmtAddr());
                        model.put(areaCode, new String[] { areaVo.getCode(), areaVo.getName(), url.getHost() });
                    }
                } catch (Exception e) {
                    logger.error("node worker status alarm error",e);
                    sendConnErrorMail(areaCode);
                }
            }
        }
        if (flag) {
            return model;
        }
        return null;
    }

    private String executeConnectionsAlarm(AlarmConfigVo task) {
        StringBuffer sbError = new StringBuffer("");
        boolean flag = false;
        for (String areaCode : task.getArea()) {
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
                if (service == null) {
                    continue;
                }
                int max = Integer.parseInt(task.getAlarmSettings().get(AlarmSettingsKey.CONNECTIONS_THRESHOLD.name()));
                int cnt = service.connections().all().size();
                if (cnt > max) {
                    flag = true;
                    String tmp = applicationContext.getMessage(Constants.KEY_CONNECTIONS_CONTENT, new Object[] {
                            areaCode, max }, Locale.CHINA);
                    sbError.append(tmp + "<br>");
                }
                max = Integer.parseInt(task.getAlarmSettings().get(AlarmSettingsKey.CHANNELS_THRESHOLD.name()));
                cnt = service.channels().all().size();
                if (cnt > max) {
                    flag = true;
                    String tmp = applicationContext.getMessage(Constants.KEY_CHANNELS_CONTENT, new Object[] { areaCode,
                            max }, Locale.CHINA);
                    sbError.append(tmp + "<br>");
                }
                // cnt=service.overview().get
            } catch (Exception e) {
                logger.error("connection alarm error",e);
                sendConnErrorMail(areaCode);
            }
        }
        if (flag) {
            return sbError.toString();
        } else {
            return null;
        }
    }

    private List<ExchangeCheck> executeExchangeAlarm(AlarmConfigVo task) {
        List<ExchangeCheck> datas = new ArrayList<ExchangeCheck>();
        List<ExchangeCheck> lstExchagneCheck = ExchangeCheckMaker.check(task);
        for (ExchangeCheck exchangeCheck : lstExchagneCheck) {
            if (exchangeCheck.isExceptional()) {
                // winnit_send是否存在，winnit_receive是否存在，是否建立绑定关系，send是否持久化，类型,receive是否持久化,类型
                datas.add(exchangeCheck);
            }
        }

        if (datas.size() > 0) {
            return datas;
        } else {
            return null;
        }
    }

    private List<QueueCheck> executeQueueAlarm(AlarmConfigVo task) {
        AppIdManager appIdManager = applicationContext.getBean(AppIdManager.class);
        QueueAlarmManager queueAlarmManager = applicationContext.getBean(QueueAlarmManager.class);
        List<AppIdVo> lstAllAppId = appIdManager.findEnable();
        List<QueueAlarmVo> lstAllAppQueue = queueAlarmManager.findAll();
        QueueAlarmCheckMaker queueAlarmCheckMaker = applicationContext.getBean(QueueAlarmCheckMaker.class);
        List<QueueCheck> lstQueueCheck = queueAlarmCheckMaker.check(task, lstAllAppQueue, true);
        List<QueueCheck> errors = new ArrayList<QueueCheck>();
        for (QueueCheck queueCheck : lstQueueCheck) {
            if (queueCheck.isExceptional()) {
                // 数据中心，队列名，是否创建，是否持久化，是否自动删除,消息堆积数量,消息堆积数量是否超过阈值,是否正常消费
                errors.add(queueCheck);
            }
        }
        sendEmailToHandler(errors, task);
        if (errors.size() > 0) {
            return errors;
        } else {
            return null;
        }
    }

    /**
     * 发送告警邮件到各个出错队列的负责人
     * 
     * @param errors
     */
    private void sendEmailToHandler(List<QueueCheck> errors, AlarmConfigVo task) {

        // 发送告警短信
        sendSmsToHandler(errors, task);

        Set<String> emailAddrs = new HashSet<String>();
        for (QueueCheck queueCheck : errors) {
            if (queueCheck.getEmails() != null) emailAddrs.addAll(queueCheck.getEmails());
        }
        for (String emailAddr : emailAddrs) {
            List<QueueCheck> errorsSend = new ArrayList<QueueCheck>();
            for (QueueCheck queueCheck : errors) {
                if (queueCheck.getEmails() != null && queueCheck.getEmails().contains(emailAddr)) {
                    errorsSend.add(queueCheck);
                }
            }
            MailHelper mailHelper = applicationContext.getBean(MailHelper.class);
            String templateName = VelocityTemplate.getTemplateNameByAlarmType(AlarmType.valueOf(task.getAlarmType()));
            String subject = getMailSubject(templateName);
            Map model = new HashMap();
            model.put("info", errorsSend);
            mailHelper.sendWithTemplate(new String[] { emailAddr }, subject, templateName, model);
        }
    }

    private void sendSmsToHandler(List<QueueCheck> errors, AlarmConfigVo task) {
        if (ArrayUtils.contains(task.getAlarmWay(), AlarmConfigVo.ALARM_SMS)) {
            String gmobile = task.getMobile();
            Set<String> globalMobiles = new HashSet<String>();
            if (StringUtils.isNotBlank(gmobile)) {
                String[] arr = org.springframework.util.StringUtils.tokenizeToStringArray(gmobile, ",", true, true);
                globalMobiles.addAll(Arrays.asList(arr));
            }

            Set<String> customMobiles = new HashSet<String>();
            for (QueueCheck queueCheck : errors) {
                customMobiles.addAll(queueCheck.getMobiles());
            }

            customMobiles.removeAll(globalMobiles);

            for (String mobile : customMobiles) {
                List<QueueCheck> errorsSend = new ArrayList<QueueCheck>();
                for (QueueCheck queueCheck : errors) {
                    if (queueCheck.getEmails() != null && queueCheck.getMobiles().contains(mobile)) {
                        errorsSend.add(queueCheck);
                    }
                }
                sendSMS(errorsSend.toString(), mobile);
            }
        }
    }

    /**
     * Shovel配置告警检测,并发送告警
     * 
     * @param task
     */
    private List<ShovelCheck> executeShovelAlarm(AlarmConfigVo task) {
        boolean hasExceptionShovel = false;
        List<ShovelCheck> checks = ShovelCheckMaker.buildShovelCheck(Arrays.asList(task.getArea()));
        for (String areaCode : task.getArea()) {
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
                if (null != service) {

                    Collection<ShovelLink> shovelLinks = service.shovel().links(service.getVhost());
                    Collection<ShovelStatus> shovelStatus = service.shovel().status();

                    ShovelCheckMaker.checkShovels(areaCode, checks, shovelLinks, shovelStatus);

                }
            } catch (Exception e) {
                logger.error("shovel alarm error",e);
                sendConnErrorMail(areaCode);
            }
        }

        int idx = checks.size() - 1;
        ShovelCheck check = null;
        for (int i = idx; i >= 0; i--) {
            check = checks.get(i);
            if (check.isExceptonShovel()) {
                hasExceptionShovel = true;
            } else {
                // shovel检测正常则移除检查对象
                checks.remove(check);
            }
        }

        if (hasExceptionShovel) {
            return checks;
        }
        return null;
    }

    /**
     * 检测集群节点状态，并并发送异常告警
     * 
     * @param task
     */
    private String executeCusterNodeStatusAlarm(AlarmConfigVo task) {
        StringBuffer sbError = new StringBuffer("");
        boolean flag = false;
        for (String areaCode : task.getArea()) {
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
                if (service == null) {
                    continue;
                }
                Collection<Node> nodes = service.nodes().all();
                for (Node node : nodes) {
                    if (!node.isRunning()) {
                        flag = true;
                        String tmp = applicationContext.getMessage(Constants.KEY_NODE_STATUS_CONTENT, new Object[] {
                                areaCode, node.getName() }, Locale.CHINA);
                        sbError.append(tmp + "<br>");
                    }
                }
            } catch (Exception e) {
                logger.error("cluster alarm error",e);
                sendConnErrorMail(areaCode);
            }
        }
        if (flag) {
            return sbError.toString();
        } else {
            return null;
        }
    }

    /**
     * 发送告警邮件
     * 
     * @param alarmType
     * @param task
     * @param model
     */
    private void sendAlarmMail(AlarmType alarmType, AlarmConfigVo task, Map<String, Object> model) {

        if (ArrayUtils.contains(task.getAlarmWay(), AlarmConfigVo.ALARM_SMS)) {
            String gmobile = task.getMobile();
            if (StringUtils.isNotBlank(gmobile)) {
                String[] mobiles = org.springframework.util.StringUtils.tokenizeToStringArray(gmobile, ",", true, true);
                for (String mobile : mobiles) {
                    sendSMS(model.get("info").toString(), mobile);
                }
            }
        }

        MailHelper mailHelper = applicationContext.getBean(MailHelper.class);
        String templateName = VelocityTemplate.getTemplateNameByAlarmType(alarmType);
        String subject = getMailSubject(templateName);
        mailHelper.sendWithTemplate(getEmails(task), subject, templateName, model);
    }

    private void sendMail(String[] receivers, String subject, String content) {
        try {
            MailHelper mailHelper = applicationContext.getBean(MailHelper.class);
            mailHelper.sendHtml(receivers, subject, content);
        } catch (Exception e) {
            logger.error("Alarm send email failure.", e);
        }
    }

    private void sendConnErrorMail(String areaCode) {
        AreaVo areaVo = RabbitMgmtServiceHelper.area4AreaVoMap.get(areaCode);
        URL url = URL.valueOf(areaVo.getMqMgmtAddr());
        sendMail(SystemConfigs.getEmail().split(";"), "RabbitMQ连接异常", "数据中心" + areaCode + "的RabbitMQ(" + url.getHost()
                                                                      + ":" + url.getPort() + ")无法访问!");
        logger.info("------------------------>>>>>>>>>>>>>>>>>>>>>>数据中心" + areaCode + "连接不能!");
    }

    /**
     * 构建邮件地址
     * 
     * @param task
     * @return
     */
    private String[] getEmails(AlarmConfigVo task) {
        Set<String> lstEmail = new HashSet<String>();
        if (null != SystemConfigs.getEmail() && StringUtils.isNotBlank(SystemConfigs.getEmail())) {
            lstEmail.addAll(Arrays.asList(SystemConfigs.getEmail().split(";")));
        }
        if (null != task.getAlarmEmail() && StringUtils.isNotBlank(task.getAlarmEmail())) {
            lstEmail.addAll(Arrays.asList(task.getAlarmEmail().split(";")));
        }
        String[] arr = new String[lstEmail.size()];
        lstEmail.toArray(arr);
        return arr;
    }

    private String getMailSubject(String code) {
        return applicationContext.getMessage(code, null, LOCALE);
    }

    private void sendSMS(String content, String... to) {
        try {
            SmsSender smsSender = applicationContext.getBean(SmsSender.class);
            smsSender.sendSms("MQ告警：" + content, to);
        } catch (Throwable e) {
            logger.error("send sms error.",  e);
        }
    }

    public static void main(String[] args) {
        URL url = URL.valueOf("amqp://admin:admin@127.0.0.1:5672");

        System.out.println(url.getProtocol());
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getUsername());
        System.out.println(url.getPassword());
    }
}
