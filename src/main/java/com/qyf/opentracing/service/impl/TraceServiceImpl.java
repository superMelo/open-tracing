package com.qyf.opentracing.service.impl;

import com.qyf.opentracing.entity.Trace;
import com.qyf.opentracing.repository.TraceRepository;
import com.qyf.opentracing.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceServiceImpl implements TraceService{

    @Autowired
    private TraceRepository dao;

    @Override
    public Iterable<Trace> findAll() {
        return dao.findAll();
    }

    @Override
    public void save(Trace trace) {
        dao.save(trace);
    }
}
