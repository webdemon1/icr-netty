package com.alibaba.demon.domain;

import lombok.Data;

import java.util.Arrays;

@Data
public class AudioPack {

    private int id;               // 2 bytes

    private int seq;                // 序列号

    private byte type;              // 类型

    private byte tag;               //

    private short dataLen;          // 数据长度   char的长度

    private byte data[];            // pcm数据

    @Override
    public String toString() {
        return "AudioPack{" +
                "id=" + id +
                ", seq=" + seq +
                ", type=" + type +
                ", tag=" + tag +
                ", dataLen=" + dataLen +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
