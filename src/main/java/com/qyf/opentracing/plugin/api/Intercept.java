package com.qyf.opentracing.plugin.api;

import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.reflect.Method;

public interface Intercept {

    void beforeMethod(Method method, Object object, Object[] allArguments);

    void afterMethod(Method method);

    void handleException(Method method, Exception e);

    ElementMatcher<MethodDescription> getMethodsMatcher();


    ElementMatcher.Junction<NamedElement> getConstructorsInterceptPoints();
}
