package com.example.myapplication.user;

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

public class list_rice extends AppCompatActivity {
    GridView gridFood;
    ImageView imgBack;
    ArrayList<Food> list;
    FoodAdapter adapter = null;
    DBhelper dBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_rice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });//grid_rice
         dBhelper = new DBhelper(this);

        gridFood = findViewById(R.id.grid_rice);
        imgBack = findViewById(R.id.img_listRice_back);
        list = new ArrayList<>();
        adapter = new FoodAdapter(this, R.layout.row_food , list);
        gridFood.setAdapter(adapter);
        String sql_select = "select * from "+DBhelper.TB_FOOD+" where categoryid = 3";
        Cursor cs = dBhelper.selectData(sql_select);
        while (cs.moveToNext()){
            int foodid = cs.getInt(0);
            int category = cs.getInt(1);
            String foodname  = cs.getString(2);
            byte[] image = cs.getBlob(3);
            String video = cs.getString(4);
            String address = cs.getString(5);
            String tacgia = cs.getString(6);
            String congthuc = cs.getString(7);
            String motadai = cs.getString(8);
            Food food = new Food(foodid , category , foodname , image, video,address,tacgia,congthuc,motadai);
            list.add(food);
        }
        adapter.notifyDataSetChanged();
        addAction();
    }
    private void addAction() {
        gridFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = (Food) adapter.getItem(i);
                int foodid = food.getFoodID();
                System.out.println(foodid);
                Intent intent = new Intent(list_rice.this, user_detailFood.class);
                intent.putExtra("foodid",foodid);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list_rice.this, main_user.class);
                startActivity(intent);
            }
        });
    }

}
