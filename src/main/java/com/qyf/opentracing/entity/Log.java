package com.qyf.opentracing.entity;

import lombok.Data;


@Data
//日志记录，栈信息
public class Log {
    private String errMsg;

    private StackTraceElement[] stacks;

    public Log(String errMsg) {
        this.errMsg = errMsg;
    }
}
