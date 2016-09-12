package com.winit.cloudlink.common;

import com.winit.cloudlink.common.utils.StringUtils;

public class AppID {

    public static final String SEPARATOR       = ".";

    public static final String REGEX_SEPARATOR = "\\.";

    /**
     * 区域标识
     */
    private String             area;

    /**
     * 国家标识
     */
    private String             country;

    /**
     * 应用类型标识
     */
    private String             appType;

    /**
     * 应用唯一ID
     */
    private String             appUniqueId;

    public AppID(String appId){
        if (StringUtils.isBlank(appId)) {
            throw new IllegalStateException("the 'appId' parameter of cloudlink must not be blank.");
        }

        String[] seq = appId.split(REGEX_SEPARATOR);
        if (null == seq || seq.length != 4) {
            throw new IllegalStateException("the 'appId[" + appId
                                            + "]' parameter of cloudlink Format error, example:[OMS001.OMS.ALL.CNR]");
        }

        this.appUniqueId = seq[0];
        this.appType = seq[1];
        this.country = seq[2];
        this.area = seq[3];
    }

    public AppID(String area, String country, String appType, String appUniqueId){
        super();
        this.area = area;
        this.country = country;
        this.appType = appType;
        this.appUniqueId = appUniqueId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppUniqueId() {
        return appUniqueId;
    }

    public void setAppUniqueId(String appUniqueId) {
        this.appUniqueId = appUniqueId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.appUniqueId)
            .append(SEPARATOR)
            .append(this.appType)
            .append(SEPARATOR)
            .append(this.country)
            .append(SEPARATOR)
            .append(this.area)

        ;
        return sb.toString();
    }

    public static void main(String[] args) {
        AppID appid = new AppID("OMS001.OMS.CN.CNR");
        System.out.println(appid.getArea());
        System.out.println(appid.getAppType());
        System.out.println(appid.getAppUniqueId());
        System.out.println(appid);
    }
}
