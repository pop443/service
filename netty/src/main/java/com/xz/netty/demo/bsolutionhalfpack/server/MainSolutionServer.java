package com.xz.netty.demo.bsolutionhalfpack.server;

import com.xz.netty.util.NetUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xz on 2020/1/24.
 */
public class MainSolutionServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup() ;
        EventLoopGroup workGroup = new NioEventLoopGroup() ;
        try {
            ServerBootstrap server = new ServerBootstrap() ;
            server.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SolutionServerChannelInit())
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true) ;
            ChannelFuture channelFuture = server.bind(NetUtil.getPort()).sync() ;
            channelFuture.channel().closeFuture().sync() ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully() ;
        }
    }
}
