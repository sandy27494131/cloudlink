package com.winit.cloudlink.appid;

import java.util.List;

/**
 * AppID源
 * <p/>
 * Created by stvli on 2015/12/28.
 */
public interface AppIdSource {
    void addRegion(String region);

    void addCountry(String region);

    void addAppType(String region);

    void addAppId(AppId appId);

    void removeRegion(String region);

    void removeCountry(String region);

    void removeAppType(String region);

    void removeAppId(AppId appId);

    List<String> getRegions();

    List<String> getCountries();

    List<String> getAppTypes();

    List<AppId> getAppIds();

    AppId getAppIdByCode(String appCode);

    boolean checkRegion(String region);

    boolean checkCountry(String country);

    boolean checkAppType(String appType);

    boolean checkAppCode(String appCode);

    boolean checkAppId(AppId appId);
}
