package com.winit.cloudlink.storage.cassandra.manager;

import com.winit.cloudlink.storage.api.constants.AlarmSettingsKey;
import com.winit.cloudlink.storage.api.constants.AlarmType;
import com.winit.cloudlink.storage.api.manager.AlarmConfigManager;
import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import com.winit.cloudlink.storage.cassandra.SpringBase;
import com.winit.cloudlink.storage.cassandra.entity.AlarmConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by xiangyu.liang on 2016/1/5.
 */
public class AlarmConfigManagerTest  extends SpringBase {
    @Autowired
    private AlarmConfigManager alarmConfigManager;
    @Test
    public void testSaveOrUpdate()
    {
        /*AlarmConfigVo alarmConfigVo=new AlarmConfigVo();
        alarmConfigVo.setId(AlarmType.NODE_WORKER_STATUS.name());
        alarmConfigVo.setAlarmCron("0 0/1 * * * ?");
        alarmConfigVo.setAlarmEmail("jianke.zhang@winit.com.cn");
        Map<String,String> map=new HashMap<String,String>();
        map.put("age","18");
        map.put("name","-。-");
        alarmConfigVo.setAlarmSettings(map);
        alarmConfigVo.setAlarmType(AlarmType.NODE_WORKER_STATUS.name());
        alarmConfigVo.setAlarmWay(new String[]{"EMAIL"});
        alarmConfigVo.setArea(new String[]{"USR","CNR"});
        alarmConfigVo.setEnabled(true);
        alarmConfigManager.saveOrUpdate(alarmConfigVo);*/

       /* AlarmConfigVo alarmConfigVo=new AlarmConfigVo();
        alarmConfigVo.setId(AlarmType.CONNECTIONS.name());
        alarmConfigVo.setAlarmCron("0 0/1 * * * ?");
        alarmConfigVo.setAlarmEmail("jianke.zhang@winit.com.cn");
        Map<String,String> map=new HashMap<String,String>();
        map.put(AlarmSettingsKey.CONNECTIONS_THRESHOLD.name(),"18");
        map.put(AlarmSettingsKey.CHANNELS_THRESHOLD.name(),"12");
        alarmConfigVo.setAlarmSettings(map);
        alarmConfigVo.setAlarmType(AlarmType.CONNECTIONS.name());
        alarmConfigVo.setAlarmWay(new String[]{"EMAIL"});
        alarmConfigVo.setArea(new String[]{"USR","CNR"});
        alarmConfigVo.setEnabled(true);
        alarmConfigManager.saveOrUpdate(alarmConfigVo);*/

        /*AlarmConfigVo alarmConfigVo=new AlarmConfigVo();
        alarmConfigVo.setId(AlarmType.EXCHANGE_STATUS.name());
        alarmConfigVo.setAlarmCron("0 0/1 * * * ?");
        alarmConfigVo.setAlarmEmail("jianke.zhang@winit.com.cn");
        Map<String,String> map=new HashMap<String,String>();
        map.put("age","18");
        map.put("name","-。-");
        alarmConfigVo.setAlarmSettings(map);
        alarmConfigVo.setAlarmType(AlarmType.EXCHANGE_STATUS.name());
        alarmConfigVo.setAlarmWay(new String[]{"EMAIL"});
        alarmConfigVo.setArea(new String[]{"USR","CNR"});
        alarmConfigVo.setEnabled(true);
        alarmConfigManager.saveOrUpdate(alarmConfigVo);*/

       /* AlarmConfigVo alarmConfigVo=new AlarmConfigVo();
        alarmConfigVo.setId(AlarmType.QUEUE_STATUS.name());
        alarmConfigVo.setAlarmCron("0 0/1 * * * ?");
        alarmConfigVo.setAlarmEmail("jianke.zhang@winit.com.cn");
        Map<String,String> map=new HashMap<String,String>();
        map.put(AlarmSettingsKey.QUEUE_MESSAGE_AMOUNT_THRESHOLD.name(),"222");
        map.put(AlarmSettingsKey.QUEUE_MESSAGE_IS_CONSUMED.name(),"true");
        alarmConfigVo.setAlarmSettings(map);
        alarmConfigVo.setAlarmType(AlarmType.QUEUE_STATUS.name());
        alarmConfigVo.setAlarmWay(new String[]{"EMAIL"});
        alarmConfigVo.setArea(new String[]{"USR","CNR"});
        alarmConfigVo.setEnabled(true);
        alarmConfigManager.saveOrUpdate(alarmConfigVo);*/

        /*AlarmConfigVo alarmConfigVo=new AlarmConfigVo();
        alarmConfigVo.setId(AlarmType.SHOVEL_STATUS.name());
        alarmConfigVo.setAlarmCron("0 0/1 * * * ?");
        alarmConfigVo.setAlarmEmail("jianke.zhang@winit.com.cn");
        Map<String,String> map=new HashMap<String,String>();
        map.put("age","18");
        map.put("name","-。-");
        alarmConfigVo.setAlarmSettings(map);
        alarmConfigVo.setAlarmType(AlarmType.SHOVEL_STATUS.name());
        alarmConfigVo.setAlarmWay(new String[]{"EMAIL"});
        alarmConfigVo.setArea(new String[]{"USR","CNR"});
        alarmConfigVo.setEnabled(true);
        alarmConfigManager.saveOrUpdate(alarmConfigVo);*/

        List<AlarmConfigVo> list= alarmConfigManager.findAll();
        System.out.println("list.size()+\"----------------------------------\" = " + list.size()+"----------------------------------");
    }
}
