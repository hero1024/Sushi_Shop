package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装json对象
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {

    /**
     * 返回信息
     */
    private Object body;

    /**
     * 状态码0表示成功，1表示失败
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

}


