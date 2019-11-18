package com.raonhaze_pbl.myapplication.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.raonhaze_pbl.myapplication.Main2Activity;
import com.raonhaze_pbl.myapplication.R;
import com.raonhaze_pbl.myapplication.User;
import com.raonhaze_pbl.myapplication.UserListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class mytrashbag extends AppCompatActivity {

    private ListView mListView;
    private UserListAdapter adapter;
    private List<User> userList;
    private EditText search_name;
    private Button search_button;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrashbag);
        intent = getIntent();
        mListView = (ListView) findViewById(R.id.my_list);
        userList = new ArrayList<User>();//바코드와 날짜로 구성되어있는 배열
        search_name = (EditText) findViewById(R.id.search_name);
        search_button = (Button) findViewById(R.id.search_button);

        //어댑터 초기화부분 userList와 어댑터를 연결해준다.
        adapter = new UserListAdapter(getApplicationContext(), userList);
        mListView.setAdapter(adapter);
        search_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String name = search_name.getText().toString();
                try {
                    userList.clear();
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String userBarcode, userTime, userID;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        userID = object.getString("userID");
                        userBarcode = object.getString("userBarcode");
                        userTime = object.getString("userTime");
                        User user = new User(userBarcode, userTime);
                        if (userID.equals(name))
                            userList.add(user);
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}

