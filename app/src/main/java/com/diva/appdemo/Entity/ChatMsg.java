package com.diva.appdemo.Entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.IdentityHashMap;

/**
 * Created by 刘迪 on 2018/9/18 16:00.
 * 邮箱：divaliu1408@qq.com
 */

@Table(name = "chat_message",onCreated = "")
public class ChatMsg {
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private long messageId;
    @Column(name = "from")
    private String from;
    @Column(name = "to")
    private String to;
    @Column(name = "content")
    private String content;
    @Column(name = "time")
    private long time;

    public ChatMsg() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "id=" + messageId +
                ",from=" + from +
                ",to=" + to +
                ",content=" + content +
                ",time=" + time +
                '}';
    }
}
