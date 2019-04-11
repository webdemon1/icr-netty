package com.alibaba.demon.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum State {

    INIT(0) {
        @Override
        public void checkStatus() {
            log.info(" INIT");
        }
        @Override
        public void changeStatus() {
            log.info("changeStatus INIT");
        }
    },

    START(1) {
        @Override
        public void checkStatus() {
            log.info(" START");
        }
        @Override
        public void changeStatus() {
            log.info("changeStatus START");
        }
    },

    END(1) {
        @Override
        public void checkStatus() {
            log.info("END");
        }
        @Override
        public void changeStatus() {
            log.info("changeStatus END");
        }
    };

    public abstract void checkStatus();

    public abstract void changeStatus();

    int value;

    State(int value) {
        this.value = value;
    }

    public static void main(String[] args) {
        END.checkStatus();
        END.changeStatus();
    }
}
