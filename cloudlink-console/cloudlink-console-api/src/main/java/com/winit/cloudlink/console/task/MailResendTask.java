package com.winit.cloudlink.console.task;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.winit.cloudlink.console.utils.MailHelper;
import com.winit.cloudlink.storage.api.manager.MailManager;
import com.winit.cloudlink.storage.api.vo.MailVo;

/**
 * Created by xiangyu.liang on 2016/1/25.
 */
public class MailResendTask implements Job {

    private static final Logger logger = LoggerFactory.getLogger(MailResendTask.class);
    private ApplicationContext  applicationContext;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        applicationContext = (ApplicationContext) context.getMergedJobDataMap().get("applicationContext");
        MailManager mailManager = applicationContext.getBean(MailManager.class);
        MailHelper mailHelper = applicationContext.getBean(MailHelper.class);
        List<MailVo> lstMailVo = mailManager.findSendFail();
        for (MailVo mailVo : lstMailVo) {
            try {
                String[] to = new String[mailVo.getReceivers().size()];
                mailVo.getReceivers().toArray(to);
                mailHelper.sendHtml(to, mailVo.getSubject(), mailVo.getContent());
                mailManager.delete(mailVo.getId());
            } catch (Exception e) {
                logger.error("mail resend failed", e);
            }
        }
    }
}
