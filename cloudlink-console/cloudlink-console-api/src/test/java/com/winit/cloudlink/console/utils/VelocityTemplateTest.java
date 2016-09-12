package com.winit.cloudlink.console.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.winit.cloudlink.storage.api.constants.AlarmType;

public class VelocityTemplateTest {

    @Test
    public void testGetTemplateNameByAlarmType() {
        String templateName = VelocityTemplate.getTemplateNameByAlarmType(AlarmType.CLUSTER_NODE_STATUS);

        assertEquals(VelocityTemplate.NODE_STATUS.getTemplateName(), templateName);
    }

}
