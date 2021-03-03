package com.qyf.opentracing.plugin;

import com.qyf.opentracing.plugin.api.Intercept;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;

public class TestIntercept implements Intercept {


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
