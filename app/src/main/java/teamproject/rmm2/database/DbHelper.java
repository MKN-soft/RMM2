package teamproject.rmm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import teamproject.rmm2.models.CalendarRow;
import teamproject.rmm2.models.HabitRow;

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
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "RMM2.db";

    /**
     * "constructor" for database
     * @param context
     */
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_HABITS);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_STATES);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_CALENDAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
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

    //auxiliary classes for GETTING data from DB





    public HabitRow getHabit(String title){
        //SHIT WORKS! Good example for other stuff
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();
        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Habits.TABLE_NAME + " " + "WHERE " + Contract.Habits.COLUMN_HABIT_TITLE + " LIKE \'" + title + "\'";
        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);

        // looping through all rows and selecting
        if(cursor.moveToFirst()){
            do{
                HabitRow habitRow = new HabitRow();
                habitRow.setTitle(cursor.getString(0));

                if (habitRow.getTitle().equals(title)) {
                    habitRow.setDescription(cursor.getString(1));
                    habitRow.setFrequency(cursor.getInt(2));

                    //returns found row
                    return habitRow;
                }

            }while(cursor.moveToNext());
        }

        //Returns null if nothing is found.
        return null;
    }

    /**
     * returns list of ALL rows in table HABITS. Return null if table is empty.
     * @return
     */
    public List<HabitRow> getAllHabits() {
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();
        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Habits.TABLE_NAME;
        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);
        //Creating list
        List<HabitRow> habitRowList = new ArrayList<HabitRow>();

        // looping through all rows and selecting
        if (cursor.moveToFirst()) {
            do {
                HabitRow habitRow = new HabitRow();
                habitRow.setTitle(cursor.getString(0));
                habitRow.setDescription(cursor.getString(1));
                habitRow.setFrequency(cursor.getInt(2));
                //adding to list
                habitRowList.add(habitRow);

            } while (cursor.moveToNext());

            return habitRowList;
        }
        else return null;
    }

    /**
     * returns list of rows from DATES table - searches by date (column [1]), returns null if nothing is found
     * @param unixTimestamp
     * @return row from DATES table
     */
    public CalendarRow  getDate(long unixTimestamp, String habitTitle) {
        //TODO it should return LIST of rows
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();

        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Calendar.TABLE_NAME +
                " WHERE " + Contract.Calendar.COLUMN_DATE + " = " + unixTimestamp +
                " AND " + Contract.Calendar.COLUMN_HABIT_TITLE + " LIKE \'" +  habitTitle + "\'";

        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);

        // looping through all rows and selecting
        if (cursor.moveToFirst()) {
            CalendarRow calendarRow = new CalendarRow();
            calendarRow.setTime(unixTimestamp);
            calendarRow.setHabit(cursor.getString(1));
            calendarRow.setState(cursor.getInt(2));

            //returns found row
            return calendarRow;
        }
        return null;
    }

    /**
     * Returns list of all rows from table STATES
     * @return List<String> of STATES (null if empty)
     */
    public List<String>  getAllStates() {
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();

        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.States.TABLE_NAME;
        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);

        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            List<String> statesList = new ArrayList<String>();
            do{
                statesList.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        return null;
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
        database.insertOrThrow(
                Contract.Habits.TABLE_NAME,
                null,
                values);
    }

    /**
     * insert into CALENDAR table in database. Takes CURRENT DATE
     * @param habittitle
     * @param state
     */
    public void insertDate(long unixTimestamp, String habittitle, int state){
        //We insert dates as TEXT   yyyy-MM-dd
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();





        ContentValues values = new ContentValues();
        values.put(Contract.Calendar.COLUMN_DATE, unixTimestamp);   // uses  date in unix time - only hours 00:00:00
        values.put(Contract.Calendar.COLUMN_HABIT_TITLE, habittitle);
        values.put(Contract.Calendar.COLUMN_STATE, state);


        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        database.insert(
                Contract.Calendar.TABLE_NAME,
                null,
                values);
    }

    public void insertState(String state){
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







    public void deleteHabit(String habit){
        //TODO habit deletion
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(Contract.Habits.TABLE_NAME, Contract.Habits.COLUMN_HABIT_TITLE + " = " + habit, null);
    }

}
