package com.winit.cloudlink.config;

import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.common.Builder;
import com.winit.cloudlink.common.Constants;
import com.winit.cloudlink.common.compress.Compress.CompressCodec;
import com.winit.cloudlink.common.utils.StringUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * ApplicationOptions
 *
 * @author Steven Liu
 * @export
 */
public class ApplicationOptions extends Options {

    private static final long serialVersionUID = 5508512956753757169L;

    /**
     * 云链接参数配置的前缀
     */
    public static final String CONFIG_PERFIX = "cloudlink.";

    // 數據中心ID
    private String zone;

    // 应用ID,必须全局唯一,建议使用应用类型+组织或区域或国家来组成,如:OMS.CN,TMS.ER,WMS.SZ
    private AppID appId;

    // 应用类型,如:OMS,TMS,WMS
    private String appType;

    // 应用版本
    private String appVersion;

    // 所属组织
    private String organization;

    // 所有人
    private String owner;

    // 消息消费失败时是否阻塞队列
    private boolean blockQueue = false;

    //是否开启消息体限制
    private boolean messageSizeLimited = Constants.DEFAULT_MESSAGE_SIZE_LIMITED;
    //消息载体警告字节数
    private int messageWarnBytes = Constants.DEFAULT_MESSAGE_WARN_BYTES;
    //消息体载最大字节数
    private int messageMaxBytes = Constants.DEFAULT_MESSAGE_MAX_BYTES;

    //是否开启压缩
    private boolean compressEnabled = Constants.DEFAULT_COMPRESSION_ENABLED;
    // 压缩类型
    private CompressCodec compressCodec = Constants.DEFAULT_COMPRESSION_CODEC;

    // 不进行压缩的最大字节数
    private int nonCompressMaxBytes = Constants.DEFAULT_NO_COMPRESSION_SIZE;

    private AppID epcAppId;

    // 验证目的队列是否存在
    private boolean validDestinationExists = true;

    private Map<String, Integer> concurrentConsumers = new Hashtable<String, Integer>();

    public AppID getAppId() {
        return this.appId;
    }

    public String getAppId2String() {
        return appId != null ? appId.toString() : "";
    }

