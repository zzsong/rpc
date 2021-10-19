package com.zss.rpc.registry;

import com.zss.rpc.registry.service.EurekaRegistryService;
import com.zss.rpc.registry.service.RegistryService;
import com.zss.rpc.registry.service.ZookeeperRegistryService;

public class RegistryServiceFactory {

    private static volatile RegistryService registryService;

    public static RegistryService getInstance(String registryAddress, RegistryType registryType) throws Exception {
        if (null == registryService){
            synchronized (RegistryServiceFactory.class) {
                if (null == registryService) {
                    switch (registryType) {
                        case ZOOKEEPER:
                            registryService = new ZookeeperRegistryService(registryAddress);
                            break;
                        case EUREKA:
                            registryService = new EurekaRegistryService(registryAddress);
                            break;
                    }
                }
            }
        }
        return registryService;
    }
}
