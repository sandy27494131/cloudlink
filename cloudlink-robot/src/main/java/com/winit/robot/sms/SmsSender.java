package com.winit.robot.sms;

import com.esms.MOMsg;
import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by stvli on 2017/2/21.
 */
@Component
public class SmsSender {
    private static Logger logger = LoggerFactory.getLogger(SmsSender.class);
    public static final String SUCCESS = "0";
    @Value("${sms.esms.username}")
    private String esmsUsername;
    @Value("${sms.esms.password}")
    private String esmsPassword;
    @Value("${sms.esms.cm.host}")
    private String esmsCmHost;
    @Value("${sms.esms.cm.port}")
    private Integer esmsCmPort;
    @Value("${sms.esms.ws.host}")
    private String esmsWsHost;
    @Value("${sms.esms.ws.port}")
    private Integer esmsWsPort;


    private PostMsg esmsPostMsg;
    private Account esmsAccount;

    @PostConstruct
    public void init() {
        esmsAccount = new Account(esmsUsername, esmsPassword);//
        esmsPostMsg = new PostMsg();
        esmsPostMsg.getCmHost().setHost(esmsCmHost, esmsCmPort);//设置网关的IP和port，用于发送信息
        esmsPostMsg.getWsHost().setHost(esmsWsHost, esmsWsPort);//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等
    }

     /**
     * 群发
     */
    public String sendSms(String content, String... toList) throws SmsSendException {
        MTPack pack = new MTPack();
        pack.setBatchID(UUID.randomUUID());
        pack.setBatchName("短信测试批次");
        pack.setMsgType(MTPack.MsgType.SMS);
        pack.setBizType(0);
        pack.setDistinctFlag(false);
        pack.setSendType(MTPack.SendType.MASS);
        ArrayList<MessageData> msgs = new ArrayList<MessageData>();
        for (String to : toList) {
            msgs.add(new MessageData(to, content));
        }
        pack.setMsgs(msgs);
        GsmsResponse resp = null;
        try {
            resp = esmsPostMsg.post(esmsAccount, pack);
            return resp.getUuid().toString();
        } catch (Exception e) {
            logger.error("批量发送短信失败!", e);
            throw new SmsSendException("批量发送短信失败!", e);
        }
    }

    /**
     * 群发
     */
    public String sendSms(String content, List<String> toArray) throws SmsSendException {
        MTPack pack = new MTPack();
        pack.setBatchID(UUID.randomUUID());
        pack.setBatchName("短信测试批次");
        pack.setMsgType(MTPack.MsgType.SMS);
        pack.setBizType(0);
        pack.setDistinctFlag(false);
        pack.setSendType(MTPack.SendType.MASS);
        ArrayList<MessageData> msgs = new ArrayList<MessageData>();
        for (String to : toArray) {
            msgs.add(new MessageData(to, content));
        }
        pack.setMsgs(msgs);
        GsmsResponse resp = null;
        try {
            resp = esmsPostMsg.post(esmsAccount, pack);
            return resp.getUuid().toString();
        } catch (Exception e) {
            logger.error("批量发送短信失败!", e);
            throw new SmsSendException("批量发送短信失败!", e);
        }
    }

    /**
     * 获取上行信息
     *
     * @throws Exception
     */
    public MOMsg[] getMos() throws Exception {
        return esmsPostMsg.getMOMsgs(esmsAccount, 100);

    }

    /**
     * 查询提交报告
     *
     * @throws Exception
     */
    public MTResponse[] getResps(String batchId) throws Exception {
        UUID bid = UUID.fromString(batchId); //如果需要按批次ID来查询
        return esmsPostMsg.findResps(esmsAccount, 1, bid, null, 0);
    }

    /**
     * 获取提交报告
     *
     * @throws Exception
     */
    public MTResponse[] getResps() throws Exception {
        return esmsPostMsg.getResps(esmsAccount, 100);
    }

    /**
     * 查询状态报告
     *
     * @throws Exception
     */
    public MTReport[] findReports(String batchId) throws Exception {
        UUID bid = UUID.fromString(batchId); //如果需要按批次ID来查询
        return esmsPostMsg.findReports(esmsAccount, 1, bid, null, 0);
    }

    /**
     * 获取状态报告
     *
     * @throws Exception
     */
    public MTReport[] getReports() throws Exception {
        return esmsPostMsg.getReports(esmsAccount, 100);
    }
}
