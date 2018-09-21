package com.diva.appdemo.Utils;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public  class MyXMPPTCPConnection extends XMPPTCPConnection {

    public static final String SERVER_NAME = "lhx-pc";
    public static final String HOST = "192.168.1.112";
    public static final int ADMIN_PORT = 9090;
    public static final int C2S_PORT = 5222;
    public static final String RESOURCE = "Android";




    private static MyXMPPTCPConnection connection;
    private MyXMPPTCPConnection(XMPPTCPConnectionConfiguration config) {
        super(config);
    }
    public static synchronized MyXMPPTCPConnection getInstance(){
        //初始化XMPPTCPConnection相关配置
        if(connection == null){
            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            //设置连接超时的最大时间
            builder.setConnectTimeout(9000);
            //设置登录openfire的用户名和密码
//            builder.setUsernameAndPassword(username, password);
            //设置安全模式
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            builder.setResource(RESOURCE);
            //设置服务器名称
            builder.setServiceName(SERVER_NAME);
            // 设置主机地址
            builder.setHost(HOST);
            //设置端口号
            builder.setPort(C2S_PORT);
            //是否查看debug日志
            builder.setDebuggerEnabled(true);
            connection = new MyXMPPTCPConnection(builder.build());
            connection.setPacketReplyTimeout(3000);
            Log.e("TAG-XMPP", "getInstance: "
                    +"user:"+connection.getUser()
                    +",port:"+connection.getPort()
                    +",isConnected:"+connection.isConnected() );
        }
        return connection;
    }
    public static synchronized void clear(){
       if (connection!=null)
       {connection.disconnect();}else {
           Log.e("TAG-XMPP", "clear: connection ==null" );
       }
        connection=null;
    }

    @Override
    public void disconnect() {
        Log.e("TAG-XMPP", "disconnect" );
        super.disconnect();
    }
}
