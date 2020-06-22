package com.example.demo.thread;

import com.example.demo.entity.Sushi;
import com.example.demo.entity.SushiOrder;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.SushiOrderRepository;
import com.example.demo.repository.SushiRepository;
import com.example.demo.utils.DataSets;
import com.example.demo.vo.ProcessStatus;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;

/**
 * 寿司制作线程
 *
 * @author songpeijiang
 * @since 2020/6/11
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProcessThread extends Thread {

    private static SushiRepository sushiRepository;

    private static SushiOrderRepository sushiOrderRepository;

    private static StatusRepository statusRepository;

    private SushiOrder sushiOrder;

    /**
     * 线程运行时间
     */
    private volatile long timeSpent;
    /**
     * 线程恢复时间
     */
    private volatile long resumeTime;
    /**
     * 中间时间
     */
    private volatile long tempTime;
    /**
     * 制作时间
     */
    private long countTime;
    /**
     * 线程暂停标识
     */
    private volatile boolean suspendFlag;

    public ProcessThread(SushiOrder sushiOrder) {
        super(String.valueOf(sushiOrder.getId()));
        this.suspendFlag = false;
        this.sushiOrder = sushiOrder;
        this.timeSpent = 0;
        this.tempTime = 0;
        this.countTime = 0;
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            DataSets.threadMap.put(sushiOrder.getId(), Thread.currentThread().getId());
            Sushi sushi = sushiRepository.getOne(sushiOrder.getSushiId());
            int timeToMake = sushi.getTimeToMake();
            System.out.println(sushiOrder.getId() + "开始制作，预计用时：" + timeToMake);
            ///状态改为处理中
            sushiOrder.setStatusId(2);
            sushiOrderRepository.saveAndFlush(sushiOrder);
            resumeTime = start;
            while (!isInterrupted() && (countTime + tempTime < timeToMake)) {
                synchronized (this) {
                    while (suspendFlag) {
                        ///状态改为已暂停
                        sushiOrder.setStatusId(3);
                        sushiOrderRepository.saveAndFlush(sushiOrder);
                        tempTime = (System.currentTimeMillis() - resumeTime) / 1000;
                        timeSpent = (System.currentTimeMillis() - start) / 1000;
                        System.out.println(sushiOrder.getId() + "暂停中，预计用时：" + (timeToMake) + "，已用时：" + timeSpent);
                        wait();
                    }
                }
                //制作中
                tempTime = (System.currentTimeMillis() - resumeTime) / 1000;
                timeSpent = (System.currentTimeMillis() - start) / 1000;
            }
            timeSpent = (System.currentTimeMillis() - start) / 1000;
            tempTime = 0;
            if (!isInterrupted()) {
                //状态改为已完成
                sushiOrder.setStatusId(4);
                sushiOrderRepository.saveAndFlush(sushiOrder);
                ProcessStatus processStatus = new ProcessStatus(sushiOrder.getId(), timeSpent);
                DataSets.finishedStatus.add(processStatus);
                System.out.println(sushiOrder.getId() + "已完成，预计用时：" + (timeToMake) + "，实际用时：" + timeSpent);
            }
        } catch (Exception e) {
            interrupt();
            e.printStackTrace();
        }

    }

    /**
     * 暂停
     */
    public void isSuspend() {
        suspendFlag = true;
        countTime += tempTime;
        tempTime = 0;
    }

    /**
     * 恢复
     */
    public synchronized void isResume() {
        suspendFlag = false;
        //状态改为处理中
        sushiOrder.setStatusId(2);
        sushiOrderRepository.saveAndFlush(sushiOrder);
        System.out.println(sushiOrder.getId() + "恢复中");
        resumeTime = System.currentTimeMillis();
        notify();
    }

    public boolean isSuspendFlag() {
        return suspendFlag;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setSushiOrder(SushiOrder sushiOrder) {
        this.sushiOrder = sushiOrder;
    }

    public SushiOrder getSushiOrder() {
        return sushiOrder;
    }

    @Resource
    public void setSushiRepository(SushiRepository sushiRepository) {
        ProcessThread.sushiRepository = sushiRepository;
    }

    @Resource
    public void setSushiOrderRepository(SushiOrderRepository sushiOrderRepository) {
        ProcessThread.sushiOrderRepository = sushiOrderRepository;
    }

    @Resource
    public void setStatusRepository(StatusRepository statusRepository) {
        ProcessThread.statusRepository = statusRepository;
    }

}
