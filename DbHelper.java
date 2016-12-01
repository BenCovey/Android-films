package com.example.benvc.films;

/**
 * Created by benvc on 2016-11-29.
 */
import android.database.*;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.res.*;
import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Films.db";
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE `films` (`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "`rating`	INTEGER NOT NULL DEFAULT 0," +
                    "`title`	TEXT NOT NULL," +
                    "`desc`	TEXT," +
                    "`thumbnail`	TEXT NOT NULL DEFAULT '?'," +
                    "`trailer`	TEXT NOT NULL DEFAULT '?'" +
                    ");";
    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS films";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL(SQL_DELETE_ENTRIES);
// Creating tables again
        onCreate(db);
    }
}