package com.raonhaze_pbl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button qr_button,notice_button,map_button;
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qr_button =findViewById(R.id.Button_click);
        qrScan = new IntentIntegrator(this);
        // qrScan.setCaptureActivity(MainActivity.class);

        qr_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
                //scan option
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });
        //notice 버튼을 누르면 저 홈페이지로 가게..
        notice_button =findViewById(R.id.Button_notice);
        notice_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yeonsu.go.kr/main/part/clean/house.asp"));
                startActivity(webIntent);
            }
        });
        map_button = findViewById(R.id.Button_map);
        map_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(getApplicationContext(), TmapActivity.class);
                startActivity(webIntent);
            }
        });
    }
    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(MainActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(MainActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


