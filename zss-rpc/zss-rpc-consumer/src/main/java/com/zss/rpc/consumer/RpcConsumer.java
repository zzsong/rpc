package com.zss.rpc.consumer;

import com.zss.rpc.codec.RpcDecoder;
import com.zss.rpc.codec.RpcEncoder;
import com.zss.rpc.core.common.RpcServiceHelper;
import com.zss.rpc.core.common.ServiceMeta;
import com.zss.rpc.core.common.ZssRpcRequest;
import com.zss.rpc.handler.RpcResponseHandler;
import com.zss.rpc.protocol.RpcProtocol;
import com.zss.rpc.registry.service.RegistryService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcConsumer {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final Bootstrap bootstrap;
    private final EventLoopGroup workGroup;

    public RpcConsumer(){
        bootstrap = new Bootstrap();
        workGroup = new NioEventLoopGroup();

        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RpcEncoder())
                                .addLast(new RpcDecoder())
                                .addLast(new RpcResponseHandler());
                    }
                });
    }


    public void sendRequest(RpcProtocol<ZssRpcRequest> protocol, RegistryService registryService) throws Exception {
        ZssRpcRequest request = protocol.getBody();
        Object[] params = request.getParams();
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());

        int invokerHashCode = params.length > 0 ? params[0].hashCode() : serviceKey.hashCode();
        ServiceMeta serviceMetadata = registryService.discovery(serviceKey, invokerHashCode);

        if (serviceMetadata != null) {
            ChannelFuture future = bootstrap.connect(serviceMetadata.getServiceAddress(), serviceMetadata.getServicePort()).sync();
            future.addListener((ChannelFutureListener) arg0 -> {
                if (future.isSuccess()) {
                    log.info("connect rpc server {} on port {} success.", serviceMetadata.getServiceAddress(), serviceMetadata.getServicePort());
                } else {
                    log.error("connect rpc server {} on port {} failed.", serviceMetadata.getServiceAddress(), serviceMetadata.getServicePort());
                    future.cause().printStackTrace();
                    workGroup.shutdownGracefully();
                }
            });
            future.channel().writeAndFlush(protocol);
        }
    }
}
