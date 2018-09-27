package com.diva.appdemo.Activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diva.appdemo.R;
import com.diva.appdemo.Service.BlueToothService;
import com.huashi.bluetooth.ExcelUtlis;
import com.huashi.bluetooth.IDCardInfo;

import java.util.ArrayList;
import java.util.List;

public class IDCReaderActivity extends AppCompatActivity {
    private static final String TAG = "IDCReaderActivity";
    private TextView tv_sam, tv_info;
    private ImageView iv;
    private Button btconn, btread, btclose, btsleep, btweak,btService,btListener;
    String filepath = "";
    private View diaView;
    private TextView tv_ts;
    private ListView lv;
    private Button scanf;
    public ProgressDialog connectDialog;

    int initResult;
    boolean isConn;
    boolean isSleep;
    public static MyAdapter adapter;
    public static List<BluetoothDevice> bundDevices;
    public static List<BluetoothDevice> notDevices;
    private BlueToothService.BLTBinder bltBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bltBinder = (BlueToothService.BLTBinder)service;
            bltBinder.onInit();
            Log.e(TAG, "onServiceConnected: " );
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: " );
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExcelUtlis.createXls();
        initView();
        initData();
        if (BlueToothService.api == null) {
            initService();
            Log.e(TAG, "onCreate: init" );
        }else {
            isConn = true;
            bindService();
            Log.e(TAG, "onCreate: bind" );
        }

