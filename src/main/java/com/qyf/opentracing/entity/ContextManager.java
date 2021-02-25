package com.qyf.opentracing.entity;

import java.util.UUID;

public class ContextManager {

    private static ThreadLocal<Trace> TRACE_CONTEXT = new ThreadLocal<>();

    private static ThreadLocal<Span> SPAN_CONTEXT = new ThreadLocal<>();

    public static Trace getOrCreate(){
        Trace trace = TRACE_CONTEXT.get();
        if (trace == null){
            trace = new Trace();
            trace.setTraceId(getId());
            trace.setTime(System.currentTimeMillis());
            TRACE_CONTEXT.set(trace);
        }
        return trace;
    }


    public static String getId(){
        return UUID.randomUUID().toString();
    }

    public static Span createSpan(String name){
        Trace trace = getOrCreate();
        return trace.createSpan(name);
    }

    public static void stopSpan(){
        TRACE_CONTEXT.remove();
    }

    public static void stopTrace(){
        TRACE_CONTEXT.remove();
    }
}
