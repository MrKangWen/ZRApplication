package com.zhaorou.zrapplication.eventbus;

public class MessageEvent<T> {

    private String command;
    private T data;

    public MessageEvent() {
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
