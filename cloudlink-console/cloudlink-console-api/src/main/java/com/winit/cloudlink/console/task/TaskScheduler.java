package com.winit.cloudlink.console.task;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.winit.cloudlink.console.bean.SystemConfig;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.console.utils.SystemConfigs;
import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;
import com.winit.cloudlink.storage.api.manager.AreaManager;
import com.winit.cloudlink.storage.api.manager.SystemConfigManager;
import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import com.winit.cloudlink.storage.api.vo.SystemConfigVo;

@Component("taskScheduler")
public class TaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    @Autowired
    private Scheduler           scheduler;

    @Autowired
    private AlarmConfigManager  alarmConfigManager;

    @Autowired
    private ApplicationContext  applicationContext;

    @Autowired
    private AreaManager         areaManager;

    @Autowired
    private SystemConfigManager systemConfigManager;

    @Resource
    private SystemConfig        systemConfig;

    /**
     * 初始化定时任务
     * 
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        logger.info(String.format(">>>>>>Quartz timed task start status : %s", scheduler.isStarted()));

        // 初始化默认邮箱地址
        SystemConfigVo systemConfig = systemConfigManager.findSystemConfig();
        if (null != systemConfig && null != systemConfig.getAlarmEmail()) {
            SystemConfigs.setEmail(systemConfig.getAlarmEmail());
        }
        // 初始化rabbit管理api的调用客户端
        RabbitMgmtServiceHelper.init(areaManager.findAll());
        // 获取所有的告警配置
        Collection<AlarmConfigVo> taskList = alarmConfigManager.findAll();

        if (null != taskList) {
            logger.info(String.format(">>>>>>Start alarm timer task, The number of tasks is %s.", taskList.size()));
            for (AlarmConfigVo task : taskList) {
                try {
                    startTask(task);
                } catch (Exception e) {
                    logger.error(">>>>>>Start alarm task failed.", e);
                }
            }
        } else {
            logger.info(">>>>>>Start alarm timer task, There is no need to start the alarm task.");
        }

        // 开启邮件重发服务
        startMailResend();
    }

    public void startMailResend() throws Exception {
        String taskId = "task_mail_resend";
        String groupId = "group_mail_resend";
        TriggerKey triggerKey = TriggerKey.triggerKey(taskId, groupId);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (null == trigger) {
            logger.info(">>>>>>Start the mail resend task ");
            JobDetail jobDetail = JobBuilder.newJob(MailResendTask.class).withIdentity(taskId, groupId).build();
            jobDetail.getJobDataMap().put("applicationContext", applicationContext);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(systemConfig.getMailResendCron());
            // 按新的表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(taskId, groupId).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info(String.format(">>>>>>Task to start successfully, the task is %s", "mail resend"));
        }
    }

    public void startTask(AlarmConfigVo task) throws Exception {
        if (null != task) {
            String taskId = "task_" + task.getAlarmType();
            String groupId = "group_" + task.getAlarmType();
            // 任务名称和任务组设置规则：
            TriggerKey triggerKey = TriggerKey.triggerKey(taskId, groupId);

            // 不存在，创建一个
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (task.isEnabled()) {
                if (null == trigger) {
                    logger.info(String.format(">>>>>>Start the alarm task, the task is %s", taskId));
                    JobDetail jobDetail = JobBuilder.newJob(QuartzTaskFactory.class)
                        .withIdentity(taskId, groupId)
                        .build();
                    jobDetail.getJobDataMap().put("scheduleJob", task);
                    jobDetail.getJobDataMap().put("applicationContext", applicationContext);
                    // 表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getAlarmCron());
                    // 按新的表达式构建一个新的trigger
                    trigger = TriggerBuilder.newTrigger()
                        .withIdentity(taskId, groupId)
                        .withSchedule(scheduleBuilder)
                        .build();
                    scheduler.scheduleJob(jobDetail, trigger);
                    logger.info(String.format(">>>>>>Task to start successfully, the task is %s", taskId));
                } else {
                    logger.info(String.format(">>>>>>Restart the alarm task, the task is %s", taskId));
                    // trigger已存在，则更新相应的定时设置
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getAlarmCron());
                    // 按新的cronExpression表达式重新构建trigger
                    trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                    scheduler.getJobDetail(new JobKey(taskId, groupId)).getJobDataMap().put("scheduleJob", task);
                    // 按新的trigger重新设置job执行
                    scheduler.rescheduleJob(triggerKey, trigger);
                    logger.info(String.format(">>>>>>Restart timed task successfully, the task is %s", taskId));
                }
            } else {
                if (null != trigger) {
                    logger.info(String.format(">>>>>>Alarm task disabled, the task is %s", taskId));
                    scheduler.unscheduleJob(triggerKey);
                }
            }

        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        if (null != scheduler) {
            scheduler.shutdown();
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

}
