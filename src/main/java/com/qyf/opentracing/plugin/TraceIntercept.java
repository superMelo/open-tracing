package com.qyf.opentracing.plugin;

import com.qyf.opentracing.agent.Intercept;
import com.qyf.opentracing.entity.ContextManager;
import com.qyf.opentracing.entity.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public  class TraceIntercept implements Intercept {

    public static List<Trace> list = new LinkedList<>();

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object beforeMethod(Method method) {
        Trace trace = ContextManager.getOrCreate();
        trace.plus();
        trace.createSpan(method.getDeclaringClass().getName()+"."+method.getName());
        return trace;
    }

    @Override
    public void afterMethod(Object o) {
        Trace trace = (Trace)o;
        trace.cut();
        if (trace.getNum() == 0) {
            ContextManager.stopTrace();
            Long startTime = trace.getTime();
            long endTime = System.currentTimeMillis();
            trace.setTime(endTime - startTime);
            list.add(trace);
        }
    }

    @Override
    public void handleException(Exception e) {
        log.error(e.getMessage());
    }
}
