package com.example.thebloomroom.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.thebloomroom.Classes.Category;
import com.example.thebloomroom.Classes.Flower;
import com.example.thebloomroom.Classes.Order;
import com.example.thebloomroom.Classes.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String FLOWER_TABLE = "flower";
    public static final String USER_TABLE = "user";
    public static final String CATEGORY_TABLE = "category";
    public static final String FLOWER_ORDER_TABLE = "flowerOrder";
    public static final String ID = "id";
    public static final String COLUMN_FLOWER_NAME = "flowerName";
    public static final String COLUMN_FLOWER_DESC = "flowerDesc";
    public static final String COLUMN_IMG_NAME = "imgName";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CAT_ID = "catId";
    public static final String COLUMN_CAT_NAME = "catName";
    public static final String COLUMN_CAT_DESC = "catDesc";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_USERNAME = USER_TABLE + "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_ID = USER_TABLE + "Id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_ITEMS = "items";
    public static final String COLUMN_TOTAL = "total";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "bloomRoom.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + FLOWER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FLOWER_NAME + " TEXT, " + COLUMN_FLOWER_DESC + " TEXT, " + COLUMN_IMG_NAME + " TEXT, " + COLUMN_PRICE + " REAL, " + COLUMN_CAT_ID + " INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CAT_NAME + " TEXT, " + COLUMN_CAT_DESC + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_ROLE + " TEXT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PASSWORD + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + FLOWER_ORDER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_ID + " INTEGER, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_ITEMS + " TEXT, " + COLUMN_TOTAL + " REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FLOWER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FLOWER_ORDER_TABLE);
        onCreate(db);
    }

    public boolean insertFlower(Flower flower) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FLOWER_NAME, flower.getFlowerName());
        contentValues.put(COLUMN_FLOWER_DESC, flower.getFlowerDesc());
        contentValues.put(COLUMN_IMG_NAME, flower.getImgName());
        contentValues.put(COLUMN_CAT_ID, flower.getCatId());
        contentValues.put(COLUMN_PRICE, flower.getPrice());
        long insertToFlower = sqLiteDatabase.insert(FLOWER_TABLE, null, contentValues);


        if (insertToFlower == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToFlower);
            return true;
        }

    }

    public List<Flower> getAllFlowers() {
        List<Flower> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + FLOWER_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String flowerName = cursor.getString(1);
                String flowerDesc = cursor.getString(2);
                String imgName = cursor.getString(3);
                double price = cursor.getDouble(4);
                int catId = cursor.getInt(5);

                Flower flower = new Flower(id, flowerName, flowerDesc, imgName, price, catId);
                returnList.add(flower);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
//        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteFlower(Flower flower) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromFlower = sqLiteDatabase.delete(FLOWER_TABLE, ID + " = " + flower.getId(), null);
        if (deleteFromFlower != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateFlower(Flower flower) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FLOWER_NAME, flower.getFlowerName());
        contentValues.put(COLUMN_FLOWER_DESC, flower.getFlowerDesc());
        contentValues.put(COLUMN_IMG_NAME, flower.getImgName());
        contentValues.put(COLUMN_CAT_ID, flower.getCatId());
        contentValues.put(COLUMN_PRICE, flower.getPrice());
        long updateFlower = sqLiteDatabase.update(FLOWER_TABLE, contentValues, ID + " = " + flower.getId(), null);

        if (updateFlower == -1) {
            return false;
        }

        Log.e(TAG, "update data :" + updateFlower);
        return true;

    }

    public boolean insertCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CAT_NAME, category.getCatName());
        contentValues.put(COLUMN_CAT_DESC, category.getCatDesc());
        long insertToCat = sqLiteDatabase.insert(CATEGORY_TABLE, null, contentValues);


        if (insertToCat == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToCat);
            return true;
        }

    }

    public List<Category> getAllCategories() {
        List<Category> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + CATEGORY_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String catName = cursor.getString(1);
                String catDesc = cursor.getString(2);

                Category category = new Category(id, catName, catDesc);
                returnList.add(category);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
        //sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromCat = sqLiteDatabase.delete(CATEGORY_TABLE, ID + " = " + category.getId(), null);

        if (deleteFromCat != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CAT_NAME, category.getCatName());
        contentValues.put(COLUMN_CAT_DESC, category.getCatDesc());
        long updateCat = sqLiteDatabase.update(CATEGORY_TABLE, contentValues, ID + " = " + category.getId(), null);

        if (updateCat == -1) {
            return false;
        } else {
            Log.e(TAG, "update data :" + updateCat);
            return true;
        }
    }

    public boolean insertUser(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_ROLE, user.getRole());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        long insertToUser = sqLiteDatabase.insert(USER_TABLE, null, contentValues);


        if (insertToUser == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToUser);
            return true;
        }

    }

    public List<User> getAllUsers() {
        List<User> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USER_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userRole = cursor.getString(2);
                String userUserName = cursor.getString(3);
                String userEmail = cursor.getString(4);
                String userPassword = cursor.getString(5);

                User user = new User(id, userName, userRole, userUserName, userEmail, userPassword);
                returnList.add(user);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
        //sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromUser = sqLiteDatabase.delete(USER_TABLE, ID + " = " + user.getId(), null);

        if (deleteFromUser != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_ROLE, user.getRole());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        long updateUser = sqLiteDatabase.update(USER_TABLE, contentValues, ID + " = " + user.getId(), null);

        if (updateUser == -1) {
            return false;
        } else {
            Log.e(TAG, "update data :" + updateUser);
            return true;
        }
    }

    public boolean insertOrder(Order order){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, order.getUserId());
        contentValues.put(COLUMN_ADDRESS, order.getAddress());
        contentValues.put(COLUMN_ITEMS, order.getItems());
        contentValues.put(COLUMN_TOTAL, order.getTotal());
        long insertToOrder = sqLiteDatabase.insert(FLOWER_ORDER_TABLE, null, contentValues);


        if (insertToOrder == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToOrder);
            return true;
        }

    }

    public List<Order> getAllOrders() {
        List<Order> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + FLOWER_ORDER_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int userId = cursor.getInt(1);
                String address = cursor.getString(2);
                String items = cursor.getString(3);
                double total = cursor.getDouble(4);

                Order order = new Order(id, userId, address, items, total);
                returnList.add(order);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
        //sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteOrder(Order order) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromUser = sqLiteDatabase.delete(FLOWER_ORDER_TABLE, ID + " = " + order.getId(), null);

        if (deleteFromUser != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateOrder(Order order) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, order.getUserId());
        contentValues.put(COLUMN_ADDRESS, order.getAddress());
        contentValues.put(COLUMN_ITEMS, order.getItems());
        contentValues.put(COLUMN_TOTAL, order.getTotal());
        long updateOrder = sqLiteDatabase.update(FLOWER_ORDER_TABLE, contentValues, ID + " = " + order.getId(), null);

        if (updateOrder == -1) {
            return false;
        } else {
            Log.e(TAG, "update data :" + updateOrder);
            return true;
        }
    }
    //todo close db in getAll methods

}


