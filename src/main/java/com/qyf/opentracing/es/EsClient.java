package com.qyf.opentracing.es;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class EsClient {


    private static RestHighLevelClient getClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
    }


    public static void save(Object object) {
        RestHighLevelClient client = getClient();
        IndexRequest indexRequest = new IndexRequest("open-trace");
        indexRequest.source(JSON.toJSONString(object), XContentType.JSON);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
