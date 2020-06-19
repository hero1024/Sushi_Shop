package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * sushi_order pojo
 * @author songpeijiang
 * @since 2020/6/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SushiOrderVo {
    private int id;
    private int statusId;
    private int sushiId;
    private Timestamp createdAt;
}
