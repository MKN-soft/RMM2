package teamproject.rmm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcin on 2015-07-21.
 *
 * A useful set of APIs is available in the SQLiteOpenHelper class.
 * When you use this class to obtain references to your database,
 * the system performs the potentially long-running operations of creating
 * and updating the database only when needed and not during app startup.
 * All you need to do is call getWritableDatabase() or getReadableDatabase().
 *
 * Note: Because they can be long-running, be sure that you call getWritableDatabase() or getReadableDatabase() in a background thread, such as with AsyncTask or IntentService.
 */
public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "RMM2.db";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO schema, it's still just an example/ foundation
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_HABITS);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_STATES);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_CALENDAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO think about upgrading policy and how to do it (I think it's ok as it is though) (MR)
        //Current upgrade policy is to simply to discard the data and start over
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabase.execSQL(Contract.SQL_DELETE_CALENDAR);
        sqLiteDatabase.execSQL(Contract.SQL_DELETE_HABITS);
        sqLiteDatabase.execSQL(Contract.SQL_DELETE_STATES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    /**
     * insert into HABITS table in database
     * @param title
     * @param description
     * @param frequency
     */
    public void insertHabit(String title, String description, int frequency){
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.Habits.COLUMN_HABIT_TITLE, title);
        values.put(Contract.Habits.COLUMN_HABIT_DESCRIPTION, description);
        values.put(Contract.Habits.COLUMN_HABIT_FREQUENCY, frequency);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        database.insert(
                Contract.Habits.TABLE_NAME,
                null,
                values);
    }


    public void getHabit(String habit){
        //TODO
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();
    }

    public void deleteHabit(String habit){
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(Contract.Habits.TABLE_NAME, Contract.Habits.COLUMN_HABIT_TITLE + " = " + habit, null);
    }




    /**
     * insert into CALENDAR table in database. Takes CURRENT DATE in YYYY-MM-DD
     * @param habittitle
     * @param state
     */
    public void insertDate(String habittitle, int state){
        //We insert dates as TEXT   yyyy-MM-dd
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        //getting date and formatting date to yyyy-mm-dd
        Date date = new Date(); //constructor gets current date
        String formattedDate = new SimpleDateFormat("yyy-MM-dd").format(date);

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.Calendar.COLUMN_HABIT_TITLE, habittitle);
        values.put(Contract.Calendar.COLUMN_DATE, formattedDate);   // uses CURRENT date
        values.put(Contract.Calendar.COLUMN_STATE, state);


        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        database.insert(
                Contract.Habits.TABLE_NAME,
                null,
                values);
    }

    public void updateCalendar(int state, String date, String habit){
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(Contract.Calendar.COLUMN_HABIT_TITLE, habit);
        cValues.put(Contract.Calendar.COLUMN_DATE, date);
        cValues.put(Contract.Calendar.COLUMN_STATE, state);

        database.update(Contract.Calendar.TABLE_NAME, cValues,
                Contract.Calendar.COLUMN_HABIT_TITLE + " = " + habit  + " AND " + Contract.Calendar.COLUMN_DATE + " = " + date,
                null);
    }

    private void insertState(String state){
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.States.COLUMN_STATE, state);


        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        database.insert(
                Contract.States.TABLE_NAME,
                null,
                values);
    }



}
