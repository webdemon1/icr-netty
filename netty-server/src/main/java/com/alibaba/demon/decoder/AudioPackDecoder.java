package com.alibaba.demon.decoder;

import com.alibaba.demon.domain.AudioPack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component("audioPackDecoder")
@Slf4j
@Scope("prototype")
@SuppressWarnings("all")
public class AudioPackDecoder extends ByteToMessageDecoder {

    private Integer microNo;

    private File pcmFile;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        log.info("@AudioPackDecoder.decode byteBuf:{}", byteBuf);

        AudioPack audioPack = new AudioPack();

        if (byteBuf.readableBytes() < 12) {
            return;
        }

        int readerIndex = byteBuf.readerIndex();
        byteBuf.markReaderIndex();

        // 读取 id 4byte
        int id = byteBuf.readInt();
        audioPack.setId(id);
        log.info("@AudioPackDecoder.decode microPhoneNo:{}", id);

        // 读取 seq 4byte
        int seq = byteBuf.readInt();
        audioPack.setSeq(seq);

        // 读取 type 1byte
        byte type = byteBuf.readByte();
        audioPack.setType(type);

        // 读取 tag 1byte
        byte tag = byteBuf.readByte();
        audioPack.setTag(tag);

        // 读取 dataLen 2byte
        short dataLen = byteBuf.readShort();
        audioPack.setDataLen(dataLen);

        if (byteBuf.readableBytes() < dataLen) {
            byteBuf.resetReaderIndex();
            return;
        }

        // 读取data数据
        byte[] data = new byte[dataLen];
        byteBuf.readBytes(data);
        audioPack.setData(data);

        log.info("@audioPackDecoder.decode audioPack:{}", audioPack);

        createPcmFile(microNo);
        writeAudio2Disk(data, dataLen);
        out.add(audioPack);
    }

    /**
     * 创建文件
     *
     * @am id
     */
    private void createPcmFile(int id) {
        if (Objects.isNull(id)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                this.microNo = id;
                pcmFile = new File("/Users/admin/Documents/tmp" + File.separator + sdf.format(new Date()) + "_" + id + "netty.pcm");
                pcmFile.createNewFile();
            } catch (IOException ex) {
                log.error("@audioPackDecoder.writeAudio2Disk exception", ex);
            }
        }

    }

    /**
     * 写数据
     *
     * @param buffer
     * @param length
     */
    private void writeAudio2Disk(byte[] buffer, int length) {
        try {
            FileOutputStream fos = new FileOutputStream(pcmFile, true);
            fos.write(buffer, 0, length);
            fos.close();
        } catch (Exception ex) {
            log.error("@audioPackDecoder.writeAudio2Disk exception", ex);
        }
    }

}
