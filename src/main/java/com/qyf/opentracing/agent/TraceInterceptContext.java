package com.qyf.opentracing.agent;


import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;

import java.util.concurrent.Callable;

public class TraceInterceptContext {

    private static Intercept intercept;

    @RuntimeType
    public static Object intercept(@Origin Method method,
                            @SuperCall Callable<?> callable) throws Exception {
        Object o = intercept.beforeMethod(method);
        try {
            // 原有函数执行
            return callable.call();
        } catch (Exception e) {
           intercept.handleException(e);
           return null;
        } finally {
            intercept.afterMethod(o);
        }
    }


    public void load(Intercept intercept){
        this.intercept = intercept;
    }

}
