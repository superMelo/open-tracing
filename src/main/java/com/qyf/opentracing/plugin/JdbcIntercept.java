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
    public void beforeMethod(Method method, Object object, Object[] allArguments) {
        ContextManager.getOrCreate();
        Span span = ContextManager.createSpan(method.getDeclaringClass().getName()+"."+method.getName());
        putTags(object, span, (String) allArguments[0]);
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

    private void putTags(Object o, Span span, String sqlStr){
        JdbcTemplate template = (JdbcTemplate) o;
        DataSource dataSource = template.getDataSource();
        List<Tag> tags = new LinkedList<>();
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            Tag userName = new Tag("userName", metaData.getUserName());
            Tag url = new Tag("url", metaData.getURL());
            Tag data = new Tag("data", metaData.getDatabaseProductName());
            Tag sql = new Tag("sql", sqlStr);
            Tag driverName = new Tag("driverName", metaData.getDriverName());
            tags.add(userName);
            tags.add(url);
            tags.add(data);
            tags.add(sql);
            tags.add(driverName);
            span.setTag(tags);
            span.setLog(new Log("success"));
        }catch (Exception e){
            span.setLog(new Log(e.getMessage()));
        }

    }
}