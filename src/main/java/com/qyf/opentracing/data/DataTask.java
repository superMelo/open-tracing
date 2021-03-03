package com.qyf.opentracing.data;

import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.plugin.TraceIntercept;
import com.qyf.opentracing.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataTask {


    @Autowired
    private TraceService traceService;


    @Scheduled(cron = "*/2 * * * * ?")
    public void collect(){
        synchronized (TraceIntercept.list){
            List<Trace> list = TraceIntercept.list;
            traceService.saveAll(list);
            TraceIntercept.list.clear();
        }
    }
}
