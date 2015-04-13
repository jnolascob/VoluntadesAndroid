package me.doapps.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jnolascob on 26/09/2014.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME =  "voluntadesrrhh.sqlite";
    private static final int DB_SCHEME_VERSION = 2;


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE_VOLUNTEER);
        db.execSQL(DataBaseManager.CREATE_TABLE_CALENDAR);
        db.execSQL(DataBaseManager.CREATE_TABLE_ASSISTANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBaseManager.DROP_TABLE_VOLUNTEER);
        db.execSQL(DataBaseManager.DROP_TABLE_CALENDAR);
        db.execSQL(DataBaseManager.DROP_TABLE_ASSISTANCE);
        onCreate(db);
    }
}
