package com.qyf.opentracing.service;

import com.qyf.opentracing.entity.Trace;

import java.util.List;

public interface TraceService {

    Iterable<Trace> findAll();

    void saveAll(Iterable<Trace> list);

    void deleteAll();

}
