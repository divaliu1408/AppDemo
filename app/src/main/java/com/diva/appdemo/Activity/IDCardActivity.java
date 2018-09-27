package com.diva.appdemo.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.diva.appdemo.Entity.Info;
import com.diva.appdemo.R;
import com.huashi.bluetooth.ExcelUtlis;
import com.huashi.bluetooth.HSBlueApi;
import com.huashi.bluetooth.HsInterface;
import com.huashi.bluetooth.IDCardInfo;

public class IDCardActivity extends Activity {
    private static final String TAG = "IDCardActivity";

    private TextView tv_sam, tv_info;
    private ImageView iv;
    private Button btconn, btread, btclose, btsleep, btweak;
    String filepath = "";
    boolean isSlepp = false;

    private HSBlueApi api;
    private IDCardInfo ic;
    private boolean isConn = false;
    int ret;


    private View diaView;
    private TextView tv_ts;
    private ListView lv;
    private Button scanf;
    private MyAdapter adapter;
    private List<BluetoothDevice> bundDevices;
    private List<BluetoothDevice> notDevices;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isConn) {
            api.unInit();
            isConn = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("UUUU", Environment.getExternalStorageDirectory().toString());
        ExcelUtlis.createXls();
        initView();
        initData();
    }

    private void initData() {
        adapter = new MyAdapter();
        api = new HSBlueApi(this);
        ret = api.init();
        if (ret == -1)
            Toast.makeText(this, "初始化失败", Toast.LENGTH_LONG).show();
        api.setmInterface(new HsInterface() {
            @Override
            public void reslut2Devices(Map<String, List<BluetoothDevice>> map) {
                bundDevices = map.get("bind");
                notDevices = map.get("notBind");
                adapter.notifyDataSetChanged();
            }
        });
        btconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ret == -1)
                    return;
                showDialog();
            }
        });

        btclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ret = api.unInit();
                if (ret == 0) {
                    isConn = false;
                    iv.setImageBitmap(null);
                    tv_info.setText("设备已断开");
                } else {
                    Toast.makeText(IDCardActivity.this, "断开失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        btsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConn) {
                    api.sleep();
                    isSlepp = true;
                    iv.setImageBitmap(null);
                    tv_info.setText("设备已休眠");
                }
            }
        });

        btweak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConn) {
                    api.weak();
                    isSlepp = false;
                    iv.setImageBitmap(null);
                    tv_info.setText("设备已唤醒");
                }
            }
        });

        btread.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!isConn)
                    return;
                api.aut();
                ret = api.Authenticate(200);
//                if (ret == 1) {
                ic = new IDCardInfo();
                ret = api.Read_Card(ic, 1500);
                if (ret == 1) {
                    Toast.makeText(getApplicationContext(), "读卡成功", Toast.LENGTH_SHORT).show();
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");// 设置日期格式
                    saveInfo();
                    tv_info.setText("姓名：" + ic.getPeopleName() + "\n" + "性别：" + ic.getSex() + "\n" + "民族：" + ic.getPeople()
                            + "\n" + "出生日期：" + ic.getBirthDay() + "\n" + "地址：" + ic.getAddr() + "\n" + "身份号码："
                            + ic.getIDCard() + "\n" + "签发机关：" + ic.getDepartment() + "\n" + "有效期限：" + ic.getStrartDate()
                            + "-" + ic.getEndDate() + "\n");
                    try {
                        ret = api.Unpack(ic.getwltdata());// 照片解码
                        if (ret != 0) {// 读卡失败
                            Toast.makeText(getApplicationContext(), "头像解码失败", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (ExcelUtlis.Contains(ic) == -1) {
                            boolean reslut = ExcelUtlis.addContent(IDCardActivity.this, ic);
                            if (reslut) {
                                Toast.makeText(IDCardActivity.this, "录入成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(IDCardActivity.this, "录入失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        FileInputStream fis = new FileInputStream(filepath + "/zp.bmp");
                        Bitmap bmp = BitmapFactory.decodeStream(fis);
                        iv.setImageBitmap(bmp);
                        fis.close();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "头像不存在！", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // TODO 自动生成的 catch 块
                        Toast.makeText(getApplicationContext(), "头像读取错误", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "异常", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tv_info.setText("读卡错误");
                }
            }
        });
    }

    private void saveInfo() {
        Info.setInfo_PeopleName(ic.getPeopleName());
        Info.setInfo_Sex(ic.getSex());
        Info.setInfo_People(ic.getPeople());
        Info.setInfo_Addr(ic.getAddr());
        Info.setInfo_BirthDay(ic.getBirthDay());
        Info.setInfo_Department(ic.getDepartment());
        Info.setInfo_IDCard(ic.getIDCard());
        Info.setInfo_check(true);

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
    }

    private void showDialog() {
        if (isConn) {
            Toast.makeText(IDCardActivity.this, "已连接设备", Toast.LENGTH_LONG).show();
            return;
        }
        if (bundDevices != null && notDevices != null) {
            bundDevices.clear();
            notDevices.clear();
        }
        final AlertDialog dialog;
        diaView = View.inflate(IDCardActivity.this, R.layout.test, null);
        tv_ts = (TextView) diaView.findViewById(R.id.tv_ts);
        lv = (ListView) diaView.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        scanf = (Button) diaView.findViewById(R.id.scanf);
        AlertDialog.Builder builder = new AlertDialog.Builder(IDCardActivity.this);
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
                int ret = api.connect(d.getAddress());
                if (ret == 0) {
                    Toast.makeText(IDCardActivity.this, "已连接", Toast.LENGTH_LONG).show();
                    String sam = api.Get_SAM(500);
                    tv_sam.setText("SAM：" + sam);
                    isConn = true;
                } else {
                    Toast.makeText(IDCardActivity.this, "连接失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        scanf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.scanf();
                tv_ts.setVisibility(View.INVISIBLE);
                scanf.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.VISIBLE);
            }
        });
    }

    class MyAdapter extends BaseAdapter {

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
                TextView tv = new TextView(IDCardActivity.this);
                tv.setBackgroundResource(R.color.app_item);
                tv.setTextColor(Color.WHITE);
                tv.setText("绑定设备");
                return tv;
            }
            if (position == bundDevices.size() + 1) {
                TextView tv = new TextView(IDCardActivity.this);
                tv.setBackgroundResource(R.color.app_item);
                tv.setTextColor(Color.WHITE);
                tv.setText("其他设备");
                return tv;
            }
            View v = null;
            ViewHodler hodler;
            BluetoothDevice device = null;
            if (position < bundDevices.size() + 1) {
                device = bundDevices.get(position - 1);
            } else {
                device = notDevices.get(position - 2 - bundDevices.size());
            }
            if (convertView != null && convertView instanceof LinearLayout) {
                v = convertView;
                hodler = (ViewHodler) convertView.getTag();
            } else {
                v = View.inflate(IDCardActivity.this, R.layout.dialog, null);
                hodler = new ViewHodler();
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
}

