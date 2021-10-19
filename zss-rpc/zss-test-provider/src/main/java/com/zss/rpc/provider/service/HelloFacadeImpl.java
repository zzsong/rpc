package com.zss.rpc.provider.service;

import com.zss.rpc.provider.annotation.RpcService;
import com.zss.rpc.service.HelloFacade;

@RpcService(serviceInterface = HelloFacade.class, version = "1.0.0")
public class HelloFacadeImpl implements HelloFacade {

    @Override
    public String hello(String msg) {
        return "hello! "+msg;
    }
}
