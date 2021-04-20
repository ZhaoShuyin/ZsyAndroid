package com.zsy.socket.netty;


import android.util.Log;

import java.io.FileReader;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Title com.netty
 */

public class SocketClient {

    public Channel channel;
    private Bootstrap boot;

    private ClientListener clientListener;

    public interface ClientListener {
        void message(String message);
    }

    public boolean sendMessage(String s) {
        return sendBytes(s.getBytes());
    }

    public boolean sendBytes(byte[] bytes) {
        if (channel != null) {
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            writeBuffer.rewind();
            ByteBuf buf = Unpooled.copiedBuffer(writeBuffer);   // 转为ByteBuf
            channel.writeAndFlush(buf);                         // 写消息到管道
            writeBuffer.clear();                                // 清理缓冲区
            return true;
        }
        return false;
    }


    public void connect(String host, int port, ClientListener listener) {
        if (listener == null) {
            listener = new ClientListener() {
                @Override
                public void message(String message) {
                }
            };
        }
        clientListener = listener;
        EventLoopGroup group = new NioEventLoopGroup();
        boot = new Bootstrap();
        boot.group(group);
        boot.channel(NioSocketChannel.class);
        boot.option(ChannelOption.SO_KEEPALIVE, true);
        boot.option(ChannelOption.TCP_NODELAY, true);
        boot.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new MyHandler());
            }
        });
        try {
            clientListener.message("客户端启动");
            ChannelFuture sync = boot.connect(host, port).sync();
            channel = sync.channel();
            clientListener.message("客户端连接");
            channel.closeFuture().sync();
            clientListener.message("客户端关闭");
        } catch (InterruptedException e) {
            e.printStackTrace();
            clientListener.message("客户端异常:" + e.toString());
        } finally {
            clientListener.message("客户端关闭...");
            group.shutdownGracefully();
        }
    }

    public void disConnect() {
        if (boot != null) {
            boot.clone();
            clientListener.message("客户端 clone() 关闭...");
        }
    }

    public class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            int length = buf.readableBytes();
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            buf.release();
            clientListener.message("channelRead(): "+length);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
            clientListener.message("channelReadComplete(): ");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
            clientListener.message(cause.getMessage());
        }
    }

}
