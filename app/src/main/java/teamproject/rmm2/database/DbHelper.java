package teamproject.rmm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import teamproject.rmm2.models.Habit;

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

    //auxiliary classes for GETTING data from DB





    public Habit getHabit(String title){
        //TODO: SHIT WORKS! Good example for other stuff
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();
        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Habits.TABLE_NAME + " " + "WHERE " + Contract.Habits.COLUMN_HABIT_TITLE + " LIKE \'" + title + "\'";
        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);

        //TODO: error: for some reason it doesn't find habit by title

        // looping through all rows and selecting
        if(cursor.moveToFirst()){
            do{
                Habit habit = new Habit();
                habit.setTitle(cursor.getString(0));

                if (habit.getTitle().equals(title)) {
                    habit.setDescription(cursor.getString(1));
                    habit.setFrequency(cursor.getInt(2));

                    //returns found row
                    return habit;
                }

            }while(cursor.moveToNext());
        }

        //TODO exception handling. Returns null if nothing is found.
        return null;
    }

    public List<Habit> getAllHabits() {
        //TODO: check & test
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();
        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Habits.TABLE_NAME;
        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);
        //Creating list
        List<Habit> habitList = new ArrayList<Habit>();

        // looping through all rows and selecting
        if (cursor.moveToFirst()) {
            do {
                Habit habit = new Habit();
                habit.setTitle(cursor.getString(0));
                habit.setDescription(cursor.getString(1));
                habit.setFrequency(cursor.getInt(2));
                //adding to list
                habitList.add(habit);

            } while (cursor.moveToNext());

            return habitList;
        }
        else return null;
    }

    public Date  getDate(String day){
        //TODO
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();

        Date date = new Date();
        return date;
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


    public void updateCalendar(int state, String date, String habit){
        //TODO check dat shieeeeet. I have no idea if it works at all.
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(Contract.Calendar.COLUMN_HABIT_TITLE, habit);
        cValues.put(Contract.Calendar.COLUMN_DATE, date);
        cValues.put(Contract.Calendar.COLUMN_STATE, state);

        database.update(Contract.Calendar.TABLE_NAME, cValues,
                Contract.Calendar.COLUMN_HABIT_TITLE + " = " + habit + " AND " + Contract.Calendar.COLUMN_DATE + " = " + date,
                null);
    }






    public void deleteHabit(String habit){
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(Contract.Habits.TABLE_NAME, Contract.Habits.COLUMN_HABIT_TITLE + " = " + habit, null);
    }

}
