package com.qyf.opentracing.data;

import com.alibaba.fastjson.JSON;
import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.plugin.ControllerIntercept;
import com.qyf.opentracing.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DataController {

    @Autowired
    private TraceService traceService;

    @RequestMapping("findAll")
    public String findAll(){
        Iterable<Trace> list = traceService.findAll();
        return JSON.toJSONString(list);
    }

    @RequestMapping("delete")
    public void delete(){
        ControllerIntercept.list.clear();
        traceService.deleteAll();
    }

    @RequestMapping("save")
    public void save(){
        List<Trace> list = ControllerIntercept.list;
        traceService.saveAll(list);
    }
}
