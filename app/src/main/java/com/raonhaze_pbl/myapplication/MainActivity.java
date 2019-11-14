package com.raonhaze_pbl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button qr_button,notice_button,map_button;
    private IntentIntegrator qrScan;
    Intent intent;
    public String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=getIntent();
        userID=intent.getStringExtra("userID");
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
            } else {
                //qrcode 결과가 있으면
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());

                } catch (JSONException e) {
                    e.printStackTrace();

                    Intent intent =  getIntent();

                    final String userBarcode = result.getContents();


                    Toast.makeText(getApplicationContext(),userID+""+userBarcode,Toast.LENGTH_SHORT).show();


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //여기서 부터 json object를 활용한다. json object는 string 형태.. 담아서 보내는 운반체라고 생각하면 된다.
                            try {

                                //Toast.makeText(getApplicationContext(),userID+""+userBarcode,Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = new JSONObject(response);
                                Boolean success = jsonObject.getBoolean("success");//success를 가지고 와야해 true냐?->성공 false냐?->실패
                                if(success)//회원등록에 성공한 경우
                                {
                                    Toast.makeText(getApplicationContext(),"전송이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                                }
                                else //회원등록에 실패한 경우
                                {
                                    Toast.makeText(getApplicationContext(),"전송이 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;//넘어가면 안되니까! return 해버리기
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    BarcodeRequest barcodeRequest = new BarcodeRequest(userID,userBarcode,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(barcodeRequest);


                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


