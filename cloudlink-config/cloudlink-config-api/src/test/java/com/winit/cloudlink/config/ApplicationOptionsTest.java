package com.winit.cloudlink.config;

import com.winit.cloudlink.common.Constants;
import com.winit.cloudlink.common.compress.Compress;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationOptionsTest {
    @Test
    public void test1() {
        CloudlinkOptions cloudlinkOptions = new CloudlinkOptions();
        cloudlinkOptions.setAppId("OMS1.OMS.CN.CNR");
        cloudlinkOptions.setCompressionEnabled("false");
        cloudlinkOptions.setMessageSizeLimited("false");
        ApplicationOptions applicationOptions = ApplicationOptions.build(cloudlinkOptions);
        Assert.assertFalse(applicationOptions.isMessageSizeLimited());
        Assert.assertEquals(applicationOptions.getMessageWarnBytes(), Constants.DEFAULT_MESSAGE_WARN_BYTES);
        Assert.assertEquals(applicationOptions.getMessageMaxBytes(), Constants.DEFAULT_MESSAGE_MAX_BYTES);
        Assert.assertFalse(applicationOptions.isCompressEnabled());
        Assert.assertEquals(applicationOptions.getCompressCodec(), Constants.DEFAULT_COMPRESSION_CODEC);
        Assert.assertEquals(applicationOptions.getNonCompressMaxBytes(), Constants.DEFAULT_NO_COMPRESSION_SIZE);
    }

    @Test
    public void test2() {
        CloudlinkOptions cloudlinkOptions = new CloudlinkOptions();
        cloudlinkOptions.setAppId("OMS1.OMS.CN.CNR");
        boolean compressionEnabled = true;
        boolean messageSizeLimited = true;
        int messageWarnBytes = 10000;
        int messageMaxBytes = 100000;
        Compress.CompressCodec compressCodec = Compress.CompressCodec.GZIP;
        int nonCompressMaxBytes = 10000;
        cloudlinkOptions.setCompressionEnabled(String.valueOf(compressionEnabled));
        cloudlinkOptions.setMessageSizeLimited(String.valueOf(messageSizeLimited));
        cloudlinkOptions.setCompressionCodec(compressCodec.name());
        cloudlinkOptions.setMessageWarnBytes(String.valueOf(messageWarnBytes));
        cloudlinkOptions.setMessageMaxBytes(String.valueOf(messageMaxBytes));
        cloudlinkOptions.setNonCompressionMaxByte(String.valueOf(nonCompressMaxBytes));
        ApplicationOptions applicationOptions = ApplicationOptions.build(cloudlinkOptions);
        Assert.assertTrue(applicationOptions.isMessageSizeLimited());
        Assert.assertEquals(applicationOptions.getMessageWarnBytes(), messageWarnBytes);
        Assert.assertEquals(applicationOptions.getMessageMaxBytes(), messageMaxBytes);
        Assert.assertTrue(applicationOptions.isCompressEnabled());
        Assert.assertEquals(applicationOptions.getCompressCodec(), compressCodec);
        Assert.assertEquals(applicationOptions.getNonCompressMaxBytes(), nonCompressMaxBytes);
    }
}
