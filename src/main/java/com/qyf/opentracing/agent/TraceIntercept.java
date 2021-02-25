package com.qyf.opentracing.agent;

import com.qyf.opentracing.entity.ContextManager;
import com.qyf.opentracing.entity.Span;
import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.service.TraceService;
import com.qyf.opentracing.service.impl.TraceServiceImpl;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class TraceIntercept {

    public static List<Trace> list = new LinkedList<>();

    private static Logger log = LoggerFactory.getLogger(TraceIntercept.class);

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        Trace trace = ContextManager.getOrCreate();
        trace.plus();
        trace.createSpan(method.getDeclaringClass().getName()+"."+method.getName());
        try {
            // 原有函数执行
            return callable.call();
        } catch (Exception e) {
            log.error("error:{}", e.getMessage());
            return null;
        } finally {
            trace.cut();
            if (trace.getNum() == 0) {
                ContextManager.stopTrace();
                Long startTime = trace.getTime();
                long endTime = System.currentTimeMillis();
                trace.setTime(endTime - startTime);
                list.add(trace);
            }
        }
    }
}
