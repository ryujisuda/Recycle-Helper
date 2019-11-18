package com.raonhaze_pbl.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//User을 ListView에 뿌려주기 위한 어댑터 관련된 디자인패턴 : 어댑터패턴
public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return userList.size();
    }

    //특정한 유저를 반환하는 메소드
    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    //아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    //가장 중요한 부분
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌
        TextView userBarcode = (TextView)v.findViewById(R.id.userBarcode);
        TextView userTime = (TextView)v.findViewById(R.id.userTime );

        userBarcode.setText(userList.get(i).getUserBarcode());
        userTime .setText(userList.get(i).getUserTime ());


        //이렇게하면 findViewWithTag를 쓸 수 있음 없어도 되는 문장임
        v.setTag(userList.get(i).getUserBarcode());

        //만든뷰를 반환함
        return v;
    }
}

