package com.winit.cloudlink.appid;

/**
 * Created by stvli on 2015/12/29.
 */
public final class AppIdBuilder {
    private String appCode;
    private String appType;
    private String country;
    private String region;
    private String description;

    public AppIdBuilder region(final String region) {
        this.region = region;
        return this;
    }


    public AppIdBuilder country(final String country) {
        this.country = country;
        return this;
    }

    public AppIdBuilder appType(final String appType) {
        this.appType = appType;
        return this;
    }


    public AppIdBuilder appCode(final String appCode) {
        this.appCode = appCode;
        return this;
    }

    public AppId build() {
        return new AppId(this.appCode, this.appType, this.country, this.region);
    }
}
