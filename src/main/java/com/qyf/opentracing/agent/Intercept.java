package com.qyf.opentracing.agent;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.reflect.Method;

public interface Intercept {

    void beforeMethod(Method method);

    void afterMethod();

    void handleException(Exception e);

    ElementMatcher<MethodDescription> getMethodsMatcher();
}
