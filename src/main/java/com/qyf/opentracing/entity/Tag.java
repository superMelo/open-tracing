package com.qyf.opentracing.entity;

import lombok.Data;

@Data
//保存基本信息
public class Tag {
    private String key;

    private String value;

    public Tag(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
