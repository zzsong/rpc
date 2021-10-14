package com.zss.rpc.core.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ZssRpcRequestHolder {

    public final static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public final static Map<Long, RpcFuture<ZssRpcResponse>> REQUEST_MAP = new ConcurrentHashMap<>();

}
