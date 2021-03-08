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
        HttpServletRequest request = (HttpServletRequest) allArguments[0];
        StringBuffer requestURL = request.getRequestURL();
        String url = requestURL.toString();
        if (url.equals("http://127.0.0.1:8081/delete") || url.equals("http://127.0.0.1:8081/findAll")){
        }else {
            ContextManager.getOrCreate();
            Span span = ContextManager.createSpan(method.getDeclaringClass().getName() + "." + method.getName());
            putTags(allArguments, span);
        }
    }

    @Override
    public void afterMethod(Method method) {
        ContextManager.stopSpan();
        ContextManager.stopTrace();
    }

    @Override
    public void handleException(Method method, Exception e) {
        ContextManager.setLog(method, e);
    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
//        return ElementMatchers.named("test1")
//                .or(ElementMatchers.named("callB"))
//                .or(ElementMatchers.named("test"))
//                .or(ElementMatchers.named("findMenu"))
//                .or(ElementMatchers.named("findRole"));
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
