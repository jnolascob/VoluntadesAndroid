package me.doapps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jnolascob on 26/09/2014.
 */
public class DataBaseManager {
    public static final String TABLE_VOLUNTEER = "volunteer";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String LAST_NAME = "last_name";
    public static final String GENDER = "gender";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    public static final String TABLE_CALENDAR = "calendar";
    public static final String DATE = "date";

    public static final String TABLE_ASSISTANCE = "assistance";
    public static final String CALENDAR_ID = "calendar_id";
    public static final String VOLUNTEER_ID = "volunteer_id";
    public static final String STATE = "state";

    public static final String CREATE_TABLE_VOLUNTEER = "create table " + TABLE_VOLUNTEER + " ("
            + ID + " integer primary key autoincrement,"
            + NAME + " text not null,"
            + LAST_NAME + " text,"
            + GENDER + " integer,"
            + CREATED_AT + " text,"
            + UPDATED_AT + " text);";
    public static final String DROP_TABLE_VOLUNTEER = "drop table if exist " + TABLE_VOLUNTEER;


    public static final String CREATE_TABLE_CALENDAR = "create table "+ TABLE_CALENDAR + " ("
            + ID + " integer primary key autoincrement,"
            + DATE + " text,"
            + CREATED_AT + " text,"
            + UPDATED_AT + " text);";
    public static final String DROP_TABLE_CALENDAR = "drop table if exist " + TABLE_CALENDAR;


    public static final String CREATE_TABLE_ASSISTANCE = "create table " + TABLE_ASSISTANCE + " ("
            + ID + " integer primary key autoincrement,"
            + CALENDAR_ID + " integer not null,"
            + VOLUNTEER_ID + " integer not null,"
            + STATE + " integer);";
    public static final String DROP_TABLE_ASSISTANCE = "drop table if exist " + TABLE_ASSISTANCE;

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formater_simple = new SimpleDateFormat("dd/MM/yyyy");
    Calendar calendar = Calendar.getInstance();

    public DataBaseManager(Context context){
        helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    /** VOLUNTEER **/
    /*generate ContentValues*/
    private ContentValues generateContentValues(String name, String last_name, String gender, String created_at, String updated_at){
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(LAST_NAME, last_name);
        values.put(GENDER, gender);
        values.put(CREATED_AT, created_at);
        values.put(UPDATED_AT, updated_at);
        return values;
    }
    /*Insert*/
    public void insertVolunteer(String name, String last_name, String gender){
        String created_at = formater.format(calendar.getTime());
        String updated_at = formater.format(calendar.getTime());
        db.insert(TABLE_VOLUNTEER, null, generateContentValues(name, last_name, gender, created_at, updated_at));
    }
    /*Delete*/
    public void deleteVolunteer(String id){
        db.delete(TABLE_VOLUNTEER, ID+"=?", new String[]{id});
    }
    public void multipleDeleteVolunteer(String id1, String id2){
        db.delete(TABLE_VOLUNTEER, ID+"IN (?, ?)", new String[]{id1, id2});
    }
    /*Update*/
    public void updateDB(String id, String name, String last_name,String gender, String created_at, String updated_at){
        db.update(TABLE_VOLUNTEER, generateContentValues(name, last_name, gender, created_at, updated_at), NAME+"=?", new String[]{id});
    }
    /*Select*/
    public Cursor selectVolunteer(){
        String[] columns = new String[]{ID, NAME, LAST_NAME, GENDER};
        return db.query(TABLE_VOLUNTEER, columns, null, null, null, null, null);
    }
    /*select by ids*/
    public Cursor selectVolunteer(String[] ids){
        String[] columns = new String[]{ID, NAME, LAST_NAME, GENDER};
        return db.query(TABLE_VOLUNTEER,columns,ID+"=?",ids,null, null, null);
    }
    /*select by id*/
    public Cursor selectVolunteer(String id){
        String[] columns = new String[]{ID, NAME, LAST_NAME, GENDER};
        return db.query(TABLE_VOLUNTEER,columns,ID+"=?",new String[]{id},null, null, null);
    }
    /** ASSITANCE **/
    /*Generate content values*/
    private ContentValues contentValuesAssistance(int calendar_id, int volunteer_id, int state){
        ContentValues values = new ContentValues();
        values.put(CALENDAR_ID, calendar_id);
        values.put(VOLUNTEER_ID, volunteer_id);
        values.put(STATE, state);
        return values;
    }
    /*Insert Assitance*/
    public void insertAssitance(int calendar_id, int volunteer_id, int state){
        db.insert(TABLE_ASSISTANCE, null,contentValuesAssistance(calendar_id, volunteer_id, state));
    }
    /*Update Assitance*/
    public void updateAssistance(String id, String state){
        ContentValues values = new ContentValues();
        values.put(STATE, state);
        db.update(TABLE_ASSISTANCE, values, ID+"=?", new String[]{id});
    }
    /*Select Assitance*/
    public Cursor selectAssitance(){
        String[] columns = new String[]{ID, CALENDAR_ID, VOLUNTEER_ID, STATE};
        return db.query(TABLE_ASSISTANCE, columns, null, null, null, null, null);
    }
    /*Select Assitance*/
    public Cursor selectAssitance(String calendar_id){
        String[] columns = new String[]{ID, CALENDAR_ID, VOLUNTEER_ID, STATE};
        return db.query(TABLE_ASSISTANCE, columns, CALENDAR_ID+"=?",new String[]{calendar_id}, null, null, null);
    }
    /*Count Assitance by Calendar_id*/
    public int countAssistance(int calendar_id){
        Cursor mCount = db.rawQuery("select count(*) from "+ TABLE_ASSISTANCE + " where " + CALENDAR_ID + " = " + calendar_id + "", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        return count;
    }

    /** CALENDAR **/
    /*Generate content values*/
    private ContentValues contentValuesCalendar(String date, String created_at, String updated_at){
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(CREATED_AT, created_at);
        values.put(UPDATED_AT, updated_at);
        return values;
    }
    /*Insert Calendar*/
    public void insertCalendar(String date){
        String created_at = formater_simple.format(calendar.getTime());
        String updated_at = formater_simple.format(calendar.getTime());
        db.insert(TABLE_CALENDAR, null, contentValuesCalendar(date, created_at, updated_at));
    }
    /*Select Calendar*/
    public Cursor selectCalendar(){
        String[] columns = new String[]{ID, DATE, CREATED_AT, UPDATED_AT};
        return db.query(TABLE_CALENDAR, columns, null, null, null, null, null);
    }
    /*Get Calendar_id*/
    public int getCalendarId(String date){
        Cursor mCount = db.rawQuery("select "+ID+" from " + TABLE_CALENDAR + " where "+ DATE +" = '" + date+ "'", null);
        mCount.moveToFirst();
        int calendar_id = mCount.getInt(0);
        return calendar_id;
    }
    /*Exist Calendar*/
    public int countCalendar(String date){
        Cursor mCount = db.rawQuery("select count(*) from "+ TABLE_CALENDAR + " where " + DATE + " = '" + date+ "'", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        return count;
    }
 }
