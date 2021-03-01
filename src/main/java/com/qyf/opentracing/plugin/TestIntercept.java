package com.qyf.opentracing.plugin;

import com.qyf.opentracing.agent.Intercept;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;

public class TestIntercept implements Intercept{
    @Override
    public Object beforeMethod(Method method) {
        System.out.println("after");
        return null;
    }

    @Override
    public void afterMethod(Object object) {
        System.out.println("after");
    }

    @Override
    public void handleException(Exception e) {

    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("test2").or(ElementMatchers.named("callA")).or(ElementMatchers.named("test"));
    }
}
