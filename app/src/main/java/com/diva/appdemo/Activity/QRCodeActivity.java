package com.diva.appdemo.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.diva.appdemo.Manager.BaseActivity;
import com.diva.appdemo.R;
import com.diva.appdemo.Utils.QRCode;

/**
 * Created by 刘迪 on 2018/8/16 17:37.
 * 邮箱：divaliu1408@qq.com
 */

public class QRCodeActivity extends BaseActivity {
    private ImageView ivBarCode;
    private ImageView ivLogoBarCode;

    private EditText etInputMessage;
    private ImageView ivCreateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
//这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.app_name);
        isSetNavigationIcon = false;

        initView();
        initData();
    }


    //初始化UI空间
    private void initView() {
        ivBarCode = (ImageView) findViewById(R.id.ivBarCode);
        ivLogoBarCode = (ImageView) findViewById(R.id.ivLogoBarCode);

        etInputMessage = (EditText) findViewById(R.id.etInputMessage);
        ivCreateCode = (ImageView) findViewById(R.id.ivCreateCode);
    }

    //初始化数据
    private void initData() {
        Bitmap bitmap = QRCode.createQRCode("樊亚风", 500);
        ivBarCode.setImageBitmap(bitmap);

        Bitmap bitmap1 = QRCode.createQRCodeWithLogo("http://news.ifeng.com/a/20161201/50345907_0.shtml", 500, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        ivLogoBarCode.setImageBitmap(bitmap1);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScanCode:
                startActivity(new Intent(this, ScanCodeActivity.class));
                break;
            case R.id.btnCreateCode:
                String inputString = etInputMessage.getText().toString().trim();
                if (inputString != null && !inputString.equals("")) {
                    Bitmap bitmap = QRCode.createQRCode(inputString, 500);
                    ivCreateCode.setImageBitmap(bitmap);
                }
                break;
        }
    }
}
