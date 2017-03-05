package com.amador.cites.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.amador.cites.CitesAplication;
import com.amador.cites.provider.CitesProviderContract;

/**
 * Created by amador on 5/03/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cites.db";
    private static final int DATABASE_VERSION = 1;
    private static DataBaseHelper instance;
    private SQLiteDatabase database;

    public static DataBaseHelper getInstance(){

        if(instance == null){

            instance = new DataBaseHelper();
        }

        return instance;
    }

    public SQLiteDatabase getDatabase(){

        return database;
    }

    private DataBaseHelper() {
        super(CitesAplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.beginTransaction();

        try {
            sqLiteDatabase.execSQL(CitesProviderContract.ClienEntry.SQL_CREATE);
            sqLiteDatabase.execSQL(CitesProviderContract.CitesEntry.SQL_CREATE);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.beginTransaction();

        try {
            sqLiteDatabase.execSQL(CitesProviderContract.CitesEntry.SQL_DELETE);
            sqLiteDatabase.execSQL(CitesProviderContract.ClienEntry.SQL_DELETE);
            onCreate(sqLiteDatabase);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){

            db.setForeignKeyConstraintsEnabled(true);

        }else {

            db.execSQL("PRAGMA foreign_keys = ON");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }
}
