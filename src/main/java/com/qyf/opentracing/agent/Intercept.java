package com.qyf.opentracing.agent;

import java.lang.reflect.Method;

public interface Intercept {

    Object beforeMethod(Method method);

    void afterMethod(Object object);

    void handleException(Exception e);
}
