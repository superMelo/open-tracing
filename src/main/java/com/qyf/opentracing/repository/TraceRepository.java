package com.qyf.opentracing.repository;

import com.qyf.opentracing.entity.Trace;
import org.springframework.data.repository.CrudRepository;

public interface TraceRepository extends CrudRepository<Trace, String> {


}
