package com.winit.cloudlink.appid;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by stvli on 2015/12/29.
 */
public class AppIdBuilderTest {

    @Test
    public void testRegion() {
        AppIdBuilder builder = new AppIdBuilder();
        Assert.assertEquals(builder, builder.region("CNR"));
    }

    @Test
    public void testCountry() {
        AppIdBuilder builder = new AppIdBuilder();
        Assert.assertEquals(builder, builder.country("CN"));
    }

    @Test
    public void testAppType() {
        AppIdBuilder builder = new AppIdBuilder();
        Assert.assertEquals(builder, builder.appType("OMS"));
    }

    @Test
    public void testAppCode() {
        AppIdBuilder builder = new AppIdBuilder();
        Assert.assertEquals(builder, builder.appCode("OMS1"));
    }

    @Test
    public void testBuild() {
        String appCode = "OMS1";
        String appType = "OMS";
        String country = "CN";
        String region = "CNR";

        AppIdBuilder builder = new AppIdBuilder();
        AppId appId = builder.region(region).country(country).appType(appType).appCode(appCode).build();
        Assert.assertEquals(region, appId.getRegion());
        Assert.assertEquals(country, appId.getCountry());
        Assert.assertEquals(appType, appId.getAppType());
        Assert.assertEquals(appCode, appId.getAppCode());
    }
}
