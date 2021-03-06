package com.example.demo.thread;

import com.example.demo.utils.LogUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 *
 * @author songpeijiang
 * @since 2020/6/17
 */
public class ProcessThreadPoolExecutor implements Executor {

    /**
     * 线程池初始限定大小
     */
    private int threadNum;
    /**
     * 已经工作的线程数目
     */
    private int workThreadNum;
    /**
     * 任务队列
     */
    public static BlockingQueue<Runnable> taskQueue;
    /**
     * 暂停待恢复队列
     */
    public static BlockingQueue<Runnable> suspendQueue = new LinkedBlockingQueue<>();
    /**
     * 存放线程的集合
     */
    private static List<ChefThread> threads = new Vector<>(3);
    /**
     * 重入锁
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    public ProcessThreadPoolExecutor(int initPoolNum) {
        threadNum = initPoolNum;
        threads = new Vector<>(initPoolNum);
        taskQueue = new LinkedBlockingQueue<>();
        threadNum = initPoolNum;
        workThreadNum = 0;
    }

    @Override
    public void execute(@NotNull Runnable command) {
        mainLock.lock();
        try {
            //线程池未满，每加入一个任务则开启一个线程
            if (workThreadNum < threadNum) {
                ChefThread chefThead = new ChefThread(command);
                chefThead.start();
                threads.add(chefThead);
                workThreadNum++;
            }
            //线程池已满，放入任务队列，等待有空闲线程时执行
            else {
                //队列已满，无法添加时，拒绝任务
                if (!taskQueue.offer(command)) {
                    rejectTask();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mainLock.unlock();
        }
    }

    private void rejectTask() {
        LogUtil.info("任务队列已满，无法继续添加！");
    }

    /**
     * 厨师工作线程
     */
    static class ChefThread extends Thread {

        /**
         * 寿司制作线程任务
         */
        private Runnable task;

        public ChefThread(Runnable command) {
            this.task = command;
        }

        @Override
        public void run() {
            //该线程一直启动着，不断从任务队列取出任务执行
            while (true) {
                try {
                    //如果初始化任务不为空，则执行初始化任务
                    if (task != null) {
                        ProcessThread processThread = (ProcessThread) task;
                        //设置为守护线程，厨师不工作了寿司也不制作了
                        processThread.setDaemon(true);
                        //开辟寿司制作线程
                        processThread.start();
                        //等待寿司制作线程暂停或结束
                        while (!processThread.isSuspendFlag() && processThread.isAlive()) {

                        }
                        task = null;
                    } else {
                        //优先去暂停待恢复队列取任务并执行
                        Runnable suspendTask = suspendQueue.poll();
                        if (suspendTask != null) {
                            ProcessThread processThread = (ProcessThread) suspendTask;
                            //设置为守护线程，厨师不工作了寿司也不制作了
                            processThread.setDaemon(true);
                            //唤醒被暂停的线程
                            processThread.isResume();
                            while (!processThread.isSuspendFlag() && processThread.isAlive()) {

                            }
                        } else {
                            //否则去任务队列取任务并执行
                            Runnable queueTask = taskQueue.poll();
                            if (queueTask != null) {
                                ProcessThread processThread = (ProcessThread) queueTask;
                                //设置为守护线程，厨师不工作了寿司也不制作了
                                processThread.setDaemon(true);
                                processThread.start();
                                while (!processThread.isSuspendFlag() && processThread.isAlive()) {

                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


