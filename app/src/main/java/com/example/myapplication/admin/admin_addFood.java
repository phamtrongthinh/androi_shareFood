package com.example.myapplication.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class admin_addFood extends AppCompatActivity {
    ImageView imgAddAnh,imgBack,imgAnhFood;
    TextView txtDanhMuc,txtTenMon,txtTacGia,txtVideo,txtCongThuc,txtAddress,txtMoTa;
    Button btnDanhMuc,btnThemMon;
    DBhelper dbAdmin;
    private static final int REQUEST_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbAdmin = new DBhelper(this);
        mapping();
        addAction();
    }

    private void addAction() {
        btnDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialog = new SinggleChoiceDialogFragment();//tạo một phiên bản SingleChoiceDialogFragment tùy chỉnh của mình, phiên bản này mở rộng DialogFragment. Hộp thoại này được thiết kế để hiển thị danh sách một lựa chọn.
                singleChoiceDialog.setCancelable(false) ;//Dòng này đặt người dùng không thể hủy hộp thoại, nghĩa là người dùng không thể loại bỏ hộp thoại bằng cách nhấn vào bên ngoài hoặc nhấn nút quay lại.
                singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
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

        btnThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtTenMon.getText().toString()) || TextUtils.isEmpty(txtCongThuc.getText().toString()) ||
                        TextUtils.isEmpty(txtVideo.getText().toString()) ||
                        TextUtils.isEmpty(txtAddress.getText().toString()) ||
                        TextUtils.isEmpty(txtTacGia.getText().toString()) ||
                        TextUtils.isEmpty(txtMoTa.getText().toString()) ||
                        TextUtils.isEmpty(txtDanhMuc.getText().toString())||
                        imgAnhFood.getDrawable()==null){
                    Toast.makeText(admin_addFood.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    int i = inSertData();
                    if (i==1){
                        Toast.makeText(admin_addFood.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        clear();
                    } else Toast.makeText(admin_addFood.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_addFood.this, main_admin.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                imgAnhFood.setImageURI(uri);
            }
        }
    }
    //Hàm clear dữ liệu sau khi thêm vào csdl thành công
    public void clear(){
        txtTenMon.setText("");
        txtDanhMuc.setText("");
        txtMoTa.setText("");
        txtCongThuc.setText("");
        txtAddress.setText("");
        txtTacGia.setText("");
        txtVideo.setText("");
    }
    //Insert data vào database
    public int inSertData(){
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
//        "foodid integer primary key autoincrement," +
//                "categoryid integer," +
//                "foodname text not null," +
//                "anh Blog not null, " +
//                "video text not null," +
//                "address text not null," +
//                "tacgia text not null," +
//                "congthuc text not null, " +
//                "motadai text not null)";
        ContentValues values = new ContentValues();
        values.put("categoryid", categoryID);
        values.put("foodname", txtTenMon.getText().toString());
        values.put("anh", imageViewToByte(imgAnhFood));
        values.put("video", txtVideo.getText().toString());
        values.put("address", txtAddress.getText().toString());
        values.put("tacgia", txtTacGia.getText().toString());
        values.put("congthuc", txtCongThuc.getText().toString());
        values.put("motadai", txtMoTa.getText().toString());
        long result = dbAdmin.insert(DBhelper.TB_FOOD, null, values);
        if (result != -1) {
            return 1; // Thành công
        } else {
            return -1; // Thất bại
        }
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
        btnDanhMuc = findViewById(R.id.btn_addFood_danhmuc);
        btnThemMon = findViewById(R.id.btn_addFood_them);
        txtDanhMuc = findViewById(R.id.txt_addFood_danhmuc);
        txtTenMon = findViewById(R.id.edt_adFood_namefood);
        txtVideo = findViewById(R.id.edt_adFood_link);
        txtCongThuc = findViewById(R.id.edt_adFood_congthuc);
        txtAddress = findViewById(R.id.edt_adFood_adress);
        txtTacGia = findViewById(R.id.edt_adFood_tacgia);
        txtMoTa = findViewById(R.id.medt_addFood_mota);
        imgAnhFood = findViewById(R.id.img_addFood_chonanh);
        imgBack = findViewById(R.id.img_updateFood_back);
    }

    public void onPositiveButtonClicked(String[] list, int position) {
        txtDanhMuc.setText(list[position]);
    }




}