package com.alibaba.demon.domain;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TextDTO implements Serializable {

    private static final long serialVersionUID = -7169058067113835814L;

    private Date startTime;

    private Date endTime;

    private String eventType;

    private String text;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
