package com.winit.cloudlink.storage.cassandra.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winit.cloudlink.storage.api.manager.AppIdManager;
import com.winit.cloudlink.storage.api.vo.AppIdVo;
import com.winit.cloudlink.storage.cassandra.entity.AppId;
import com.winit.cloudlink.storage.cassandra.repository.AppIdRepository;

@Service("appIdManager")
public class AppIdManagerImpl implements AppIdManager {


    @Resource
    private AppIdRepository appIdRepository;

    @Override
    public List<AppIdVo> findAll() {
        Iterable<AppId> datas = appIdRepository.findAll();
        List<AppIdVo> appIdVoList = new ArrayList<AppIdVo>();
        if (null != datas) {
            AppIdVo appIdVo = null;
            AppId data = null;
            Iterator<AppId> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                /*
                 * appIdVo = new AppIdVo(); appIdVo.setAppId(data.getAppId());
                 * appIdVo.setAppType(data.getAppType());
                 * appIdVo.setArea(data.getArea());
                 * appIdVo.setCountry(data.getCountry());
                 * appIdVo.setRemark(data.getRemark());
                 * appIdVo.setUniqueId(data.getUniqueId());
                 */
                appIdVo = data.toAppIdVo();
                appIdVoList.add(appIdVo);
            }
        }

        return appIdVoList;
    }

    @Override
    public AppIdVo findByAppId(String appId) {
        AppId data = appIdRepository.findOne(appId);
        AppIdVo appIdVo = null;
        if (null != data) {
            /*
             * appIdVo = new AppIdVo(); appIdVo.setAppId(data.getAppId());
             * appIdVo.setAppType(data.getAppType());
             * appIdVo.setArea(data.getArea());
             * appIdVo.setCountry(data.getCountry());
             * appIdVo.setRemark(data.getRemark());
             * appIdVo.setUniqueId(data.getUniqueId());
             */
            appIdVo = data.toAppIdVo();
        }
        return appIdVo;
    }

    @Override
    public void saveOrUpdate(AppIdVo appIdVo) {
        if (null != appIdVo) {
            /*
             * AppId data = new AppId(); data.setAppId(appIdVo.getAppId());
             * data.setAppType(appIdVo.getAppType());
             * data.setArea(appIdVo.getArea());
             * data.setCountry(appIdVo.getCountry());
             * data.setRemark(appIdVo.getRemark());
             * data.setUniqueId(appIdVo.getUniqueId());
             */
            AppId data = AppId.fromAppIdVo(appIdVo);
            appIdRepository.save(data);
        }
    }

    @Override
    public void deleteAppId(String appId) {
        appIdRepository.delete(appId);
    }

    @Override
    public Long findCountByArea(String area) {
        return appIdRepository.findCountByArea(area);
    }

    @Override
    public List<AppIdVo> findByArea(String area) {
        List<AppId> datas = appIdRepository.findByArea(area);
        List<AppIdVo> appIdVoList = new ArrayList<AppIdVo>();
        if (null != datas) {
            AppIdVo appIdVo = null;
            AppId data = null;
            Iterator<AppId> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                /*
                 * appIdVo = new AppIdVo(); appIdVo.setAppId(data.getAppId());
                 * appIdVo.setAppType(data.getAppType());
                 * appIdVo.setArea(data.getArea());
                 * appIdVo.setCountry(data.getCountry());
                 * appIdVo.setRemark(data.getRemark());
                 * appIdVo.setUniqueId(data.getUniqueId());
                 */
                appIdVo = data.toAppIdVo();
                appIdVoList.add(appIdVo);
            }
        }

        return appIdVoList;
    }

    @Override
    public List<AppIdVo> findByAppType(String appType) {
        List<AppId> datas = appIdRepository.findByAppType(appType);
        List<AppIdVo> appIdVoList = new ArrayList<AppIdVo>();
        if (null != datas) {
            AppIdVo appIdVo = null;
            AppId data = null;
            Iterator<AppId> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                /*
                 * appIdVo = new AppIdVo(); appIdVo.setAppId(data.getAppId());
                 * appIdVo.setAppType(data.getAppType());
                 * appIdVo.setArea(data.getArea());
                 * appIdVo.setCountry(data.getCountry());
                 * appIdVo.setRemark(data.getRemark());
                 * appIdVo.setUniqueId(data.getUniqueId());
                 */
                appIdVo = data.toAppIdVo();
                appIdVoList.add(appIdVo);
            }
        }

        return appIdVoList;
    }

    @Override
    public List<AppIdVo> findByAreaAndAppType(String area, String appType) {
        List<AppId> datas = appIdRepository.findByAreaAndAppType(area, appType);
        List<AppIdVo> appIdVoList = new ArrayList<AppIdVo>();
        if (null != datas) {
            AppIdVo appIdVo = null;
            AppId data = null;
            Iterator<AppId> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                /*
                 * appIdVo = new AppIdVo(); appIdVo.setAppId(data.getAppId());
                 * appIdVo.setAppType(data.getAppType());
                 * appIdVo.setArea(data.getArea());
                 * appIdVo.setCountry(data.getCountry());
                 * appIdVo.setRemark(data.getRemark());
                 * appIdVo.setUniqueId(data.getUniqueId());
                 */
                appIdVo = data.toAppIdVo();
                appIdVoList.add(appIdVo);
            }
        }

        return appIdVoList;
    }

    @Override
    public List<AppIdVo> findByUniqueId(String uniqueId) {
        List<AppId> datas = appIdRepository.findByUniqueId(uniqueId);
        List<AppIdVo> appIdVoList = new ArrayList<AppIdVo>();
        if (null != datas) {
            AppIdVo appIdVo = null;
            AppId data = null;
            Iterator<AppId> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                appIdVo = data.toAppIdVo();
                appIdVoList.add(appIdVo);
            }
        }
        return appIdVoList;
    }
    @Override
    public List<AppIdVo> findEnable() {
        List<AppId> datas = appIdRepository.findEnable();
        List<AppIdVo> appIdVoList = new ArrayList<AppIdVo>();
        if (null != datas) {
            AppIdVo appIdVo = null;
            AppId data = null;
            Iterator<AppId> iter = datas.iterator();
            while (iter.hasNext()) {
                data = iter.next();
                appIdVo = data.toAppIdVo();
                appIdVoList.add(appIdVo);
            }
        }
        return appIdVoList;
    }

    @Override
    public Long findCountByUniqueId(String uniqueId) {
        return appIdRepository.findCountByUniqueId(uniqueId);
    }

    @Override
    public Long findCountByAppType(String appType) {
        return appIdRepository.findCountByAppType(appType);
    }

    @Override
    public boolean exists(String appId) {
        return appIdRepository.exists(appId);
    }

    @Override
    public void incrementInitial(List<AppIdVo> appIdVos) {
        if (null != appIdVos && appIdVos.size() > 0) {
            List<AppId> dataList = new ArrayList<AppId>();
            AppId data = null;
            for (AppIdVo appIdVo : appIdVos) {
                if (!appIdRepository.exists(appIdVo.getAppId())) {
                    data = AppId.fromAppIdVo(appIdVo);
                    dataList.add(data);
                }
            }
            
            if (dataList.size() > 0) {
                appIdRepository.save(dataList);
            }
        }
    }
}
