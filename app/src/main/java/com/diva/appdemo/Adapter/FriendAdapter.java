package com.diva.appdemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.diva.appdemo.R;
import com.diva.appdemo.model.Friend;

import java.util.List;


/**
 * Created by Administrator on 2017/7/14.
 */

public class FriendAdapter extends BaseAdapter {

    private List<Friend> friends;
    private Context context;
    private LayoutInflater layoutInflater;

    public FriendAdapter(Context context, List<Friend> friends) {
        this.context = context;
        this.friends = friends;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_friend, parent, false);
            holder = new ViewHolder();
            holder.tv_friend =  convertView.findViewById(R.id.friendName);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_friend.setText(friends.get(position).getJid().split("@")[0]);
        return convertView;
    }

    static class ViewHolder{
        TextView tv_friend;
    }
}
