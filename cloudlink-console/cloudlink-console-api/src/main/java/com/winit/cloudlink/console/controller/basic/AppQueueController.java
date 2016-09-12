package com.winit.cloudlink.console.controller.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.storage.api.manager.AppQueueManager;
import com.winit.cloudlink.storage.api.manager.AppTypeManager;
import com.winit.cloudlink.storage.api.manager.AreaManager;
import com.winit.cloudlink.storage.api.vo.AppQueueVo;
import com.winit.cloudlink.storage.api.vo.AppTypeVo;
import com.winit.cloudlink.storage.cassandra.entity.User.Role;

@RestController
@AllowAccessRole({ Role.BASIC })
public class AppQueueController {

    private static final Logger logger = LoggerFactory.getLogger(AppQueueController.class);

    @Resource
    private AppQueueManager     appQueueManager;

    @Resource
    private AreaManager         areaManager;

    @Resource
    private AppTypeManager      appTypeManager;

    @AllowAccessRole({ Role.BASIC,Role.ALARM })
    @ResponseBody
    @RequestMapping(value = "/api/queues", method = RequestMethod.GET)
    public ResponseBean queryAllQueue(AppQueueVo queryVo, HttpServletRequest request) {
        List<AppQueueVo> datas = null;
        try {
            if (StringUtils.isBlank(queryVo.getSender()) && StringUtils.isBlank(queryVo.getReceiver())) {
                datas = appQueueManager.findAll();
            } else if (StringUtils.isNotBlank(queryVo.getSender()) && StringUtils.isNotBlank(queryVo.getReceiver())) {
                datas = appQueueManager.findBySenderAndReceiver(queryVo.getSender(), queryVo.getReceiver());
            } else if (StringUtils.isNotBlank(queryVo.getSender())) {
                datas = appQueueManager.findBySender(queryVo.getSender());
            } else {
                datas = appQueueManager.findByReceiver(queryVo.getReceiver());
            }
        } catch (Exception e) {
            logger.error("Query app queue list failed", e);
            datas = new ArrayList<AppQueueVo>();
        }
        return ResponseBean.buildSuccess(request, datas);
    }

    @AllowAccessRole({ Role.BASIC,Role.ALARM })
    @ResponseBody
    @RequestMapping(value = "/api/queue/{name}", method = RequestMethod.GET)
    public ResponseBean queryQueueByName(@PathVariable String name, HttpServletRequest request,
                                         HttpServletResponse response) {
        AppQueueVo appQueueVo = null;
        List<AppTypeVo> appTypes = null;
        try {
            if (StringUtils.isNoneBlank(name)) {
                appQueueVo = appQueueManager.findByName(name);
            } else {
                appQueueVo = new AppQueueVo();
            }

            appTypes = appTypeManager.findAll();
        } catch (Exception e) {
            logger.error("Query app queue by code failed", e);
        }

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("queue", appQueueVo);
        dataMap.put("appTypes", appTypes);
        return ResponseBean.buildSuccess(request, dataMap);
    }

    @ResponseBody
    @RequestMapping(value = "/api/queue/{name}", method = RequestMethod.POST)
    public ResponseBean saveQueue(@PathVariable String name, @RequestBody @Valid AppQueueVo appQueueVo,
                                  BindingResult result, HttpServletRequest request) {
        try {

            if (result.hasErrors()) {
                return ResponseBean.buildParamError(request, result);
            }

            if (appQueueManager.exists(appQueueVo.getName())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_EXISTS, null);
            }

            if (!appTypeManager.exists(appQueueVo.getSender())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_SENDER_NOT_EXISTS, null);
            }

            if (!appTypeManager.exists(appQueueVo.getReceiver())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_RECEIVER_NOT_EXISTS, null);
            }

            appQueueManager.saveOrUpdate(appQueueVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("save app queue failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @AllowAccessRole({ Role.BASIC,Role.ALARM })
    @ResponseBody
    @RequestMapping(value = "/api/queue/{name}", method = RequestMethod.PUT)
    public ResponseBean updateQueue(@PathVariable String name, @RequestBody @Valid AppQueueVo appQueueVo,
                                    HttpServletRequest request) {
        try {
            if (!appQueueManager.exists(appQueueVo.getName())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_NOT_EXISTS, null);
            }

            if (!appTypeManager.exists(appQueueVo.getSender())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_SENDER_NOT_EXISTS, null);
            }

            if (!appTypeManager.exists(appQueueVo.getReceiver())) {
                return ResponseBean.buildResponse(request, ErrorCode.APP_QUEUE_RECEIVER_NOT_EXISTS, null);
            }

            appQueueManager.saveOrUpdate(appQueueVo);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("update app queue failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/queue/{name}", method = RequestMethod.DELETE)
    public ResponseBean deleteQueue(@PathVariable String name, HttpServletRequest request) {
        try {
            appQueueManager.deleteAppQueue(name);
            return ResponseBean.buildSuccess(request, null);
        } catch (Exception e) {
            logger.error("delete app queue failed", e);
            return ResponseBean.buildResponse(request, ErrorCode.SYSTEM_ERROR, null);
        }
    }
}
