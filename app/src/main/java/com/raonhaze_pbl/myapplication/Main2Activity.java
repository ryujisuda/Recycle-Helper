package com.raonhaze_pbl.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.raonhaze_pbl.myapplication.ui.gallery.GalleryFragment;
import com.raonhaze_pbl.myapplication.ui.gallery.mytrashbag;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main2Activity extends AppCompatActivity {
    private Button qr_button,notice_button,map_button;
    private IntentIntegrator qrScan;
    private String userID;
    Intent intent;

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        intent =getIntent();
        TextView hi = (TextView)navigationView.getHeaderView(0).findViewById(R.id.user_place);//다른 xml 파일에 있는 TextView 건들기.

        userID=intent.getStringExtra("userID");

        hi.setText(userID+"님, 환영합니다!");



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
                finish();
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

                    Calendar cal = new GregorianCalendar();
                    Timestamp ts = new Timestamp(cal.getTimeInMillis());
                    final String userBarcode = result.getContents();


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //여기서 부터 json object를 활용한다. json object는 string 형태.. 담아서 보내는 운반체라고 생각하면 된다.
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                Boolean success = jsonObject.getBoolean("success");//success를 가지고 와야해 true냐?->성공 false냐?->실패
                                if(success)
                                {
                                    Toast.makeText(getApplicationContext(),"전송이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"전송이 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;//넘어가면 안되니까! return 해버리기
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    BarcodeRequest barcodeRequest = new BarcodeRequest(userID,userBarcode,ts,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
                    queue.add(barcodeRequest);


                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
