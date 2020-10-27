package com.example.demo.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.demo.service.ProcessService;
import com.example.demo.vo.BodyResult;
import com.example.demo.vo.GraphQLResult;
import com.example.demo.vo.ProcessStatus;
import com.example.demo.vo.StatusResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author songpeijiang
 * @since 2020/10/27
 */
@Component
public class Query implements GraphQLQueryResolver {

    private final ProcessService processService;
    @Autowired
    public Query(ProcessService processService) {
        this.processService = processService;
    }

    public GraphQLResult status() {
        Map<String, List<ProcessStatus>> allStatus = processService.queryStatus();
        StatusResult statusResult = new StatusResult(
        allStatus.get("in-progress"),
        allStatus.get("pending"),
        allStatus.get("paused"),
        allStatus.get("finished"),
        allStatus.get("cancelled"));
        return new GraphQLResult(new BodyResult(null,statusResult), 0, "");
    }
}
