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
public class GraphQLResult {

    /**
     * 返回信息
     */
    private BodyResult body;
    /**
     * 状态码0表示成功，1表示失败
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

}
