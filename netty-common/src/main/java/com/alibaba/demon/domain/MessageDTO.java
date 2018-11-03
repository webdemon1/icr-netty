package com.alibaba.demon.domain;

public class MessageDTO<T> {

    private String eventType = "TEXT";

    private Long meetingRoomId = 1L;

    private T data;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(Long meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
