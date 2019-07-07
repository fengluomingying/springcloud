package com.zjut.springcloud.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zjut.springcloud.entity.Dept;
import com.zjut.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService service;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "dept/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Dept dept){
        return service.add(dept);
    }

    @RequestMapping(value = "dept/get/{id}", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    public Dept get(@PathVariable("id") Long id){
        Dept dept = service.get(id);
        if (dept == null){
            throw new RuntimeException("该ID: " + "没有对应的信息");
        }
        return dept;
    }

    public Dept processHystrix_Get(@PathVariable("id") Long id){
        return new Dept().setDeptId(id).setDeptName("该ID： " + "没有对应的信息，null--@HystrixCommand")
                .setDeptSource("no this database in MySQL");
    }
    @RequestMapping(value = "dept/list", method = RequestMethod.GET)
    public List<Dept> list(){
        return service.list();
    }
}
