package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
