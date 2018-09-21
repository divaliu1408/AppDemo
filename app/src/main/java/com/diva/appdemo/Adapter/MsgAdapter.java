package com.diva.appdemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diva.appdemo.R;
import com.diva.appdemo.model.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 刘迪 on 2018/9/18 16:02.
 * 邮箱：divaliu1408@qq.com
 */

public class MsgAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private ArrayList<ChatMessage> messageList;
    private MyItemClickListener myItemClickListener;

    public MsgAdapter(Context context,ArrayList<ChatMessage> messageList){
        inflater = LayoutInflater.from(context);
        this.messageList = messageList;
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView messageTxt,timeTxt;
        private ChatMessageType type;

        public void setMessageTxt(String messageContent) {
            messageTxt.setText(messageContent);
        }

        public void setTimeTxt(String timeContent) {
            timeTxt.setText(timeContent);
        }

        @SuppressLint("WrongViewCast")
        public myViewHolder(View itemView) {
            super(itemView);
            messageTxt = itemView.findViewById(R.id.message_TextView);
            timeTxt = itemView.findViewById(R.id.id_tvTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (myItemClickListener!=null){
                        myItemClickListener.onItemClick(v,getPosition());
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getFrom();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1){
        return new myViewHolder(inflater.inflate(R.layout.chatbubble_left,null));
        }else
        {
            return new myViewHolder(inflater.inflate(R.layout.chatbubble_rigth,null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        myViewHolder vh = (myViewHolder)holder;
        vh.setMessageTxt(messageList.get(position).getMsgContent());
        String timeContent = convertTime(messageList.get(position).getTime());
        vh.setTimeTxt(timeContent);
    }

    @Override
    public int getItemCount() {
        return messageList == null?0:messageList.size();
    }

    public enum ChatMessageType{
        FROM(0),TO(1);
        private final int type;
        private ChatMessageType(int type){
            this.type = type;
        }
    }
    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }


    private String convertTime(long time){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
        Date date = new Date(time);
        return formatter.format(date);
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        myItemClickListener =listener;
    }

}
