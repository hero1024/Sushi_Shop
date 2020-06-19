package com.example.demo.service;

import com.example.demo.entity.Sushi;
import com.example.demo.entity.SushiOrder;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.SushiOrderRepository;
import com.example.demo.repository.SushiRepository;
import com.example.demo.thread.ProcessThread;
import com.example.demo.thread.ProcessThreadPoolExecutor;
import com.example.demo.utils.DataSets;
import com.example.demo.vo.ProcessStatus;
import com.example.demo.vo.SushiOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单处理Service
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
@Service
public class ProcessService {

    private final SushiRepository sushiRepository;
    private final SushiOrderRepository sushiOrderRepository;
    private final StatusRepository statusRepository;
    private final BeanMapperService beanMapperService;
    private final ChefExecuteService chefExecuteService;

    @Autowired
    public ProcessService(SushiRepository sushiRepository, SushiOrderRepository sushiOrderRepository, StatusRepository statusRepository, BeanMapperService beanMapperService, ChefExecuteService chefExecuteService) {
        this.sushiRepository = sushiRepository;
        this.sushiOrderRepository = sushiOrderRepository;
        this.statusRepository = statusRepository;
        this.beanMapperService = beanMapperService;
        this.chefExecuteService = chefExecuteService;
    }

    /**
     * 提交订单
     *
     * @param sushiName 寿司名称
     * @return 订单信息
     */
    public SushiOrderVo submitOrder(String sushiName) {
        Sushi sushi = sushiRepository.findByName(sushiName);
        SushiOrder sushiOrder = new SushiOrder();
        sushiOrder.setStatusId(1);
        sushiOrder.setSushiId(sushi.getId());
        return beanMapperService.mapper(sushiOrderRepository.saveAndFlush(sushiOrder), SushiOrderVo.class);
    }

    /**
     * 取消订单
     *
     * @param orderId 订单号
     */
    public boolean cancel(String orderId) {

        ProcessThread orderThread = (ProcessThread) chefExecuteService.getThreadByName(orderId);
        if (orderThread == null) {
            ProcessThread processThread = DataSets.orderthreadMap.get(Integer.valueOf(orderId));
            if (ProcessThreadPoolExecutor.taskQueue.contains(processThread)) {
                ProcessThreadPoolExecutor.taskQueue.remove(processThread);
                SushiOrder sushiOrder = sushiOrderRepository.findById(Integer.valueOf(orderId)).orElse(null);
                //状态改为已取消
                if (sushiOrder != null) {
                    sushiOrder.setStatusId(5);
                    sushiOrderRepository.saveAndFlush(sushiOrder);
                }
                ProcessStatus processStatus = new ProcessStatus();
                processStatus.setTimeSpend(0);
                processStatus.setOrderId(Integer.parseInt(orderId));
                DataSets.cancelledStatus.add(processStatus);
                return true;
            }
            return false;
        } else {
            ProcessStatus processStatus = new ProcessStatus();
            processStatus.setTimeSpend(orderThread.getTimeSpent());
            System.out.println(orderThread.getSushiOrder().getId() + "取消中，已用时：" + orderThread.getTimeSpent());
            orderThread.interrupt();
            SushiOrder sushiOrder = sushiOrderRepository.findById(Integer.valueOf(orderId)).orElse(null);
            //状态改为已取消
            if (sushiOrder != null) {
                sushiOrder.setStatusId(5);
                processStatus.setOrderId(sushiOrderRepository.saveAndFlush(sushiOrder).getId());
            }
            DataSets.cancelledStatus.add(processStatus);
            if (ProcessThreadPoolExecutor.suspendQueue.contains(orderThread)) {
                ProcessThreadPoolExecutor.suspendQueue.remove(orderThread);
                return true;
            }
            return true;
        }
    }

    /**
     * 暂停
     *
     * @param orderId 订单号
     */
    public boolean pause(String orderId) {

        ProcessThread orderThread = (ProcessThread) chefExecuteService.getThreadByName(orderId);
        if (orderThread == null) {
            return false;
        } else {
            orderThread.isSuspend();
            return true;
        }
    }

    /**
     * 恢复
     *
     * @param orderId 订单号
     */
    public boolean resume(String orderId) {

        ProcessThread orderThread = (ProcessThread) chefExecuteService.getThreadByName(orderId);
        if (orderThread == null) {
            return false;
        } else {
            ProcessThreadPoolExecutor.suspendQueue.offer(orderThread);
            return true;
        }
    }

    public Map<String, List<ProcessStatus>> queryStatus() {
        List<SushiOrder> sushiOrderList = sushiOrderRepository.findAll();
        DataSets.inprogressStatus.clear();
        DataSets.pausedStatus.clear();
        DataSets.pendingStatus.clear();
        for (SushiOrder sushiOrder : sushiOrderList) {

            if (sushiOrder.getStatusId() == 1) {
                ProcessStatus processStatus = new ProcessStatus(sushiOrder.getId(), 0);
                DataSets.pendingStatus.add(processStatus);
            }
            ProcessThread orderThread = (ProcessThread) chefExecuteService.getThreadByName(String.valueOf(sushiOrder.getId()));
            if (orderThread != null) {
                ProcessStatus processStatus = new ProcessStatus(sushiOrder.getId(), orderThread.getTimeSpent());
                if (sushiOrder.getStatusId() == 2) {
                    DataSets.inprogressStatus.add(processStatus);
                } else if (sushiOrder.getStatusId() == 3) {
                    DataSets.pausedStatus.add(processStatus);
                }
            }
        }
        Map<String, List<ProcessStatus>> allStatus = new HashMap<>();
        allStatus.put("in-progress", DataSets.inprogressStatus);
        allStatus.put("pending", DataSets.pendingStatus);
        allStatus.put("paused", DataSets.pausedStatus);
        allStatus.put("finished", DataSets.finishedStatus);
        allStatus.put("cancelled", DataSets.cancelledStatus);
        return allStatus;
    }
}
