package com.qyf.opentracing.plugin;

import com.qyf.opentracing.entity.*;
import com.qyf.opentracing.plugin.api.Intercept;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class DispatchIntercept implements Intercept {

    public static List<Trace> list = new LinkedList<>();

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeMethod(Method method, Object o, Object[] allArguments) {
        ContextManager.getOrCreate();
        Span span = ContextManager.createSpan(method.getDeclaringClass().getName() + "." + method.getName());
        putTags(allArguments, span);
    }

    @Override
    public void afterMethod(Method method) {
        ContextManager.stopSpan();
        ContextManager.stopTrace();
    }

    @Override
    public void handleException(Method method, Exception e) {
        ContextManager.setLog(method, e.getMessage(), e.getStackTrace());
    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("doDispatch");
    }

    @Override
    public ElementMatcher.Junction<NamedElement> getConstructorsInterceptPoints() {
        return ElementMatchers.named("org.springframework.web.servlet.DispatcherServlet");
    }


    private void putTags(Object[] allArguments, Span span) {
        HttpServletRequest request = (HttpServletRequest) allArguments[0];
        StringBuffer requestURL = request.getRequestURL();
        LinkedList<Tag> tags = new LinkedList<>();
        tags.add(new Tag("url", requestURL.toString()));
        span.setTag(tags);
    }
}
