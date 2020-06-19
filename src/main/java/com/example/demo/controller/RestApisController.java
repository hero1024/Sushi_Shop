package com.example.demo.controller;

import com.example.demo.service.ProcessService;
import com.example.demo.utils.JsonUtil;
import com.example.demo.vo.JsonResult;
import com.example.demo.vo.ProcessStatus;
import com.example.demo.vo.SushiOrderVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST APIs
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
@RestController
@RequestMapping(value = "/api/orders")
public class RestApisController {

    private final ProcessService processService;

    @Autowired
    public RestApisController(ProcessService processService) {
        this.processService = processService;
    }

    /**
     * 提交订单
     *
     * @param sushi_name 寿司名称
     * @return 订单信息
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String order(String sushi_name) throws JsonProcessingException {
        SushiOrderVo sushiOrder = processService.submitOrder(sushi_name);
        JsonResult jsonResult = new JsonResult(sushiOrder, 0, "Order submitted");
        return JsonUtil.getStrByEntity(jsonResult);
    }

    /**
     * 取消订单
     *
     * @param orderId 订单号
     * @return 相应信息
     * @throws JsonProcessingException JsonProcessingException
     */
    @RequestMapping(value = "/cancel/{order_id}", method = RequestMethod.PUT)
    public String cancel(@PathVariable("order_id") String orderId) throws JsonProcessingException {
        JsonResult jsonResult = new JsonResult();
        if (processService.cancel(orderId)) {
            jsonResult.setCode(0);
            jsonResult.setMsg("Order cancelled");
        } else {
            jsonResult.setCode(1);
            jsonResult.setMsg("Order not found");
        }
        return JsonUtil.getStrByEntity(jsonResult);
    }

    /**
     * 暂停制作
     *
     * @param orderId 订单号
     * @return 响应信息
     * @throws JsonProcessingException JsonProcessingException
     */
    @RequestMapping(value = "/pause/{order_id}", method = RequestMethod.PUT)
    public String pause(@PathVariable("order_id") String orderId) throws JsonProcessingException {
        JsonResult jsonResult = new JsonResult();
        if (processService.pause(orderId)) {
            jsonResult.setCode(0);
            jsonResult.setMsg("Order paused");
        } else {
            jsonResult.setCode(1);
            jsonResult.setMsg("Order not found");
        }
        return JsonUtil.getStrByEntity(jsonResult);
    }

    /**
     * 恢复制作
     *
     * @param orderId 订单号
     * @return 响应信息
     * @throws JsonProcessingException JsonProcessingException
     */
    @RequestMapping(value = "/resume/{order_id}", method = RequestMethod.PUT)
    public String resume(@PathVariable("order_id") String orderId) throws JsonProcessingException {
        processService.resume(orderId);
        JsonResult jsonResult = new JsonResult(null, 0, "Order resumed");
        return JsonUtil.getStrByEntity(jsonResult);
    }

    /**
     * 查看订单状态
     *
     * @return 订单状态
     * @throws JsonProcessingException JsonProcessingException
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String status() throws JsonProcessingException {
        Map<String, List<ProcessStatus>> allStatus = processService.queryStatus();
        JsonResult jsonResult = new JsonResult(allStatus, 0, "");
        return JsonUtil.getStrByEntity(jsonResult);
    }

}
