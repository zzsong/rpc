package com.zss.rpc.registry;

import com.zss.rpc.core.common.ServiceMeta;
import com.zss.rpc.registry.service.RegistryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegistryTest {

    private RegistryService registryService;

    @Before
    public void before(){
        try {
            registryService = RegistryServiceFactory.getInstance("81.69.4.34:2181",RegistryType.ZOOKEEPER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @After
    public void close() throws Exception {
        registryService.destroy();
    }

    @Test
    public void testAll() throws Exception {
        ServiceMeta serviceMeta1 = new ServiceMeta();
        serviceMeta1.setServiceAddress("127.0.0.1");
        serviceMeta1.setServicePort(8080);
        serviceMeta1.setServiceName("test1");
        serviceMeta1.setServiceVersion("1.0.0");

        registryService.register(serviceMeta1);

        ServiceMeta discovery1 = registryService.discovery("test1#1.0.0", "test1".hashCode());

        registryService.unRegister(discovery1);
    }
}
