package com.winit.cloudlink.storage.cassandra.entity;

import com.winit.cloudlink.storage.api.vo.AppIdVo;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

/**
 * 應用唯一ID
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月17日 	Created
 *
 * </pre>
 * @since 1.
 */
@Table(TableConstants.TABLE_APP_ID)
public class AppId {

    @PrimaryKey(value = "app_id")
    private String appId;

    @Column(value = "area")
    private String area;

    @Column(value = "country")
    private String country;

    @Column(value = "app_type")
    private String appType;

    @Column(value = "unique_id")
    private String uniqueId;

    @Column(value="enable")
    private Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Column(value = "remark")
    private String remark;

    public AppId(){
    }

    public AppId(String appId, String area, String country, String appType, String uniqueId, Boolean enable, String remark) {
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

    public AppIdVo toAppIdVo()
    {
        AppIdVo appIdVo = new AppIdVo();
        appIdVo.setAppId(this.getAppId());
        appIdVo.setAppType(this.getAppType());
        appIdVo.setArea(this.getArea());
        appIdVo.setCountry(this.getCountry());
        appIdVo.setRemark(this.getRemark());
        appIdVo.setUniqueId(this.getUniqueId());
        appIdVo.setEnable(this.getEnable());
        return appIdVo;
    }
    public static AppId fromAppIdVo(AppIdVo appIdVo)
    {
        AppId appId=new AppId();
        appId.setAppId(appIdVo.getAppId());
        appId.setAppType(appIdVo.getAppType());
        appId.setArea(appIdVo.getArea());
        appId.setCountry(appIdVo.getCountry());
        appId.setRemark(appIdVo.getRemark());
        appId.setUniqueId(appIdVo.getUniqueId());
        appId.setEnable(appIdVo.getEnable());
        return appId;
    }
}
