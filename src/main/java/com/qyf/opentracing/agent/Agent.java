package com.qyf.opentracing.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.ServiceLoader;

public class Agent {

    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        //spi加载所有intercept
        ServiceLoader<Intercept> intercepts = ServiceLoader.load(Intercept.class);
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            for (Intercept intercept : intercepts) {
                builder = builder.method(intercept.getMethodsMatcher()) //拦截方法
                        .intercept(MethodDelegation.withDefaultConfiguration().to(new TraceInterceptContext(intercept))); //执行切入
            }
            return builder;
        };
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }
        };
        //加载拦截的包路径
        ElementMatcher.Junction<NamedElement> junction = null;
        for (Intercept intercept : intercepts) {
            if (junction == null){
                junction = intercept.getConstructorsInterceptPoints();
            }else {
                junction = junction.or(intercept.getConstructorsInterceptPoints());
            }
        }
        new AgentBuilder.Default()
                //需要拦截的包路径
                .type(junction)
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);
    }
}
