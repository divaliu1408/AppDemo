package com.diva.appdemo.Utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by 刘迪 on 2018/9/19 17:36.
 * 邮箱：divaliu1408@qq.com
 */

public class NetworkUtil {
    private static final String TAG = "NetworkUtil";
    public interface onlineListener{
        void online();
        void offline();
    }

   static class checkOfflineTask extends AsyncTask<String, Integer, Boolean>{
        private onlineListener mListener;

        public checkOfflineTask(onlineListener listener){
            mListener = listener;
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            boolean isOnline;
            final String strUrl = "http://"+MyXMPPTCPConnection.HOST+":"+MyXMPPTCPConnection.ADMIN_PORT+"/plugins/presence/status?jid="+strings[0]+"&type=xml";
            isOnline= IsUserOnLine(strUrl) == 1? true:false;
            Log.e(TAG, strings[0]+" showOnline: "+ isOnline);
            return  isOnline;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                Log.e(TAG, "onPostExecute: online true" );
                mListener.online();

            }else {
                Log.e(TAG, "onPostExecute: online false" );
                mListener.offline();
            }
        }
    }


    /**
     * 判断当前用户是否在线
     * @param Jid
     * @param listener
     */
    @SuppressLint("StaticFieldLeak")
    public static void showOnline(final String Jid,onlineListener listener) {
       checkOfflineTask task=new checkOfflineTask(listener);
        task.execute(Jid);
    }

    /**
     * 判断在线的网络请求
     * @param strUrl
     * @return
     */
    private   static short IsUserOnLine(String strUrl)
    {
        short shOnLineState    = 0;    //-不存在-

        try
        {
            URL oUrl = new URL(strUrl);
            URLConnection oConn = oUrl.openConnection();
            if(oConn!=null)
            {
                BufferedReader oIn = new BufferedReader(new InputStreamReader(oConn.getInputStream()));
                if(null!=oIn)
                {
                    String strFlag = oIn.readLine();
                    oIn.close();

                    if(strFlag.indexOf("type=\"unavailable\"")>=0)
                    {
                        shOnLineState = 2;
                    }
                    if(strFlag.indexOf("type=\"error\"")>=0)
                    {
                        shOnLineState = 0;
                    }
                    else if(strFlag.indexOf("priority")>=0 || strFlag.indexOf("id=\"")>=0)
                    {
                        shOnLineState = 1;
                    }
                }
            }
        }
        catch(Exception e)
        {e.printStackTrace();}
        return     shOnLineState;
    }
}
