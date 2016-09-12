package com.winit.cloudlink.storage.cassandra.entity;

import java.util.Set;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.winit.cloudlink.storage.cassandra.utils.TableConstants;

@Table(TableConstants.TABLE_USER_PERMISSION)
public class User {

    @PrimaryKey(value = "username")
    private String      username;

    @Column(value = "password")
    private String      password;

    @Column(value = "rolename")
    private Set<String> roleName;

    @Column(value = "token")
    private String      token;

    @Column(value = "token_expire")
    private Long        tokenExpire;

    public User(){
    }

    public User(String username, String password, Set<String> roleName, String token, long tokenExpire){
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

    public static enum Role {
        ADMIN, BASIC, MONITOR, ALARM, CONTROL, INITAL, SEARCH, COMMON;
    }

}
