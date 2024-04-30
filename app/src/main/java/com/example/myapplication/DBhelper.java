package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    private static final String dbname ="foodapp.sqlite";
    public static final String TB_FOOD="food";
    public static final String TB_USER= "user";
    private static int version = 1;

    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, version);
    }

    public DBhelper(@Nullable Context context) {
        super(context,dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao bang user
        String sqlUserTable = "create table if not exists user(" +
                "accountID integer primary key autoincrement," +
                "roleID interger not null," +
                "fullName text not null," +
                "email text not null," +
                "password text not null," +
                "phone text not null," +
                "address text not null)";
        db.execSQL(sqlUserTable);
        String sql_insert ="insert into user values (null,1,'Admin','admin@admin.com','admin','0111111','Ha Noi')";
        db.execSQL(sql_insert);

        //Tạo bảng Food
        String sqlFoodTable = "create table if not exists food(" +
                "foodid integer primary key autoincrement," +
                "categoryid integer," +
                "foodname text not null," +
                "anh text not null, " +
                "video text not null," +
                "address text not null," +
                "tacgia text not null," +
                "congthuc text not null, " +
                "motadai text not null)";
        db.execSQL(sqlFoodTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor selectData(String sql){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cs = sqLiteDatabase.rawQuery(sql,null);
        return cs;
    }
    public long insert(String table, String nullCollumnHack, ContentValues values){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(table,nullCollumnHack,values);
    }
    public long update(String table, ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.update(table,values,whereClause,whereArgs);
    }
    public long delete(String table,String whereClause, String[] whereArgs){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.delete(table,whereClause,whereArgs);
    }


    public boolean checkUsername (String email)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cs = db.rawQuery("select * from user where email=?", new String[]{email});
        if (cs.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String email , String pass)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cs = db.rawQuery("select * from user where email=? and password=?", new String[]{email,pass});
        if (cs.getCount()>0)
            return true;
        else
            return false;
    }
}
