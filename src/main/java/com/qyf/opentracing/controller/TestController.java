package com.qyf.opentracing.controller;

import com.alibaba.fastjson.JSON;
import com.qyf.opentracing.load.DynamicEngine;
import com.qyf.opentracing.service.ServiceA;
import com.qyf.opentracing.service.ServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.IOUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {



    @RequestMapping("test")
    public String test(String className, String classBody, String filePath, String methodName) throws Exception {
        String fullName = className;
        File file = new File(filePath);
        if (!file.exists()) {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file, true), true);
            printWriter.print(classBody);
            printWriter.close();
        }
        InputStream in = new FileInputStream(file);
        byte[] bytes = IOUtils.readFully(in, -1, false);
        String src = new String(bytes);
        in.close();

        DynamicEngine de = DynamicEngine.getInstance();
        Object instance = de.javaCodeToObject(fullName, src.toString());
        Class<?> clz = instance.getClass();
        Method declaredMethod = clz.getDeclaredMethod(methodName);
        declaredMethod.invoke(instance);
        return "success";
    }



    @Autowired
    private ServiceA serviceA;
    @Autowired
    private ServiceB serviceB;
    @RequestMapping("test1")
    public String test1(){
        return serviceA.callB();
    }
    @RequestMapping("test2")
    public String test2(){
        serviceB.callA();
        return "success";
    }



}
