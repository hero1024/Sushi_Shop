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

    public GraphQLResult order(String sushiName) {
        SushiOrderVo sushiOrder = processService.submitOrder(sushiName);
        return new GraphQLResult(new BodyResult(sushiOrder,null), 0, "Order submitted");
    }

}
