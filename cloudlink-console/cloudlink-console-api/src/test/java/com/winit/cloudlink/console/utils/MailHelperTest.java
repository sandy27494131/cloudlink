package com.winit.cloudlink.console.utils;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.winit.cloudlink.console.SpringControllerTestBase;

public class MailHelperTest extends SpringControllerTestBase {

    private static String[] to      = new String[] { "jianke.zhang1@winit.com.cn" };

    private static String   subject = "云链监控邮件测试";

    private static String   content = "云链监控邮件测试";

    @Resource
    private MailHelper      mailHelper;

    @Test
    public void testSendWithTemplate() {
        String templateName = "test.vm";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("hello", "测试成功");
        mailHelper.sendWithTemplate(to, subject, templateName, model);
    }

    @Test
    public void testSendText() {
        mailHelper.sendText(to, subject, content);
    }

    @Test
    public void testSendHtml() {
        mailHelper.sendHtml(to, subject, content);
    }

    @Test
    public void testSendHtmlWithImage() {
        // ignore
    }

    @Test
    public void testSendHtmlWithAttachment() {
        // ignore
    }

}
