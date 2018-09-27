package com.diva.appdemo.Service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.diva.appdemo.Activity.IDCReaderActivity;
import com.huashi.bluetooth.HSBlueApi;
import com.huashi.bluetooth.HsInterface;
import com.huashi.bluetooth.IDCardInfo;

import java.util.List;
import java.util.Map;

public class BlueToothService extends Service {

    public static IDCardInfo ic;
    private BLTBinder mBinder = new BLTBinder();
    public static HSBlueApi api ;
    private static final String TAG = "BlueToothService";


    public BlueToothService() {
    }

    @Override
    public void onCreate() {
        api = new HSBlueApi(this);
        ic = new IDCardInfo();
        Log.e(TAG, "onCreate: " );
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " );
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: "+intent.getPackage());
        return  mBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }


    public class BLTBinder extends Binder implements IDCRInterface {

        @Override
        public int onInit() {
            int result = api.init();
            return result;
        }

        @Override
        public int onUninit() {
            int result = api.unInit();
            return result;
        }

        @Override
        public int onConnect(String macAddress) {
            int result = api.connect(macAddress);
            return result;
        }

        @Override
        public void onSleep() {
            api.sleep();
        }

        @Override
        public void onWeak() {
            api.weak();
        }

        @Override
        public int onRead(long timeOut) {
            int result = api.Read_Card(ic,timeOut);
            return result;
        }

        @Override
        public void onAut() {
            api.aut();
        }

        @Override
        public void onScanf() {
            api.scanf();
        }

        @Override
        public int Authenticate(long timeOut) {
            ic = new IDCardInfo();
            int result = api.Authenticate(timeOut);
            return result;
        }

        @Override
        public int Unpack(byte[] wltdata) {
            int result = api.Unpack(wltdata);
            return result;
        }

        @Override
        public String Get_SAM(long timeOut){
            String result = api.Get_SAM(timeOut);
            return result;
        }

        @Override
        public void onSetmInterface() {
            api.setmInterface(new HsInterface() {
                @Override
                public void reslut2Devices(Map<String, List<BluetoothDevice>> map) {
                   IDCReaderActivity.bundDevices = map.get("bind");
                    IDCReaderActivity.notDevices = map.get("notBind");
                    IDCReaderActivity.adapter.notifyDataSetChanged();
                    Log.e(TAG, "reslut2Devices: "+IDCReaderActivity.bundDevices.get(0).getName() );
                }
            });
            Log.e(TAG, "onSetmInterface: " );
        }


    }



}
