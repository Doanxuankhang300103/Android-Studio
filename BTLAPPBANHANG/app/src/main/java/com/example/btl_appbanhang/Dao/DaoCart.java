package com.example.btl_appbanhang.Dao;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.btl_appbanhang.Model.Cart;
import com.example.btl_appbanhang.database.DBHelper;

import java.util.ArrayList;

public class DaoCart {
    private final DBHelper dbHelper;

    public DaoCart(Context context) {
        dbHelper = new DBHelper(context);
    }
    public ArrayList<Cart> selectCartByIdUser(String iduser) {
        ArrayList<Cart> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        SQLiteDatabase dbb = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM cart WHERE cart.iduser = ?", new String[] {iduser});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Cart cart = new Cart();
                    cart.setId(cursor.getInt(0));
                    cart.setQuantity(cursor.getInt(1));
                    cart.setPrice(cursor.getInt(2));
                    cart.setIduser(cursor.getInt(3));
                    cart.setIdproduct(cursor.getInt(4));
                    list.add(cart);
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
    public boolean insert(Cart cart) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        String query = "SELECT quantity FROM cart WHERE iduser = ? AND idproduct = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cart.getIduser()), String.valueOf(cart.getIdproduct())});

        if (cursor.moveToFirst()) {
            // Nếu sản phẩm đã tồn tại, tăng số lượng
            int existingQuantity = cursor.getInt(0);
            int newQuantity = existingQuantity + cart.getQuantity();

            // Cập nhật số lượng mới
            ContentValues values = new ContentValues();
            values.put("quantity", newQuantity);
            values.put("price", cart.getPrice()); // Cập nhật tổng giá mới nếu cần
            db.update("cart", values, "iduser = ? AND idproduct = ?", new String[]{String.valueOf(cart.getIduser()), String.valueOf(cart.getIdproduct())});
        } else {
            // Nếu sản phẩm không tồn tại, thêm mới vào giỏ hàng
            ContentValues values = new ContentValues();
            values.put("iduser", cart.getIduser());
            values.put("idproduct", cart.getIdproduct());
            values.put("quantity", cart.getQuantity());
            values.put("price", cart.getPrice());
            db.insert("cart", null, values);
        }

        cursor.close();
        return true;
    }
    public boolean update(Cart cart) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", cart.getQuantity());
        values.put("price", cart.getPrice());

        // Cập nhật số lượng và giá trị trong bảng cart
        return db.update("cart", values, "id = ?", new String[]{String.valueOf(cart.getId())}) > 0;
    }

    public boolean deleteAll(String idUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("cart", "iduser=?", new String[]{String.valueOf(idUser)});
        return (row > 0);
    }
    public boolean delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("cart", "id=?", new String[]{String.valueOf(id)});
        return (row > 0);
    }
}
