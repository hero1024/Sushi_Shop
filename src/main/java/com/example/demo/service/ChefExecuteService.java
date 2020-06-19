package com.example.demo.service;

import com.example.demo.entity.SushiOrder;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.SushiOrderRepository;
import com.example.demo.repository.SushiRepository;
import com.example.demo.thread.ProcessThread;
import com.example.demo.thread.ProcessThreadPoolExecutor;
import com.example.demo.utils.DataSets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 厨师制作寿司Service
 *
 * @author songpeijiang
 * @since 2020/6/11
 */
@Service
public class ChefExecuteService {

    private final ProcessThreadPoolExecutor executorService = new ProcessThreadPoolExecutor(3);

    private final SushiRepository sushiRepository;
    private final SushiOrderRepository sushiOrderRepository;
    private final StatusRepository statusRepository;
    private final AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Autowired
    public ChefExecuteService(SushiRepository sushiRepository, SushiOrderRepository sushiOrderRepository, StatusRepository statusRepository, AutowireCapableBeanFactory autowireCapableBeanFactory) {
        this.sushiRepository = sushiRepository;
        this.sushiOrderRepository = sushiOrderRepository;
        this.statusRepository = statusRepository;
        this.autowireCapableBeanFactory = autowireCapableBeanFactory;
    }


    public void worker() {
        try {
            //创建单线程池
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            //循环执行轮询业务体
            while (!Thread.interrupted()) {
                executorService.execute(this::processExecute);
                //轮询等待时间
                Thread.sleep(30 * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processExecute() {
        //查询待处理数据
        List<SushiOrder> sushiOrders = sushiOrderRepository.findByStatusId(1);
        if (!sushiOrders.isEmpty()) {
            //循环处理
            for (SushiOrder sushiOrder : sushiOrders) {
                //添加待处理
                if(!DataSets.ordersList.contains(sushiOrder)) {
                    ProcessThread processThread = new ProcessThread(sushiOrder);
                    DataSets.orderthreadMap.put(sushiOrder.getId(), processThread);
                    DataSets.ordersList.add(sushiOrder);
                    autowireCapableBeanFactory.autowireBean(processThread);
                    executorService.execute(processThread);
                }
            }
        }
    }

    /**
     * 通过线程组获得线程
     *
     * @param threadId 线程id
     * @return 线程
     */
    public Thread getThreadById(long threadId) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group != null) {
            Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for (int i = 0; i < count; i++) {
                if (threadId == threads[i].getId()) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return null;
    }

    /**
     * 通过线程名获得线程
     *
     * @param threadName 线程名
     * @return 线程
     */
    public Thread getThreadByName(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) {
                return t;
            }
        }
        return null;
    }

}

