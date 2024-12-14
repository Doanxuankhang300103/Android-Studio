package com.example.btl_appbanhang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbName = "Cart";
    private static final int version = 1;

    public DBHelper(@Nullable Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_cart = "CREATE TABLE cart(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "quantity INTEGER NOT NULL, " +
                "price INTEGER NOT NULL, " +
                "iduser INTEGER NOT NULL, " +
                "idproduct INTEGER NOT NULL)";
        db.execSQL(tb_cart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i1 != i) {
            db.execSQL("DROP TABLE IF EXISTS tb_cart");
            onCreate(db);
        }
    }
}
