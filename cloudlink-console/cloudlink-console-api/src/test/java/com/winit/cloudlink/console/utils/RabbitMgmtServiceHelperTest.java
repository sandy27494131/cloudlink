package com.winit.cloudlink.console.utils;

import com.winit.cloudlink.common.URL;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Exchange;
import com.winit.cloudlink.rabbitmq.mgmt.model.Message;
import com.winit.cloudlink.rabbitmq.mgmt.model.Queue;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

/**
 * Created by xiangyu.liang on 2016/1/6.
 */
public class RabbitMgmtServiceHelperTest  {

    public class ss{
        private double b;
        public double getB()
        {
            return this.b;
        }
    }
    @Test
    public void testTestConn()
    {
        Object o= (Object) new String("ss");
        System.out.println( String.valueOf(o));
        URL url = URL.valueOf("amqp://admin:admin123@172.16.3.147:15672/cn");
        System.out.println(url.getHost());
        RabbitMgmtService service= RabbitMgmtService.builder().host(url.getHost()).port(url.getPort()).credentials(url.getUsername(),url.getPassword())
                .vhost(StringUtils.isNotBlank(url.getPath()) ? url.getPath() : "/").build();
        boolean isEnable=true;
        try {
            Collection<Queue> queues=service.queues().allOnDefault();
            Collection<Exchange> exchanges=service.exchanges().allOnDefault();
            /*for(Queue q:queues)
            {
                if(q.getMessageStats()!=null  && q.getMessageStats().getDeliverGetDetails()!=null    ){
                    System.out.println(q.getMessageStats().getDeliverGetDetails().getRate());
                }
            }*/
            /*Collection<Message> msgs=service.queues().getMessages("message_CMD.GZIDC.WS.CN.CNR");
            System.out.println("msgs.size() = " + msgs.size());
            System.out.println("msgs.iterator().next().getPayload() = " + ((Map)msgs.iterator().next().getProperties().get("headers")).get("id").toString());*/
            System.out.println("exchange num: " + exchanges.size());
            System.out.println("queue num: " + queues.size());
            long seconds=60;
            long unackCnt=service.overviewBySeconds(seconds).getQueueTotals().getMessagesUnacknowledged();
            System.out.println("unackCnt = " + unackCnt);
        }catch (Exception e)
        {
            isEnable=false;
           e.printStackTrace();
        }
        System.out.println("isEnable = " + isEnable);
    }

}
