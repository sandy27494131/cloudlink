package com.winit.cloudlink.storage.api.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class AppTypeVo {

    @NotBlank
    @Length(min = 1, max = 10)
    private String code;

    @Length(min = 1, max = 32)
    private String name;

    public AppTypeVo(){

    }

    public AppTypeVo(String code, String name){
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
}
