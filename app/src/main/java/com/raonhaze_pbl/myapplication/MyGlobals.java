//싱글톤 디자인패턴 사용. 전역변수 사용.
// userID를 사용할 곳이 너무 많아 액티비티 또는 프레그먼트마다 intent로 넘겨주기는 무리가 있다.
package com.raonhaze_pbl.myapplication;

public class MyGlobals {
    private String data;
    public String getData()
    {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
    private static MyGlobals instance = null;

    public static synchronized MyGlobals getInstance(){
        if(null == instance){
            instance = new MyGlobals();
        }
        return instance;
    }

}
