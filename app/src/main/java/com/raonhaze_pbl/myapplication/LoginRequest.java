package com.raonhaze_pbl.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://akaishuichi.dothome.co.kr/Login.php";//Register.php 파일과 연결하는 과정
    private Map<String,String> map;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener)
    {
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
