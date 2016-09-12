package com.winit.cloudlink.console.controller.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winit.cloudlink.console.annotation.AllowAccessRole;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.controller.search.model.CmdSearch;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Queue;
import com.winit.cloudlink.storage.cassandra.entity.User;

/**
 * Created by xiangyu.liang on 2016/1/20.
 */
@RestController
@AllowAccessRole({ User.Role.SEARCH })
public class CmdSearchController {
    private static final Logger logger = LoggerFactory.getLogger(CmdSearchController.class);

    @ResponseBody
    @RequestMapping(value = "/api/search/cmd/{areaCodes}", method = RequestMethod.GET)
    public ResponseBean queryAllArea(@PathVariable String areaCodes,HttpServletRequest request) {
        List<CmdSearch> data = new ArrayList<CmdSearch>();
        try {
            if(StringUtils.isNotBlank(areaCodes)){
                String[] tmp=areaCodes.split(",");
                if(tmp.length==2){
                    String strAreaFrom=tmp[0];
                    String strAreaTo=tmp[1];
                    RabbitMgmtService serviceTo=RabbitMgmtServiceHelper.getServiceByArea(strAreaTo);
                    RabbitMgmtService serviceFrom=RabbitMgmtServiceHelper.getServiceByArea(strAreaFrom);
                    if(serviceTo!=null && serviceFrom!=null){
                        Collection<Queue> queues=serviceTo.queues().all();
                        for(Queue q:queues){
                            String queueName=q.getName();
                            tmp=queueName.split("\\.");
                            if(tmp.length!=5)continue;
                            //判断是否为接收方队列
                            if(!tmp[0].contains("CMD") && tmp[0].contains("CALLBACK"))continue;
                            String strAppid=queueName.substring(queueName.indexOf(".")+1);
                            String strReceiver=tmp[2];
                            String[] tmp2=tmp[0].split("_");
                            if(tmp2.length!=2)continue;
                            String strCmdName=tmp2[0];
                            boolean flag=true;
                            for(CmdSearch  cmdSearch:data){
                                if(strCmdName.equals(cmdSearch.getCmdName())){
                                    if( StringUtils.isNotBlank( cmdSearch.getReceiver()) && (!cmdSearch.getReceiver().equals(strReceiver))){
                                        continue;
                                    }
                                    cmdSearch.setReceiver(strReceiver);
                                    cmdSearch.getReceiverAppids().add(strAppid);
                                    flag=false;
                                }
                            }
                            if(flag){
                                CmdSearch cmdSearch=new CmdSearch();
                                cmdSearch.setCmdName(strCmdName);
                                cmdSearch.setReceiver(strReceiver);
                                cmdSearch.getReceiverAppids().add(strAppid);
                                data.add(cmdSearch);
                            }
                        }
                        queues=serviceFrom.queues().all();
                        for(Queue q:queues){
                            String queueName=q.getName();
                            tmp=queueName.split("\\.");
                            if(tmp.length!=5)continue;
                            //判断是否为发送方队列
                            if((!tmp[0].contains("CMD")) && (!tmp[0].contains("CALLBACK")))continue;
                            String strAppid=queueName.substring(queueName.indexOf(".")+1);
                            String strSender=tmp[2];
                            String[] tmp2=tmp[0].split("_");
                            if(tmp2.length!=3)continue;
                            String strCmdName=tmp2[0];
                            for(CmdSearch  cmdSearch:data){
                                if(strCmdName.equals(cmdSearch.getCmdName())){
                                    cmdSearch.setSender(strSender);
                                    cmdSearch.getSenderAppids().add(strAppid);
                                    cmdSearch.setCallback(true);
                                }
                            }
                        }
                    }

                }
                /*
                for(String areaCode:areaCodes.split(",")){
                    RabbitMgmtService service=RabbitMgmtServiceHelper.getServiceByArea(areaCode);
                    if(service==null)continue;
                    Collection<Queue> queues=service.queues().all();
                    for(Queue q:queues){
                        String queueName=q.getName();
                        String[] tmp=queueName.split("\\.");
                        if(tmp.length!=5)continue;
                        if(!tmp[0].contains("CMD"))continue;
                        String strAppid=queueName.substring(queueName.indexOf(".")+1);
                        String strSenderOrReceiver=tmp[2];
                        boolean isSender=false;
                        String[] tmp2=tmp[0].split("_");
                        String strCmdName=tmp2[0];
                        if(tmp[0].contains("CALLBACK")){
                            isSender=true;
                            if(tmp2.length<3)continue;;
                        }
                        if(tmp2.length<2)continue;
                        boolean flag=true;
                        for(CmdSearch  cmdSearch:data){
                            if(strCmdName.equals(cmdSearch.getCmdName())){
                                if(isSender){
                                    if(cmdSearch.getSender()!=null&& (!cmdSearch.getSender().equals(strSenderOrReceiver))){
                                        continue;
                                    }
                                    cmdSearch.setSender(strSenderOrReceiver);
                                    cmdSearch.getSenderAppids().add(strAppid);
                                }else {
                                    if(cmdSearch.getReceiver()!=null&& (!cmdSearch.getReceiver().equals(strSenderOrReceiver))){
                                        continue;
                                    }
                                    cmdSearch.setReceiver(strSenderOrReceiver);
                                    cmdSearch.getReceiverAppids().add(strAppid);
                                }
                                flag=false;
                            }
                        }
                        if(flag){
                            CmdSearch cmdSearch=new CmdSearch();
                            cmdSearch.setCmdName(strCmdName);
                            if(isSender){
                                cmdSearch.setSender(strSenderOrReceiver);
                                cmdSearch.getSenderAppids().add(strAppid);
                            }else {
                                cmdSearch.setReceiver(strSenderOrReceiver);
                                cmdSearch.getReceiverAppids().add(strAppid);
                            }
                            data.add(cmdSearch);
                        }
                    }
                }*/
            }

        } catch (Exception e) {
            logger.error("Query appid search failed", e);
        }
        return ResponseBean.buildSuccess(request, data);
    }
}
