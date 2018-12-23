package com.alibaba.demon.handler;

import com.alibaba.demon.domain.AudioPack;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("speech2TextHandler")
@Scope("prototype")
public class Speech2TextHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        AudioPack audioPack = (AudioPack) msg;
        byte[] data = audioPack.getData();

        //write data to disk
    }
}
