package com.alibaba.demon.handler;

import com.alibaba.demon.decoder.AudioPackDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Scope("prototype")
public class TcpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void initChannel(SocketChannel sc) {
        ChannelPipeline pipeline = sc.pipeline();
        AudioPackDecoder audioPackDecoder = applicationContext.getBean(AudioPackDecoder.class);
        pipeline.addLast(audioPackDecoder);

    }


}
