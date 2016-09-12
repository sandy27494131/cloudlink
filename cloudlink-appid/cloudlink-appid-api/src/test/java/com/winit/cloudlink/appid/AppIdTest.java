package com.winit.cloudlink.appid;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by stvli on 2015/12/28.
 */
public class AppIdTest {

    @Test
    public void testParse() {
        Assert.assertNotNull(AppId.parse("TOM1.TOM.ALL.CNR"));
    }


    @Test
    public void testToString() {
        String appIdString = "TOM1.TOM.ALL.CNR";
        AppId appId = AppId.parse(appIdString);
        Assert.assertEquals(appIdString, appId.toString());
    }

    @Test
    public void testEquals() {
        String appIdString1 = "TOM1.TOM.ALL.CNR";
        String appIdString2 = "TOM2.TOM.ALL.CNR";
        AppId appId1 = AppId.parse(appIdString1);
        AppId appId2 = AppId.parse(appIdString2);
        AppId appId3 = AppId.parse(appIdString2);
        Assert.assertNotEquals(appId1, appId2);
        Assert.assertEquals(appId2, appId3);

    }
}
