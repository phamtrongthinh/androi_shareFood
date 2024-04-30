package com.example.myapplication.adapter;


import com.example.myapplication.R;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import com.example.myapplication.model.User;

import java.util.ArrayList;

public class AccountAdapter extends BaseAdapter {
    private Activity activity;
   private Context context;
   private int layout ;
   private ArrayList<User> userArrayList;

    public AccountAdapter(Context context, int layout, ArrayList<User> userArrayList) {
        this.context = context;
        this.layout = layout;
        this.userArrayList = userArrayList;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem convertView có null không, nếu có thì inflate layout mới
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
        }
        // Tìm các TextView trong convertView bằng ID tương ứng
        TextView txtRole = convertView.findViewById(R.id.txtRole);
        TextView txtFullName = convertView.findViewById(R.id.txtFullName);
        TextView txtEmail = convertView.findViewById(R.id.txtEmail);
        // Lấy User tương ứng với vị trí trong danh sách
        User user = userArrayList.get(position);
        String roleText = user.getRoleID() == 1 ? "Admin" : "Người dùng";
        txtRole.setText("Quyền: " + roleText);
        txtFullName.setText(user.getFullname());
        txtEmail.setText("Email: " + user.getEmail());
        // Trả về convertView đã được cập nhật dữ liệu
        return convertView;
    }
}
