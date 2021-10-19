package com.zss.rpc.provider;

import com.zss.rpc.core.common.RpcProperties;
import com.zss.rpc.registry.RegistryServiceFactory;
import com.zss.rpc.registry.RegistryType;
import com.zss.rpc.registry.service.RegistryService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcProviderAutoConfiguration {

    @Resource
    private RpcProperties rpcProperties;

    @Bean
    public RpcProvider init() throws Exception {
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistryType().toUpperCase());
        RegistryService serviceRegistry = RegistryServiceFactory.getInstance(rpcProperties.getRegistryAddr(), type);
        return new RpcProvider(rpcProperties.getServicePort(), serviceRegistry);

    }
}
