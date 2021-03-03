package com.qyf.opentracing.service.impl;

import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.repository.TraceRepository;
import com.qyf.opentracing.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceServiceImpl implements TraceService{

    @Autowired
    TraceRepository repository;

    @Override
    public Iterable<Trace> findAll() {
        return repository.findAll();
    }

    @Override
    public void saveAll(Iterable<Trace> list) {
       repository.saveAll(list);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
