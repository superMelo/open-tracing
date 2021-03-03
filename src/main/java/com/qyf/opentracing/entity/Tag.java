package com.qyf.opentracing.entity;

import lombok.Data;

@Data
public class Tag {
    private String key;

    private String value;

    public Tag(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
