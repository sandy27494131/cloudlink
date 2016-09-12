package com.winit.cloudlink.event.eventid;

import com.winit.cloudlink.common.AppID;
import org.junit.Test;

/**
 * Created by stvli on 2016/8/15.
 */
public class AppIdEventIdGeneratorTest {
    @Test
    public void testGenerate1(){
        AppID appID=new AppID("OMS1.OMS.ALL.CNR");
        AppIdEventIdGenerator generator=new AppIdEventIdGenerator(appID);
        for(int i=0;i<5;i++){
            System.out.println(generator.generate());
        }
        System.out.println();
        appID=new AppID("USLA.CWM.US.USR");
        generator=new AppIdEventIdGenerator(appID);
        for(int i=0;i<5;i++){
            System.out.println(generator.generate());
        }
    }
}
