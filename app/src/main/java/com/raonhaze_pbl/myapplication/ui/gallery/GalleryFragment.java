package com.raonhaze_pbl.myapplication.ui.gallery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.raonhaze_pbl.myapplication.LoadingActivity;
import com.raonhaze_pbl.myapplication.LoginActivity;
import com.raonhaze_pbl.myapplication.Main2Activity;
import com.raonhaze_pbl.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GalleryFragment extends Fragment {

    public String userIDD;
    private GalleryViewModel galleryViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


  /*      Bundle bundle =this.getArguments();
        if(bundle!=null)
        {
            userIDD = bundle.getString("userIDD");
        }*/

        new BackgroundTask().execute();
        return root;
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String target;
        @Override
        protected void onPreExecute()//초기화 하는 부분
        {
            target = "http://akaishuichi.dothome.co.kr/List.php";
        }
        @Override
        protected String doInBackground(Void...  voids)
        {
            try {
                {
                    URL url = new URL(target);
                    HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String temp;
                    StringBuilder stringBuilder = new StringBuilder();
                    while((temp=bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(temp+'\n');
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result)
        {

            Intent intent = new Intent(getActivity(), mytrashbag.class);
            intent.putExtra("userList",result);
            startActivity(intent);

        }
    }
}