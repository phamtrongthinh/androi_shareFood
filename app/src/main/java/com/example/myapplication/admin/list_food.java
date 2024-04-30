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
import com.example.myapplication.adapter.FoodAdapter;
import com.example.myapplication.model.Food;

import java.util.ArrayList;

public class list_food extends AppCompatActivity {
    GridView gridFood;
    ArrayList<Food> list;
    FoodAdapter adapter = null;
    ImageView imgBack;
    DBhelper dBhelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_food_ad);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dBhelper = new DBhelper(this);
        gridFood = findViewById(R.id.grFood);
        imgBack = findViewById(R.id.img_userlistfood_back);
        list = new ArrayList<>();
        adapter = new FoodAdapter(this, R.layout.row_food, list);
        gridFood.setAdapter(adapter);
        String sql_select = "select * from "+dBhelper.TB_FOOD;
        Cursor cs = dBhelper.selectData(sql_select);
        while (cs.moveToNext()){
            int foodid = cs.getInt(0);
            int categoryid = cs.getInt(1);
            String foodname = cs.getString(2);
            byte[] image = cs.getBlob(3);
            String video = cs.getString(4);
            String address = cs.getString(5);
            String tacgia = cs.getString(6);
            String congthuc = cs.getString(7);
            String motadai = cs.getString(8);
            Food food = new Food(foodid,categoryid,foodname,image,video,address,tacgia,congthuc,motadai);
            list.add(food);
        }
        adapter.notifyDataSetChanged();
        addAction() ;
    }
    private void addAction() {
        gridFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = (Food) adapter.getItem(i);
                int foodid = food.getFoodID();
                System.out.println(foodid);
                 Intent intent = new Intent(list_food.this, admin_detailFood.class);
                intent.putExtra("foodid",foodid);
                 startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(list_food.this, main_admin.class);
               startActivity(intent1);
            }
        });
    }
}