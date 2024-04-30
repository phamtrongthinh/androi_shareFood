package com.example.myapplication.user;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DBhelper;
import com.example.myapplication.R;
import com.example.myapplication.login;

public class main_user extends AppCompatActivity {
    TextView txtNameUser;
    Button btnXemAll;
    ImageView imgProfile,imgLogOut;
    int accountID = 0;
    public static DBhelper dbHelper;
    public static String dbName = "food.sqlite";
    public static final String TB_FOOD="food";
    public static final String FOODCATEGORYID="categoryid";
    public static final String FOOD_NAME="foodname";
    public static final String ANH="anh";
    public static final String VIDEO="video";
    public static final String ADDRESS="address";
    public static final String TACGIA="tacgia";
    public static final String CONGTHUC="congthuc";
    public static final String MOTADAI="motadai";

    EditText edtSearch;
    Button btnSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        dbHelper = new DBhelper(this);
        mapping();
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        txtNameUser.setText(user);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cont =edtSearch.getText().toString().trim();
                startActivity(new Intent(main_user.this,listSearch.class));
               Intent it = new Intent(main_user.this, listSearch.class);
               it.putExtra("cont", cont);startActivity(it);
            }
        });
       logout();
    }

    private void mapping (){
        imgProfile = findViewById(R.id.img_mainuser_acc);
        imgLogOut = findViewById(R.id.img_mainuser_logout);
        txtNameUser = findViewById(R .id.txt_mainuser_nguoidung);
        btnXemAll = findViewById(R.id.btn_mainuser_hientatca);
    }

    public void openCategory(View v) {
        Intent intent;
        int id = v.getId();
        if(id == R.id.lnDrink){
            intent = new Intent(this, list_drink.class);
            startActivity(intent);
        }else if(id == R.id.lnMeet) {
            intent = new Intent(this, list_meet.class);
            startActivity(intent);
        }else if(id == R.id.lnRice) {
            intent = new Intent(this, list_rice.class);
            startActivity(intent);
        }else if(id == R.id.lnSalat) {
            intent = new Intent(this, list_salat.class);
            startActivity(intent);
        }else if(id == R.id.lnFish) {
            intent = new Intent(this, list_fish.class);
            startActivity(intent);
        }else if(id == R.id.lnNuong) {
            intent = new Intent(this, list_nuong.class);
            startActivity(intent);
        }


    }


    public void logout(){
        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_user.this, login.class);
                startActivity(intent);

            }
        });
        btnXemAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_user.this , user_listfood.class);
                startActivity(intent);
            }
        });
    }


}