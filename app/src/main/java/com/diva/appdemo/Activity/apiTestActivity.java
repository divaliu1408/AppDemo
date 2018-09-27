package com.diva.appdemo.Activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diva.appdemo.Entity.Info;
import com.diva.appdemo.R;
import com.diva.appdemo.Service.BlueToothService;
import com.huashi.bluetooth.IDCardInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import static com.diva.appdemo.Service.BlueToothService.ic;

public class apiTestActivity extends AppCompatActivity {

    EditText etParam1 ;
    EditText etParam2 ;
    EditText etParam3;
    Button btnPost;
    TextView tvResult;
    Button btnShow;
    Button btnRead;

    private BlueToothService.BLTBinder bltBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bltBinder = (BlueToothService.BLTBinder)service;
            bltBinder.onInit();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bltBinder.onUninit();
        }
    };
    private static final String TAG = "apiTest";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0:
                    tvResult.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);
        etParam1 = findViewById(R.id.et_param1);
        etParam2 = findViewById(R.id.et_param2);
        etParam3 = findViewById(R.id.et_param3);
        btnPost = findViewById(R.id.btn_postTest);
        btnShow = findViewById(R.id.btn_showInfo);
        btnRead = findViewById(R.id.btn_readIdInfo);
        tvResult = findViewById(R.id.apiResult);
        btnPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                uploadRecord();
