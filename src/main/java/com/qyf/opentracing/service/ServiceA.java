package com.qyf.opentracing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {

    @Autowired
    private ServiceB serviceB;

    public void callB(){
        serviceB.say();
    }

    public void test(){
        System.out.println("test");
    }
}
