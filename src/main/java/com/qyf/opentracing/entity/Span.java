package com.qyf.opentracing.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Document(indexName = "open-trace")
public class Span {
    @Id
    private String spanId;

    private String childFrom;

    private String methodName;

    private Long time;

    private List<Tag> tag;

    private Log log;

    private int num;
}
