package com.qyf.opentracing.data;

import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.plugin.ControllerIntercept;
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
        synchronized (ControllerIntercept.list){
            if (ControllerIntercept.list != null && ControllerIntercept.list.size() > 0){
                List<Trace> list = ControllerIntercept.list;
                traceService.saveAll(list);
                ControllerIntercept.list.clear();
            }
        }
    }
}
