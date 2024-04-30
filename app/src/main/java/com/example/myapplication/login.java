package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.admin.main_admin;
import com.example.myapplication.user.main_user;

public class login extends AppCompatActivity {
    EditText edtname , edtpass;
    Button btnlogin ;
    TextView txtCreateacc;
    DBhelper dbHelper;
    int roleID;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBhelper(this);
        init();
        createACC();
        addAction();

    }
    public void init()
    {
        edtname = findViewById(R.id.edt_login_name);
        edtpass = findViewById(R.id.edt_login_pass);
        txtCreateacc = findViewById(R.id.txt_login_createACC);
        btnlogin = findViewById(R.id.btn_login_login);
    }

    public void  createACC(){
        txtCreateacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(login.this,signup.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    public void addAction(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra nguoiwfi dùng đã nhập tk và mk chưa
                String email = edtname.getText().toString();
                String password = edtpass.getText().toString();
                if (TextUtils.isEmpty(email) ||TextUtils.isEmpty(password)){
                    Toast.makeText(login.this, "Bạn chưa nhập thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    //Kiểm tra thông tin tk và mật khẩu đúng chưa
                    Boolean checkuserpass = dbHelper.checkUsernamePassword(email,password);
                    if (checkuserpass==true){
                        Toast.makeText(login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        String user =edtname.getText().toString();
                        String sql_select = "select * from "+DBhelper.TB_USER+" where email= '"+user+"'";
                        Cursor cs = dbHelper.selectData(sql_select);//lay ra lisst dap an
                        while(cs.moveToNext()){//vong lap tim kiem trong list dap an ddays
                            roleID = cs.getInt(1);}// gan voi vi tri cot roleid
                        if(roleID ==1){
                            intent = new Intent(login.this, main_admin.class);
                        }else {
                            intent = new Intent(login.this, main_user.class);
                        }
                        intent.putExtra("user",user);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }else Toast.makeText(login.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}