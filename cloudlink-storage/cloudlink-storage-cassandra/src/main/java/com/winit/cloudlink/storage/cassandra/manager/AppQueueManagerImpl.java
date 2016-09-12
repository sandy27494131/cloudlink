package com.winit.cloudlink.storage.cassandra.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winit.cloudlink.storage.api.manager.AppQueueManager;
import com.winit.cloudlink.storage.api.vo.AppQueueVo;
import com.winit.cloudlink.storage.cassandra.entity.AppQueue;
import com.winit.cloudlink.storage.cassandra.repository.AppQueueRepository;

@Service("appQueueManager")
public class AppQueueManagerImpl implements AppQueueManager {

    @Resource
    private AppQueueRepository appQueueRepository;

    @Override
    public List<AppQueueVo> findAll() {
        Iterable<AppQueue> datas = appQueueRepository.findAll();
        List<AppQueueVo> appQueueVoList = new ArrayList<AppQueueVo>();
        if (null != datas) {
            AppQueueVo appQueueVo = null;
            AppQueue data = null;
            Iterator<AppQueue> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                /*appQueueVo = new AppQueueVo();
                appQueueVo.setName(data.getName());
                appQueueVo.setSender(data.getSender());
                appQueueVo.setReceiver(data.getReceiver());
                appQueueVo.setMessageType(data.getMessageType());
                appQueueVo.setRemark(data.getRemark());*/
                appQueueVoList.add(data.toAppQueueVo());
            }
        }

        return appQueueVoList;
    }

    @Override
    public AppQueueVo findByName(String name) {
        AppQueue data = appQueueRepository.findOne(name);
        AppQueueVo appQueueVo = null;
        if (null != data) {
           /* appQueueVo = new AppQueueVo();
            appQueueVo.setName(data.getName());
            appQueueVo.setSender(data.getSender());
            appQueueVo.setReceiver(data.getReceiver());
            appQueueVo.setMessageType(data.getMessageType());
            appQueueVo.setRemark(data.getRemark());*/
            appQueueVo=data.toAppQueueVo();
        }
        return appQueueVo;
    }

    @Override
    public void saveOrUpdate(AppQueueVo appQueueVo) {
        if (null != appQueueVo) {
            AppQueue data = new AppQueue();
            /*data.setName(appQueueVo.getName());
            data.setSender(appQueueVo.getSender());
            data.setReceiver(appQueueVo.getReceiver());
            data.setMessageType(appQueueVo.getMessageType());
            data.setRemark(appQueueVo.getRemark());*/
            data=AppQueue.fromAppQueueVo(appQueueVo);
            appQueueRepository.save(data);
        }

    }

    @Override
    public void deleteAppQueue(String name) {
        appQueueRepository.delete(name);
    }

    @Override
    public List<AppQueueVo> findBySender(String sender) {
        List<AppQueue> datas = appQueueRepository.findBySender(sender);
        List<AppQueueVo> appQueueVoList = new ArrayList<AppQueueVo>();
        if (null != datas) {
            AppQueueVo appQueueVo = null;
            AppQueue data = null;
            Iterator<AppQueue> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                appQueueVo = new AppQueueVo();
               /* appQueueVo.setName(data.getName());
                appQueueVo.setSender(data.getSender());
                appQueueVo.setReceiver(data.getReceiver());
                appQueueVo.setMessageType(data.getMessageType());
                appQueueVo.setRemark(data.getRemark());
                appQueueVoList.add(appQueueVo);*/
                appQueueVo=data.toAppQueueVo();
            }
        }

        return appQueueVoList;
    }

    @Override
    public Long findCountBySender(String sender) {
        return appQueueRepository.findCountBySender(sender);
    }

    @Override
    public List<AppQueueVo> findByReceiver(String receiver) {
        List<AppQueue> datas = appQueueRepository.findByReceiver(receiver);
        List<AppQueueVo> appQueueVoList = new ArrayList<AppQueueVo>();
        if (null != datas) {
            AppQueueVo appQueueVo = null;
            AppQueue data = null;
            Iterator<AppQueue> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                /*appQueueVo = new AppQueueVo();
                appQueueVo.setName(data.getName());
                appQueueVo.setSender(data.getSender());
                appQueueVo.setReceiver(data.getReceiver());
                appQueueVo.setMessageType(data.getMessageType());
                appQueueVo.setRemark(data.getRemark());*/
                appQueueVoList.add(data.toAppQueueVo());
            }
        }

        return appQueueVoList;
    }

    @Override
    public Long findCountByReceiver(String receiver) {
        return appQueueRepository.findCountByReceiver(receiver);
    }

    @Override
    public List<AppQueueVo> findBySenderAndReceiver(String sender, String receiver) {
        List<AppQueue> datas = appQueueRepository.findBySenderAndReceiver(sender, receiver);
        List<AppQueueVo> appQueueVoList = new ArrayList<AppQueueVo>();
        if (null != datas) {
            AppQueueVo appQueueVo = null;
            AppQueue data = null;
            Iterator<AppQueue> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                /*appQueueVo = new AppQueueVo();
                appQueueVo.setName(data.getName());
                appQueueVo.setSender(data.getSender());
                appQueueVo.setReceiver(data.getReceiver());
                appQueueVo.setMessageType(data.getMessageType());
                appQueueVo.setRemark(data.getRemark());*/
                appQueueVoList.add(data.toAppQueueVo());
            }
        }

        return appQueueVoList;
    }

    @Override
    public boolean exists(String name) {
        return appQueueRepository.exists(name);
    }

}
