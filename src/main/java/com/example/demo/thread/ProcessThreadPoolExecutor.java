package com.example.demo.thread;

import java.util.ArrayList;
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
     * 暂停队列
     */
    public static BlockingQueue<Runnable> suspendQueue = new LinkedBlockingQueue<>();
    /**
     * 存放线程的集合
     */
    private final ArrayList<ChefThread> threads;

    private final ReentrantLock mainLock = new ReentrantLock();

    public ProcessThreadPoolExecutor(int initPoolNum) {
        threadNum = initPoolNum;
        threads = new ArrayList<>(initPoolNum);
        taskQueue = new LinkedBlockingQueue<>();
        threadNum = initPoolNum;
        workThreadNum = 0;
    }

    @Override
    public void execute(Runnable command) {
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
        System.out.println("任务队列已满，无法继续添加！");
    }


    static class ChefThread extends Thread {

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
                        //开辟寿司制作线程
                        processThread.start();
                        //等待寿司制作线程暂停或结束
                        while (!processThread.getSuspendFlag() && processThread.isAlive()) {
                            Thread.sleep(0);
                        }
                        task = null;
                    } else {
                        //优先去暂停队列取任务并执行
                        Runnable suspendTask = suspendQueue.poll();
                        if (suspendTask != null) {
                            ProcessThread processThread = (ProcessThread) suspendTask;
                            //唤醒被暂停的线程
                            processThread.isResume();
                            while (!processThread.getSuspendFlag() && processThread.isAlive()) {
                                Thread.sleep(0);
                            }
                        } else {
                            //否则去任务队列取任务并执行
                            Runnable queueTask = taskQueue.poll();
                            if (queueTask != null) {
                                ProcessThread processThread = (ProcessThread) queueTask;
                                processThread.start();
                                while (!processThread.getSuspendFlag() && processThread.isAlive()) {
                                    Thread.sleep(0);
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


