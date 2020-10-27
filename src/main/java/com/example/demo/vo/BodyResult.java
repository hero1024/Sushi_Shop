package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author songpeijiang
 * @since 2020/10/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyResult {

    private SushiOrderVo order;
    private StatusResult status;
}
