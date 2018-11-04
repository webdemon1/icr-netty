package com.alibaba.demon.decoder;

import com.alibaba.demon.domain.AudioPack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("audioPackDecoder")
@Slf4j
@Scope("prototype")
public class AudioPackDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        log.info("@AudioPackDecoder.decode byteBuf:{}", byteBuf);

        AudioPack audioPack = new AudioPack();
        short dataLen;
        int readerIndex;
        while (true) {
            if (byteBuf.readableBytes() >= 12) {
                readerIndex = byteBuf.readerIndex();
                byteBuf.markReaderIndex();

                // 读取 id 4byte
                int id = byteBuf.readInt();
                audioPack.setId(id);
                log.info("@AudioPackDecoder.decode microPhoneNo:{}", id);

                // 读取 seq 4byte
                byteBuf.resetReaderIndex();
                int seq = byteBuf.readInt();
                audioPack.setSeq(seq);

                // 读取 type 1byte
                byteBuf.resetReaderIndex();
                byte type = byteBuf.readByte();
                audioPack.setType(type);

                // 读取 tag 1byte
                byteBuf.resetReaderIndex();
                byte tag = byteBuf.readByte();
                audioPack.setTag(tag);

                // 读取 dataLen 2byte
                byteBuf.resetReaderIndex();
                dataLen = byteBuf.readShort();
                audioPack.setDataLen(dataLen);

                byteBuf.resetReaderIndex();
                break;
            }
        }

        if (byteBuf.readableBytes() < dataLen) {
            byteBuf.readerIndex(readerIndex);
            return;
        }
        // 读取data数据
        byte[] data = new byte[dataLen];
        byteBuf.readBytes(data);
        audioPack.setData(data);

        log.info("@audioPackDecoder.decode audioPack:{}", audioPack);
        out.add(audioPack);
    }


}
