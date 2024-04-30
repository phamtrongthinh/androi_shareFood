package com.example.myapplication.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.DBhelper;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;

public class admin_updateFood extends AppCompatActivity {
    int foodid;
    ImageView imgAddAnh,imgBack,imgAnhFood;
    TextView txtDanhMuc,txtTenMon,txtTacGia,txtVideo,txtCongThuc,txtAddress,txtMoTa;
    Button btnDanhMuc,btnSuaMon;
    DBhelper dBhelper ;
    private static final int REQUEST_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_update_food);
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
        String sql_select = "select * from "+dBhelper.TB_FOOD+" where foodid ="+foodid;
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
            txtVideo.setText(cs.getString(4));
            txtAddress.setText(cs.getString(5));
            txtTacGia.setText(cs.getString(6));
            txtCongThuc.setText(cs.getString(7));
            txtMoTa.setText(cs.getString(8));
        }

    }

    private void addAction() {
        btnDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialog = new SinggleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });

        btnSuaMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtTenMon.getText().toString()) || TextUtils.isEmpty(txtCongThuc.getText().toString()) ||
                        TextUtils.isEmpty(txtVideo.getText().toString()) ||
                        TextUtils.isEmpty(txtAddress.getText().toString()) ||
                        TextUtils.isEmpty(txtTacGia.getText().toString()) ||
                        TextUtils.isEmpty(txtMoTa.getText().toString()) ||
                        TextUtils.isEmpty(txtDanhMuc.getText().toString())|| imgAnhFood.getDrawable()==null){
                    Toast.makeText(admin_updateFood.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    String id = String.valueOf(foodid);
                    int kq = updateFood(id);
                    if (kq==1){
                        Toast.makeText(admin_updateFood.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(admin_updateFood.this, admin_detailFood.class);
                        intent1.putExtra("foodid",foodid);
                        startActivity(intent1);
                    } else Toast.makeText(admin_updateFood.this, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(admin_updateFood.this, list_food.class);
                intent1.putExtra("foodid",foodid);
                startActivity(intent1);
            }
        });

        imgAnhFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Tille"),REQUEST_GALLERY);
            }
        });
    }

    public int updateFood(String foodid){
        int categoryID=0;
        switch (txtDanhMuc.getText().toString()) {
            case "Đồ Uống":
                categoryID = 1;
                break;
            case "Món Thịt":
                categoryID = 2;
                break;
            case "Món Cơm":
                categoryID = 3;
                break;
            case "Salad":
                categoryID = 4;
                break;
            case "Món Cá":
                categoryID = 5;
                break;
            case "Món Nướng":
                categoryID = 6;
                break;
        }
        ContentValues values = new ContentValues();
        values.put("categoryid", categoryID);
        values.put("foodname", txtTenMon.getText().toString());
        values.put("anh", imageViewToByte(imgAnhFood));
        values.put("video", txtVideo.getText().toString());
        values.put("address", txtAddress.getText().toString());
        values.put("tacgia", txtTacGia.getText().toString());
        values.put("congthuc", txtCongThuc.getText().toString());
        values.put("motadai", txtMoTa.getText().toString());
        if(dBhelper.update(DBhelper.TB_FOOD,values,"foodid=?",new String[]{foodid})>0){
            return 1;
        }
        return -1;
    }

    //Convert image sang byte
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    private void mapping() {
        btnDanhMuc = findViewById(R.id.btn_updateFood_danhmuc);
        btnSuaMon = findViewById(R.id.btn_updateFood_sua);
        txtDanhMuc = findViewById(R.id.txt_updateFood_danhmuc);
        txtTenMon = findViewById(R.id.edt_updateFood_namefood);
        txtVideo = findViewById(R.id.edt_updateFood_link);
        txtCongThuc = findViewById(R.id.edt_updateFood_congthuc);
        txtAddress = findViewById(R.id.edt_updateFood_adress);
        txtTacGia = findViewById(R.id.edt_updateFood_tacgia);
        txtMoTa = findViewById(R.id.medt_updateFood_mota);
        imgAnhFood = findViewById(R.id.img_updateFood_chonanh);
        imgBack = findViewById(R.id.img_updateFood_back);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            Uri uri = data.getData();
            imgAnhFood.setImageURI(uri);
        }
    }

    public void onPositiveButtonClicked(String[] list, int position) {
        txtDanhMuc.setText(list[position]);
    }





}