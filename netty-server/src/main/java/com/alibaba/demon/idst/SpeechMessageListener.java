package com.alibaba.demon.idst;

import com.alibaba.demon.domain.TextDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("speechTranscriberListener")
@Scope(scopeName = "prototype")
@Slf4j
public class SpeechMessageListener extends SpeechTranscriberListener {

    private Date startTime;

    @Override
    public void onSentenceBegin(SpeechTranscriberResponse response) {
        startTime = new Date();
    }

    @Override
    public void onTranscriptionResultChange(SpeechTranscriberResponse response) {
        TextDTO textDTO = new TextDTO();
        textDTO.setStartTime(startTime);
        textDTO.setEventType("SentenceEnd");
        textDTO.setEndTime(new Date());
        textDTO.setText(response.getTransSentenceText());
        log.info("SpeechMessageListener.onResultChange textDTO:{}",textDTO);
    }

    @Override
    public void onSentenceEnd(SpeechTranscriberResponse response) {
        TextDTO textDTO = new TextDTO();
        textDTO.setStartTime(startTime);
        textDTO.setEventType("SentenceEnd");
        textDTO.setEndTime(new Date());
        textDTO.setText(response.getTransSentenceText());
        log.info("SpeechMessageListener.onSentenceEnd textDTO:{}",textDTO);
    }

    @Override
    public void onTranscriptionComplete(SpeechTranscriberResponse response) {
        log.info("SpeechMessageListener.onTranscriptionComplete response:{}", JSON.toJSONString(response));
    }
}
