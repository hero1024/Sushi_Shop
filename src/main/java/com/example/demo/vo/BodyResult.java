package com.example.demo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author songpeijiang
 * @since 2020/10/27
 */
@Data
public class BodyResult {
    private SushiOrderVo order;
    private StatusResult status;
    private List<SushiVo> sushiList;

    public BodyResult(SushiOrderVo order) {
        this.order = order;
    }

    public BodyResult(StatusResult status) {
        this.status = status;
    }

    public BodyResult(List<SushiVo> sushiList) {
        this.sushiList = sushiList;
    }

}
