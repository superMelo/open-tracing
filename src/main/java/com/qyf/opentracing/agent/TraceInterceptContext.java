package com.qyf.opentracing.agent;


import com.qyf.opentracing.plugin.api.Intercept;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

import java.util.concurrent.Callable;

public class TraceInterceptContext {

    private Intercept intercept;


    public TraceInterceptContext(Intercept intercept) {
        this.intercept = intercept;
    }

    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Throwable {
        intercept.beforeMethod(method);
        try {
            // 原有函数执行
            return zuper.call();
        } catch (Exception e) {
           intercept.handleException(method, e);
           return null;
        } finally {
            intercept.afterMethod(method);
        }
    }

}
