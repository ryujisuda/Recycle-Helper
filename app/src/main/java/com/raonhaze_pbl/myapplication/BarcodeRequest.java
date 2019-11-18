package com.raonhaze_pbl.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class BarcodeRequest  extends StringRequest {
    //서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://akaishuichi.dothome.co.kr/BarcodeUpdate.php";//Register.php 파일과 연결하는 과정
    private Map<String,String> map;

    public BarcodeRequest(String userID, String userBarcode, Timestamp userTime, Response.Listener<String> listener)
    {
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userBarcode",userBarcode);
        map.put("userTime",userTime+"");
    }
    @Override
    protected Map<String, String> getParams()  throws AuthFailureError {
        return map;
    }


}
