package com.diva.appdemo.Service;

import android.accounts.OnAccountsUpdateListener;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.diva.appdemo.Activity.ChatActivity2;
import com.diva.appdemo.Activity.LoginActivity;
import com.diva.appdemo.Manager.BaseActivity;
import com.diva.appdemo.Manager.ChatRecordDB;
import com.diva.appdemo.R;
import com.diva.appdemo.Utils.MyXMPPTCPConnection;
import com.diva.appdemo.Utils.NetworkUtil;
import com.diva.appdemo.model.Friend;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

public class MyService extends Service implements ChatManagerListener, ChatMessageListener,ConnectionListener, RosterListener{


    private static final int OFFLINE = 0;
    private static final int ONLINE = 1;
public static String username;
public static String password;
public static String user;
public static MyXMPPTCPConnection connection;
private static final String TAG = "TAG-Service";
private ChatManager chatManager;
public static Handler handler;
private XMPPBinder mBinder = new XMPPBinder();
private Chat mChat;
private Message mMessage;



    /**
     * Service继承方法
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: " );
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: " );
        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connection= MyXMPPTCPConnection.getInstance();
        connection.addConnectionListener(this);
        showInfo("onStartCommand:");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
   public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        MyXMPPTCPConnection.clear();
        username=null;
        password=null;
        super.onDestroy();
    }



    /**
     * ConnectionListener继承方法
     * @param connection
     */

    @Override
    public void connected(XMPPConnection connection) {
        Log.e(TAG, "connected: " );
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        Log.e(TAG, "authenticated: " );
    }

    @Override
    public void connectionClosed() {
        Log.e(TAG, "connectionClosed: " );
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.e(TAG, "connectionClosedOnError: " );
    }

    @Override
    public void reconnectionSuccessful() {
        Log.e(TAG, "reconnectionSuccessful: " );
    }

    @Override
    public void reconnectingIn(int seconds) {
        Log.e(TAG, "reconnectingIn: " );
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.e(TAG, "reconnectionFailed: " );
    }


    /**
     * RosterListener继承方法
     * @param addresses
     */
    @Override
    public void entriesAdded(Collection<String> addresses) {
        Log.e(TAG, "entriesAdded: " );
    }

    @Override
    public void entriesUpdated(Collection<String> addresses) {
        Log.e(TAG, "entriesUpdated: " );
    }

    @Override
    public void entriesDeleted(Collection<String> addresses) {
        Log.e(TAG, "entriesDeleted: " );
    }

    @Override
    public void presenceChanged(Presence presence) {
        Log.e(TAG, "presenceChanged: " );
    }


    /**
     *
     * @param chat
     * @param createdLocally
     */
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        chat.addMessageListener(this);
        Log.e(TAG, "chatCreated: " );
    }

    /**
     * ChatMessageListener继承方法
     * 当接收到消息时，回调此方法
     * @param chat
     * @param message
     */
    @Override
    public void processMessage(Chat chat, final Message message) {
        if (message.getBody() != null) {
            final String sender = chat.getParticipant().split("/")[0];
            Log.i(TAG, "processMessage: "+chat.getParticipant()+","+message.getFrom()+",msg:"
            +message.getBody());
            handler=new Handler(Looper.getMainLooper());
            handler.post(new Runnable(){
                public void run(){
                    getNotificationManager().notify(message.getFrom().hashCode(),getNotification("您有新的消息来自"+sender,sender));
                }
            });
            ChatRecordDB.saveChatMessage(message);
        }

    }



    /**
     * 自定义的方法
     */

    /**
     * 服务绑定类
     */
    public class XMPPBinder extends Binder{
        public void initChatManager(){
            if(connection != null){
                chatManager = ChatManager.getInstanceFor(connection);
                chatManager.addChatListener(MyService.this);
            }
        }
    }
    /**
     * 获取通知管理类
     * @return
     */
    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     *
     */
    private Notification getNotification(String title,String friendJid){
        Intent chatIntent = new Intent(this, ChatActivity2.class);
        chatIntent.putExtra("friend_jid", friendJid);
        chatIntent.putExtra("type","service");
        chatIntent.putExtra("test","@");
        PendingIntent pi = PendingIntent.getActivity(this,friendJid.hashCode(),chatIntent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        builder.setSound(Uri.parse("android.resource://"+ getPackageName() + "/" +R.raw.messagering));
        builder.setSmallIcon(R.drawable.ic_launcher).
                setAutoCancel(true);
        return builder.build();
    }


    /**
     * 登录
     */
    public static void loginService(){
        try {

            if (!connection.isConnected()){
                connection.connect();
            }

            if (connection.getUser()==null)
            {
                connection.login(username, password);
                Log.e(TAG, "doInBackground: connected");}
           showInfo("onLogin:");
            LoginActivity.LoginState = LoginActivity.LOGIN_SUCCESS;
        } catch (SmackException e) {
            LoginActivity.LoginState = LoginActivity.SMACK_EXCEPTION;
            e.printStackTrace();
        } catch (IOException e) {
            LoginActivity.LoginState = LoginActivity.IO_EXCEPTION;
            e.printStackTrace();
        } catch (XMPPException e) {
            LoginActivity.LoginState = LoginActivity.XMPP_EXCEPTION;
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: " +intent.getPackage());
        return super.onUnbind(intent);
    }


    public static  void showInfo(String tag){
        Log.e(TAG, tag
                +"user:"+connection.getUser()
                +",port:"+connection.getPort()
                +",isConnected:"+connection.isConnected()
                +",isAnonymous:"+connection.isAnonymous()
        +",isAuthenticated:"+connection.isAuthenticated()
        +",isSmAvailable:"+connection.isSmAvailable()
        +",isSocketClosed:"+connection.isSocketClosed()
        +",isDisconnectedButSmResumptionPossible:"+connection.isDisconnectedButSmResumptionPossible()
        +",isUsingCompression:"+connection.isUsingCompression());
    }


    public static void checkOnline(NetworkUtil.onlineListener listener, Context context){
        NetworkUtil.showOnline(MyService.connection.getUser().split("/")[0],listener);
        Log.e(TAG, "checkOnline: " );
    }

}
