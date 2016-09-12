package com.winit.cloudlink.appid;


/**
 * 应用标识类，用于唯一标识winit的一个用户，由应用编码,应用类型，国家标识，区域中心标识四部分组成
 * <p/>
 * Created by stvli on 2015/12/28.
 */
public final class AppId {

    public static final String EMPTY_COUNTRY = "ALL";
    public static final String EMPTY_DESCRIPTION = null;
    public static final String APPID_SEPARATOR = ".";

    private String appCode;
    private String appType;
    private String country;
    private String region;
    private String description;


    public AppId() {
    }

    public AppId(String appCode, String appType, String region) {
        this(appCode, appType, EMPTY_COUNTRY, region);
    }

    public AppId(String appCode, String appType, String country, String region) {
        this(appCode, appType, country, region, EMPTY_DESCRIPTION);
    }

    public AppId(String appCode, String appType, String country, String region, String description) {
        this.appCode = appCode;
        this.appType = appType;
        this.country = country;
        this.region = region;
        this.description = description;
    }

    public static AppId parse(String appIdString) {
        String[] appIdElements = appIdString.split("\\" + APPID_SEPARATOR);
        if (appIdElements.length != 4) {
            throw new IllegalArgumentException("Invalid appIdString '" + appIdString + "'.");
        }
        String region = appIdElements[3];
        String country = appIdElements[2];
        String appType = appIdElements[1];
        String appCode = appIdElements[0];
        return new AppId(appCode, appType, country, region);
    }

    public String getAppCode() {
        return appCode;
    }

    public String getAppType() {
        return appType;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getDescription() {
        return description;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getAppCode()).append(APPID_SEPARATOR);
        sb.append(getAppType()).append(APPID_SEPARATOR);
        sb.append(getCountry()).append(APPID_SEPARATOR);
        sb.append(getRegion());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppId appId = (AppId) o;

        if (getAppCode() != null ? !getAppCode().equals(appId.getAppCode()) : appId.getAppCode() != null) return false;
        if (getAppType() != null ? !getAppType().equals(appId.getAppType()) : appId.getAppType() != null) return false;
        if (getCountry() != null ? !getCountry().equals(appId.getCountry()) : appId.getCountry() != null) return false;
        return getRegion() != null ? getRegion().equals(appId.getRegion()) : appId.getRegion() == null;

    }

    @Override
    public int hashCode() {
        int result = getAppCode() != null ? getAppCode().hashCode() : 0;
        result = 31 * result + (getAppType() != null ? getAppType().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getRegion() != null ? getRegion().hashCode() : 0);
        return result;
    }
}
