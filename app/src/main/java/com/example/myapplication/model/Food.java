package com.example.myapplication.model;

import java.util.Arrays;

public class Food {
    private int foodID;
    private int caTeGoryID;
    private String foodName;
    private byte[] anh;
    private String video;
    private String address;
    private String tacGia;
    private String congThuc;
    private String moTaDai;

    public Food() {
    }

    public Food(int foodID, int caTeGoryID, String foodName, byte[] anh, String video, String address, String tacGia, String congThuc, String moTaDai) {
        this.foodID = foodID;
        this.caTeGoryID = caTeGoryID;
        this.foodName = foodName;
        this.anh = anh;
        this.video = video;
        this.address = address;
        this.tacGia = tacGia;
        this.congThuc = congThuc;
        this.moTaDai = moTaDai;
    }
    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getCaTeGoryID() {
        return caTeGoryID;
    }

    public void setCaTeGoryID(int caTeGoryID) {
        this.caTeGoryID = caTeGoryID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(String congThuc) {
        this.congThuc = congThuc;
    }

    public String getMoTaDai() {
        return moTaDai;
    }

    public void setMoTaDai(String moTaDai) {
        this.moTaDai = moTaDai;
    }


    @Override
    public String toString() {
        return "Food{" +
                "foodID=" + foodID +
                ", caTeGoryID=" + caTeGoryID +
                ", foodName='" + foodName + '\'' +
                ", anh=" + Arrays.toString(anh) +
                ", video='" + video + '\'' +
                ", address='" + address + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", congThuc='" + congThuc + '\'' +
                ", moTaDai='" + moTaDai + '\'' +
                '}';
    }
}

