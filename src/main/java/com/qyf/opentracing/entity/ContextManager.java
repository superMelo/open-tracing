package com.qyf.opentracing.entity;

import com.qyf.opentracing.plugin.DispatchIntercept;

import java.util.UUID;

public class ContextManager {

    private static ThreadLocal<Trace> TRACE_CONTEXT = new ThreadLocal<>();

//    private static ThreadLocal<Span> SPAN_CONTEXT = new ThreadLocal<>();

    private static Trace trace;

    public static Trace getOrCreate() {
        trace = TRACE_CONTEXT.get();
        if (trace == null) {
            trace = new Trace();
            trace.setTraceId(getId());
            trace.setStartTime(System.currentTimeMillis());
            TRACE_CONTEXT.set(trace);
        }
        return trace;
    }


    public static String getId() {
        return UUID.randomUUID().toString();
    }

    public static Span createSpan(String name) {
        Span span = trace.createSpan(name);
        trace.plus();
        return span;
    }

    public static void stopSpan() {
        if (trace != null){
            trace.cut();
        }
    }


    public static void stopTrace() {
        if (trace != null){
            if (trace.getNum() == 0) {
                trace.setEndTime(System.currentTimeMillis());
                DispatchIntercept.list.add(trace);
                trace = null;
                TRACE_CONTEXT.remove();
            }
        }

    }
}
