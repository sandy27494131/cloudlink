package com.winit.cloudlink.config;

import com.winit.cloudlink.common.AppID;
import org.apache.commons.lang3.StringUtils;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * Created by stvli on 2015/11/12.
 */
public class CloudlinkOptions {

    private Properties properties = new Properties();

    // 应用ID,全球唯一
    public static final String KEY_APP_ID = "appId";

    // 异常处理中心应用ID,全球唯一
    public static final String KEY_EPC_APP_ID = "epcAppId";

    // 应用类型
    public static final String KEY_APP_TYPE = "appType";

    // 应用版本
    public static final String KEY_APP_VERSION = "appVersion";

    // 所属组织
    public static final String KEY_ORGANIZATION = "organization";

    // 所有人
    public static final String KEY_OWNER = "owner";

    // ZONE
    public static final String KEY_ZONE = "zone";

    // 注册服务器
    public static final String KEY_REGISTRY = "registry";

    // mq服务器地址
    public static final String KEY_MQ = "mq";

    // 消息消费失败阻塞队列
    public static final String KEY_BLOCKQUEUE = "queue.block";

    //是否启用消息体限制
    public static final String KEY_MESSAGE_SIZE_LIMITED = "message.size.limited";

    // 消息体积警告值
    public static final String KEY_MESSAGE_WARN_BYTES = "warn.message.size";

    // 消息体积最大值，超过后拒发
    public static final String KEY_MESSAGE_MAX_BYTES = "max.message.size";
    /**
     * 是否开启压缩
     */
    public static final String KEY_COMPRESSION_ENABLED = "compression.enabled";
    /**
     * 压缩类型
     */
    public static final String KEY_COMPRESSION_CODEC = "compression.codec";

    /**
     * 大于此伐值才进行压缩
     */
    public static final String KEY_NON_COMPRESSION_MAX_BYTE = "non.compression.max.byte";

    public static final boolean DEFAULT_BLOCKQUEUE = false;

    /**
     * 验证目的队列是否存在
     */
    public static final String KEY_VALID_DESTINATION_QUEUE = "valid.destination.queue";

    public static final boolean DEFAULT_VALID_DESTINATION_QUEUE = true;

    private Map<String, Integer> concurrentConsumers = new Hashtable<String, Integer>();

    public AppID getAppId() {
        String appIdStr = properties.getProperty(KEY_APP_ID);
        return new AppID(appIdStr);
    }

    public void setAppId(String appId) {
        properties.setProperty(KEY_APP_ID, appId);
    }

    public AppID getEpcAppId() {
        String appIdStr = properties.getProperty(KEY_EPC_APP_ID);
        if (StringUtils.isNotBlank(appIdStr)) {
            return new AppID(appIdStr);
        } else {
            return null;
        }
    }

    public void setEpcAppId(String opcAppId) {
        properties.setProperty(KEY_EPC_APP_ID, opcAppId);
    }

    public String getAppType() {
        return properties.getProperty(KEY_APP_TYPE);
    }

    public void setAppType(String appType) {
        properties.setProperty(KEY_APP_TYPE, appType);
    }

    public String getAppVersion() {
        return properties.getProperty(KEY_APP_VERSION);
    }

    public void setAppVersion(String appVersion) {
        properties.setProperty(KEY_APP_VERSION, appVersion);
    }

    public String getOrganization() {
        return properties.getProperty(KEY_ORGANIZATION);
    }

    public void setOrganization(String organization) {
        properties.setProperty(KEY_ORGANIZATION, organization);
    }

    public String getOwner() {
        return properties.getProperty(KEY_OWNER);
    }

    public void setOwner(String owner) {
        properties.setProperty(KEY_OWNER, owner);
    }

    public String getZone() {
        return properties.getProperty(KEY_ZONE);
    }

    public void setZone(String zone) {
        properties.setProperty(KEY_ZONE, zone);
    }

    public String getRegistry() {
        return properties.getProperty(KEY_REGISTRY);
    }

    public void setRegistry(String registry) {
        properties.setProperty(KEY_REGISTRY, registry);
    }

    public String getMq() {
        return properties.getProperty(KEY_MQ);
    }

    public void setMq(String mq) {
        properties.setProperty(KEY_MQ, mq);
    }

    public String getQueueBlock() {
        return properties.getProperty(KEY_BLOCKQUEUE);
    }

    public void setQueueBlock(String queueBlock) {
        properties.setProperty(KEY_BLOCKQUEUE, queueBlock);
    }

    public String getCompressionEnabled() {
        return properties.getProperty(KEY_COMPRESSION_ENABLED);
    }

    public void setCompressionEnabled(String compressionEnabled) {
        properties.setProperty(KEY_COMPRESSION_ENABLED, compressionEnabled);
    }

    public String getCompressionCodec() {
        return properties.getProperty(KEY_COMPRESSION_CODEC);
    }

    public void setCompressionCodec(String compressionCodec) {
        properties.setProperty(KEY_COMPRESSION_CODEC, compressionCodec);
    }


    public String getMessageSizeLimited() {
        return properties.getProperty(KEY_MESSAGE_SIZE_LIMITED);
    }

    public void setMessageSizeLimited(String messageSizeLimited) {
        properties.setProperty(KEY_MESSAGE_SIZE_LIMITED, messageSizeLimited);
    }


    public String getMessageWarnBytes() {
        return properties.getProperty(KEY_MESSAGE_WARN_BYTES);
    }

    public void setMessageWarnBytes(String messageWarnBytes) {
        properties.setProperty(KEY_MESSAGE_WARN_BYTES, messageWarnBytes);
    }

    public String getMessageMaxBytes() {
        return properties.getProperty(KEY_MESSAGE_MAX_BYTES);
    }

    public void setMessageMaxBytes(String messageMaxBytes) {
        properties.setProperty(KEY_MESSAGE_MAX_BYTES, messageMaxBytes);
    }

    public String getNonCompressionMaxByte() {
        return properties.getProperty(KEY_NON_COMPRESSION_MAX_BYTE);
    }

    public void setNonCompressionMaxByte(String uncompressionMaxByte) {
        properties.setProperty(KEY_NON_COMPRESSION_MAX_BYTE, uncompressionMaxByte);
    }

    public String getValidDestinationQueue() {
        return properties.getProperty(KEY_VALID_DESTINATION_QUEUE, "true");
    }

    public void setValidDestinationQueue(String valid) {
        properties.setProperty(KEY_VALID_DESTINATION_QUEUE, valid);
    }


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    public Map<String, Integer> getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public void setConcurrentConsumers(Map<String, Integer> concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }
}
