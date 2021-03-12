package com.qyf.opentracing.plugin;

import com.alibaba.fastjson.JSON;
import com.qyf.opentracing.agent.Agent;
import com.qyf.opentracing.entity.ContextManager;
import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.plugin.api.Intercept;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.lang.reflect.Method;

public class RpcIntercept implements Intercept{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeMethod(Method method, Object object, Object[] allArguments) {
        ContextManager.getOrCreate();
        ContextManager.createSpan(method.getDeclaringClass().getName()+"."+method.getName());

    }

    @Override
    public void afterMethod(Method method) {
        Trace trace = ContextManager.getOrCreate();
        log.info("traceInfo:{}", JSON.toJSONString(trace));
        ContextManager.stopSpan();
        ContextManager.stopTrace();
        IndexRequest indexRequest  = new IndexRequest("open-trace");
        indexRequest.source(JSON.toJSONString(trace), XContentType.JSON);
        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleException(Method method, Exception e) {
        ContextManager.setLog(method, e);
    }

    @Override
    public ElementMatcher<MethodDescription> getMethodsMatcher() {
        return ElementMatchers.named("send");
    }

    @Override
    public ElementMatcher.Junction<NamedElement> getConstructorsInterceptPoints() {
        return ElementMatchers.nameStartsWith("com.qyf.rpc.remoting.");
    }
}
