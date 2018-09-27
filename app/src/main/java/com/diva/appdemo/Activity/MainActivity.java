package com.diva.appdemo.Activity;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.diva.appdemo.Manager.MyApplication;
import com.diva.appdemo.Interface.NetworkInterface;

import com.diva.appdemo.Entity.AppInfo;
import com.diva.appdemo.Entity.Student;
import com.diva.appdemo.Entity.StudentInfo;
import com.diva.appdemo.R;
import com.diva.appdemo.Service.MyIntentService;
import com.huashi.bluetooth.IDCardInfo;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.btn_getAppInfo)
    Button btnGetAppInfo;

    @ViewInject(R.id.btn_getStudentInfo)
    Button btnGetStudentInfon;

    @ViewInject(R.id.btn_saveStudentInfo)
            Button btnSaveStudentInfo;
    @ViewInject(R.id.btn_searchStudentInfo)
            Button btn_searchStudentInfo;
    @ViewInject(R.id.btn_dropStudentInfo)
            Button btnDropStudentInfo;
    @ViewInject(R.id.btn_toLogin)
    Button btnToLogin;
    @ViewInject(R.id.btn_startIntentService)
            Button btnStartIntentService;
    @ViewInject(R.id.btn_qrCode)
    Button btnGoToQRCode;
    @ViewInject(R.id.btn_apiTest)
            Button btnGoToApiTest;
    @ViewInject(R.id.btn_idCard)
            Button btnGoToIdCard;


    Retrofit retrofit;
    private static final String TAG="showSomething";
    List<Student> students=new ArrayList<>();
    List<StudentInfo> studentInfos =null;
    DbManager db= MyApplication.getDb();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitRequest();
    }

    private void InitRequest() {
         retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.112:8031/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }




        @Event(R.id.btn_getAppInfo)
        private void getAppInfo(View view) {
            Toast.makeText(this,"getAppInfo",Toast.LENGTH_SHORT).show();
            NetworkInterface request=retrofit.create(NetworkInterface.class);
        Call<AppInfo> call = request.getAppVersion();

        call.enqueue(new Callback<AppInfo>() {
            @Override
            public void onResponse(Call<AppInfo> call, Response<AppInfo> response) {
                Log.i("showSomething", "onResponse: "+response.body().getAppVersion().getName());

            }

            @Override
            public void onFailure(Call<AppInfo> call, Throwable t) {
                Log.i("showSomething", "onFailure: ");
            }
        });
        }


        @Event(R.id.btn_getStudentInfo)
    private void getStudentInfo(View view){
            Toast.makeText(this,"getStudentInfo",Toast.LENGTH_SHORT).show();
            NetworkInterface request=retrofit.create(NetworkInterface.class);
        Call<List<StudentInfo>> call = request.getStudentInfo();

        call.enqueue(new Callback<List<StudentInfo>>() {
            @Override
            public void onResponse(Call<List<StudentInfo>> call, Response<List<StudentInfo>> response) {
                studentInfos=response.body();
                for (StudentInfo studentInfo:studentInfos){
                    Log.i("showSomething", "onResponse: name:"+studentInfo.getName()+",Math:"+studentInfo.getScore().getMath());
            } }

            @Override
            public void onFailure(Call<List<StudentInfo>> call, Throwable t) {
                Log.i("showSomething", "onFailure: "+t);
            }
        });
        }
@Event(R.id.btn_saveStudentInfo)
        private void saveStudentInfo(View view){
if (studentInfos!= null) {
    for (StudentInfo studentInfo : studentInfos) {
        Student student = new Student();
        student.setName(studentInfo.getName());
        student.setChinese(studentInfo.getScore().getChinese());
        student.setEnglish(studentInfo.getScore().getEnglish());
        student.setMath(studentInfo.getScore().getMath());
        student.setSex(studentInfo.getSex());
        student.setsID(studentInfo.getId());
        students.add(student);
    }

    try {
        db.save(students);
        if (db.getTable(Student.class) != null) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
    } catch (DbException e) {
        e.printStackTrace();
    }
}else {
    Toast.makeText(this,"列表为空，请先保存",Toast.LENGTH_SHORT).show();
}

}

    @Event(R.id.btn_searchStudentInfo)
    private void setBtn_searchStudentInfo(View view){
        Student first=null;
        try {
             first= db.findFirst(Student.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (first!=null){

                try {
                    WhereBuilder b=WhereBuilder.b();
                    List<Student> all=db.selector(Student.class).where(b).findAll();
                    for(Student student:all){
                        try {
                            Log.i(TAG, "setBtn_searchStudentInfo: "+student.toString());
                        }catch (Exception e){
                            Log.i(TAG, "setBtn_searchStudentInfo: "+e);
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }else {
                Toast.makeText(this,"列表为空",Toast.LENGTH_SHORT).show();
            }

    }


    @Event(R.id.btn_dropStudentInfo)
    private void dropStudentInfo(View view){
        try {
            db.dropTable(Student.class);
            if (db.getTable(Student.class)==null){
                Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
@Event(R.id.btn_toLogin)
private void toLogIn(View view){
    Intent intent = new Intent(this,LoginActivity.class);
    startActivity(intent);
}


@Event(R.id.btn_sendNotification)
private void sendNotification(View view){
    Log.i(TAG, "sendNotification: executed");
    Intent intent = new Intent(this,LoginActivity.class);
    PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);

    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    Notification notification = new NotificationCompat.Builder(this)
            .setContentTitle("openFireTitle")
            .setContentText("openFileText")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pi)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build();
    manager.notify(1,notification);

}
@Event(R.id.btn_startIntentService)
private void startIntentService(View view){
    Log.i(TAG, "startIntentService: Thread id is "+Thread.currentThread().getId());
    Intent intentService = new Intent(this, MyIntentService.class);
    startService(intentService);

}
@Event(R.id.btn_popWindow)
private void goToPopUpWindow(View view){
    Intent intent = new Intent(MainActivity.this,PopupWindowActivity.class);
    startActivity(intent);
}

@Event(R.id.btn_qrCode)
private void goToQRCode(View view){
    Intent intent = new Intent(MainActivity.this,QRCodeActivity.class);
    startActivity(intent);
}
    @Event(R.id.btn_apiTest)
    private void goToApiTest(View view){
        Intent intent = new Intent(MainActivity.this,apiTestActivity.class);
        startActivity(intent);
    }
    @Event(R.id.btn_idCard)
    private void goToIdCard(View view){
        Intent intent = new Intent(MainActivity.this,IDCReaderActivity.class);
        startActivity(intent);
    }

}

