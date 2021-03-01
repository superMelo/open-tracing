package com.qyf.opentracing.agent;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.reflect.Method;

public interface Intercept {

    Object beforeMethod(Method method);

    void afterMethod(Object object);

    void handleException(Exception e);

    ElementMatcher<MethodDescription> getMethodsMatcher();
}
