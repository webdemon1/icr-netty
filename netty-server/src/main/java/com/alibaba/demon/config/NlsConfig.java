package com.alibaba.demon.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class NlsConfig {

    @Value("${nls.token}")
    private String token;

    @Value("${nls.appKey}")
    private String appKey;

    @Bean
    public NlsClient nlsClient( ) {
        NlsClient nlsClient = new NlsClient(token);
        log.info("NlsClient Instance : {}", JSON.toJSONString(nlsClient));
        return nlsClient;
    }


    @Bean
    @Scope("prototype")
    public SpeechTranscriber transcriber(NlsClient nlsClient, SpeechTranscriberListener speechTranscriberListener) {
        try {
            // Step1 创建实例,建立连接
            SpeechTranscriber transcriber = new SpeechTranscriber(nlsClient, speechTranscriberListener);
            ;
            transcriber.setAppKey(appKey);
            // 输入音频编码方式
            transcriber.setFormat(InputFormatEnum.PCM);
            // 输入音频采样率
            transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            // 是否返回中间识别结果
            transcriber.setEnableIntermediateResult(true);
            // 是否生成并返回标点符号
            transcriber.setEnablePunctuation(true);
            // 是否将返回结果规整化,比如将一百返回为100
            transcriber.setEnableITN(false);
            // Step2 此方法将以上参数设置序列化为json发送给服务端,并等待服务端确认
            log.info("SpeechTranscriber Instance : {}", transcriber);
            return transcriber;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
