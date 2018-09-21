package com.diva.appdemo.Manager;


import android.util.Log;

import com.diva.appdemo.Activity.LoginActivity;
import com.diva.appdemo.Entity.ChatMsg;
import com.diva.appdemo.model.ChatMessage;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘迪 on 2018/9/18 16:40.
 * 邮箱：divaliu1408@qq.com
 */

public class ChatRecordDB {


    private static final String TAG = "ChatRecordDB";

    private static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("ChatRecords.db")
            .setTableCreateListener(new DbManager.TableCreateListener() {
                @Override
                public void onTableCreated(DbManager db, TableEntity<?> table) {
                    Log.i(TAG, "onTableCreated: "+table.getName());
                }
            })
            .setAllowTransaction(true)
            .setDbVersion(1)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    Log.i(TAG, "onUpgrade: "+"from "+oldVersion+" to "+newVersion);
                }
            });
   static private   DbManager chatRecordsDB = x.getDb(daoConfig);

    /**
     * 向本地数据库保存聊天记录
     * @param message
     */
  static public void saveChatMessage(Message message){

      ChatMsg msg = new ChatMsg();
      msg.setFrom(message.getFrom().split("/")[0]);
      msg.setTo(message.getTo().split("/")[0]);
      msg.setTime(System.currentTimeMillis());
      msg.setContent(message.getBody());
      try {
          chatRecordsDB.save(msg);
          Log.i(TAG, "saveChatMessage: "+msg.toString());
      } catch (DbException e) {
          e.printStackTrace();
      }

  }
    static public void saveChatMessage(ChatMsg msg){
        try {
            chatRecordsDB.save(msg);
            Log.i(TAG, "saveChatMessage: "+msg.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    static public void deleteChatMessage(ChatMsg msg){
        try {
            chatRecordsDB.delete(msg);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    static public void deleteChatMessage(ChatMessage message){
        long time = message.getTime();
        WhereBuilder b = WhereBuilder.b();
        b.and("time","=",time);
        ChatMsg msg = new ChatMsg();
        try {
            msg = chatRecordsDB.selector(ChatMsg.class).where(b).findFirst();
            chatRecordsDB.delete(msg);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询聊天记录
     * @param from
     * @param to
     */
  static public List<ChatMsg> selectChatMessage(String from,String to){
      List<ChatMsg> chatMsgs = null;
      WhereBuilder b = WhereBuilder.b();
      b.and("from","=",from);
      b.and("to","=",to);
      try {
        chatMsgs = chatRecordsDB.selector(ChatMsg.class).where(b).findAll();
//        for (ChatMsg chatMsg:chatMsgs){
//            Log.e(TAG, "selectChatMessage: "+chatMsg.toString() );
//        }
      } catch (DbException e) {
          e.printStackTrace();
      }
      return chatMsgs;
  }

}
