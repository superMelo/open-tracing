package com.qyf.opentracing.entity;

import lombok.Data;

import java.util.List;


@Data
//日志记录，栈信息
public class Log {
    private String msg;

    private List<Element> stacks;

    public Log(String msg) {
        this.msg = msg;
    }
}
