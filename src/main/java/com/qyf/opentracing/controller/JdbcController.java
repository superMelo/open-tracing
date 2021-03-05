package com.qyf.opentracing.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("jdbc")
public class JdbcController {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("findMenu")
    public String findMenu(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM menu");
        return JSON.toJSONString(list);
    }






}
