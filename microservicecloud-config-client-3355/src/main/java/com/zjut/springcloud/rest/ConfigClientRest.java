package com.zjut.springcloud.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientRest {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaServer;
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/config")
    public String getConfig(){
        String str = "application: " + applicationName + "\t eurekaServers:" + eurekaServer + " \t serverPort" + serverPort;
        System.out.println(str);
        return str;
    }
}
