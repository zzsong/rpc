package com.zss.rpc.registry.service;

import com.zss.rpc.core.common.ServiceMeta;

public class EurekaRegistryService implements RegistryService {

    public EurekaRegistryService(String registryAddress) {
        // TODO
    }

    @Override
    public void register(ServiceMeta serviceMeta) {

    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) {

    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
