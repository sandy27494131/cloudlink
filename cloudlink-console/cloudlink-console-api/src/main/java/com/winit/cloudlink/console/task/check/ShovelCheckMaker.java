package com.winit.cloudlink.console.task.check;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.winit.cloudlink.console.task.model.ShovelCheck;
import com.winit.cloudlink.console.utils.Constants;
import com.winit.cloudlink.console.utils.RabbitMgmtServiceHelper;
import com.winit.cloudlink.rabbitmq.mgmt.RabbitMgmtService;
import com.winit.cloudlink.rabbitmq.mgmt.model.Binding;
import com.winit.cloudlink.rabbitmq.mgmt.model.Queue;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelLink;
import com.winit.cloudlink.rabbitmq.mgmt.model.shovel.ShovelStatus;
import com.winit.cloudlink.storage.api.vo.AreaVo;

public class ShovelCheckMaker {

    /**
     * 构建shovel待检查数据
     * 
     * @param checkAreaCodes
     * @return
     */
    public static List<ShovelCheck> buildShovelCheck(List<String> checkAreaCodes) {
        List<AreaVo> allAreas = RabbitMgmtServiceHelper.getAllAreas();
        List<AreaVo> checkAreas = new ArrayList<AreaVo>();
        for (AreaVo vo : allAreas) {
            if (checkAreaCodes.contains(vo.getCode())) {
                checkAreas.add(vo);
            }
        }

        AreaVo srcArea = null;
        AreaVo destArea = null;
        List<ShovelCheck> checks = new ArrayList<ShovelCheck>();
        ShovelCheck shovelCheck = null;
        for (int i = 0; i < checkAreas.size(); i++) {
            srcArea = checkAreas.get(i);
            if (srcArea != null) {
                for (int j = 0; j < checkAreas.size(); j++) {
                    destArea = checkAreas.get(j);
                    if (null != destArea && !srcArea.getCode().equals(destArea.getCode())) {
                        shovelCheck = new ShovelCheck();
                        shovelCheck.setSrcArea(srcArea.getCode());
                        shovelCheck.setSrcQueueName(Constants.QUEUE_SHOVEL_TO_PREFIX + destArea.getCode());
                        shovelCheck.setDestArea(destArea.getCode());
                        shovelCheck.setShovelName(srcArea.getCode() + Constants.SHOVEL_CONN_STR + destArea.getCode());
                        shovelCheck.setDestExchageName(Constants.EXCHANGE_WINIT_RECEIVE);
                        shovelCheck.setSrcURI(srcArea.getMqWanAddr());
                        shovelCheck.setDestURI(destArea.getMqWanAddr());
                        checks.add(shovelCheck);
                    }
                }
            }
        }
        return checks;
    }

    /**
     * 检查shovel配置是否正确
     * 
     * @param areaCode 數據中心
     * @param checks 檢查對象
     * @param shovelLinks rabbitmq shovel配置
     * @param shovelStatus rabbitmq shovel狀態
     * @param removeNormal 是否删除非异常数据
     * @return
     */
    public static void checkShovels(String areaCode, List<ShovelCheck> checks, Collection<ShovelLink> shovelLinks,
                                       Collection<ShovelStatus> shovelStatus) {
        RabbitMgmtService service = RabbitMgmtServiceHelper.getServiceByArea(areaCode);
        Collection<Queue> queues = service.queues().allOnDefault();
        ShovelCheck check = null;
        int idx = checks.size() - 1;
        for (int i = idx; i >= 0; i--) {
            check = checks.get(i);
            if (check.getSrcArea().equals(areaCode)) {
                Queue queue = getQueue(queues, check.getSrcQueueName());
                if (null != queue) {
                    check.setSrcQueueExists(true);
                    check.setSrcQueueDurable(queue.isDurable());
                    check.setSrcQueueAutoDelete(queue.isAutoDelete());
                    Collection<Binding> bindings = service.queues()
                        .bindings(service.getVhost(), check.getSrcQueueName())
                        .get();
                    if (null != bindings && bindings.size() > 0) {
                        for (Binding binding : bindings) {
                            if (Constants.EXCHANGE_WINIT_SEND.equals(binding.getSource())
                                && (Constants.EXCHANGE_ROUTING_KEY_PREFIX + check.getDestArea()).equals(binding.getRoutingKey())) {
                                check.setSrcQueueBinding(true);
                                break;
                            }
                        }
                    }
                }
            }

            if (check.getDestArea().equals(areaCode)) {
                for (ShovelLink link : shovelLinks) {
                    if (check.getShovelName().equals(link.getName())) {
                        check.setShovelExists(true);
                        check.setOnConfirm(link.isOnConfirm());
                        check.setNeverAutoDelete(link.isNeverAutoDelete());

                        if (link.getValue().getSrcUri().equals(check.getSrcURI())) {
                            check.setSrcURIRight(true);
                        }

                        if (link.getValue().getDestUri().equals(check.getDestURI())) {
                            check.setDestURIRight(true);
                        }
                        break;
                    }
                }

                for (ShovelStatus status : shovelStatus) {
                    if (check.getShovelName().equals(status.getName()) && status.isRunning()) {
                        check.setRunning(true);
                        break;
                    }
                }
            }
        }
    }

    private static Queue getQueue(Collection<Queue> queues, String queueName) {
        if (null != queues && queues.size() > 0) {
            for (Queue queue : queues) {
                if (queue.getName().equals(queueName)) {
                    return queue;
                }
            }
        }
        return null;
    }
}
