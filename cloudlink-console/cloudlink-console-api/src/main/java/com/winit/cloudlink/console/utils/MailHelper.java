package com.winit.cloudlink.console.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.winit.cloudlink.storage.api.manager.MailManager;
import com.winit.cloudlink.storage.api.vo.MailVo;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 邮件发送工具类
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月31日 	Created
 *
 * </pre>
 * @since 1.
 */
@Component("mailHelper")
public class MailHelper {

    private static final Logger logger = LoggerFactory.getLogger(MailHelper.class);

    @Resource
    private JavaMailSender      mailSender;

    @Resource
    private VelocityEngine      velocityEngine;

    @Resource
    private SimpleMailMessage   simpleMailMessage;

    @Resource
    private MailManager mailManager;

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * 发送模板邮件
     */
    public void sendWithTemplate(String[] to, String subject, String templateName, Map<String, Object> model) {
        mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        String result = null;
        try {
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
            messageHelper.setSubject(subject);
            result = VelocityEngineUtils.mergeTemplateIntoString(this.getVelocityEngine(), templateName, "UTF-8", model);
            messageHelper.setText(result, true);
        } catch (Exception e) {
            logger.error("merge Velocity Template error", e);

        }
        //发送失败的存入数据库中继续发送
        boolean sendSuccess=true;
        try {
            mailSender.send(mimeMessage);
        }catch (Exception e){
            sendSuccess=false;
            logger.error("mail send error", e);
        }
        if(!sendSuccess){
            MailVo mailVo=new MailVo();
            mailVo.setId(UUID.randomUUID().toString().replace("-",""));
            Date now= new Date();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=format.format(now);
            mailVo.setSubject(subject+" "+time);
            mailVo.setContent(result);
            Set<String> setReceiver=new HashSet<String>();
            for(String str:to){
                setReceiver.add(str);
            }
            mailVo.setReceivers(setReceiver);
            mailVo.setDate(now);
            mailVo.setSendSuccess(false);
            try {
                mailManager.saveOrUpdate(mailVo);
            }catch (Exception e){
                logger.error("save mail error", e);
            }
        }
    }

    /**
     * 发送普通文本邮件
     */
    public void sendText(String[] to, String subject, String content) {
        mailSender = this.getMailSender();
        simpleMailMessage.setTo(to); // 接收人
        simpleMailMessage.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        mailSender.send(simpleMailMessage);
    }

    /**
     * 发送普通Html邮件
     */
    public void sendHtml(String[] to, String subject, String content) {
        mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            logger.error("Build MimeMessageHelper error", e);
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送普通带一张图片的Html邮件
     */
    public void sendHtmlWithImage(String[] to, String subject, String content, String imagePath) {
        mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            if (logger.isDebugEnabled()) {
                logger.debug("Mail attachments add images :" + imagePath);
            }
            FileSystemResource img = new FileSystemResource(new File(imagePath));
            messageHelper.addInline("image", img);
        } catch (MessagingException e) {
            logger.error("Build MimeMessageHelper error", e);
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送普通带附件的Html邮件
     */
    public void sendHtmlWithAttachment(String[] to, String subject, String content, String filePath) {
        mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            if (logger.isDebugEnabled()) {
                logger.debug("Mail attachments add file :" + filePath);
            }
            FileSystemResource file = new FileSystemResource(new File(filePath));
            messageHelper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            logger.error("Build MimeMessageHelper error", e);
        }
        mailSender.send(mimeMessage);
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }
}
