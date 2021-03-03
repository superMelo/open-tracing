package com.qyf.opentracing.data;

import com.alibaba.fastjson.JSON;
import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.plugin.TraceIntercept;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    @RequestMapping("findAll")
    public String findAll(){
        List<Trace> list = TraceIntercept.list;
        return JSON.toJSONString(TraceIntercept.list);
    }

    @RequestMapping("delete")
    public void delete(){
        TraceIntercept.list.clear();
    }
}
