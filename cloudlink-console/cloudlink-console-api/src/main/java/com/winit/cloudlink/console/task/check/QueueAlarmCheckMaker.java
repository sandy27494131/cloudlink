package com.winit.cloudlink.console.task.check;

import com.winit.cloudlink.console.bean.SystemConfig;
import com.winit.cloudlink.console.task.model.QueueCheck;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Message;
import com.winit.cloudlink.rabbitmq.mgmt.model.Queue;
import com.winit.cloudlink.storage.api.constants.AlarmSettingsKey;
import com.winit.cloudlink.storage.api.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by jianke.zhang on 2017/6/6.
 */
@Component("queueAlarmCheckMaker") public class QueueAlarmCheckMaker {

    private final Logger logger = LoggerFactory.getLogger(QueueAlarmCheckMaker.class);

    @Resource private SystemConfig systemConfig;

    public List<QueueCheck> check(AlarmConfigVo task, List<QueueAlarmVo> lstQueueAlarm, boolean needSecondCheck) {
        List<QueueCheck> queueChecks = new ArrayList<QueueCheck>();
        if (queueCheck(queueChecks, lstQueueAlarm, task) && needSecondCheck) {
            queueSecondCheck(queueChecks);
        }
        return queueChecks;
    }

    private boolean queueCheck(List<QueueCheck> queueChecks, List<QueueAlarmVo> lstQueueAlarm, AlarmConfigVo task) {
        Map<String, Collection<Queue>> mapQueues = new HashMap<String, Collection<Queue>>();
        Collection<Queue> queues = new HashSet<Queue>();
        boolean needSecondCheck = false;

        long maxMsg;
        try {
            maxMsg = Long.parseLong(task.getAlarmSettings()
                    .get(AlarmSettingsKey.QUEUE_MESSAGE_AMOUNT_THRESHOLD.name()));
        } catch (Exception e) {
            maxMsg = 10000;
            logger.error("", e);
        }

        /* 获取rabbitMQ上的队列set到QueueCheck中进行检测 */
        List<AreaVo> areaVos = RabbitMgmtServiceHelper.getAllAreas();

        QueueCheck queueCheck = null;
        for (AreaVo area : areaVos) {
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(area.getCode());
                if (service == null) {
                    continue;
                }
                if (mapQueues.keySet().contains(area.getCode())) {
                    queues = mapQueues.get(area.getCode());
                } else {
                    queues = service.queues().allOnDefault();
                    mapQueues.put(area.getCode(), queues);
                }
                for (Queue queue : queues) {
                    queueCheck = buildQueueCheck(maxMsg, queue, area.getCode(), lstQueueAlarm);

                    if (null != queueCheck) {
                        // 检测队列，顺便检测是否需要二次检测
                        needSecondCheck = queueCheck.check().isNeedSecondCheck() ? true : needSecondCheck;

                        queueChecks.add(queueCheck);
                    }
                }
            } catch (Exception e) {
                System.out.println(">>>>>>>>>>>>>>\n\n\n\n\n\n>>>>>>>>>>\n\n\n>>>>>>>>>" + queueCheck.getQueueName());
                logger.error("queue check failed", e);
            }
        }

        return needSecondCheck;
    }

    private QueueCheck buildQueueCheck(long globalMsgMax, Queue queue, String areaCode, List<QueueAlarmVo> queueAlarmVos) {

        long msgMax = globalMsgMax;
        Set<String> emails = null;
        String mobile = null;
        if (null != queueAlarmVos) {
            for (QueueAlarmVo vo : queueAlarmVos) {
                if (queue.getName().matches(vo.getName())) {
                    if (vo.isDisabled()) {
                        return null;
                    } else {
                        if (vo.getMaxMsg() > 0) {
                            msgMax = vo.getMaxMsg();
                        }
                        emails = vo.getEmails();
                        mobile = vo.getMobile();
//                        queueAlarmVos.remove(vo);
                        break;
                    }
                }
            }
        }

        return QueueCheck.newQueueCheck(queue.getName(), areaCode).setQueue(queue).setMsgMax(msgMax).setEmails(emails).setMobile(mobile);
    }

    private void queueSecondCheck(List<QueueCheck> queueChecks) {
        for (QueueCheck queueCheck : queueChecks) {
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
        for (QueueCheck queueCheck : queueChecks) {
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

}
