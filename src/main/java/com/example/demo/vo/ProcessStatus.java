package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单处理状态
 * @author songpeijiang
 * @since 2020/6/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStatus {
    private int orderId;
    private long timeSpend;

}
