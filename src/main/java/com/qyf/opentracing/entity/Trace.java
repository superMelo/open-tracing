package com.qyf.opentracing.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@Document(indexName = "trace")
public class Trace {

    @Id
    private String traceId;

    private int num;

    private List<Span> spans = new LinkedList<>();

    private String spanStr;

    private Long time;


    public void plus(){
        num = num+1;
    }

    public void cut(){
        num = num-1;
    }



    public Span createSpan(String name){
        Span span = new Span();
        span.setSpanId(UUID.randomUUID().toString());
        span.setMethodName(name);
        span.setTraceId(traceId);
        spans.add(span);
        if (spanStr == null){
            spanStr = span.getMethodName();
        }else {
            spanStr += "->" + span.getMethodName();
        }
        return span;
    }
}