//                getHospitalList(param1, param2, param3);

            }


        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText(Info.toShow());
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readIdInfo();

            }
        });
        Intent bltIntent = new Intent(apiTestActivity.this, BlueToothService.class);
        bindService(bltIntent,connection,BIND_AUTO_CREATE);
    }

    private void readIdInfo() {
        new readAsyncTask().execute(ic);

}
    private void uploadRecord(){
        String url = "http://192.168.1.146:8080/record/upload?userId=1140&handover=true";
        RequestParams params = new RequestParams(url);
        params.addHeader("Content-Type", "application/json");
        params.setBodyContent(record);
        x.http().post(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 0;
                msg.obj = result;
                handler.sendMessage(msg);
                Log.e(TAG, "onSuccess: "+result );
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError: " +ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "onCancelled: "+cex.toString() );
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished: " );
            }
        });
    }








    private void getHospitalList() {
        String param1 = etParam1.getText().toString();
        String param2 = etParam2.getText().toString();
        String param3 = etParam3.getText().toString();
        String url = "http://121.42.150.214:8777/carController/lujingGuiHua";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userName",param3);
        params.addBodyParameter("dis_longitude",param1);
        params.addBodyParameter("dis_latitude",param2);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 0;
                msg.obj = result;
                handler.sendMessage(msg);
                Log.e(TAG, "onSuccess: "+result );
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError: "+ex.toString() );
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "onCancelled: "+cex.toString() );
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished: " );
            }
        });
    }


    String record = " {\"rescueStageId\":10,\"ecgInfoList\":[],\"institutionId\":1,\"recognitionCode\":\"2143091309473501\",\"intelligentAdvice\":\"建议保守治疗，包括予高流量吸氧、强心、利尿、扩张冠状动脉等治疗。连续心电监护，监测心率、心律、血压、内生肌酐、心肌酶谱等动态演变。密切观察病情变化，及时处理。\",\"ccr\":126,\"treatmentRegisters\":[{\"unit\":\"mg\",\"medicineId\":36,\"medicineName\":\"吗啡\",\"treatmentId\":36,\"medicineMethodName\":\"静滴\",\"value\":\"3\",\"treatTimeString\":\"2018-09-25 10:31:00\",\"medicineMethodId\":3},{\"unit\":\"mg\",\"medicineId\":35,\"medicineName\":\"静滴硝酸甘油\",\"treatmentId\":35,\"medicineMethodName\":\"静滴\",\"value\":\"5\",\"treatTimeString\":\"2018-09-25 10:31:00\",\"medicineMethodId\":3},{\"value\":\"\",\"treatTimeString\":\"2018-09-25 10:49:00\",\"unit\":\"\",\"medicineId\":11,\"medicineName\":\"心肺复苏\",\"treatmentId\":11,\"medicineMethodName\":\"\"},{\"value\":\"\",\"treatTimeString\":\"2018-09-25 10:49:00\",\"unit\":\"null\",\"medicineId\":334,\"medicineName\":\"静脉输液\",\"treatmentId\":334,\"medicineMethodName\":\"\"}],\"intelligentDiagnose\":\"中危\",\"anshanRecord\":{\"tigejianche_bailv\":\"60\",\"recognitionCode\":\"2143091309473501\",\"fabingdidian\":\"\",\"tel\":\"\",\"jiuzhenfangshi\":\"\",\"jianyanjiancha\":\"急诊彩超\",\"shenzhi_yishi\":\"昏迷\",\"yihuqianming\":\"\",\"jiacengleixing\":\"B型\",\"menZhenId\":\"\",\"zhiliaocelue\":\"外科手术\",\"chubuzhenduan\":\"诊断中,非ACS心源性胸痛 (\",\"chubuzhenduan_other\":\"\",\"shenzhi_Killip\":\"5\",\"huanzhequxiang\":\"\",\"tigejianche_huxi\":\"5\"},\"graceScore\":130,\"symptomContent\":\"情绪激动后持续性，间断性胸闷\\/胸痛、心肺复苏伴出汗本次胸痛持续≥20min，无法缓解\",\"patient\":{\"sex\":2,\"certID\":\"\",\"address\":\"2清华大学\",\"age\":30,\"name\":\"李想\"},\"medicalTests\":[{\"valueToShow\":\"60\",\"valueOfTest\":\"60\",\"testItemId\":2,\"timeString\":\"2018-09-25 10:30:25\"},{\"valueToShow\":\"62\\/92\",\"valueOfTest\":\"62\\/92\",\"testItemId\":3,\"timeString\":\"2018-09-25 10:30:29\"},{\"valueToShow\":\"50\",\"valueOfTest\":\"50\",\"testItemId\":1,\"timeString\":\"2018-09-25 10:30:35\"},{\"valueToShow\":\"50\",\"valueOfTest\":\"正常\",\"testItemId\":12,\"timeString\":\"2018-09-25 10:30:00\"},{\"valueToShow\":\"0.02\",\"valueOfTest\":\"正常\",\"testItemId\":19,\"timeString\":\"2018-09-25 10:30:00\"},{\"valueToShow\":\"\\/res\\/upload\\/20180925\\/4a75c769-ffc2-4cf8-8e35-c77d7da6b58c.png\",\"valueOfTest\":\"\",\"testItemId\":4}],\"timelines\":[{\"actTime\":1537842060000,\"itemId\":6,\"actTimeString\":\"2018-9-25 10:21:00\",\"itemName\":\"院前首份心电图时间\"},{\"actTime\":1536805800000,\"itemId\":7,\"actTimeString\":\"2018-9-13 10:30:00\",\"itemName\":\"肌钙蛋白测试时间\"},{\"actTime\":1537523340000,\"itemId\":9,\"actTimeString\":\"2018-9-21 17:49:00\",\"itemName\":\"远程心电传输时间\"},{\"actTime\":1537523700000,\"itemId\":10,\"actTimeString\":\"2018-9-21 17:55:00\",\"itemName\":\"明确诊断时间\"}],\"registerItemOptions\":[{\"id\":2},{\"id\":54},{\"id\":7},{\"id\":14},{\"id\":17},{\"id\":21},{\"id\":24},{\"id\":32},{\"id\":26},{\"id\":30}],\"createTimeString\":\"2018-09-25 10:21:44\"}\n";

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    class readAsyncTask extends AsyncTask<IDCardInfo,Integer,Integer>{

        @Override
        protected Integer doInBackground(IDCardInfo... idCardInfos) {
            bltBinder.onAut();
            bltBinder.Authenticate(200);
            int result = bltBinder.onRead(1500);
            return result;
        }


        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1){
            Info.saveInfo(ic);
            tvResult.setText(Info.toShow());
            }else {
                Toast.makeText(apiTestActivity.this,"读取出错",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
