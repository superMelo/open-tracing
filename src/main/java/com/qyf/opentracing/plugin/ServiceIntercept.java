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

import java.lang.reflect.Method;

public class ServiceIntercept implements Intercept {


    @Override
    public void beforeMethod(Method method, Object object, Object[] allArguments) {
        ContextManager.getOrCreate();
        ContextManager.createSpan(method.getDeclaringClass().getName()+"."+method.getName());
    }

    @Override
    public void afterMethod(Method method) {
        ContextManager.stopSpan();
        ContextManager.stopTrace();
    }

    @Override
    public void handleException(Method method, Exception e) {
        ContextManager.setLog(method, e);
    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("test2").or(ElementMatchers.named("callA")).or(ElementMatchers.named("say"));
    }

    @Override
    public ElementMatcher.Junction<NamedElement> getConstructorsInterceptPoints() {
        return ElementMatchers.nameStartsWith("com.qyf.opentracing.service.");
    }
}
