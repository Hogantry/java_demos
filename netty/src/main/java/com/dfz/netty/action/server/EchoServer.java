package com.dfz.netty.action.server;

import com.dfz.netty.action.server.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author DFZ
 * @Date 2021-10-11 9:56
 * @Description
 *
 * Server端继承ChannelInboundHandlerAdapter，方法channelRead执行完，不会释放ByteBuf，因为Server需要将读取到的消息回发给客户端，
 * 而write方法是异步的。
 * Client端继承SimpleChannelInboundHandler，方法channelRead0执行完，就会释放ByteBuf，因为方法执行完，即代表该消息已经处理完成，无后续
 * 操作，可直接释放
 *
 */
public class EchoServer {

    public static void main(String[] args) throws InterruptedException {
        new EchoServer().start();
    }

    private void start() throws InterruptedException {
        final EchoServerHandler echoHandler = new EchoServerHandler();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(nioEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(8080)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(echoHandler);
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }
    }

}
