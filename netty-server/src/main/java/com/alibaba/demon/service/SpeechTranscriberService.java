package com.alibaba.demon.service;

import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;

@Service("speechTranscriberService")
@Scope("prototype")
@SuppressWarnings("all")
public class SpeechTranscriberService {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 开始idst 调用
     *
     * @param ips
     */
    public void start(InputStream ips) {
        SpeechTranscriber speechTranscriber = applicationContext.getBean(SpeechTranscriber.class);
        speechTranscriber.send(ips, 6400, 200);
    }
}
