package com.example.myapplication.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DBhelper;
import com.example.myapplication.R;

public class admin_detailFood extends AppCompatActivity {
    int foodid;
    String linkVideo;
    ImageView imgAnhFood,imgBack;
    DBhelper dbAdmin;
    TextView txtDanhMuc,txtTenMon,txtAddress,txtMoTa,txtLinkVideo,txtCongThuc,txtXoa,txtSua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_detail_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbAdmin = new DBhelper(this);
        mapping();
        addAction();


        Intent intent = getIntent();
        foodid = intent.getIntExtra("foodid",0);
        String sql_select = "select * from "+DBhelper.TB_FOOD+" where foodid ="+foodid;
        Cursor cs = dbAdmin.selectData(sql_select);
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
                    txtDanhMuc.setText("Món Cơm");
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
            byte[] image =cs.getBlob(3); // Use getBlob to get byte array
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
        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xacNhanXoa();
            }
        });

        txtSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_detailFood.this, admin_updateFood.class);
                intent.putExtra("foodid",foodid);
                startActivity(intent);
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(admin_detailFood.this, list_food.class);
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
    private void xacNhanXoa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh báo");
//        builder.setIcon(R.drawable.warning);
        builder.setMessage("Bạn có thật sự muốn xóa món "+txtTenMon.getText().toString()+" không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                int foodid = intent.getIntExtra("foodid",0);
                String id = String.valueOf(foodid);
                if(id!=""){
                    int i =deleteFood(id);
                    if (i==1){
                        Toast.makeText(admin_detailFood.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(admin_detailFood.this, list_food.class);
                        startActivity(intent1);
                    } else Toast.makeText(admin_detailFood.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    public int deleteFood(String foodid){
        if (dbAdmin.delete(DBhelper.TB_FOOD,"foodid=?",new String[]{foodid})<0){
            return -1;
        }
        return 1;
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
        txtXoa = findViewById(R.id.txtDelete);
        txtSua = findViewById(R.id.txtSuaMon);
    }
}