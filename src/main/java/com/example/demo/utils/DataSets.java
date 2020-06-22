package com.example.demo.utils;

import com.example.demo.entity.SushiOrder;
import com.example.demo.thread.ProcessThread;
import com.example.demo.vo.ProcessStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author songpeijiang
 * @since 2020/6/12
 */
public class DataSets {
    /**
     * 订单线程id
     */
    public static Map<Integer, Long> threadMap = new HashMap<>();
    /**
     * 订单线程
     */
    public static Map<Integer, ProcessThread> orderthreadMap = new HashMap<>();
    public static Map<Runnable, SushiOrder> orderMap = new HashMap<>();
    /**
     * 轮询到的订单
     */
    public static List<SushiOrder> ordersList = new ArrayList<>();
    /**
     * 处理状态的订单
     */
    public static List<ProcessStatus> inprogressStatus = new ArrayList<>();
    /**
     * 暂停状态的订单
     */
    public static List<ProcessStatus> pausedStatus = new ArrayList<>();
    /**
     * 完成状态的订单
     */
    public static List<ProcessStatus> finishedStatus = new ArrayList<>();
    /**
     * 取消状态的订单
     */
    public static List<ProcessStatus> cancelledStatus = new ArrayList<>();
    /**
     * 待处理的订单
     */
    public static List<ProcessStatus> pendingStatus = new ArrayList<>();
}
