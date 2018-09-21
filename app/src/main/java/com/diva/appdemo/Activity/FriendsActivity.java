package com.diva.appdemo.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.diva.appdemo.Adapter.FriendAdapter;
import com.diva.appdemo.Manager.BaseActivity;
import com.diva.appdemo.R;
import com.diva.appdemo.Service.MyService;
import com.diva.appdemo.Utils.MyXMPPTCPConnection;
import com.diva.appdemo.model.Friend;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/14.
 */
public class FriendsActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TAG="FriendActivity";
    private ListView listView;
    private List<Friend> friendList;
    private Button mucButton, confirmButton,showButton;
    private boolean isInInvitationState = false;
    private Context context;
    private MyXMPPTCPConnection connection;
    private Roster roster;
    private String FriendsActivityUserName;
    private String FriendsActivityPassword;
    private MyService.XMPPBinder XMPPBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            XMPPBinder = (MyService.XMPPBinder)service;
            XMPPBinder.initChatManager();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_friends);
        Intent intent = getIntent();
        FriendsActivityUserName = intent.getStringExtra("username");
        FriendsActivityPassword = intent.getStringExtra("password");
        Intent bindIntent = new Intent(this,MyService.class);
        bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
        initView();
        initListener();
        initXMPPTCPConnection();
        friendList = getMyFriends();
        listView.setAdapter(new FriendAdapter(this, friendList));
        context=this;
    }




    private void initXMPPTCPConnection(){
        connection = MyService.connection;
        roster = Roster.getInstanceFor(connection);
    }
    private void initView(){
        listView = (ListView) findViewById(R.id.friends);
        mucButton = (Button) findViewById(R.id.invite_muc_btn);
        confirmButton = (Button) findViewById(R.id.confirmBtn);
        showButton = findViewById(R.id.showInfoBtn);
    }

    private void initListener(){
        listView.setOnItemClickListener(this);
        mucButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
    }

    private List<Friend> getMyFriends(){

        List<Friend> friends = new ArrayList<Friend>();
        //通过Roster.getInstanceFor(connection)获取Roast对象;
        //通过Roster对象的getEntries()获取Set，遍历该Set就可以获取好友的信息了;
        for(RosterEntry entry : Roster.getInstanceFor(connection).getEntries()){
            Friend friend = new Friend(entry.getUser(), entry.getName());
            friends.add(friend);
            Log.i(TAG, "getMyFriends: "+friend.getName()+" "+friend.getJid());
            Log.i(TAG, "getMyName: "+connection.getUser()+" "+connection.getServiceName());
        }
        return friends;
    }

    /**
     * 创建聊天室
     */
//    private void createMucRoom(){
//        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(MyXMPPTCPConnection.getInstance());
//        MultiUserChat muc = manager.getMultiUserChat("room01@conference.192.168.0.245");
//        try {
//            //queen为昵称
//            muc.create("queen");
//            Form form = muc.getConfigurationForm();
//            Form submitForm = form.createAnswerForm();
//
//            for (Iterator fields = form.getFields().iterator(); fields.hasNext();) {
//                FormField field = (FormField) fields.next();
//                if (!FormField.Type.hidden.equals(field.getType()) && field.getVariable() != null) {
//                    // Sets the default value as the answer
//                    submitForm.setDefaultAnswer(field.getVariable());
//                }
//            }
//            List list =  new ArrayList();
//            list.add("20");
//            List owners = new ArrayList();
//            owners.add("guochen@192.168.0.245");
//            submitForm.setAnswer("muc#roomconfig_roomowners", owners);
//            submitForm.setAnswer("muc#roomconfig_maxusers", list);
//            submitForm.setAnswer("muc#roomconfig_roomname", "room01");
//            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
//            submitForm.setAnswer("muc#roomconfig_membersonly", false);
//            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
//            submitForm.setAnswer("muc#roomconfig_enablelogging", true);
//            submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
//            submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
//            submitForm.setAnswer("x-muc#roomconfig_registration", false);
//            muc.sendConfigurationForm(submitForm);
//        } catch (XMPPException.XMPPErrorException e) {
//            e.printStackTrace();
//        } catch (SmackException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 加入聊天室
     */
//    private void joinChatRoom(){
//        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(MyXMPPTCPConnection.getInstance());
//        MultiUserChat muc = manager.getMultiUserChat("room01@conference.192.168.0.245");
//        try {
//            muc.join("TestUser01");
//        } catch (SmackException.NoResponseException e) {
//            e.printStackTrace();
//        } catch (XMPPException.XMPPErrorException e) {
//            e.printStackTrace();
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 邀请进入聊天室
     */
//    private void inviteToTalkRoom(){
//        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(MyXMPPTCPConnection.getInstance());
//        MultiUserChat muc = manager.getMultiUserChat("room01@conference.192.168.0.245");
//        try {
//            muc.join("invitor");
//            muc.addInvitationRejectionListener(new InvitationRejectionListener() {
//                @Override
//                public void invitationDeclined(String invitee, String reason) {
//                    Toast.makeText(FriendsActivity.this, reason, Toast.LENGTH_SHORT).show();
//                }
//            });
//            muc.invite("jaychou@192.168.0.245", "Come on,It's a party!");
//        } catch (SmackException.NoResponseException e) {
//            e.printStackTrace();
//        } catch (XMPPException.XMPPErrorException e) {
//            e.printStackTrace();
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 添加被邀请监听
     */
//    private void addInvitationListener(){
//        final MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(MyXMPPTCPConnection.getInstance());
//        manager.addInvitationListener(new InvitationListener() {
//            @Override
//            public void invitationReceived(XMPPConnection conn, MultiUserChat room, String inviter, String reason, String password, Message message) {
//                try {
//                    manager.decline(room.getRoom(), inviter, "Sorry ! I'm busy right now");
//                } catch (SmackException.NotConnectedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FriendsActivity.this, ChatActivity2.class);
        intent.putExtra("friend_jid", friendList.get(position).getJid());
        intent.putExtra("username",FriendsActivityUserName);
        intent.putExtra("password",FriendsActivityPassword);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invite_muc_btn:
                isInInvitationState = true;
//                createMucRoom();
//                joinChatRoom();
//                inviteToTalkRoom();
                break;
            case R.id.confirmBtn:
                isInInvitationState = false;
                break;
            case R.id.showInfoBtn:
                MyService.showInfo("show:");
            default:
                break;
        }
    }


    public boolean onKeyDown(int KeyCode, KeyEvent event){
        if (KeyCode == KeyEvent.KEYCODE_BACK){
            //当返回键被按下
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("确定要退出登录吗?");
            //设置确定按钮并监听
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    connection.disconnect();
                    if (!connection.isConnected()){
                        Toast.makeText(context,"已退出", Toast.LENGTH_SHORT).show();
                        unbindService(serviceConnection);
                        stopService(new Intent(FriendsActivity.this,MyService.class));
                        Log.i(TAG, "onClick: 已退出");
                        finish();
                    }
                }
            });
            //设置取消按钮监听
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //这里什么也不用做
                }
            });
            dialog.show();
        }
        return false;
    }
}
