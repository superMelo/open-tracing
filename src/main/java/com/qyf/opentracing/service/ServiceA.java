package com.qyf.opentracing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {


    public void callB(){
       test();
    }

    public void test(){
        System.out.println("test");
    }
}
