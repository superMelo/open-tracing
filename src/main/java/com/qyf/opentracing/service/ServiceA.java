package com.qyf.opentracing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {


    public String callB(){
       return test();
    }

    public String test(){
       return "success";
    }
}
