package com.example.Android_App;
import Models.ItemCart;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import Models.Item;
import Models.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Set database properties
    public static final String DATABASE_NAME = "ItemDB";
    public static final int DATABASE_VERSION = 1;
    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Item.CREATE_STATEMENT);
        db.execSQL(Cart.CREATE_STATEMENT);
        db.execSQL(ItemCart.CREATE_STATEMENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Item.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Cart.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemCart.TABLE_NAME);
        onCreate(db);
    }
//
// ITEM database methods
//
    public void addItem(Item i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Item.COLUMN_NAME, i.getName());
        values.put(Item.COLUMN_PRICE, i.getPrice());
        db.insert(Item.TABLE_NAME, null, values);
        db.close();
    }
    public HashMap<Long, Item> getAllItems() {
        HashMap<Long, Item> items = new LinkedHashMap<Long, Item>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Item.TABLE_NAME, null);
// Add item to hash map for each row result
        if(cursor.moveToFirst()) {
            do {
                Item i = new Item(cursor.getLong(0), cursor.getString(1), cursor.getDouble(2));
                items.put(i.getId(), i);
            } while(cursor.moveToNext());
        }
// Close cursor
        cursor.close();
        return items;
    }
 //
// CART database methods
//
    public void addCart(Cart c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Cart.COLUMN_NAME, c.getName());
        long id = db.insert(Cart.TABLE_NAME, null, values);
        db.close();
    }
    public Cart getDefaultCart() {
// Just a quick method to get first cart created
// You can extend this if you wish to manage multiple carts
        SQLiteDatabase db = this.getWritableDatabase();
        Cart c = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Cart.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            c = new Cart(cursor.getLong(0), cursor.getString(1));
        }
        cursor.close();
        db.close();
        return c;
    }
//
// ITEMCART database methods
//
    public void addItemToCart(Cart c, Item i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemCart.COLUMN_CART_ID, c.getId());
        values.put(ItemCart.COLUMN_CART_ID, i.getId());
        db.insert(ItemCart.TABLE_NAME, null, values);
    }
    public ArrayList<Item> getItemsFromCart(Cart c) {
        SQLiteDatabase db = this.getWritableDatabase();
// Get all items in database and create a new ArrayList to return restricted result
        HashMap<Long, Item> itemData = getAllItems();
        ArrayList<Item> resultItems = new ArrayList<Item>();
// Get all Items where Cart = parameter id
        Cursor cursor = db.rawQuery("SELECT * FROM " + ItemCart.TABLE_NAME +
                        " WHERE " + ItemCart.COLUMN_CART_ID + " = 1",
                null);
// Add item to list for each row result
        if(cursor.moveToFirst()) {
            do {
                long itemID = cursor.getLong(0);
                resultItems.add(itemData.get(itemID));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultItems;
    };
    public void removeItemFromCart(Cart p, Item m) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ItemCart.TABLE_NAME,
                ItemCart.COLUMN_ITEM_ID + " = ? AND " +
                        ItemCart.COLUMN_CART_ID + " = ?",
                new String[]{String.valueOf(m.getId()), String.valueOf(p.getId())}
        );
        db.close();
    }
}