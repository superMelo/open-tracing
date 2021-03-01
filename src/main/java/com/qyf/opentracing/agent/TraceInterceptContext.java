package com.qyf.opentracing.agent;


import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

import java.util.concurrent.Callable;

public class TraceInterceptContext {

    private  Intercept intercept;


    public TraceInterceptContext(Intercept intercept) {
        this.intercept = intercept;
    }

    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Throwable {
        Object o = intercept.beforeMethod(method);
        try {
            // 原有函数执行
            return zuper.call();
        } catch (Exception e) {
           intercept.handleException(e);
           return null;
        } finally {
            intercept.afterMethod(o);
        }
    }

}
