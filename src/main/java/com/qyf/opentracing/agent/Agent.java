package com.qyf.opentracing.agent;

import com.qyf.opentracing.plugin.TraceIntercept;
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
        System.out.println("premain start");
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {

                //扫描指定包下的所有拦截类
                ServiceLoader<Intercept> loader = ServiceLoader.load(Intercept.class);
                for (Intercept intercept : loader) {
                    builder = builder.method(intercept.getMethodsMatcher()) //拦截方法
                            .intercept(MethodDelegation.withDefaultConfiguration().to(new TraceInterceptContext(intercept))); //执行切入
                }
                return builder;
//                context.load(new TraceIntercept());
//                return builder.method(ElementMatchers.any()).intercept(MethodDelegation.to(TraceIntercept.class));
//                return builder.method(ElementMatchers.any()) //拦截任意方法
//                        .intercept(MethodDelegation.to(TraceInterceptContext.class)); //执行切入
            }
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