    public void setAppId(AppID appId) {
        this.appId = appId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isBlockQueue() {
        return blockQueue;
    }

    public void setBlockQueue(boolean blockQueue) {
        this.blockQueue = blockQueue;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public boolean isCompressEnabled() {
        return compressEnabled;
    }

    public void setCompressEnabled(boolean compressEnabled) {
        this.compressEnabled = compressEnabled;
    }

    public CompressCodec getCompressCodec() {
        return compressCodec;
    }

    public void setCompressCodec(CompressCodec compressCodec) {
        this.compressCodec = compressCodec;
    }

    public int getNonCompressMaxBytes() {
        return nonCompressMaxBytes;
    }

    public void setNonCompressMaxBytes(int nonCompressMaxBytes) {
        this.nonCompressMaxBytes = nonCompressMaxBytes;
    }

    public boolean isMessageSizeLimited() {
        return messageSizeLimited;
    }

    public void setMessageSizeLimited(boolean messageSizeLimited) {
        this.messageSizeLimited = messageSizeLimited;
    }

    public int getMessageWarnBytes() {
        return messageWarnBytes;
    }

    public void setMessageWarnBytes(int messageWarnBytes) {
        this.messageWarnBytes = messageWarnBytes;
    }

    public int getMessageMaxBytes() {
        return messageMaxBytes;
    }

    public void setMessageMaxBytes(int messageMaxBytes) {
        this.messageMaxBytes = messageMaxBytes;
    }

    public AppID getEpcAppId() {
        return epcAppId;
    }

    public void setEpcAppId(AppID epcAppId) {
        this.epcAppId = epcAppId;
    }

    public String getEpcAppId2String() {
        return epcAppId != null ? epcAppId.toString() : "";
    }

    public boolean isValidDestinationExists() {
        return validDestinationExists;
    }

    public void setValidDestinationExists(boolean validDestinationExists) {
        this.validDestinationExists = validDestinationExists;
    }

    public Map<String, Integer> getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public void setConcurrentConsumers(Map<String, Integer> concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }

    public synchronized void addConcurrentConsumers(String handler, Integer concurrentConsumers) {
        if (this.concurrentConsumers == null) {
            this.concurrentConsumers = new Hashtable<String, Integer>();
        }
        this.concurrentConsumers.put(handler, concurrentConsumers);
    }

    public Integer getConcurrentConsumers(String handler) {
        if (this.concurrentConsumers != null) {
            return this.concurrentConsumers.get(handler);
        }
        return null;
    }

    public static ApplicationOptions build(CloudlinkOptions options) {
        boolean block = (null == options.getQueueBlock() ? CloudlinkOptions.DEFAULT_BLOCKQUEUE : Boolean.valueOf(options.getQueueBlock()));
        boolean validDestinationQueue = (null == options.getValidDestinationQueue() ? CloudlinkOptions.DEFAULT_VALID_DESTINATION_QUEUE : Boolean.valueOf(options.getValidDestinationQueue()));
        return new ApplicationConfigBuilder().zone(options.getZone())
                .id(options.getAppId())
                .type(options.getAppType())
                .appVersion(options.getAppVersion())
                .organization(options.getOrganization())
                .owner(options.getOwner())
                .blockQueue(block)
                .compressEnabled(options.getCompressionEnabled())
                .compressCodec(options.getCompressionCodec())
                .nonCompressMaxByte(options.getNonCompressionMaxByte())
                .messageSizeLimited(options.getMessageSizeLimited())
                .messageWarnBytes(options.getMessageWarnBytes())
                .messageMaxBytes(options.getMessageMaxBytes())
                .epcAppid(options.getEpcAppId())
                .validDestinationExists(validDestinationQueue)
                .concurrentConsumers(options.getConcurrentConsumers())
                .build();
    }

    public static class ApplicationConfigBuilder implements Builder<ApplicationOptions> {

        private ApplicationOptions applicationConfig = new ApplicationOptions();

        public ApplicationConfigBuilder zone(String zone) {
            applicationConfig.setZone(zone);
            return this;
        }

        public ApplicationConfigBuilder id(AppID appId) {
            applicationConfig.setAppId(appId);
            return this;
        }

        public ApplicationConfigBuilder epcAppid(AppID epcAppId) {
            applicationConfig.setEpcAppId(epcAppId);
            return this;
        }

        public ApplicationConfigBuilder type(String appType) {
            applicationConfig.setAppType(appType);
            return this;
        }

        public ApplicationConfigBuilder version(String appVersion) {
            applicationConfig.setAppVersion(appVersion);
            return this;
        }

        public ApplicationConfigBuilder organization(String appVersion) {
            applicationConfig.setAppVersion(appVersion);
            return this;
        }

        public ApplicationConfigBuilder appVersion(String organization) {
            applicationConfig.setOrganization(organization);
            return this;
        }

        public ApplicationConfigBuilder owner(String owner) {
            applicationConfig.setOwner(owner);
            return this;
        }

        public ApplicationConfigBuilder blockQueue(boolean block) {
            applicationConfig.setBlockQueue(block);
            return this;
        }

        public ApplicationConfigBuilder message(String compressCodec) {
            applicationConfig.setCompressCodec(CompressCodec.nameOf(compressCodec));
            return this;
        }


        public ApplicationConfigBuilder compressEnabled(String compressEnabled) {
            if (!StringUtils.isBlank(compressEnabled)) {
                applicationConfig.setCompressEnabled(Boolean.valueOf(compressEnabled));
            }
            return this;
        }

        public ApplicationConfigBuilder compressCodec(String compressCodec) {
            if (!StringUtils.isBlank(compressCodec)) {
                applicationConfig.setCompressCodec(CompressCodec.nameOf(compressCodec));
            }
            return this;
        }

        public ApplicationConfigBuilder nonCompressMaxByte(String uncompressMaxByte) {
            int value = StringUtils.isBlank(uncompressMaxByte) ? Constants.DEFAULT_NO_COMPRESSION_SIZE : Integer.valueOf(uncompressMaxByte);
            applicationConfig.setNonCompressMaxBytes(value);
            return this;
        }


        public ApplicationConfigBuilder messageSizeLimited(String messageSizeLimited) {
            if (StringUtils.isNotEmpty(messageSizeLimited)) {
                applicationConfig.setMessageSizeLimited(Boolean.valueOf(messageSizeLimited));
            }
            return this;
        }

        public ApplicationConfigBuilder messageWarnBytes(String messageWarnBytes) {
            if (StringUtils.isNotEmpty(messageWarnBytes)) {
                applicationConfig.setMessageWarnBytes(Integer.valueOf(messageWarnBytes));
            }
            return this;
        }

        public ApplicationConfigBuilder messageMaxBytes(String messageMaxBytes) {
            if (StringUtils.isNotEmpty(messageMaxBytes)) {
                applicationConfig.setMessageMaxBytes(Integer.valueOf(messageMaxBytes));
            }
            return this;
        }

        public ApplicationConfigBuilder validDestinationExists(boolean valid) {
            applicationConfig.setValidDestinationExists(valid);
            return this;
        }

        public ApplicationConfigBuilder concurrentConsumers(Map<String, Integer> concurrentConsumers) {
            applicationConfig.setConcurrentConsumers(concurrentConsumers);
            return this;
        }

        @Override
        public ApplicationOptions build() {
            return applicationConfig;
        }


    }
}
