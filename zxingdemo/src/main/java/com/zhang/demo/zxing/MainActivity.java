package com.zhang.demo.zxing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhang.demo.zxing.qrcode.CaptureActivity;
import com.zhang.demo.zxing.qrcode.utils.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGenerate = (Button) findViewById(R.id.btn_create);
        Button btnScaner = (Button) findViewById(R.id.btn_scaner);
        btnGenerate.setOnClickListener(this);
        btnScaner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                startActivity(new Intent(this, GenrateQRActivity.class));
                break;
            case R.id.btn_scaner:
                if (Util.checkCameraHardware(getApplicationContext())) {
                    startActivity(new Intent(this, CaptureActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "没有检测的摄像头", Toast.LENGTH_SHORT).show();
                } 
                break;
        }
    }
}