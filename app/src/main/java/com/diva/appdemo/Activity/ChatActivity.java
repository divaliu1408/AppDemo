package com.diva.appdemo.Activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diva.appdemo.Adapter.ChatAdapter;
import com.diva.appdemo.Entity.ChatMsg;
import com.diva.appdemo.Manager.BaseActivity;
import com.diva.appdemo.Manager.ChatRecordDB;
import com.diva.appdemo.R;
import com.diva.appdemo.Service.MyService;
import com.diva.appdemo.Utils.CompareUtil;
import com.diva.appdemo.Utils.MyXMPPTCPConnection;
import com.diva.appdemo.Utils.NetworkUtil;
import com.diva.appdemo.model.ChatMessage;
import com.diva.mylibrary.Utils.LogUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by 刘迪 on 2018/9/14 14:28.
 * 邮箱：divaliu1408@qq.com
 */

public class ChatActivity extends BaseActivity implements ChatManagerListener, ChatMessageListener, View.OnClickListener {
    private ListView chatListView;
    private EditText et_chat;
    private Button sendBtn;
    private ChatManager chatManager;
    private List<ChatMessage> messageList;
    private String friendJid;
    private Chat chat;
    private ChatAdapter adapter;
    private static final String TAG = "ChatActivity";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.XMPPBinder XMPPBinder = (MyService.XMPPBinder) service;
            XMPPBinder.initChatManager();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0:
                    ChatMessage chatMessage = new ChatMessage((String) msg.obj, 1);
                    messageList.add(chatMessage);
                    adapter.notifyDataSetChanged();
                    chatListView.setSelection(messageList.size() - 1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Intent intent = getIntent();
        friendJid = intent.getStringExtra("friend_jid");
        Log.e(TAG, friendJid+",onCreate");
    }

    private void initView(){
        chatListView = (ListView) findViewById(R.id.chatListView);
        et_chat = (EditText) findViewById(R.id.chatEditText);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        TextView headView = findViewById(R.id.typeState);
        headView.setText(friendJid.split("@")[0]);
    }

    private void initListener(){
        sendBtn.setOnClickListener(this);
    }

    private void initChatManager(){
        MyXMPPTCPConnection connection = MyService.connection;
//        connection = MyXMPPTCPConnection.getInstance();
        if(connection != null){
            chatManager = ChatManager.getInstanceFor(connection);
            chatManager.addChatListener(this);
        }
    }

    private void initChat(){
        if(chatManager != null){
            //第一个参数是 用户的ID
            //第二个参数是 ChatMessageListener，我们这里传null就好了
            chat = chatManager.createChat(friendJid, this);
        }
    }

    private void sendChatMessage(String msgContent){
        ChatMessage chatMessage = new ChatMessage(msgContent, 0);
        messageList.add(chatMessage);
        if(chat != null){
            try {
                //发送消息，参数为发送的消息内容
                chat.sendMessage(msgContent);
                et_chat.setText("");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
        ChatMsg sendMessage = new ChatMsg();
        sendMessage.setTime(System.currentTimeMillis());
        sendMessage.setTo(chat.getParticipant());
        sendMessage.setFrom(MyService.connection.getUser().split("/")[0]);
        sendMessage.setContent(msgContent);
        ChatRecordDB.saveChatMessage(sendMessage);
        adapter.notifyDataSetChanged();
        chatListView.setSelection(messageList.size() - 1);
    }

    //ChatListener中需要重写的方法
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        //在这里面给chat对象添加ChatMessageListener
        chat.addMessageListener(this);
    }

    //ChatMessageListener中需要重写的方法
    //当接收到对方发来的消息的时候，就会回调processMessage方法
    @Override
    public void processMessage(Chat chat, Message message) {
        if(message.getType().equals(Message.Type.chat) || message.getType().equals(Message.Type.normal)){
            if(message.getBody() != null &&message.getFrom().split("/")[0].equals(friendJid)){
                Log.i(TAG, "processMessage: chat.participant"+chat.getParticipant()+"message.From()"+message.getFrom()
                        +",friendJid:"+friendJid);
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 0;
                msg.obj = message.getBody();
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBtn:
                if(!TextUtils.isEmpty(et_chat.getText().toString())){
                    sendChatMessage(et_chat.getText().toString());
                }else{
                    Toast.makeText(ChatActivity.this, "消息不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        Log.e(TAG, friendJid+",onDestroy ");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, friendJid+",onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, friendJid+",onStop");
    }

    @Override
    protected void onResume() {
        Log.e(TAG, friendJid+",onResume");
        messageList = new ArrayList<ChatMessage>();
        initView();
        initListener();
        initChatManager();
        initChat();
        Intent bindIntent = new Intent(this,MyService.class);
        bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
        getChatList();
        super.onResume();
    }

    private void getChatList() {
        String me = MyService.connection.getUser().split("/")[0];
        String firend = chat.getParticipant();
        List<ChatMsg> chatReceiveMsgs = ChatRecordDB.selectChatMessage(firend,me );
        List<ChatMsg> chatSendMsgs = ChatRecordDB.selectChatMessage(me, firend);
       if (chatReceiveMsgs!=null){for (ChatMsg msg : chatReceiveMsgs) {
            ChatMessage message = new ChatMessage(msg.getContent(),1,msg.getTime());
                messageList.add(message);
        }}
        if (chatSendMsgs!=null){for (ChatMsg msg : chatSendMsgs) {
            ChatMessage message = new ChatMessage(msg.getContent(),0,msg.getTime());
            messageList.add(message);
        }}
        Comparator comp = new CompareUtil();
        Collections.sort(messageList, comp);
        adapter = new ChatAdapter(ChatActivity.this, messageList);
        chatListView.setAdapter(adapter);
        chatListView.setSelection(messageList.size() - 1);
    }
    @Override
    protected void onStart() {
        Log.e(TAG, friendJid+",onStart" );
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Intent newintent = intent;
        friendJid = newintent.getStringExtra("friend_jid");
        Log.e(TAG, friendJid+",onNewIntent");
        super.onNewIntent(intent);
    }
}
