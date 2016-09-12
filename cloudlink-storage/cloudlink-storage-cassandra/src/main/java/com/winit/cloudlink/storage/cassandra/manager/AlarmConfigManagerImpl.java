package com.winit.cloudlink.storage.cassandra.manager;

import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import com.winit.cloudlink.storage.cassandra.entity.AlarmConfig;
import com.winit.cloudlink.storage.cassandra.repository.AlarmConfigRepository;
import org.springframework.stereotype.Service;

import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("alarmConfigManager")
public class AlarmConfigManagerImpl implements AlarmConfigManager {

    @Resource
    private AlarmConfigRepository alarmConfigRepository;

    @Override
    public AlarmConfigVo findByAlarmType(String alarmType) {
       /* List<AlarmConfig> datas = alarmConfigRepository.findByAlarmType(alarmType);
        AlarmConfigVo alarmConfigVo = null;
        if (datas != null && datas.size() > 0) {
            alarmConfigVo = datas.get(0).toAlarmConfigVo();
        }*/
        AlarmConfigVo alarmConfigVo = alarmConfigRepository.findOne(alarmType).toAlarmConfigVo();
        return alarmConfigVo;
    }

    @Override
    public void saveOrUpdate(AlarmConfigVo alarmConfigVo) {
        alarmConfigRepository.save(AlarmConfig.fromAlarmConfigVo(alarmConfigVo));
    }

    @Override
    public List<AlarmConfigVo> findAll() {
        List<AlarmConfig> datas = (List<AlarmConfig>) alarmConfigRepository.findAll();
        List<AlarmConfigVo> list = new ArrayList<AlarmConfigVo>();
        if (datas != null) {
            for (AlarmConfig a : datas) {
                list.add(a.toAlarmConfigVo());
            }
        }
        return list;
    }
}
