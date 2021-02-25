package com.qyf.opentracing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    @Autowired
    private ServiceA serviceA;

    public void say(){
        System.out.println("test");
    }

    public void callA(){
        serviceA.test();
    }
}