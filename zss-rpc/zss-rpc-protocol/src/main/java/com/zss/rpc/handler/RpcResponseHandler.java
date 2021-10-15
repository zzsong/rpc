package com.zss.rpc.handler;

import com.zss.rpc.core.common.RpcFuture;
import com.zss.rpc.core.common.ZssRpcRequestHolder;
import com.zss.rpc.core.common.ZssRpcResponse;
import com.zss.rpc.protocol.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcProtocol<ZssRpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<ZssRpcResponse> msg) throws Exception {
        long requestId = msg.getHeader().getRequestId();
        RpcFuture<ZssRpcResponse> future = ZssRpcRequestHolder.REQUEST_MAP.get(requestId);
        future.getPromise().setSuccess(msg.getBody());
    }
}
