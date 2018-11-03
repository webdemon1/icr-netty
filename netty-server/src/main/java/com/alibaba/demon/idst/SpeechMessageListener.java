package com.alibaba.demon.idst;

import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("speechTranscriberListener")
@Scope(scopeName = "prototype")
@Slf4j
public class SpeechMessageListener extends SpeechTranscriberListener {

    @Autowired
    public SpeechMessageListener(ApplicationEventPublisher applicationEventPublisher) {
        log.info("ApplicationEventPublisher Instance : {}", applicationEventPublisher);
        log.info("SpeechTranscriberListener Instance : {}", this);
    }

    @Override
    public void onTranscriberStart(SpeechTranscriberResponse response) {

    }

    @Override
    public void onSentenceBegin(SpeechTranscriberResponse response) {

    }

    @Override
    public void onSentenceEnd(SpeechTranscriberResponse response) {

    }

    @Override
    public void onTranscriptionResultChange(SpeechTranscriberResponse response) {


    }

    @Override
    public void onTranscriptionComplete(SpeechTranscriberResponse response) {

    }
}
