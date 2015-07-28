package teamproject.rmm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "RMM2.db";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO schema, it's still just an example/ foundation
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_CALENDAR);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_HABITS);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_STATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
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
     * @param id
     * @param title
     * @param description
     * @param frequency
     */
    private void insertHabit(int id, String title, String description, int frequency){
        //TODO insert into HABITS
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
        newRowId = database.insert(
                Contract.Habits.TABLE_NAME,
                null,
                values);
    }

    //TODO delete from HABITS


    /**
     * insert into CALENDAR table in database
     * @param id
     * @param habittitle
     * @param date
     * @param state
     */
    private void insertDate(int id, int habittitle, String date, int state){
        //TODO insert into DATES, dates are held as TEXT, REAL, INTEGER using built-in date and time functions
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.Calendar.COLUMN_HABIT_TITLE, habittitle);
        values.put(Contract.Calendar.COLUMN_DATE, date);
        values.put(Contract.Calendar.COLUMN_STATE, state);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        newRowId = database.insert(
                Contract.Habits.TABLE_NAME,
                null,
                values);
    }


    //TODO insert into STATES (only on create and on upgrade)



}
