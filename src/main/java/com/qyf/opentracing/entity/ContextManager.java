package com.qyf.opentracing.entity;

import java.util.UUID;

public class ContextManager {

    private static ThreadLocal<Trace> TRACE_CONTEXT = new ThreadLocal<>();

//    private static ThreadLocal<Span> SPAN_CONTEXT = new ThreadLocal<>();

    private static Trace trace;

    public static Trace getOrCreate(){
        trace = TRACE_CONTEXT.get();
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
        Span span = trace.createSpan(name);
        return span;
    }

    public static void stopSpan(){
//        SPAN_CONTEXT.remove();
    }

    public static void stopTrace(){
        trace = null;
        TRACE_CONTEXT.remove();
    }


    public static void plus(){
        trace.plus();
    }

    public static void cut(){
        trace.cut();
    }
}
