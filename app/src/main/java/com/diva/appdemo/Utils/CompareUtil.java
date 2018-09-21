package com.diva.appdemo.Utils;

import com.diva.appdemo.model.ChatMessage;

import java.util.Comparator;

/**
 * Created by 刘迪 on 2018/9/19 09:33.
 * 邮箱：divaliu1408@qq.com
 */

public class CompareUtil implements Comparator<ChatMessage> {
    @Override
    public int compare(ChatMessage o1, ChatMessage o2) {
        int flag = o1.getTime()>o2.getTime()?1:-1;
        return flag;
    }
}
