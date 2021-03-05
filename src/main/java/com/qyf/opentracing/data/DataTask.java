package com.qyf.opentracing.data;

import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.plugin.DispatchIntercept;
import com.qyf.opentracing.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataTask {


    @Autowired
    private TraceService traceService;


    @Scheduled(cron = "*/1 * * * * ?")
    public void collect(){
        synchronized (DispatchIntercept.list){
            if (DispatchIntercept.list != null && DispatchIntercept.list.size() > 0){
                List<Trace> list = DispatchIntercept.list;
                traceService.saveAll(list);
                DispatchIntercept.list.clear();
            }
        }
    }
}
