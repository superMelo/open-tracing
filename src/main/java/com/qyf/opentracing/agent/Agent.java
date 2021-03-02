package com.qyf.opentracing.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.ServiceLoader;

public class Agent {

    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            //spi加载所有intercept
            ServiceLoader<Intercept> loader = ServiceLoader.load(Intercept.class);
            for (Intercept intercept : loader) {
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

        new AgentBuilder.Default()
                //需要拦截的包路径
                .type(ElementMatchers.nameStartsWith("com.qyf.opentracing")
                        .and(ElementMatchers.not(ElementMatchers.nameStartsWith("com.qyf.opentracing.entity")))
                        .and(ElementMatchers.not(ElementMatchers.nameStartsWith("com.qyf.opentracing.data")))
                        .and(ElementMatchers.not(ElementMatchers.nameStartsWith("com.qyf.opentracing.OpenTracingApplication")))
                        .and(ElementMatchers.not(ElementMatchers.nameStartsWith("com.qyf.opentracing.plugin")))
                )
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);
    }
}
