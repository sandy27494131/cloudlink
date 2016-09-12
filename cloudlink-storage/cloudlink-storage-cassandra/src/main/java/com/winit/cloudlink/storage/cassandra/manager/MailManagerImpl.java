package com.winit.cloudlink.storage.cassandra.manager;

import com.winit.cloudlink.storage.api.manager.MailManager;
import com.winit.cloudlink.storage.api.vo.MailVo;
import com.winit.cloudlink.storage.cassandra.entity.Mail;
import com.winit.cloudlink.storage.cassandra.repository.MailRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiangyu.liang on 2016/1/25.
 */
@Service("mailManager")
public class MailManagerImpl implements MailManager {
    @Resource
    private MailRepository mailRepository;

    @Override
    public List<MailVo> findAll() {
        Iterable<Mail> datas=mailRepository.findAll();
        List<MailVo> lstMailVo=new ArrayList<MailVo>();
        if(datas!=null){
            Mail mail=null;
            MailVo mailVo=null;
            Iterator<Mail> iter=datas.iterator();
            while (iter.hasNext()){
                mail=iter.next();
                mailVo=mail.toMailVo();
                lstMailVo.add(mailVo);
            }
        }
        return lstMailVo;
    }

    @Override
    public List<MailVo> findSendSuccess() {
        Iterable<Mail> datas=mailRepository.findSendSuccess();
        List<MailVo> lstMailVo=new ArrayList<MailVo>();
        if(datas!=null){
            Mail mail=null;
            MailVo mailVo=null;
            Iterator<Mail> iter=datas.iterator();
            while (iter.hasNext()){
                mail=iter.next();
                mailVo=mail.toMailVo();
                lstMailVo.add(mailVo);
            }
        }
        return lstMailVo;
    }

    @Override
    public List<MailVo> findSendFail() {
        Iterable<Mail> datas=mailRepository.findSendFail();
        List<MailVo> lstMailVo=new ArrayList<MailVo>();
        if(datas!=null){
            Mail mail=null;
            MailVo mailVo=null;
            Iterator<Mail> iter=datas.iterator();
            while (iter.hasNext()){
                mail=iter.next();
                mailVo=mail.toMailVo();
                lstMailVo.add(mailVo);
            }
        }
        return lstMailVo;
    }

    @Override
    public void saveOrUpdate(MailVo mailVo) {
        if(mailVo!=null){
            mailRepository.save(Mail.fromMailVo(mailVo));
        }
    }

    @Override
    public void delete(String mailId) {
        mailRepository.delete(mailId);
    }
}
