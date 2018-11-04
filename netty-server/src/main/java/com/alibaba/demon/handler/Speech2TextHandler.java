package com.alibaba.demon.handler;

import com.alibaba.demon.domain.AudioPack;
import com.alibaba.demon.service.SpeechTranscriberService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component("speech2TextHandler")
@Scope("prototype")
public class Speech2TextHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        AudioPack audioPack = (AudioPack) msg;
        byte[] data = audioPack.getData();
        InputStream ips = new ByteArrayInputStream(data);
        SpeechTranscriberService speechService = applicationContext.getBean(SpeechTranscriberService.class);
        speechService.start(ips);
    }
}
