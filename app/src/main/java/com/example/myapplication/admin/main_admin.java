package com.example.myapplication.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DBhelper;
import com.example.myapplication.R;
import com.example.myapplication.login;

public class main_admin extends AppCompatActivity {
    Button btnDangXuat;
    public DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnDangXuat = findViewById(R.id.btn_mainadmin_dangsuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_admin.this, login.class);
                startActivity(intent);
            }

        });


    }
    public void openCategory(View v) {
        int id = v.getId();
        if(id ==R.id.lnDSFood ){
           Intent intent2 = new Intent(main_admin.this, list_food.class);
            startActivity(intent2);
        }else if (id == R.id.ln_AddFood){
           Intent intent3 = new Intent(main_admin.this, admin_addFood.class);
            startActivity(intent3);
        }
        else if (id == R.id.lnQLTK){
          Intent intent4 = new Intent(main_admin.this, admin_account.class);
          startActivity(intent4);
        }
    }
}