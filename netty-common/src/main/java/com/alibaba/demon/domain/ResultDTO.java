package com.alibaba.demon.domain;

import com.google.common.base.Preconditions;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDTO implements Serializable {

    private static final long serialVersionUID = 9203140342042533456L;
    private final Object content;
    private final boolean success;
    private final String errorMsg;
    private final String errorCode;
    private final String errorLevel;

    public ResultDTO(Object content, boolean success, String errorMsg, String errorCode, String errorLevel) {
        this.content = content;
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.errorLevel = errorLevel;
    }

    private ResultDTO(Builder builder) {
        this.content = builder.content;
        this.success = Preconditions.checkNotNull(builder.success);
        this.errorMsg = builder.errorMsg;
        this.errorCode = builder.errorCode;
        this.errorLevel = builder.errorLevel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ResultDTO success(Object content) {
        return builder().success(true).content(content).build();
    }
    public static ResultDTO success() {
        return builder().success(true).build();
    }
    public static ResultDTO fail(String message) {
        return builder().success(false).errorMsg(message).build();
    }



    public static class Builder {
        private Object content;
        private boolean success;
        private String errorMsg;
        private String errorCode;
        private String errorLevel;

        public Builder content(Object content) {
            this.content = content;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder errorLevel(String errorLevel) {
            this.errorLevel = errorLevel;
            return this;
        }

        public Builder fromPrototype(ResultDTO prototype) {
            content = prototype.content;
            success = prototype.success;
            errorMsg = prototype.errorMsg;
            errorCode = prototype.errorCode;
            errorLevel = prototype.errorLevel;
            return this;
        }

        public ResultDTO build() {
            return new ResultDTO(this);
        }
    }
}
