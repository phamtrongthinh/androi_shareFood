package com.example.myapplication.admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DBhelper;
import com.example.myapplication.R;
import com.example.myapplication.adapter.AccountAdapter;
import com.example.myapplication.model.User;

import java.util.ArrayList;

public class admin_account extends AppCompatActivity {
    GridView gridAccount;
    ArrayList<User> userArrayList;
    AccountAdapter  adapterUser = null;
    ImageView imgBack;
    DBhelper dbAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbAdmin = new DBhelper(this);
        gridAccount = findViewById(R.id.grv_adminAccount);
        imgBack = findViewById(R.id.img_adminAccount_back);
        userArrayList = new ArrayList<>();
        //Adapter để kết nối dữ liệu người dùng với GridView.
        adapterUser = new AccountAdapter(this, R.layout.row_account, userArrayList);
        gridAccount.setAdapter(adapterUser);
        String sql_select = "select * from user";
        Cursor cs = dbAdmin.selectData(sql_select);
        while (cs.moveToNext()){
            int userid = cs.getInt(0);
            int roleID = cs.getInt(1);
            String fullName = cs.getString(2);
            String email = cs.getString(3);
            String password = cs.getString(4);
            String phone = cs.getString(5);
            String address = cs.getString(6);
            User user = new User(userid,roleID,fullName,email,password,phone,address);
           // lay du lieu trong sqltie dua vao arrraylist
            userArrayList.add(user);
        }
        adapterUser.notifyDataSetChanged();//cập nhật giao diện bằng cách gọi
        addAction();
    }

    private void addAction() {
        gridAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = (User) adapterUser.getItem(i);// lay dung vi tri
                int userID = user.getAccountID();
              Intent intent = new Intent(admin_account.this,admin_updateAccount.class);
               intent.putExtra("userID",userID);// gui vi tri
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(admin_account.this, main_admin.class);
                startActivity(intent1);
            }
        });
    }
}