package com.zhaorou.zrapplication.user.model;

/**
 * @author kang
 */
public class UserMessageEvent {
    public UserMessageEvent(String unread_msg_count) {

        this.messageCount = unread_msg_count;
    }

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

    private String messageCount;
}
