package com.example.myapplication.user;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DBhelper;
import com.example.myapplication.R;

public class user_detailFood extends AppCompatActivity {

    int foodid;
    ImageView imgAnhFood,imgBack;
    TextView txtDanhMuc,txtTenMon,txtAddress,txtMoTa,txtLinkVideo,txtCongThuc;
    String linkVideo;
    DBhelper dBhelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_detail_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dBhelper = new DBhelper(this);
        mapping();
       addAction();
        Intent intent = getIntent();
        foodid = intent.getIntExtra("foodid",0);
        String sql_select = "select * from "+DBhelper.TB_FOOD+" where foodid ="+foodid;
        Cursor cs = dBhelper.selectData(sql_select);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            int categoryid = cs.getInt(1);
            switch (categoryid){
                case 1:
                    txtDanhMuc.setText("Đồ Uống");
                    break;
                case 2:
                    txtDanhMuc.setText("Món Thịt");
                    break;
                case 3:
                    txtDanhMuc.setText(" Món Cơm");
                    break;
                case 4:
                    txtDanhMuc.setText("Salad");
                    break;
                case 5:
                    txtDanhMuc.setText("Món Cá");
                    break;
                case 6:
                    txtDanhMuc.setText("Món Nướng");
                    break;
            }
            txtTenMon.setText(cs.getString(2));
            byte[] image = cs.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgAnhFood.setImageBitmap(bitmap);
            txtLinkVideo.setText(cs.getString(4));
            linkVideo = cs.getString(4);
            txtAddress.setText(cs.getString(5));
            String tacgia = cs.getString(6);
            txtCongThuc.setText(cs.getString(7));
            txtMoTa.setText(cs.getString(8));
        }


    }


    private void addAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(user_detailFood.this, main_user.class);
                startActivity(intent1);
            }
        });
        txtLinkVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linkVideo !=null){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(linkVideo));
                    startActivity(intent);
                }
            }
        });
    }
    private void mapping() {
        imgAnhFood = findViewById(R.id.imgAnhMon);
        imgBack = findViewById(R.id.img_userlistfood_back);
        txtDanhMuc = findViewById(R.id.txtDanhMuc);
        txtTenMon = findViewById(R.id.txtTenMon);
        txtAddress = findViewById(R.id.txtAddress);
        txtMoTa = findViewById(R.id.txtMoTa);
        txtLinkVideo = findViewById(R.id.txtLinkVideo);
        txtCongThuc = findViewById(R.id.txtCongThuc);
    }

}