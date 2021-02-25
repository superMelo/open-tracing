package com.qyf.opentracing.data;

import com.alibaba.fastjson.JSON;
import com.qyf.opentracing.agent.TraceIntercept;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @RequestMapping("findAll")
    public String findAll(){
        return JSON.toJSONString(TraceIntercept.list);
    }

    @RequestMapping("delete")
    public void delete(){
        TraceIntercept.list.clear();
    }
}
