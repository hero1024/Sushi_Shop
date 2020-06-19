package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sushi pojo
 * @author songpeijiang
 * @since 2020/6/10
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SushiVo {
    private int id;
    private String name;
    private int timeToMake;
}
