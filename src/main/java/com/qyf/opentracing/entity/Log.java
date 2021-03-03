package com.qyf.opentracing.entity;

import lombok.Data;


@Data
public class Log {
    private String errMsg;

    private StackTraceElement[] stacks;

    public Log(String errMsg) {
        this.errMsg = errMsg;
    }
}
