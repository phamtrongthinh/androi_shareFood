package com.example.myapplication.model;

public class User {
    private  int accountID , roleID;
    private  String fullname , email , password, phone , adress;

    public User() {
    }

    public User(int accountID, int roleID, String fullname, String email, String password, String phone, String adress) {
        this.accountID = accountID;
        this.roleID = roleID;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.adress = adress;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
