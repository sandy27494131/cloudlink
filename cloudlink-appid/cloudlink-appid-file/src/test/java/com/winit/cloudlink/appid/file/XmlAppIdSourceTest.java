package com.winit.cloudlink.appid.file;

import com.winit.cloudlink.appid.AppId;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by stvli on 2015/12/28.
 */
public class XmlAppIdSourceTest {
    private static XmlAppIdSource appIdSource;

    @BeforeClass
    public static void setup() {
        appIdSource = XmlAppIdSource.instance();
    }

    @Test
    public void testGetRegions() {
        List<String> regions = appIdSource.getRegions();
        Assert.assertEquals(4, regions.size());
        Assert.assertEquals("CNR", regions.get(0));
        Assert.assertEquals("USR", regions.get(1));
        Assert.assertEquals("EUR", regions.get(2));
        Assert.assertEquals("AUR", regions.get(3));
    }

    @Test
    public void testGetCountries() {
        List<String> countries = appIdSource.getCountries();
        Assert.assertEquals(5, countries.size());
        Assert.assertEquals("CN", countries.get(0));
        Assert.assertEquals("US", countries.get(1));
        Assert.assertEquals("UK", countries.get(2));
        Assert.assertEquals("DE", countries.get(3));
        Assert.assertEquals("AU", countries.get(4));
    }

    @Test
    public void testGetAppTypes() {
        List<String> appTypes = appIdSource.getAppTypes();
        Assert.assertEquals(15, appTypes.size());
    }

    @Test
    public void testGetAppIds() {
        List<AppId> appIds = appIdSource.getAppIds();
        Assert.assertTrue(appIds.size() > 0);
    }

    @Test
    public void testGetAppIdByCode() {
        Assert.assertNotNull(appIdSource.getAppIdByCode("OPC1"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OPC2"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OPC31"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OPC32"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OPC4"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OMS1"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OMS2"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OMS3"));
        Assert.assertNotNull(appIdSource.getAppIdByCode("OMS4"));
        Assert.assertNull(appIdSource.getAppIdByCode("opc1"));
        Assert.assertNull(appIdSource.getAppIdByCode("OMSDER"));
        Assert.assertNull(appIdSource.getAppIdByCode("OMSUKR"));
        Assert.assertNull(appIdSource.getAppIdByCode("OMSUKR"));
    }

    @Test
    public void testCheckRegion() {
        Assert.assertTrue(appIdSource.checkRegion("CNR"));
        Assert.assertTrue(appIdSource.checkRegion("USR"));
        Assert.assertTrue(appIdSource.checkRegion("EUR"));
        Assert.assertTrue(appIdSource.checkRegion("AUR"));
        Assert.assertFalse(appIdSource.checkRegion("cnr"));
        Assert.assertFalse(appIdSource.checkRegion("DE"));
        Assert.assertFalse(appIdSource.checkRegion("UK"));
    }

    @Test
    public void testCheckCountry() {
        Assert.assertTrue(appIdSource.checkCountry("CN"));
        Assert.assertTrue(appIdSource.checkCountry("US"));
        Assert.assertTrue(appIdSource.checkCountry("UK"));
        Assert.assertTrue(appIdSource.checkCountry("DE"));
        Assert.assertTrue(appIdSource.checkCountry("AU"));
        Assert.assertFalse(appIdSource.checkCountry("cn"));
        Assert.assertFalse(appIdSource.checkCountry("DER"));
        Assert.assertFalse(appIdSource.checkCountry("UKR"));
    }

    @Test
    public void testCheckAppType() {
        Assert.assertTrue(appIdSource.checkAppType("OMS"));
        Assert.assertTrue(appIdSource.checkAppType("TMS"));
        Assert.assertFalse(appIdSource.checkAppType("oms"));
        Assert.assertFalse(appIdSource.checkAppType("tms"));
        Assert.assertFalse(appIdSource.checkAppType("VMS"));
    }

    @Test
    public void testCheckAppCode() {
        Assert.assertTrue(appIdSource.checkAppCode("OMS1"));
        Assert.assertTrue(appIdSource.checkAppCode("CNNGB"));
        Assert.assertFalse(appIdSource.checkAppCode("OMS"));
        Assert.assertFalse(appIdSource.checkAppCode("TMS"));
    }

    @Test
    public void testCheckAppId() {
        Assert.assertTrue(appIdSource.checkAppId(AppId.parse("TOM1.TOM.ALL.CNR")));
        Assert.assertTrue(appIdSource.checkAppId(AppId.parse("CNNGB.CWM.CN.CNR")));
        Assert.assertTrue(appIdSource.checkAppId(AppId.parse("AAUYD.CWM.AU.AUR")));
        Assert.assertFalse(appIdSource.checkAppId(AppId.parse("CWM1.CWM.CN.CNR")));
        Assert.assertFalse(appIdSource.checkAppId(AppId.parse("OMSCN.OMS.ALL.CNR")));
    }
}
