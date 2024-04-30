package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
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

public class signup extends AppCompatActivity {
    EditText edtname , edtemail , edtpass , edtphone , edtconfirm , edtadress;
    Button btnsignup;
    TextView txtlogin;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dBhelper = new DBhelper(this);
        init();
        signupclick();
        loginclick();


    }

    public void init(){
        edtname = findViewById(R.id.edt_signup_name);
        edtemail= findViewById(R.id.edt_signup_email);
        edtadress = findViewById(R.id.edt_sigup_adress);
        edtpass = findViewById(R.id.edt_signup_pass);
        edtphone = findViewById(R.id.edt_signup_phone);
        edtconfirm = findViewById(R.id.edt_signup_confirm);
        btnsignup= findViewById(R.id.btn_sigup_sigup);
        txtlogin = findViewById(R.id.txt_signup_login);

    }

     public void signupclick(){
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemail.getText().toString();
                String name = edtname.getText().toString();
                String address = edtadress.getText().toString();
                String phone = edtphone.getText().toString();
                String password = edtpass.getText().toString();
                String confirm = edtconfirm.getText().toString();
                //Kiểm tra người dùng nhập đủ thông tin chưa
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(name)  || TextUtils.isEmpty(address)  ||
                    TextUtils.isEmpty(phone) || TextUtils.isEmpty(confirm)) {
                    //Nếu chưa nhập thì hiển thị ra là chưa nhập đủ thông tin
                    Toast.makeText(signup.this, "Bạn chưa nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    //Kiểm tra mật khẩu nhập lại có giống mật khẩu không
                    if(password.equals(confirm)){
                        Boolean checkEmail = dBhelper.checkUsername(email);
                        if(checkEmail ==false){
                            ContentValues values = new ContentValues();
                            values.put("roleID", 2);
                            values.put("fullName", name);
                            values.put("email", email);
                            values.put("password", password);
                            values.put("phone",phone);
                            values.put("address", address);
                            Boolean insertUser;
                            //Thực hiện insert user vào database
                            if(dBhelper.insert(DBhelper.TB_USER,null, values)>0){
                                insertUser = true;
                            }else insertUser =false;
                            if(insertUser ==true){
                                Toast.makeText(signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(signup.this, login.class);
                               startActivity(intent);
                            } else
                                Toast.makeText(signup.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(signup.this, "Email này đã tồn tại", Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(signup.this, "Mật khẩu xác nhận chưa đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
     }

     public void loginclick(){
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(signup.this, login.class);
                startActivity(itent);
                finish();

            }
        });
     }

}