package com.winit.cloudlink.storage.cassandra.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winit.cloudlink.storage.api.manager.AreaManager;
import com.winit.cloudlink.storage.api.vo.AreaVo;
import com.winit.cloudlink.storage.cassandra.entity.AlarmConfig;
import com.winit.cloudlink.storage.cassandra.entity.Area;
import com.winit.cloudlink.storage.cassandra.repository.AlarmConfigRepository;
import com.winit.cloudlink.storage.cassandra.repository.AreaRepository;

@Service("areaManager")
public class AreaManagerImpl implements AreaManager {

    @Resource
    private AreaRepository        areaRepository;

    @Resource
    private AlarmConfigRepository alarmConfigRepository;

    @Override
    public List<AreaVo> findAll() {
        Iterable<Area> datas = areaRepository.findAll();
        List<AreaVo> areaVoList = new ArrayList<AreaVo>();
        if (null != datas) {
            AreaVo areaVo = null;
            Area data = null;
            Iterator<Area> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                areaVo = new AreaVo();
                areaVo.setCode(data.getCode());
                areaVo.setMqMgmtAddr(data.getMqMgmtAddr());
                areaVo.setMqWanAddr(data.getMqWanAddr());
                areaVo.setName(data.getName());
                areaVo.setRemark(data.getRemark());
                areaVoList.add(areaVo);
            }
        }

        return areaVoList;
    }

    @Override
    public AreaVo findByCode(String code) {
        Area data = areaRepository.findOne(code);
        AreaVo areaVo = null;
        if (null != data) {
            areaVo = new AreaVo();
            areaVo.setCode(data.getCode());
            areaVo.setMqMgmtAddr(data.getMqMgmtAddr());
            areaVo.setMqWanAddr(data.getMqWanAddr());
            areaVo.setName(data.getName());
            areaVo.setRemark(data.getRemark());

        }
        return areaVo;
    }

    @Override
    public void saveOrUpdate(AreaVo areaVo) {
        if (null != areaVo) {
            Area entity = new Area();
            entity.setCode(areaVo.getCode());
            entity.setMqMgmtAddr(areaVo.getMqMgmtAddr());
            entity.setMqWanAddr(areaVo.getMqWanAddr());
            entity.setName(areaVo.getName());
            entity.setRemark(areaVo.getRemark());
            areaRepository.save(entity);

            boolean exists = areaRepository.exists(areaVo.getCode());
            if (exists) {
                Iterable<AlarmConfig> configs = alarmConfigRepository.findAll();
                Iterator<AlarmConfig> iter = configs.iterator();
                AlarmConfig config = null;
                Set<String> areas = null;
                while (iter.hasNext()) {
                    config = iter.next();
                    areas = new HashSet<String>();
                    if(config.getArea()==null){
                        config.setArea(new HashSet<String>());
                    }
                    areas.addAll(config.getArea());
                    areas.add(areaVo.getCode());
                    config.setArea(areas);
                }
                alarmConfigRepository.save(configs);
            }
        }
    }

    @Override
    public void deleteByCode(String code) {
        boolean exists = areaRepository.exists(code);
        if (exists) {
            Iterable<AlarmConfig> configs = alarmConfigRepository.findAll();
            Iterator<AlarmConfig> iter = configs.iterator();
            AlarmConfig config = null;
            Set<String> areas = null;
            while (iter.hasNext()) {
                config = iter.next();
                areas = new HashSet<String>();
                if(config.getArea()==null){
                    config.setArea(new HashSet<String>());
                }
                areas.addAll(config.getArea());
                areas.remove(code);
                config.setArea(areas);
            }
            alarmConfigRepository.save(configs);
        }

        areaRepository.delete(code);
    }

    @Override
    public boolean exists(String code) {
        return areaRepository.exists(code);
    }

    @Override
    public List<AreaVo> findAllOnlyCodeAndName() {
        Iterable<Area> datas = areaRepository.findAll();
        List<AreaVo> areaVoList = new ArrayList<AreaVo>();
        if (null != datas) {
            AreaVo areaVo = null;
            Area data = null;
            Iterator<Area> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                areaVo = new AreaVo();
                areaVo.setCode(data.getCode());
                areaVo.setName(data.getName());
                areaVoList.add(areaVo);
            }
        }

        return areaVoList;
    }

    @Override
    public void incrementInitial(List<AreaVo> areaVos) {
        if (null != areaVos && areaVos.size() > 0) {
            List<Area> areas = new ArrayList<Area>();
            Area entity = null;
            for (AreaVo areaVo : areaVos) {
                if (!areaRepository.exists(areaVo.getCode())) {
                    entity = new Area();
                    entity.setCode(areaVo.getCode());
                    entity.setMqMgmtAddr(areaVo.getMqMgmtAddr());
                    entity.setMqWanAddr(areaVo.getMqWanAddr());
                    entity.setName(areaVo.getName());
                    entity.setRemark(areaVo.getRemark());
                    areas.add(entity);
                }
            }
            
            if (areas.size() > 0) {
                areaRepository.save(areas);
            }
            
            // 更新告警配置
            for (AreaVo areaVo : areaVos) {
                Iterable<AlarmConfig> configs = alarmConfigRepository.findAll();
                Iterator<AlarmConfig> iter = configs.iterator();
                AlarmConfig config = null;
                Set<String> areaCodes = null;
                while (iter.hasNext()) {
                    config = iter.next();
                    areaCodes = new HashSet<String>();
                    if(config.getArea()==null){
                        config.setArea(new HashSet<String>());
                    }
                    areaCodes.addAll(config.getArea());
                    areaCodes.add(areaVo.getCode());
                    config.setArea(areaCodes);
                }
                alarmConfigRepository.save(configs);
            }
            
        }
        
    }

}
