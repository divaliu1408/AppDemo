package com.ivsign.android.IDCReader;

import android.os.Environment;

public class IDCReaderSDK {
	
	private static final String TAG = "unpack";

	public IDCReaderSDK()
	{
	}
	public static int Init()
	{
		return wltInit(Environment.getExternalStorageDirectory() + "/wltlib");
	}
	public static int unpack(byte[] wltdata, byte[] licdata)
	{
		return wltGetBMP(wltdata, licdata);
	}

    public static native int wltInit(String workPath);

    public static native int wltGetBMP(byte[] wltdata, byte[] licdata);

    static {
        System.loadLibrary("wltdecode");
    }
}
