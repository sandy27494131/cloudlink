package com.winit.cloudlink.storage.api.vo;

import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class UserVo {

    @NotBlank
    @Length(min = 1, max = 16)
    private String      username;

    @NotBlank
    @Length(min = 1, max = 16)
    private String      password;

    private Set<String> roleName;

    private String      token;

    private Long        tokenExpire;

    public UserVo(){
    }

    public UserVo(String username, String password, Set<String> roleName, String token, Long tokenExpire){
        this.username = username;
        this.password = password;
        this.roleName = roleName;
        this.token = token;
        this.tokenExpire = tokenExpire;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(Set<String> roleName) {
        this.roleName = roleName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Long tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

}
