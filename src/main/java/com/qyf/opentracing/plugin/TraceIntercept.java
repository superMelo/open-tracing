package com.qyf.opentracing.plugin;

import com.qyf.opentracing.agent.Intercept;
import com.qyf.opentracing.entity.ContextManager;
import com.qyf.opentracing.entity.Trace;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public  class TraceIntercept implements Intercept {

    public static List<Trace> list = new LinkedList<>();

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeMethod(Method method) {
        ContextManager.getOrCreate();
        ContextManager.plus();
        ContextManager.createSpan(method.getDeclaringClass().getName()+"."+method.getName());
    }

    @Override
    public void afterMethod() {
        Trace trace = ContextManager.getOrCreate();
        trace.cut();
        ContextManager.stopSpan();
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

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("test1").or(ElementMatchers.named("callB")).or(ElementMatchers.named("test"));
    }
}
