package com.winit.cloudlink.console.task.model;

import com.winit.cloudlink.rabbitmq.mgmt.model.Queue;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiangyu.liang on 2016/1/19.
 */
public class QueueCheck {
    private String queueName;
    private String areaCode;
    private String testMsgId;
    private String compareMsgId;
    private boolean exist;
    private boolean durable;
    private boolean autoDelete;
    private long msgAmount;
    private boolean msgOver;
    private boolean consumed;
    private long msgMax;
    private boolean needSecondCheck;
    private Set<String> emails;

    public Set<String> getEmails() {
        return emails;
    }

    public QueueCheck setEmails(Set<String> emails) {
        this.emails = emails;
        return this;
    }

    public boolean isNeedSecondCheck() {
        return needSecondCheck;
    }

    public void setNeedSecondCheck(boolean needSecondCheck) {
        this.needSecondCheck = needSecondCheck;
    }

    private Queue queue;

    public QueueCheck(String queueName, String areaCode) {
        this.queueName = queueName;
        this.areaCode = areaCode;
        this.exist=false;
        this.durable=false;
        this.autoDelete=true;
        this.msgAmount=0;
        this.msgMax=0;
        this.msgOver=true;
        this.consumed=false;
        this.needSecondCheck=false;
        this.emails=new HashSet<String>();
    }
    public static  QueueCheck newQueueCheck(String queueName, String areaCode){
        QueueCheck q=new QueueCheck(queueName,areaCode);
        return q;
    }
    public Queue getQueue() {
        return queue;
    }

    public QueueCheck setQueue(Queue queue) {
        this.queue = queue;
        return this;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTestMsgId() {
        return testMsgId;
    }

    public void setTestMsgId(String testMsgId) {
        this.testMsgId = testMsgId;
    }

    public String getCompareMsgId() {
        return compareMsgId;
    }

    public QueueCheck setCompareMsgId(String compareMsgId) {
        this.compareMsgId = compareMsgId;
        return this;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public long getMsgAmount() {
        return msgAmount;
    }

    public void setMsgAmount(long msgAmount) {
        this.msgAmount = msgAmount;
    }

    public boolean isMsgOver() {
        return msgOver;
    }

    public void setMsgOver(boolean msgOver) {
        this.msgOver = msgOver;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    public long getMsgMax() {
        return msgMax;
    }

    public QueueCheck setMsgMax(long msgMax) {
        this.msgMax = msgMax;
        return this;
    }
    public QueueCheck check(){
        this.exist=(this.queue!=null);
        if(this.exist){
            this.durable=queue.isDurable();
            this.autoDelete=queue.isAutoDelete();
            this.msgAmount=queue.getMessages();
            this.msgOver=(msgAmount>msgMax);
            if(queue.getMessagesReady()<=0){
                consumed=true;
            }else if(queue.getConsumers()<=0){
                consumed=false;
            }else{
                if(queue.getMessageStats()!=null && queue.getMessageStats().getDeliverGetDetails()!=null){
                    double rate=queue.getMessageStats().getDeliverGetDetails().getRate();
                    //消费率大于0为正常消费
                    if(rate>0){
                        consumed=true;
                    }else {
                        //如果不大于0，为保险起见，进行二次检测
                        needSecondCheck=true;
                    }
                }
            }
        }
        return this;
    }
    public QueueCheck secondCheck(){
        if( StringUtils.isNotBlank(testMsgId)&&StringUtils.isNotBlank(compareMsgId)&& (!testMsgId.equals(compareMsgId))){
            consumed=true;
        }else consumed=false;
        return this;
    }
    public boolean isExceptional(){
        if(this.exist&&this.durable&& (!this.autoDelete) && this.consumed&& (!this.msgOver)){
            return false;
        }else return true;
    }
}
