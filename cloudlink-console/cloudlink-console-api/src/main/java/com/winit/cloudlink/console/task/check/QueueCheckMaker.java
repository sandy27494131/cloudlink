package com.winit.cloudlink.console.task.check;

import com.winit.cloudlink.console.bean.SystemConfig;
import com.winit.cloudlink.console.task.model.QueueCheck;
import com.winit.cloudlink.console.utils.Constants;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Message;
import com.winit.cloudlink.rabbitmq.mgmt.model.Queue;
import com.winit.cloudlink.storage.api.constants.AlarmSettingsKey;
import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import com.winit.cloudlink.storage.api.vo.AppIdVo;
import com.winit.cloudlink.storage.api.vo.AppQueueVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by xiangyu.liang on 2016/1/19.
 */
@Component("queueCheckMaker")
public class QueueCheckMaker {

    private   final Logger logger = LoggerFactory.getLogger(QueueCheckMaker.class);

    @Resource
    private SystemConfig systemConfig;

    public   List<QueueCheck> check(AlarmConfigVo task, List<AppIdVo> lstAllAppId,
                                         List<AppQueueVo> lstAllAppQueue, boolean needSecondCheck) {
        List<QueueCheck> lstQueueCheck = getQueueCheckList(task, lstAllAppId, lstAllAppQueue);
        if (queueCheck(lstQueueCheck, task) && needSecondCheck) {
            queueSecondCheck(lstQueueCheck);
        }
        return lstQueueCheck;
    }

