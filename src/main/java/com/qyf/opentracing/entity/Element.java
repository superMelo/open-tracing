package com.qyf.opentracing.entity;


import lombok.Data;

@Data
public class Element {

    private Integer lineNum;

    private String methodName;

    private String className;
}
