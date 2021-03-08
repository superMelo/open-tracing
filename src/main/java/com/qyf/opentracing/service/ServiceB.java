package com.qyf.opentracing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    public void say() throws Exception{
        System.out.println("test");
        throw new Exception("test exception");
    }

    public void callA() throws Exception{
        say();
    }
}
