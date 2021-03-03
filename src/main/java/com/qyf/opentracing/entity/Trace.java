package com.qyf.opentracing.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.*;

@Data
@Document(indexName = "trace")
public class Trace {

    @Id
    private String traceId;

    private int num;

    private Map<String, Span> spans = new HashMap<>();

    private String spanStr;

    private Long time;


    public void plus() {
        num = num + 1;
    }

    public void cut() {
        num = num - 1;
    }


    public Span createSpan(String name) {
        if (!spans.containsKey(name)) {
            Span span = new Span();
            span.setSpanId(UUID.randomUUID().toString());
            span.setMethodName(name);
            span.setChildFrom(traceId);
            spans.put(name, span);
            if (spanStr == null) {
                spanStr = span.getMethodName();
            } else {
                spanStr += "->" + span.getMethodName();
            }
            return span;
        }
        return spans.get(name);
    }

    public Span activeSpan(String name) {
        return spans.get(name);
    }
}
