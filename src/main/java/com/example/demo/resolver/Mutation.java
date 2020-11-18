package com.example.demo.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.demo.service.ProcessService;
import com.example.demo.vo.BodyResult;
import com.example.demo.vo.GraphQLResult;
import com.example.demo.vo.SushiOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author songpeijiang
 * @since 2020/10/27
 */
@Component
public class Mutation implements GraphQLMutationResolver {

    private final ProcessService processService;

    @Autowired
    public Mutation(ProcessService processService) {
        this.processService = processService;
    }

    /**
     * 提交订单
     *
     * @param sushiName 寿司名称
     * @return 订单信息
     */
    public GraphQLResult order(String sushiName) {
        SushiOrderVo sushiOrder = processService.submitOrder(sushiName);
        return new GraphQLResult(new BodyResult(sushiOrder), 0, "Order submitted");
    }

    /**
     * 取消订单
     *
     * @param orderId 订单号
     * @return 响应信息
     */
    public GraphQLResult cancel(String orderId) {
        GraphQLResult graphQLResult = new GraphQLResult();
        if (processService.cancel(orderId)) {
            graphQLResult.setCode(0);
            graphQLResult.setMsg("Order cancelled");
        } else {
            graphQLResult.setCode(1);
            graphQLResult.setMsg("Order not found");
        }
        return graphQLResult;
    }

    /**
     * 暂停制作
     *
     * @param orderId 订单号
     * @return 响应信息
     */
    public GraphQLResult pause(String orderId) {
        GraphQLResult graphQLResult = new GraphQLResult();
        if (processService.pause(orderId)) {
            graphQLResult.setCode(0);
            graphQLResult.setMsg("Order paused");
        } else {
            graphQLResult.setCode(1);
            graphQLResult.setMsg("Order not found");
        }
        return graphQLResult;
    }

    /**
     * 恢复制作
     *
     * @param orderId 订单号
     * @return 响应信息
     */
    public GraphQLResult resume(String orderId) {
        processService.resume(orderId);
        return new GraphQLResult(0, "Order resumed");
    }

}
