package com.winit.cloudlink.storage.api.manager;

import com.winit.cloudlink.storage.api.vo.MailVo;

import java.util.List;

/**
 * Created by xiangyu.liang on 2016/1/25.
 */
public interface MailManager {

    public List<MailVo> findAll();
    public List<MailVo> findSendSuccess();
    public List<MailVo> findSendFail();
    public void saveOrUpdate(MailVo mailVo);
    public void delete(String mailId);
}
