package com.winit.cloudlink.console.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.storage.api.vo.AreaVo;

public class RabbitMgmtServiceHelper {

    private static final Logger                  logger          = LoggerFactory.getLogger(RabbitMgmtServiceHelper.class);

    public static Map<String, AreaVo>            area4AreaVoMap  = new ConcurrentHashMap<String, AreaVo>();
    public static Map<String, RabbitMgmtService> area4ServiceMap = new ConcurrentHashMap<String, RabbitMgmtService>();

    public static void init(List<AreaVo> lstArea) {
        for (AreaVo area : lstArea) {
            if (StringUtils.isNotBlank(area.getMqMgmtAddr())) {
                area4AreaVoMap.put(area.getCode(), area);
                area4ServiceMap.put(area.getCode(), getServiceByAddr(area.getMqMgmtAddr()));
            } else {
                logger.warn("===================>create RabbitMgmtService error, mqMgmtAddr is null; area is : " + area);
            }
        }
    }

    public static void saveOrUpdateArea(AreaVo areaVo) {
        if (areaVo != null && StringUtils.isNotBlank(areaVo.getMqMgmtAddr())) {
            area4AreaVoMap.put(areaVo.getCode(), areaVo);
            RabbitMgmtService service = area4ServiceMap.get(areaVo.getCode());
            if (null != service) {
                service.destroy();
            }
            // 创建
            area4ServiceMap.put(areaVo.getCode(), getServiceByAddr(areaVo.getMqMgmtAddr()));
        }
    }

    public static void deleteArea(String areaCode) {
        if (StringUtils.isNotBlank(areaCode)) {
            /*
             * area4addr.remove(areaVo.getCode() );
             * area4ConnectionMap.remove(areaVo.getCode());
             */
            area4AreaVoMap.remove(areaCode);
            RabbitMgmtService service = area4ServiceMap.get(areaCode);
            if (null != service) {
                // 销毁
                service.destroy();

            }
            area4ServiceMap.remove(areaCode);
        }
    }

    private static RabbitMgmtService getServiceByAddr(String addr) {
        URL url = URL.valueOf(addr);
        return RabbitMgmtService.builder()
            .host(url.getHost())
            .port(url.getPort())
            .credentials(url.getUsername(), url.getPassword())
            .build();
    }

    public static RabbitMgmtService getServiceByArea(String areaCode) {

        return area4ServiceMap.get(areaCode);
    }

    public static AreaVo getAreaVoByAreaCode(String areaCode) {
        return area4AreaVoMap.get(areaCode);
    }

    public static List<AreaVo> getAllAreas() {
        return new ArrayList<AreaVo>(area4AreaVoMap.values());
    }

    public static boolean isValidAddr(String addr) {
        boolean isEnable = true;
        try {
            URL url = URL.valueOf(addr);
            RabbitMgmtService service = RabbitMgmtService.builder()
                .host(url.getHost())
                .port(url.getPort())
                .credentials(url.getUsername(), url.getPassword())
                .build();
            service.users().all();
            service.destroy();
        } catch (Exception e) {
            isEnable = false;
        }
        return isEnable;
    }

    public RabbitMgmtServiceHelper(){
        // TODO Auto-generated constructor stub
    }

}
