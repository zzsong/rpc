package com.zss.rpc.consumer.controller;

import com.zss.rpc.consumer.annotation.RpcReference;
import com.zss.rpc.service.HelloFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HelloController {

    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "SpringJavaInjectionPointsAutowiringInspection"})
    @RpcReference(serviceVersion = "1.0.0", timeout = 3000, registryAddress = "81.69.4.34:2181")
    private HelloFacade helloFacade;

    @RequestMapping(value = "/hello")
    public String sayHello() {
        System.out.println(LocalDateTime.now());
        return helloFacade.hello("mini rpc");
    }
}
