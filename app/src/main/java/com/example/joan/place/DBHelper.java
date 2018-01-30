package com.example.joan.place;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joan on 2017/9/27.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String S_DB = "MyDatabases.db";
    public static final int _DB_VERSION = 1;
    public static final String tablenamelist="Table_name_list";
    public static final String tablename="name";
    public static String create_table_cloumn=" ( _ID INTEGER PRIMARY KEY," +tablename
            +" TEXT)";

    public DBHelper(Context context) {
        super(context, S_DB, null, _DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+tablenamelist+create_table_cloumn);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+tablenamelist+create_table_cloumn);
    }
}
