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
    public static Map<Integer,Long> threadMap = new HashMap<>();
    public static Map<Integer, ProcessThread> orderthreadMap = new HashMap<>();
    public static Map<Runnable, SushiOrder> orderMap = new HashMap<>();
    public static List<SushiOrder> ordersList = new ArrayList<>();
    public static List<ProcessStatus> inprogressStatus = new ArrayList<>();
    public static List<ProcessStatus> pausedStatus = new ArrayList<>();
    public static List<ProcessStatus> finishedStatus = new ArrayList<>();
    public static List<ProcessStatus> cancelledStatus = new ArrayList<>();
    public static List<ProcessStatus> pendingStatus = new ArrayList<>();
}
