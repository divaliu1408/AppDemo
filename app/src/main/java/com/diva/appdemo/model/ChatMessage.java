package com.diva.appdemo.model;

import android.database.CursorJoiner;
import android.support.annotation.NonNull;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/7/14.
 */

public class ChatMessage {
    private String msgContent;
    private int from;
    private long time;

    public ChatMessage(String msgContent, int from,long time) {
        this.msgContent = msgContent;
        this.from = from;
        this.time = time;
    }
    public ChatMessage(String msgContent, int from ){
        this.msgContent = msgContent;
        this.from = from;
    }
    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }



    @Override
    public String toString() {
        return "ChatMessage:{"
                +"msgContent:"+getMsgContent()
                +",from:"+getFrom()
                +",time:" +getTime()
                +"}";
    }
}
