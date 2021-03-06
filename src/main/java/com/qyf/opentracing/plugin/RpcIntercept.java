package com.qyf.opentracing.plugin;

import com.alibaba.fastjson.JSON;
import com.qyf.opentracing.entity.*;
import com.qyf.opentracing.es.EsClient;
import com.qyf.opentracing.plugin.api.Intercept;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class RpcIntercept implements Intercept{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeMethod(Method method, Object object, Object[] allArguments) {
        Trace trace = ContextManager.getOrCreate();
        log.info("start traceInfo:{}", JSON.toJSONString(trace));
        ContextManager.getOrCreate();
        ContextManager.createSpan(method.getDeclaringClass().getName()+"."+method.getName());
        ContextManager.setLog(method, "success", Thread.currentThread().getStackTrace());
    }

    @Override
    public void afterMethod(Method method) {
        Trace trace = ContextManager.getOrCreate();
        log.info("end traceInfo:{}", JSON.toJSONString(trace));
        ContextManager.stopSpan();
        ContextManager.stopTrace();
        EsClient.save(trace);
    }

    @Override
    public void handleException(Method method, Exception e) {
        ContextManager.setLog(method, e.getMessage(), e.getStackTrace());
    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("send");
    }

    @Override
    public ElementMatcher.Junction<NamedElement> getConstructorsInterceptPoints() {
        return ElementMatchers.named("com.qyf.rpc.remoting.netty.client.NettyClient")
                .or(ElementMatchers.named("com.qyf.rpc.remoting.http.client.HttpClient"));
    }
}
