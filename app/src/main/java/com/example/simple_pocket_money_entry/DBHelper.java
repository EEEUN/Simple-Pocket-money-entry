package com.example.simple_pocket_money_entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "myList.db";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TableInfo.TABLE_NAME + " (" +
                    TableInfo.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    TableInfo.COLUMN_NAME_TYPE + " TEXT," +
                    TableInfo.COLUMN_NAME_DATE + " TEXT," +
                    TableInfo.COLUMN_NAME_FULL_DATE + " TEXT," +
                    TableInfo.COLUMN_NAME_CONTENT + " TEXT," +
                    TableInfo.COLUMN_NAME_CATEGORY + " TEXT," +
                    TableInfo.COLUMN_NAME_AMOUNT + " TEXT," +
                    TableInfo.COLUMN_NAME_FULL_AMOUNT + " TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public boolean updateData(String id, String type, String date, String fullDate, String content,
                             String category, String amount, String fullAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String strFilter = "_id=" + id;

        values.put(TableInfo.COLUMN_NAME_TYPE, type);
        values.put(TableInfo.COLUMN_NAME_DATE, date);
        values.put(TableInfo.COLUMN_NAME_FULL_DATE, fullDate);
        values.put(TableInfo.COLUMN_NAME_CONTENT, content);
        values.put(TableInfo.COLUMN_NAME_CATEGORY, category);
        values.put(TableInfo.COLUMN_NAME_AMOUNT, amount);
        values.put(TableInfo.COLUMN_NAME_FULL_AMOUNT, fullAmount);

        db.update(TableInfo.TABLE_NAME, values, strFilter, null);
        db.close();

        return true;
    }

    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = "_id=" + id;

        db.delete(TableInfo.TABLE_NAME, strFilter, null);
        db.close();
        return true;
    }
}
