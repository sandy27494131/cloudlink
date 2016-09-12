package com.winit.cloudlink.config;

import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.common.Builder;
import com.winit.cloudlink.common.compress.Compress.CompressCodec;
import com.winit.cloudlink.common.utils.StringUtils;

/**
 * ApplicationOptions
 *
 * @author Steven Liu
 * @export
 */
public class ApplicationOptions extends Options {

    private static final long serialVersionUID            = 5508512956753757169L;

    public static final int   DEFAULT_UNCOMPRESS_MAX_BYTE = 10240;

    // 數據中心ID
    private String            zone;

    // 应用ID,必须全局唯一,建议使用应用类型+组织或区域或国家来组成,如:OMS.CN,TMS.ER,WMS.SZ
    private AppID             appId;

    // 应用类型,如:OMS,TMS,WMS
    private String            appType;

    // 应用版本
    private String            appVersion;

    // 所属组织
    private String            organization;

    // 所有人
    private String            owner;

    // 消息消费失败时是否阻塞队列
    private boolean           blockQueue                  = false;

    // 压缩类型
    private CompressCodec     compressCodec;

    // 不进行压缩的最大字节数
    private int               nonCompressMaxByte;

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

    public CompressCodec getCompressCodec() {
        return compressCodec;
    }

    public void setCompressCodec(CompressCodec compressCodec) {
        this.compressCodec = compressCodec;
    }

    public int getNonCompressMaxByte() {
        return nonCompressMaxByte;
    }

    public void setNonCompressMaxByte(int nonCompressMaxByte) {
        this.nonCompressMaxByte = nonCompressMaxByte;
    }

    public static ApplicationOptions build(CloudlinkOptions options) {
        boolean block = (null == options.getQueueBlock() ? CloudlinkOptions.DEFAULT_BLOCKQUEUE : Boolean.valueOf(options.getQueueBlock()));
        return new ApplicationConfigBuilder().zone(options.getZone())
            .id(options.getAppId())
            .type(options.getAppType())
            .appVersion(options.getAppVersion())
            .organization(options.getOrganization())
            .owner(options.getOwner())
            .blockQueue(block)
            .compressCodec(options.getCompressionCodec())
            .nonCompressMaxByte(options.getNonCompressionMaxByte())
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

        public ApplicationConfigBuilder compressCodec(String compressCodec) {
            applicationConfig.setCompressCodec(CompressCodec.nameOf(compressCodec));
            return this;
        }

        public ApplicationConfigBuilder nonCompressMaxByte(String uncompressMaxByte) {
            int value = StringUtils.isBlank(uncompressMaxByte) ? DEFAULT_UNCOMPRESS_MAX_BYTE : Integer.valueOf(uncompressMaxByte);
            applicationConfig.setNonCompressMaxByte(value);
            return this;
        }

        @Override
        public ApplicationOptions build() {
            return applicationConfig;
        }
    }
}
