package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import com.example.myapplication.model.Food;

import java.util.ArrayList;

public class FoodAdapter  extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<Food> foodsList;
    public FoodAdapter(Context context, int layout, ArrayList<Food> foodsList) {
        this.context = context;
        this.layout = layout;
        this.foodsList = foodsList;
    }


    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,null);
        ImageView imgAnhFood;
        TextView txtTenFood, txtTacGia,txtAddress;
        imgAnhFood = view.findViewById(R.id.imgAnhFood);
        txtTenFood = view.findViewById(R.id.txtTenFood);
        txtTacGia = view.findViewById(R.id.txtTacGia);
        txtAddress = view.findViewById(R.id.txtAddress);
        Food food = foodsList.get(i);
        byte[] foodImage = food.getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        imgAnhFood.setImageBitmap(bitmap);
        txtTenFood.setText(food.getFoodName());
        txtTacGia.setText("Tên quán: "+food.getTacGia());
        txtAddress.setText("Địa chỉ: "+food.getAddress());
        return view;
    }
}
