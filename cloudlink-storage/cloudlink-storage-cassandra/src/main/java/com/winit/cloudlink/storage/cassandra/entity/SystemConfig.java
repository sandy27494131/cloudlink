package com.winit.cloudlink.storage.cassandra.entity;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

@Table(TableConstants.TABLE_SYSTEM_CONFIG)
public class SystemConfig {

    @PrimaryKey(value = "key")
    private String key;

    @Column(value = "value")
    private String value;

    public SystemConfig(){
    }

    public SystemConfig(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
