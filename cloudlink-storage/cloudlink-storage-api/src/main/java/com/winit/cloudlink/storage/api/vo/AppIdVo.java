package com.winit.cloudlink.storage.api.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class AppIdVo {

    @NotBlank
    @Length(min = 1, max = 32)
    private String appId;

    @NotBlank
    @Length(min = 1, max = 32)
    private String area;

    @NotBlank
    @Length(min = 1, max = 10)
    private String country;

    @NotBlank
    @Length(min = 1, max = 10)
    private String appType;

    @NotBlank
    @Length(min = 1, max = 20)
    private String uniqueId;


    private Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Length(min = 0, max = 128)
    private String remark;


    public AppIdVo(){
        super();
    }

    public AppIdVo(String appId, String area, String country, String appType, String uniqueId, Boolean enable, String remark) {
        this.appId = appId;
        this.area = area;
        this.country = country;
        this.appType = appType;
        this.uniqueId = uniqueId;
        this.enable = enable;
        this.remark = remark;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
