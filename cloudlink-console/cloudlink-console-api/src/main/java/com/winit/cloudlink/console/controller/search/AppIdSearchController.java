package com.winit.cloudlink.console.controller.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.controller.search.model.AppidSearch;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Queue;
import com.winit.cloudlink.storage.cassandra.entity.User;

/**
 * Created by xiangyu.liang on 2016/1/20.
 */
@RestController
@AllowAccessRole({ User.Role.SEARCH })
public class AppIdSearchController {
    private static final Logger logger = LoggerFactory.getLogger(AppIdSearchController.class);

    @ResponseBody
    @RequestMapping(value = "/api/search/appid/{areaCodes}", method = RequestMethod.GET)
    public ResponseBean queryAllArea(@PathVariable String areaCodes,  HttpServletRequest request) {
        List<AppidSearch> data = new ArrayList<AppidSearch>();
        try {
            if(StringUtils.isNotBlank(areaCodes)){
                String[] arrAreas=areaCodes.split(",") ;
                Set<String> setAppidNames=new HashSet<String>();
                for(String areaCode:arrAreas){
                    RabbitMgmtService service=RabbitMgmtServiceHelper.getServiceByArea(areaCode);
                    if(service==null)continue;
                    Collection<Queue> queues=service.queues().all();
                    for(Queue q:queues){
                        String queueName=q.getName();
                        String[] tmp=queueName.split("\\.");
                        if(tmp.length!=5)continue;;
                        String strAppid=queueName.substring(queueName.indexOf(".")+1);
                        if(setAppidNames.contains(strAppid))continue;
                        setAppidNames.add(strAppid);
                        AppidSearch appidSearch=new AppidSearch();
                        appidSearch.setAppid(strAppid);
                        appidSearch.setAppType(tmp[2]);
                        appidSearch.setAreaCode(tmp[4]);
                        data.add(appidSearch);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Query appid search failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }
}
