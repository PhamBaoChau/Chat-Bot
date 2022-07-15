package com.baochau.dmt.chat.Model;

public class ItemChat {
    public int idItemChat;
    public int idSender;
    public int idReceiver;
    public String createTime;
    public String content;

    public ItemChat() {
    }

    public ItemChat(int idItemChat, int idSender, int idReceiver, String createTime, String content) {
        this.idItemChat = idItemChat;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.createTime = createTime;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ItemChat{" +
                "idItemChat=" + idItemChat +
                ", idSender=" + idSender +
                ", idReceiver=" + idReceiver +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
