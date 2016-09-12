package com.winit.cloudlink.storage.cassandra.entity;

import com.winit.cloudlink.storage.api.vo.AppTypeVo;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

/**
 * 应用类型
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年12月17日 	Created
 *
 * </pre>
 * @since 1.
 */
@Table(TableConstants.TABLE_APP_TYPE)
public class AppType {

    @PrimaryKey(value = "code")
    private String code;

    @Column(value = "name")
    private String name;

    public AppType(String code, String name){
        super();
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppTypeVo toAppTypeVo()
    {
        AppTypeVo obj=new AppTypeVo();
        obj.setCode(this.getCode());
        obj.setName(this.getName());
        return obj;
    }
    public static AppType fromAppTypeVo(AppTypeVo appTypeVo)
    {
        return new AppType(appTypeVo.getCode(),appTypeVo.getName());
    }
}