    /**
     * @param lstQueueCheck
     * @param task
     * @return 返回true代表需要二次检测
     */
    private   boolean queueCheck(List<QueueCheck> lstQueueCheck, AlarmConfigVo task) {
        boolean needSecondCheck = false;

        Map<String,Collection<Queue>> mapQueues=new HashMap<String, Collection<Queue>>();
        Collection<Queue> queues=new HashSet<Queue>();
        /* 获取rabbitMQ上的队列set到QueueCheck中进行检测 */
        for (QueueCheck queueCheck : lstQueueCheck) {
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(queueCheck.getAreaCode());
                if (service == null) {
                    //lstQueueCheck.remove(queueCheck);
                    continue;
                }
                if(mapQueues.keySet().contains(queueCheck.getAreaCode())){
                    queues=mapQueues.get(queueCheck.getAreaCode());
                }else {
                    queues=service.queues().all();
                    mapQueues.put(queueCheck.getAreaCode(),queues);
                }
                for (Queue q : queues) {
                    if (queueCheck.getQueueName().equals(q.getName())) {
                        // 检测队列，顺便检测是否需要二次检测
                        needSecondCheck = queueCheck.setQueue(q).check().isNeedSecondCheck() ? true : needSecondCheck;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(">>>>>>>>>>>>>>\n\n\n\n\n\n>>>>>>>>>>\n\n\n>>>>>>>>>"+queueCheck.getQueueName());
                logger.error("queue check failed", e);
            }
        }
        return needSecondCheck;
    }

    private   void queueSecondCheck(List<QueueCheck> lstQueueCheck) {
        for (QueueCheck queueCheck : lstQueueCheck) {
            if (!queueCheck.isNeedSecondCheck()) {
                continue;
            }
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(queueCheck.getAreaCode());
                Collection<Message> msgs = service.queues().getMessages(queueCheck.getQueueName());
                if (msgs != null && msgs.size() > 0) {
                    Message msg = msgs.iterator().next();
                    Map<String, Object> mapHeaders = (Map) msg.getProperties().get("headers");
                    if (mapHeaders!=null &&  mapHeaders.keySet().contains("id")) {
                        queueCheck.setTestMsgId(String.valueOf(mapHeaders.get("id")));
                    }
                } else {
                    queueCheck.setNeedSecondCheck(false);
                    queueCheck.setConsumed(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Long interval=systemConfig.getQueueSencondCheckTnterval();
        if(interval==null){
            interval=Long.valueOf(60000);
        }
        try {
            Thread.currentThread().sleep(interval);
        } catch (Exception e) {
            logger.error("queue check failed", e);
        }

        // 60秒后
        for (QueueCheck queueCheck : lstQueueCheck) {
            if (!queueCheck.isNeedSecondCheck()) {
                continue;
            }
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(queueCheck.getAreaCode());
                Collection<Message> msgs = service.queues().getMessages(queueCheck.getQueueName());
                if (msgs != null && msgs.size() > 0) {
                    Message msg = msgs.iterator().next();
                    Map<String, Object> mapHeaders = (Map) msg.getProperties().get("headers");
                    if (mapHeaders != null && mapHeaders.keySet().contains("id")) {
                        queueCheck.setCompareMsgId(String.valueOf(mapHeaders.get("id"))).secondCheck();
                    }
                } else {
                    queueCheck.setNeedSecondCheck(false);
                    queueCheck.setConsumed(true);
                }

            } catch (Exception e) {
                logger.error("queue check failed", e);
            }
        }

    }

    private   boolean containsArea(String[] areas, String area) {
        for (String str : areas) {
            if (str.equals(area)) return true;
        }
        return false;
    }
    /**
     * 获取一个list，包含待检测的队列
     *
     * @param task
     * @return
     */
    private   List<QueueCheck> getQueueCheckList(AlarmConfigVo task, List<AppIdVo> lstAllAppId,
                                                      List<AppQueueVo> lstAllAppQueue) {
        long maxMsg;
        try {
            maxMsg = Long.parseLong(task.getAlarmSettings().get(AlarmSettingsKey.QUEUE_MESSAGE_AMOUNT_THRESHOLD.name()));
        } catch (Exception e) {
            maxMsg = 10000;
            logger.error("", e);
        }
        List<QueueCheck> lstQueueCheck = new ArrayList<QueueCheck>();
        for (AppQueueVo appQueueVo : lstAllAppQueue) {
            if ("COMMAND".equals(appQueueVo.getMessageType())) {
                // 生成接收方要接收的队列
                // 获取接收方的的app类型
                String appType = appQueueVo.getReceiver();
                // 获取所有此类型的appId
                List<AppIdVo> lstAppId = getAppIdsByAppType(appType, lstAllAppId);
                // 根据appId生成相应队列存入检测的集合中
                for (AppIdVo appId : lstAppId) {
                    // 如果此appId所属数据中心在检测范围内
                    if (containsArea(task.getArea(), appId.getArea())) {
                        String queueName = Constants.QUEUE_CMD_CALL_FORMAT.replace("${name}", appQueueVo.getName())
                            .replace("${appId}", appId.getAppId());
                        // 放到集合中
                        Integer maxMsgTmp;
                        if(appQueueVo.getMaxMsg()!=null && appQueueVo.getMaxMsg()>0){
                            maxMsgTmp=appQueueVo.getMaxMsg();
                        }else {
                            maxMsgTmp=(int)maxMsg;
                        }
                        lstQueueCheck.add(QueueCheck.newQueueCheck(queueName, appId.getArea()).setMsgMax(maxMsgTmp.longValue()).setEmails(appQueueVo.getEmails()));
                    }
                }
                // 生成发送方要接收的队列
                // 获取发送方的app类型
                appType = appQueueVo.getSender();
                // 获取所有此类型的appId
                lstAppId = getAppIdsByAppType(appType, lstAllAppId);
                // 根据appId生成相应队列存入检测的集合中
                for (AppIdVo appId : lstAppId) {
                    // 如果此appId所属数据中心在检测范围内
                    if (containsArea(task.getArea(), appId.getArea())) {
                        String queueName = Constants.QUEUE_CMD_CALLBACK_FORMAT.replace("${name}", appQueueVo.getName())
                            .replace("${appId}", appId.getAppId());
                        // 放到集合中
                        Integer maxMsgTmp;
                        if(appQueueVo.getMaxMsg()!=null && appQueueVo.getMaxMsg()>0){
                            maxMsgTmp=appQueueVo.getMaxMsg();
                        }else {
                            maxMsgTmp=(int)maxMsg;
                        }
                        lstQueueCheck.add(QueueCheck.newQueueCheck(queueName, appId.getArea()).setMsgMax(maxMsgTmp.longValue()).setEmails(appQueueVo.getEmails()));
                    }
                }

            } else if ("MESSAGE".equals(appQueueVo.getMessageType())) {
                // 生成接收方要接收的队列
                // 获取接收方的的app类型
                String appType = appQueueVo.getReceiver();
                // 获取所有此类型的appId
                List<AppIdVo> lstAppId = getAppIdsByAppType(appType, lstAllAppId);
                // 根据appId生成相应队列存入检测的集合中
                for (AppIdVo appId : lstAppId) {
                    // 如果此appId所属数据中心在检测范围内
                    if (containsArea(task.getArea(), appId.getArea())) {
                        String queueName = Constants.QUEUE_MSG_FORMAT.replace("${name}", appQueueVo.getName())
                            .replace("${appId}", appId.getAppId());
                        // 放到集合中
                        Integer maxMsgTmp;
                        if(appQueueVo.getMaxMsg()!=null && appQueueVo.getMaxMsg()>0){
                            maxMsgTmp=appQueueVo.getMaxMsg();
                        }else {
                            maxMsgTmp=(int)maxMsg;
                        }
                        lstQueueCheck.add(QueueCheck.newQueueCheck(queueName, appId.getArea()).setMsgMax(maxMsgTmp.longValue()).setEmails(appQueueVo.getEmails()));
                    }
                }
            }
        }
        return lstQueueCheck;
    }

    private   List<AppIdVo> getAppIdsByAppType(String appType, List<AppIdVo> lstAppId) {
        List<AppIdVo> lst = new ArrayList<AppIdVo>();
        for (AppIdVo obj : lstAppId) {
            if (appType.equals(obj.getAppType())) lst.add(obj);
        }
        return lst;
    }
}
