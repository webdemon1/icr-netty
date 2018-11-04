package com.alibaba.demon.handler;

import com.alibaba.demon.decoder.AudioPackDecoder;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Scope("prototype")
@Slf4j
public class TcpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void initChannel(SocketChannel sc) {
        log.info("@TcpChannelInitializer.initChannel sc:{}", JSON.toJSONString(sc));
        AudioPackDecoder audioPackDecoder = applicationContext.getBean(AudioPackDecoder.class);
        sc.pipeline().addLast(audioPackDecoder);

        Speech2TextHandler speech2TextHandler = applicationContext.getBean(Speech2TextHandler.class);
        sc.pipeline().addLast(speech2TextHandler);
    }


}
