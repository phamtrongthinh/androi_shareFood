package com.example.myapplication.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DBhelper;
import com.example.myapplication.R;

public class admin_updateAccount extends AppCompatActivity {
    ImageView imgBack;
    TextView txtEmail,txtAddess,txtFullName,txtPhone;
    Switch swRole;
    int userid,id,roleid;
    String fullname,email,password,phone,address;
    Button btnSua;
    DBhelper dbAdmin;
    public static final String TB_USER="user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_update_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbAdmin = new DBhelper(this);


        mapping();
        addAction();
        Intent intent = getIntent();
        userid = intent.getIntExtra("userID",0);
        String sql_select = "select * from user where accountID ="+userid;
        Cursor cs = dbAdmin.selectData(sql_select);
        while (cs.moveToNext()){
            id = cs.getInt(0);
            roleid = cs.getInt(1);
            switch (roleid){
                case 1:
                    swRole.setChecked(true);
                    break;
                case 2:
                    swRole.setChecked(false);
                    break;
            }
            fullname = cs.getString(2);
            email=cs.getString(3);
            password=cs.getString(4);
            phone=cs.getString(5);
            address = cs.getString(6);
        }
        txtFullName.setText(fullname);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtAddess.setText(address);

    }

    public void addAction(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_updateAccount.this, admin_account.class);
                startActivity(intent);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int account = updateAccount(String.valueOf(userid));
                if (account==1){
                    Toast.makeText(admin_updateAccount.this, "Thành công! Sửa quyền thành công", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(admin_updateAccount.this, "Lỗi! Sửa không thành công", Toast.LENGTH_SHORT).show();
            }
        });




    }
    public int updateAccount(String accountID){
        if (swRole.isChecked()){
            roleid = 1;
        } else roleid = 2;
        ContentValues values = new ContentValues();
        values.put("roleID",roleid);
        values.put("fullName",fullname);
        values.put("email",email);
        values.put("password",password);
        values.put("phone",phone);
        values.put("address",address);
        if(dbAdmin.update(TB_USER,values,"accountID=?",new String[]{accountID})>0){
            return 1;
        }
        return -1;
    }

    public void mapping(){
        txtEmail = findViewById(R.id.txt_admin_updateAcc_email);
        txtFullName = findViewById(R.id.txt_admin_updateAcc_ten);
        txtPhone = findViewById(R.id.txt_admin_updateAcc_phone);
        txtAddess = findViewById(R.id.txt_admin_updateAcc_adress);
        swRole = findViewById(R.id.switch1);
        btnSua = findViewById(R.id.btnSuaRole);
        imgBack = findViewById(R.id.img_adminUpdateAccc_back);
    }

}