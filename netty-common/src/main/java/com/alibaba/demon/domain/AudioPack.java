package com.alibaba.demon.domain;

import lombok.Data;

import java.util.Arrays;

@Data
public class AudioPack {

    private int id;               // 2 bytes

    private int seq;                // ���к�

    private byte type;              // ����

    private byte tag;               //

    private short datalen;          // ���ݳ���   char�ĳ���

    private byte data[];            // pcm����

    @Override
    public String toString() {
        return "AudioPack{" +
                "id=" + id +
                ", seq=" + seq +
                ", type=" + type +
                ", tag=" + tag +
                ", datalen=" + datalen +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
