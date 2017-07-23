package com.winit.cloudlink.storage.cassandra.manager;

import com.winit.cloudlink.storage.api.manager.QueueAlarmManager;
import com.winit.cloudlink.storage.api.vo.QueueAlarmVo;
import com.winit.cloudlink.storage.cassandra.entity.QueueAlarm;
import com.winit.cloudlink.storage.cassandra.repository.QueueAlarmRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jianke.zhang on 2017/6/6.
 */
@Service("queueAlarmManager")
public class QueueAlarmManagerImpl implements QueueAlarmManager {

    @Resource
    private QueueAlarmRepository queueAlarmRepository;

    @Override
    public List<QueueAlarmVo> findAll() {
        Iterable<QueueAlarm> datas = queueAlarmRepository.findAll();
        List<QueueAlarmVo> queueAlarmVoList = new ArrayList<QueueAlarmVo>();
        if (null != datas) {
            QueueAlarmVo queueAlarmVo = null;
            QueueAlarm data = null;
            Iterator<QueueAlarm> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                queueAlarmVoList.add(data.toQueueAlarmVo());
            }
        }

        return queueAlarmVoList;
    }

    @Override
    public QueueAlarmVo findByName(String name) {
        QueueAlarm data = queueAlarmRepository.findOne(name);
        QueueAlarmVo queueAlarmVo = null;
        if (null != data) {
            queueAlarmVo=data.toQueueAlarmVo();
        }
        return queueAlarmVo;
    }

    @Override
    public void saveOrUpdate(QueueAlarmVo queueAlarmVo) {
        if (null != queueAlarmVo) {
            QueueAlarm data = new QueueAlarm();
            data=QueueAlarm.fromQueueAlarmVo(queueAlarmVo);
            queueAlarmRepository.save(data);
        }
    }

    @Override
    public void deleteQueueAlarm(String name) {
        queueAlarmRepository.delete(name);
    }

    @Override
    public boolean exists(String name) {
        return queueAlarmRepository.exists(name);
    }
}
