package com.diva.appdemo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.diva.appdemo.R;
import com.diva.appdemo.Utils.OnCallBackListener;
import com.diva.appdemo.Utils.RequestResultImpl;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class apiTestActivity extends AppCompatActivity {

    EditText etParam1 ;
    EditText etParam2 ;
    EditText etParam3;
    Button btnPost;
    private RequestResultImpl requestResult;
    private static final String TAG = "apiTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);
        etParam1 = findViewById(R.id.et_param1);
        etParam2 = findViewById(R.id.et_param2);
        etParam3 = findViewById(R.id.et_param3);
        btnPost = findViewById(R.id.btn_postTest);
        btnPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                uploadRecord();
//                getHospitalList(param1, param2, param3);

            }


        });
    }


    private void uploadRecord(){
        String url = "http://121.42.150.214:8777/record/upload?userId=1140&handover=true";
        RequestParams params = new RequestParams(url);
        params.addHeader("Content-Type", "application/json");
        params.setBodyContent(record);
        x.http().post(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
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


    String record = "{\n" +
            "\t\"rescueStageId\": 10,\n" +
            "\t\"ecgInfoList\": [],\n" +
            "\t\"institutionId\": 1,\n" +
            "\t\"recognitionCode\": \"2143082309382401\",\n" +
            "\t\"ccr\": 0,\n" +
            "\t\"treatmentRegisters\": [],\n" +
            "\t\"anshanRecord\": {\n" +
            "\t\t\"tigejianche_bailv\": \"\",\n" +
            "\t\t\"recognitionCode\": \"2143082309382401\",\n" +
            "\t\t\"fabingdidian\": \"\",\n" +
            "\t\t\"tel\": \"\",\n" +
            "\t\t\"jiuzhenfangshi\": \"\",\n" +
            "\t\t\"jianyanjiancha\": \"\",\n" +
            "\t\t\"shenzhi_yishi\": \"\",\n" +
            "\t\t\"yihuqianming\": \"\",\n" +
            "\t\t\"jiacengleixing\": \"\",\n" +
            "\t\t\"menZhenId\": \"\",\n" +
            "\t\t\"zhiliaocelue\": \"\",\n" +
            "\t\t\"chubuzhenduan\": \"\",\n" +
            "\t\t\"chubuzhenduan_other\": \"\",\n" +
            "\t\t\"shenzhi_Killip\": \"\",\n" +
            "\t\t\"huanzhequxiang\": \"\",\n" +
            "\t\t\"tigejianche_huxi\": \"\"\n" +
            "\t},\n" +
            "\t\"symptomContent\": \"\",\n" +
            "\t\"patient\": {\n" +
            "\t\t\"sex\": 1,\n" +
            "\t\t\"certID\": \"\",\n" +
            "\t\t\"address\": \"\",\n" +
            "\t\t\"age\": 35,\n" +
            "\t\t\"name\": \"何平\"\n" +
            "\t},\n" +
            "\t\"medicalTests\": [],\n" +
            "\t\"timelines\": [{\n" +
            "\t\t\"actTime\": 1534990500000,\n" +
            "\t\t\"itemId\": 9,\n" +
            "\t\t\"actTimeString\": \"2018-8-23 10:15:00\",\n" +
            "\t\t\"itemName\": \"远程心电传输时间\"\n" +
            "\t}],\n" +
            "\t\"registerItemOptions\": [],\n" +
            "\t\"createTimeString\": \"2018-08-23 10:21:15\"\n" +
            "}";
}