        btService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
        initListener();
        btconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initResult == -1)
                    return;
                bltBinder.onSetmInterface();
                showConnectDialog();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: " );
    }

    private void initListener() {


        btclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int closeResult = bltBinder.onUninit();
                if (closeResult == 0){
                    isConn = false;
                    iv.setImageBitmap(null);
                    tv_info.setText("设备已断开");
                } else {
                    Toast.makeText(IDCReaderActivity.this, "断开失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        btsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConn){
                    bltBinder.onSleep();
                    isSleep = true;
                    iv.setImageBitmap(null);
                    tv_info.setText("设备已休眠");
                }
            }
        });
        btweak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConn) {
                    bltBinder.onWeak();
                    isSleep = false;
                    iv.setImageBitmap(null);
                    tv_info.setText("设备已唤醒");
                }
            }
        });

        btread.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!isConn){
                    Toast.makeText(IDCReaderActivity.this,"已断开连接",Toast.LENGTH_SHORT).show();
                    return;
                }

                bltBinder.onAut();
                 bltBinder.Authenticate(200);
                int readResult = bltBinder.onRead(1500);
                if (readResult == 1){
                     IDCardInfo ic=BlueToothService.ic;
                    Toast.makeText(IDCReaderActivity.this,"读卡成功",Toast.LENGTH_SHORT).show();
                    tv_info.setText("姓名：" + ic.getPeopleName() + "\n" + "性别：" + ic.getSex() + "\n" + "民族：" + ic.getPeople()
                            + "\n" + "出生日期：" + ic.getBirthDay() + "\n" + "地址：" + ic.getAddr() + "\n" + "身份号码："
                            + ic.getIDCard() + "\n" + "签发机关：" + ic.getDepartment() + "\n" + "有效期限：" + ic.getStrartDate()
                            + "-" + ic.getEndDate() + "\n");
                }else {
                    tv_info.setText("读卡错误");
                }
            }
        });
    }

    private void initService() {
        Intent BLTIntent = new Intent(IDCReaderActivity.this,BlueToothService.class);
        new serviceTask().execute(BLTIntent);
    }


    private void bindService() {
        Intent BLTIntent = new Intent(IDCReaderActivity.this,BlueToothService.class);
        bindService(BLTIntent,connection,BIND_AUTO_CREATE);
    }


    private void initData() {
        adapter = new MyAdapter();


    }

    private void showConnectDialog() {
        if (isConn){
            Toast.makeText(this,"已连接设备",Toast.LENGTH_SHORT).show();
            return;
        }
        if (bundDevices != null && notDevices != null) {
            bundDevices.clear();
            notDevices.clear();
        }
        final AlertDialog dialog;
        diaView = View.inflate(IDCReaderActivity.this, R.layout.test, null);
        tv_ts = (TextView) diaView.findViewById(R.id.tv_ts);
        lv = (ListView) diaView.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        scanf = (Button) diaView.findViewById(R.id.scanf);
        final AlertDialog.Builder builder = new AlertDialog.Builder(IDCReaderActivity.this);
        builder.setView(diaView);
//        builder.setCancelable(false);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == bundDevices.size() + 1) {
                    return;
                }
                Log.i(TAG, "onItemClick: 正在连接");
                dialog.dismiss();
                BluetoothDevice d = null;
                if (position < bundDevices.size() + 1) {
                    d = bundDevices.get(position - 1);
                } else {
                    d = notDevices.get(position - 2 - bundDevices.size());
                }
                new connectAsyncTask().execute(d.getAddress());
            }
        });
        scanf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bltBinder.onScanf();
                tv_ts.setVisibility(View.INVISIBLE);
                scanf.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wltlib";// 授权目录
        bundDevices = new ArrayList<>();
        notDevices = new ArrayList<>();
        setContentView(R.layout.main);
        iv = (ImageView) findViewById(R.id.iv);
        tv_info = (TextView) findViewById(R.id.tv);
        tv_sam = (TextView) findViewById(R.id.sam);
        btconn = (Button) findViewById(R.id.btconn);
        btread = (Button) findViewById(R.id.btread);
        btclose = (Button) findViewById(R.id.btclose);
        btsleep = (Button) findViewById(R.id.btsleep);
        btweak = (Button) findViewById(R.id.btweak);
        btService = findViewById(R.id.btService);
        btListener = findViewById(R.id.btListener);
    }

   public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bundDevices.size() + notDevices.size() + 2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                TextView tv = new TextView(IDCReaderActivity.this);
                tv.setBackgroundResource(R.color.app_item);
                tv.setTextColor(Color.WHITE);
                tv.setText("绑定设备");
                return tv;
            }
            if (position == bundDevices.size() + 1) {
                TextView tv = new TextView(IDCReaderActivity.this);
                tv.setBackgroundResource(R.color.app_item);
                tv.setTextColor(Color.WHITE);
                tv.setText("其他设备");
                return tv;
            }
            View v = null;
            IDCReaderActivity.MyAdapter.ViewHodler hodler;
            BluetoothDevice device = null;
            if (position < bundDevices.size() + 1) {
                device = bundDevices.get(position - 1);
            } else {
                device = notDevices.get(position - 2 - bundDevices.size());
            }
            if (convertView != null && convertView instanceof LinearLayout) {
                v = convertView;
                hodler = (IDCReaderActivity.MyAdapter.ViewHodler) convertView.getTag();
            } else {
                v = View.inflate(IDCReaderActivity.this, R.layout.dialog, null);
                hodler = new IDCReaderActivity.MyAdapter.ViewHodler();
                hodler.tv_name = (TextView) v.findViewById(R.id.name);
                hodler.tv_address = (TextView) v.findViewById(R.id.mac);
                convertView = v;
                convertView.setTag(hodler);
            }
            hodler.tv_name.setText(device.getName());
            hodler.tv_address.setText(device.getAddress());
            return v;
        }

        class ViewHodler {
            private TextView tv_name, tv_address;
        }
    }
    class connectAsyncTask extends AsyncTask<String,Integer,Integer>{
        @Override
        protected void onPreExecute() {
            connectDialog = new ProgressDialog(IDCReaderActivity.this);
            connectDialog.setMessage("正在连接");
            connectDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int result  = bltBinder.onConnect(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            connectDialog.dismiss();
            super.onPostExecute(integer);
            if (integer == 0){
                Toast.makeText(IDCReaderActivity.this,"已连接",Toast.LENGTH_SHORT).show();
                String sam = bltBinder.Get_SAM(500);
                tv_sam.setText("SAM：" + sam);
                isConn = true;
            }else {
                Toast.makeText(IDCReaderActivity.this,"连接失败",Toast.LENGTH_SHORT).show();

            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class serviceTask extends AsyncTask<Intent,Integer,Integer>{
        ProgressDialog serviceDialog;
        @Override
        protected void onPreExecute() {
            serviceDialog = new ProgressDialog(IDCReaderActivity.this);
            serviceDialog.setMessage("正在开启服务");
            serviceDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Intent[] intents) {

            startService(intents[0]);
            bindService(intents[0],connection,BIND_AUTO_CREATE);

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            serviceDialog.dismiss();
            super.onPostExecute(result);
            if (result == 1){
                Toast.makeText(IDCReaderActivity.this,"初始化成功",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onPostExecute:初始化成功 " );
            }else {
                Toast.makeText(IDCReaderActivity.this,"初始化失败",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onPostExecute:初始化失败 " );
            }
        }
    }
}