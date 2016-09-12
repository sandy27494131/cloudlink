package com.winit.cloudlink.storage.cassandra.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winit.cloudlink.storage.api.manager.AppTypeManager;
import com.winit.cloudlink.storage.api.vo.AppTypeVo;
import com.winit.cloudlink.storage.cassandra.entity.AppType;
import com.winit.cloudlink.storage.cassandra.repository.AppTypeRepository;

@Service("appTypeManager")
public class AppTypeManagerImpl implements AppTypeManager {

    @Resource
    private AppTypeRepository appTypeRepository;

    @Override
    public List<AppTypeVo> findAll() {
        Iterable<AppType> datas = appTypeRepository.findAll();
        List<AppTypeVo> lst = new ArrayList<AppTypeVo>();
        if (null != datas) {
            Iterator<AppType> iter = datas.iterator();
            AppTypeVo obj = null;
            AppType data = null;
            while (iter.hasNext()) {
                data = iter.next();
                obj = data.toAppTypeVo();
                lst.add(obj);
            }
        }
        return lst;
    }

    @Override
    public AppTypeVo findByCode(String code) {
        AppType data = appTypeRepository.findOne(code);
        AppTypeVo obj = null;
        if (null != data) {
            obj = data.toAppTypeVo();
        }
        return obj;
    }

    @Override
    public void saveOrUpdate(AppTypeVo obj) {
        if (null != obj) {
            AppType data = AppType.fromAppTypeVo(obj);
            appTypeRepository.save(data);
        }

    }

    @Override
    public void deleteByCode(String code) {
        appTypeRepository.delete(code);
    }

    @Override
    public boolean exists(String code) {
        return appTypeRepository.exists(code);
    }

    @Override
    public void incrementInitial(List<AppTypeVo> appTypeVos) {

        if (null != appTypeVos && appTypeVos.size() > 0) {
            List<AppType> dataList = new ArrayList<AppType>();
            AppType data = null;
            for (AppTypeVo appTypeVo : appTypeVos) {
                if (!appTypeRepository.exists(appTypeVo.getCode())) {
                    data = AppType.fromAppTypeVo(appTypeVo);
                    dataList.add(data);
                }
            }
            
            if (dataList.size() > 0) {
                appTypeRepository.save(dataList);
            }
        }

    }

}
