package com.winit.cloudlink.console.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.winit.cloudlink.console.SpringControllerTestBase;

public class VelocityEngineTest extends SpringControllerTestBase {

    @Resource
    private VelocityEngine velocityEngine;

    @Test
    public void test() {
        String templateName = "test.vm";

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("hello", "hello zhang");

        String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", model);
        System.out.println(result);
        assertEquals("<!DOCTYPE html><html><body><h1>hello zhang</h1></body></html>", result);
    }
}
