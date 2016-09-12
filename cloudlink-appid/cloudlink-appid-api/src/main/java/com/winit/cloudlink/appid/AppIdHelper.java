package com.winit.cloudlink.appid;


/**
 * 应用标识助手类
 * <p/>
 * Created by stvli on 2015/12/28.
 */
public final class AppIdHelper {
    private AppIdSource appIdSource;

    public AppId getAppIdByCode(String appCode) {
        return appIdSource.getAppIdByCode(appCode);
    }

    public boolean checkAppId(String appCode, String appType, String country, String region) {
        return appIdSource.checkRegion(region) && appIdSource.checkCountry(country) && appIdSource.checkAppType(appType) && appIdSource.checkAppCode(appCode);
    }

    public boolean checkAppId(String appIdString) {
        String[] appIdElements = appIdString.split("\\" + AppId.APPID_SEPARATOR);
        if (appIdElements.length != 4) {
            return false;
        }
        String region = appIdElements[3];
        String country = appIdElements[2];
        String appType = appIdElements[1];
        String appCode = appIdElements[0];
        return appIdSource.checkRegion(region) && appIdSource.checkCountry(country) && appIdSource.checkAppType(appType) && appIdSource.checkAppCode(appCode);
    }

    public boolean checkAppId(AppId appId) {
        return appIdSource.checkAppId(appId);
    }

    public void setAppIdSource(AppIdSource appIdSource) {
        this.appIdSource = appIdSource;
    }
}
