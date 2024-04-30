package com.example.myapplication.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

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

public class listSearch extends AppCompatActivity {
    ListView listView;
    FoodAdapter foodAdapter;
    ArrayList<Food> foodList;
    DBhelper dBhelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent it = getIntent();
        String contentSeach = it.getStringExtra("cont");
        dBhelper = new DBhelper(this);
        listView = findViewById(R.id.listSearch);
        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(this, R.layout.row_food , foodList);
        listView.setAdapter(foodAdapter);
        String sql = "SELECT * FROM " + DBhelper.TB_FOOD + " WHERE foodname LIKE '%" + contentSeach + "%'";
        Cursor cs = dBhelper.selectData(sql);
        while (cs.moveToNext()) {
            int foodid = cs.getInt(0);
            int category = cs.getInt(1);
            String foodname = cs.getString(2);
            byte[] image = cs.getBlob(3);
            String video = cs.getString(4);
            String address = cs.getString(5);
            String tacgia = cs.getString(6);
            String congthuc = cs.getString(7);
            String motadai = cs.getString(8);
            Food food = new Food(foodid, category, foodname, image, video, address, tacgia, congthuc, motadai);
            foodList.add(food);
        }
        foodAdapter.notifyDataSetChanged();
    }
}