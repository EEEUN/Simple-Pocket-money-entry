package com.example.simple_pocket_money_entry.add;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ConsumptionList.db";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TableInfo.TABLE_NAME + " (" +
                    TableInfo.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    TableInfo.COLUMN_NAME_TYPE + " TEXT," +
                    TableInfo.COLUMN_NAME_DATE + " TEXT," +
                    TableInfo.COLUMN_NAME_CONTENT + " TEXT," +
                    TableInfo.COLUMN_NAME_CATEGORY + " TEXT," +
                    TableInfo.COLUMN_NAME_AMOUNT + " TEXT)";

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
}
