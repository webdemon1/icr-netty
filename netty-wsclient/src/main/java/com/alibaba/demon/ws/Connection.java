package com.alibaba.demon.ws;

/**
 * @author: Demon
 * @create: 2019-04-10
 */
public interface Connection {

    public void close();

    public void sendText(final String payload);

    public void sendBinary(byte[] payload);

    public String getId();

}
