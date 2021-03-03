package com.qyf.opentracing.plugin;

import com.qyf.opentracing.entity.ContextManager;
import com.qyf.opentracing.entity.Log;
import com.qyf.opentracing.entity.Span;
import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.plugin.api.Intercept;
import net.bytebuddy.description.NamedElement;
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
    public void afterMethod(Method method) {
        Trace trace = ContextManager.getOrCreate();
        ContextManager.cut();
//        ContextManager.stopSpan();
        if (trace.getNum() == 0) {
            ContextManager.stopTrace();
            trace.setEndTime(System.currentTimeMillis());
            list.add(trace);
        }
    }

    @Override
    public void handleException(Method method, Exception e) {
        Trace trace = ContextManager.getOrCreate();
        Span span = trace.activeSpan(method.getName());
        Log log = new Log(e.getMessage());
        log.setStacks(e.getStackTrace());
        span.setLog(log);
    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("test1").or(ElementMatchers.named("callB")).or(ElementMatchers.named("test"));
    }

    @Override
    public ElementMatcher.Junction<NamedElement> getConstructorsInterceptPoints() {
        return ElementMatchers.nameStartsWith("com.qyf.opentracing.controller.");
    }
}
