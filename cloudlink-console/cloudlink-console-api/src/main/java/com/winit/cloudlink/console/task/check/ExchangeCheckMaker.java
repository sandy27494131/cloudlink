package com.winit.cloudlink.console.task.check;

import com.winit.cloudlink.console.task.model.ExchangeCheck;
import com.winit.cloudlink.console.utils.Constants;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Exchange;
import com.winit.cloudlink.storage.api.vo.AlarmConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xiangyu.liang on 2016/1/19.
 */
public class ExchangeCheckMaker {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeCheckMaker.class);

    public static List<ExchangeCheck> check(AlarmConfigVo task){
        List<ExchangeCheck> lstExchangeCheck=getExchangeCheckList(task);
        exchangeCheck(lstExchangeCheck);
        return lstExchangeCheck;
    }

    private static void exchangeCheck(List<ExchangeCheck> lstExchangeCheck){
        for(ExchangeCheck exchangeCheck:lstExchangeCheck){
            try {
                RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(exchangeCheck.getAreaCode());
                if (service == null) {
                    continue;
                }
                Collection<Exchange> exchanges = service.exchanges().all();
                for (Exchange e : exchanges) {
                    if (e.getName().equals(Constants.EXCHANGE_WINIT_SEND)) exchangeCheck.setWinitSend(e);
                    if (e.getName().equals(Constants.EXCHANGE_WINIT_RECEIVE)) exchangeCheck.setWinitReceive(e);
                }
                if(exchangeCheck.getWinitSend()!=null&&exchangeCheck.getWinitReceive()!=null){
                    exchangeCheck.setBindings(service.bindings()
                            .getEtoE(service.getVhost(), Constants.EXCHANGE_WINIT_SEND, Constants.EXCHANGE_WINIT_RECEIVE)
                            .get());
                }
                exchangeCheck.check();
            }catch (Exception e){
                logger.error("exchange check failed", e);
            }
        }
    }
    private static List<ExchangeCheck> getExchangeCheckList(AlarmConfigVo task){
        List<ExchangeCheck> lstExchangeCheck=new ArrayList<ExchangeCheck>();
        for (String areaCode : task.getArea()) {
            lstExchangeCheck.add(ExchangeCheck.newExchangeCheck(areaCode));
        }
        return lstExchangeCheck;
    }
}
