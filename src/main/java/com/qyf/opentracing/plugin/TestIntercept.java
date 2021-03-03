package com.qyf.opentracing.plugin;

import com.qyf.opentracing.agent.Intercept;
import com.qyf.opentracing.entity.ContextManager;
import com.qyf.opentracing.entity.Log;
import com.qyf.opentracing.entity.Span;
import com.qyf.opentracing.entity.Trace;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestIntercept implements Intercept{


    @Override
    public void beforeMethod(Method method) {
        System.out.println(method.getName() + "-before");
    }

    @Override
    public void afterMethod(Method method) {
        System.out.println(method.getName() + "-after");
    }

    @Override
    public void handleException(Method method, Exception e) {
        System.out.println(e.getMessage());
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
