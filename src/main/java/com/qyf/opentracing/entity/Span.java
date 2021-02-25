package com.qyf.opentracing.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "trace")
public class Span {
    @Id
    private String spanId;

    private String traceId;

    private String methodName;

    private Long time;
}
