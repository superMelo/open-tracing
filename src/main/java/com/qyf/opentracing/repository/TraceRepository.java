package com.qyf.opentracing.repository;

import com.qyf.opentracing.entity.Trace;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TraceRepository extends ElasticsearchRepository<Trace, String> {


}
