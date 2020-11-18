package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装json对象
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public JsonResult(Object body, int code) {
        this.body = body;
        this.code = code;
    }

    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}


