package com.diva.appdemo.Service;

import com.huashi.bluetooth.HsInterface;
import com.huashi.bluetooth.IDCardInfo;

/**
 * Created by 刘迪 on 2018/9/26 09:33.
 * 邮箱：divaliu1408@qq.com
 */

public interface IDCRInterface {

    int onInit();

    int onUninit();

    int onConnect(String macAddress);

    void onSleep();

    void onWeak();

    int onRead( long timeOut);

    void onAut();

    void onScanf();

    int Authenticate(long timeOut);

    int Unpack(byte[] wltdata);

    String Get_SAM(long timeOut);

    void onSetmInterface();
}
