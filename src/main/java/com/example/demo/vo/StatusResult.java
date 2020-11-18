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
public class StatusResult {
    private List<ProcessStatus> inProgress;
    private List<ProcessStatus> pending;
    private List<ProcessStatus> paused;
    private List<ProcessStatus> finished;
    private List<ProcessStatus> cancelled;
}
