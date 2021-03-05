package com.qyf.opentracing.plugin;

import com.qyf.opentracing.entity.*;
import com.qyf.opentracing.plugin.api.Intercept;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.LinkedList;
import java.util.List;

public class JdbcIntercept implements Intercept{
    @Override
    public void beforeMethod(Method method, Object object) {
        ContextManager.getOrCreate();
        JdbcTemplate template = (JdbcTemplate) object;
        DataSource dataSource = template.getDataSource();
        Span span = ContextManager.createSpan(method.getName());
        List<Tag> tags = new LinkedList<>();
        putTags(dataSource, tags, span);
    }

    @Override
    public void afterMethod(Method method) {
        ContextManager.stopSpan();
        ContextManager.stopTrace();
    }

    @Override
    public void handleException(Method method, Exception e) {

    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("queryForList");
    }

    @Override
    public ElementMatcher.Junction<NamedElement> getConstructorsInterceptPoints() {
        return ElementMatchers.named("org.springframework.jdbc.core.JdbcTemplate");
    }

    private void putTags(DataSource dataSource, List<Tag> tags, Span span){
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            Tag userName = new Tag("userName", metaData.getUserName());
            Tag url = new Tag("url", metaData.getURL());
            Tag data = new Tag("data", metaData.getDatabaseProductName());
            tags.add(userName);
            tags.add(url);
            tags.add(data);
            span.setTag(tags);
            span.setLog(new Log("success"));
        }catch (Exception e){
            span.setLog(new Log(e.getMessage()));
        }

    }
}
