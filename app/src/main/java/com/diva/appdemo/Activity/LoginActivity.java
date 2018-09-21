package com.diva.appdemo.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.diva.appdemo.Manager.BaseActivity;
import com.diva.appdemo.R;
import com.diva.appdemo.Service.MyService;
import com.diva.appdemo.Utils.MyXMPPTCPConnection;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 刘迪 on 2018/8/8 15:10.
 * 邮箱：divaliu1408@qq.com
 */


/**
 * 不使用Service登录的loginActivity
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements ConnectionListener, RosterListener {
    @ViewInject(R.id.et_username)
    EditText etUsername;
    @ViewInject(R.id.et_password)
    EditText etPassword;
    @ViewInject(R.id.btn_logIn)
    Button btnLogin;
    @ViewInject(R.id.cbox_isRememberPassword)
    CheckBox cboxRemenber;


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public ProgressDialog loginDialog;
    private static final String TAG="loginActivity";
    private XMPPTCPConnection connection;
    private String LoginActivityUserName;
    private String LoginActivityPassword;
    private boolean isRemember;
    public static  int LoginState;
    public static final int SMACK_EXCEPTION = 1;
    public static final int IO_EXCEPTION = 2;
    public static final int XMPP_EXCEPTION = 3;
    public static final int LOGIN_SUCCESS = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        isRemember = pref.getBoolean("remember_password",false);
        checkRemember();
        Log.e(TAG, "onCreate: " );


    }


    /**
     * ConnectionListener继承方法
     * @param connection
     */

    @Override
    public void connected(XMPPConnection connection) {
        Log.d(TAG, "connected: " );
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        Log.d(TAG, "authenticated: " );
    }

    @Override
    public void connectionClosed() {
        Log.d(TAG, "connectionClosed: " );
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.d(TAG, "connectionClosedOnError: " );
    }

    @Override
    public void reconnectionSuccessful() {
        Log.d(TAG, "reconnectionSuccessful: " );
    }

    @Override
    public void reconnectingIn(int seconds) {
        Log.d(TAG, "reconnectingIn: " );
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.d(TAG, "reconnectionFailed: " );
    }


    /**
     * RosterListener继承方法
     * @param addresses
     */
    @Override
    public void entriesAdded(Collection<String> addresses) {
        Log.d(TAG, "entriesAdded: " );
    }

    @Override
    public void entriesUpdated(Collection<String> addresses) {
        Log.d(TAG, "entriesUpdated: " );
    }

    @Override
    public void entriesDeleted(Collection<String> addresses) {
        Log.d(TAG, "entriesDeleted: " );
    }

    @Override
    public void presenceChanged(Presence presence) {
        Log.d(TAG, "presenceChanged: " );
    }




    @Event(R.id.btn_logIn)
    private void Login(View view){
        LoginActivityUserName = etUsername.getText().toString();
        LoginActivityPassword = etPassword.getText().toString();
        connection = MyService.connection;
        postLogin();
    }

    private void checkRemember() {
        if (isRemember){
            //将账号和密码都设置在文本框中
            LoginActivityUserName = pref.getString("username","");
            LoginActivityPassword = pref.getString("password","");
            etUsername.setText(LoginActivityUserName);
            etPassword.setText(LoginActivityPassword);
            cboxRemenber.setChecked(true);
        }
    }

    private void postLogin() {

        List<String> loginList = new ArrayList<>();
        loginList.add(etUsername.getText().toString());
        loginList.add(etPassword.getText().toString());
        setRemember();
        new loginTask().execute(loginList);

    }

    private void setRemember() {
        editor = pref.edit();
        if (cboxRemenber.isChecked()){
            editor.putBoolean("remember_password",true);
            editor.putString("username",LoginActivityUserName);
            editor.putString("password",LoginActivityPassword);
        }else {
            editor.clear();
        }
        editor.apply();
    }

    @SuppressLint("StaticFieldLeak")
    private class loginTask extends AsyncTask<List<String>,Object,Short> {
        @Override
        protected void onPreExecute() {
            loginDialog = new ProgressDialog(LoginActivity.this);
            loginDialog.setMessage("正在登录");
            loginDialog.show();
            super.onPreExecute();
        }

        @Override
        protected final Short doInBackground(List<String>... params) {
            String loginTaskUsername = params[0].get(0);
            String loginTaskPassword = params[0].get(1);

                if (loginTaskUsername == null || loginTaskUsername.equals("")) {
                    return 0;
                } else if (loginTaskPassword == null || loginTaskPassword.equals("")) {
                    return 1;
                } else {
                    MyService.password = loginTaskPassword;
                    MyService.username = loginTaskUsername;
                   MyService.loginService();
                    if (connection.isAuthenticated()) {
                        return 2;
                    }else if (LoginActivity.LoginState > 0) {
                        switch (LoginActivity.LoginState) {
                            case IO_EXCEPTION:
                                Log.d(TAG, "doInBackground: IO_EXCEPTION");
                                break;
                            case XMPP_EXCEPTION:
                                Log.d(TAG, "doInBackground: XMPP_EXCEPTION");
                                break;
                            case SMACK_EXCEPTION:
                                Log.d(TAG, "doInBackground: SMACK_EXCEPTION");
                                break;
                        }
                        return 3;
                    }  else if (!connection.isAuthenticated()) {
                        return 5;
                    } else return 4;
                }



        }



        @Override
        protected void onPostExecute(Short state) {
            loginDialog.dismiss();
            switch (state){
                case 0:
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                   startActivity(new Intent(LoginActivity.this,FriendsActivity.class)
                           .putExtra("username",LoginActivityUserName).putExtra("password",LoginActivityPassword));
                    break;
                case 3:
                    stopService(new Intent(LoginActivity.this,MyService.class));
                    Toast.makeText(LoginActivity.this,"登录出现错误",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    stopService(new Intent(LoginActivity.this,MyService.class));
                    Toast.makeText(LoginActivity.this,"未知错误",Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    stopService(new Intent(LoginActivity.this,MyService.class));
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    break;

                default:break;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkRemember();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, MyService.class);
                startService(intent);

            }
        }).start();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onRestart() {
        checkRemember();
        Log.e(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        LoginActivityUserName = "";
        LoginActivityPassword = "";
        Log.e(TAG, "onPause " );
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart: " );
        super.onStart();
    }
}
