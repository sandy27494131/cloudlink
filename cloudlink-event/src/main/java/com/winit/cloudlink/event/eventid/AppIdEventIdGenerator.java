package com.winit.cloudlink.event.eventid;

import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.common.utils.NetUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.util.Random;

/**
 * Created by stvli on 2016/8/12.
 */
public class AppIdEventIdGenerator implements EventIdGenerator {
    private InstanceIdGenerator instanceIdGenerator = new InstanceIdGenerator();
    private RegionCodeGenerator regionCodeGenerator = new RegionCodeGenerator();
    private AppTypeCodeGenerator appTypeCodeGenerator = new AppTypeCodeGenerator();
    private SequenceGenerator sequenceGenerator = new SequenceGenerator();

    private AppID appID;

    public AppIdEventIdGenerator(AppID appID) {
        this.appID = appID;
    }

    @Override
    public String generate() {
        StringBuffer sb = new StringBuffer();
        sb.append("E").append(appTypeCodeGenerator.getId()).append(instanceIdGenerator.getId()).append(sequenceGenerator.getId()).append(regionCodeGenerator.getId());
        return sb.toString();
    }

    class InstanceIdGenerator {
        private String instanceId = "00";

        InstanceIdGenerator() {
            InetAddress localAddress = NetUtils.getLocalAddress();
            String ip = localAddress.getHostAddress();
            long ipValue = NetUtils.ipToLong(ip);
            Random random = new Random(ipValue);
            long id = (long) (1 + random.nextFloat() * 99L);
            instanceId = String.valueOf(id);
        }

        String getId() {
            return instanceId;
        }
    }

    class RegionCodeGenerator {
        String getId() {
            return StringUtils.left(appID.getArea(), 2);
        }
    }

    class AppTypeCodeGenerator {
        String getId() {
            String appId = appID.getAppType();
            appId = StringUtils.left(appId, 3);
            return StringUtils.rightPad(appId, 3, "0");
        }
    }

    class SequenceGenerator {
        IdWorker worker = new IdWorker(1, 1);
        String getId() {
            Long id = worker.nextId();
            Random random = new Random(id);
            return StringUtils.leftPad(String.valueOf(1+(int)(random.nextFloat()*999999999)), 10, "0");
        }
    }


}
