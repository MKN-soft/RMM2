package teamproject.rmm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import teamproject.rmm2.Helpers.SessionManager;
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
    public static final int DATABASE_VERSION = 9;
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
//        this didn't work anyway - see onConfigure method
//        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_HABITS);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_STATES);
        sqLiteDatabase.execSQL(Contract.SQL_CREATE_CALENDAR);

        /*insertState(-1, "FAIL");
        insertState(0, "NEUTRAL");
        insertState(1, "DONE");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Current upgrade policy is to simply to discard the data and start over
//        this didn't work anyway - see onConfigure method
//        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
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
     * This makes constraints work
     * @param db - SQLiteDatabase
     */
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    //auxiliary classes for GETTING data from DB





    public HabitRow getHabit(String title){
        //SHIT WORKS! Good example for other stuff
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();
        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Habits.TABLE_NAME +
                " WHERE " + Contract.Habits.COLUMN_HABIT_TITLE + " LIKE \'" + title + "\'";
        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);

        // looping through all rows and selecting
        if(cursor.moveToFirst()){
            do{
                HabitRow habitRow = new HabitRow();
                habitRow.setTitle(cursor.getString(1));

                if (habitRow.getTitle().equals(title)) {
                    habitRow.setId(cursor.getInt(0));
                    habitRow.setDescription(cursor.getString(2));
                    habitRow.setFrequency(cursor.getInt(3));
                    habitRow.setPeriod(cursor.getInt(4));

                    //returns found row
                    return habitRow;
                }

            }while(cursor.moveToNext());
        }

        //Returns null if nothing is found.
        return null;
    }

    public int getState(int state) throws NoSuchFieldException {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Contract.States.TABLE_NAME +
                " WHERE " + Contract.States.COLUMN_STATE  + " = " + state;

        Cursor cursor = database.rawQuery(query, null);

        // looping through all rows and selecting
        if(cursor.moveToFirst()) {
            return cursor.getInt(1);
        }
        else throw new NoSuchFieldException();


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
                habitRow.setTitle(cursor.getString(1));
                habitRow.setDescription(cursor.getString(2));
                habitRow.setFrequency(cursor.getInt(3));
                habitRow.setPeriod(cursor.getInt(4));
                //adding to list
                habitRowList.add(habitRow);

            } while (cursor.moveToNext());

            return habitRowList;
        }
        else return null;
    }

    /**
     * returns list of rows from DATES table - searches by date (column [1]), returns null if nothing is found
     * @param calendar: Calendar
     * @return row from DATES table
     */
    public CalendarRow  getDate(Calendar calendar, String habitTitle) {
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();

        final long unixTimestamp = convertTimeToUnixTimestamp(calendar);

        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Calendar.TABLE_NAME +
                " WHERE " + Contract.Calendar.COLUMN_DATE + " = " + unixTimestamp +
                " AND " + Contract.Calendar.COLUMN_HABIT_TITLE + " LIKE \'" +  habitTitle + "\'";

        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);

        // looping through all rows and selecting
        if (cursor.moveToFirst()) {
            CalendarRow calendarRow = new CalendarRow();
            calendarRow.setId(cursor.getInt(0));
            calendarRow.setTime(cursor.getLong(1));
            calendarRow.setHabit(cursor.getString(2));
            calendarRow.setState(cursor.getInt(3));

            //returns found row
            return calendarRow;
        }
        return null;
    }

    /**
     * returns list of CALENDAR entries for habitTitle in ASCENDING ORDER
     * @param habitTitle
     * @return
     */
    public List<CalendarRow>  getDatesForHabit(String habitTitle) {
        // Gets the data repository in read mode
        SQLiteDatabase database = this.getReadableDatabase();

        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Calendar.TABLE_NAME +
                " WHERE " + Contract.Calendar.COLUMN_HABIT_TITLE + " LIKE \"" +  habitTitle + "\"" +
                " ORDER BY " + Contract.Calendar.COLUMN_DATE + " ASC";




        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);

        // looping through all rows and selecting
        if (cursor.moveToFirst()) {
            //Preparing list
            List<CalendarRow> calendarRowList = new ArrayList<CalendarRow>();
            do {
                CalendarRow calendarRow = new CalendarRow();
                calendarRow.setId(cursor.getInt(0));
                calendarRow.setTime(cursor.getLong(1));
                calendarRow.setHabit(cursor.getString(2));
                calendarRow.setState(cursor.getInt(3));

                calendarRowList.add(calendarRow);
            }while(cursor.moveToNext());
            //returns found row
            return calendarRowList;
        }
        else {
            return null;
        }
    }

    public List<CalendarRow> getAllDates(){
        SQLiteDatabase database = this.getReadableDatabase();
        //Preparing query (only for convenience purposes)
        String query = "SELECT * FROM " + Contract.Calendar.TABLE_NAME;
        //Preparing cursor for getting rows
        Cursor cursor = database.rawQuery(query, null);
        //Creating list
        List<CalendarRow> calendarRowList = new ArrayList<CalendarRow>();

        // looping through all rows and selecting
        if (cursor.moveToFirst()) {
            do {
                CalendarRow calendarRow = new CalendarRow();
                //calendarRow.setHabit(cursor.getString(1));
                //calendarRow.setTime(cursor.getLong(2));
                calendarRow.setTime(cursor.getLong(1));
                calendarRow.setHabit(cursor.getString(2));
                calendarRow.setState(cursor.getInt(3));
                //adding to list
                calendarRowList.add(calendarRow);

            } while (cursor.moveToNext());

            return calendarRowList;
        }
        else return new ArrayList<CalendarRow>(); // empty list


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
                statesList.add(cursor.getString(1));
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
    public void insertHabit(String title, String description, int frequency, int period) throws SQLiteConstraintException{
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.Habits.COLUMN_HABIT_TITLE, title);
        values.put(Contract.Habits.COLUMN_HABIT_DESCRIPTION, description);
        values.put(Contract.Habits.COLUMN_HABIT_FREQUENCY, frequency);
        values.put(Contract.Habits.COLUMN_HABIT_PERIOD, period);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        database.insert//TODO uncommentOrThrow(
                (
                Contract.Habits.TABLE_NAME,
                null,
                values);
    }

    /**
     * insert into CALENDAR table in database. Takes CURRENT DATE
     * @param calendar
     * @param habittitle
     * @param state
     */
    public void insertDate(Calendar calendar, String habittitle, int state) throws SQLiteConstraintException{
   // public void insertDate(long unixTimestamp, String habittitle, int state) throws SQLiteConstraintException{
        //We insert dates as UNIX time on midnight hour 00:00:00
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        final long unixTimestamp = convertTimeToUnixTimestamp(calendar);

        ContentValues values = new ContentValues();
        values.put(Contract.Calendar.COLUMN_DATE, unixTimestamp);   // uses  date in unix time - only hours 00:00:00
        values.put(Contract.Calendar.COLUMN_HABIT_TITLE, habittitle);
        values.put(Contract.Calendar.COLUMN_STATE, state);


        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        database.insert//TODO uncomment OrThrow(
                (
                Contract.Calendar.TABLE_NAME,
                null,
                values);
    }

    /**
     * inserts state, only on creation (or update) of database
     * @param state
     * @param stateName
     * @throws SQLiteConstraintException
     */
    public void insertState(int state, String stateName) throws SQLiteConstraintException{
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
        database.insert//todo uncomment OrThrow(
                (
                Contract.States.TABLE_NAME,
                null,
                values);
    }

    public void editHabit(Context context, String description, int frequency, int periodicity) throws SQLiteConstraintException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.Habits.COLUMN_HABIT_DESCRIPTION, description);
        values.put(Contract.Habits.COLUMN_HABIT_FREQUENCY, frequency);
        values.put(Contract.Habits.COLUMN_HABIT_PERIOD, periodicity);

        SessionManager sessionManager = new SessionManager(context);

        database.update(Contract.Habits.TABLE_NAME, values, Contract.Habits.COLUMN_HABIT_TITLE + " = " + "\'" + sessionManager.getHabitTitle() + "\'", null);
        //database.execSQL("UPDATE " + Contract.Habits.TABLE_NAME +
        //        " SET " + Contract.Habits.COLUMN_HABIT_DESCRIPTION + "=" + "\'" + description + "\'" + ", " + Contract.Habits.COLUMN_HABIT_FREQUENCY + "=" + "\'" + frequency + "\'" +
        //        " WHERE " + Contract.Habits.COLUMN_HABIT_TITLE + "=" + "\'" + sessionManager.getHabitTitle() + "\'");

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        /*
        The first argument for insert() is simply the table name. The second argument provides the name of a column in which
        the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then
        the framework will not insert a row when there are no values).
         */
        /*
        database.insertOrThrow(
                Contract.Habits.TABLE_NAME,
                null,
                values);
                */
    }

    public void updateDate(Calendar calendar, String habitTitle, int state){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.Calendar.COLUMN_STATE, state);

        //database.update(Contract.Calendar.TABLE_NAME, values, Contract.Calendar.COLUMN_HABIT_TITLE + " = " + "\'" + habitTitle + "\'" +  Contract.Calendar.COLUMN_DATE  + " = " + "\'" + convertTimeToUnixTimestamp(calendar) + "\'", null);

        //database.update(Contract.Calendar.TABLE_NAME, values, Contract.Calendar.COLUMN_HABIT_TITLE + " = " + "\'" + habitTitle + "\'" +  Contract.Calendar.COLUMN_DATE  + " = " +convertTimeToUnixTimestamp(calendar), null);


        //chyba dobre
         database.update(Contract.Calendar.TABLE_NAME, values, Contract.Calendar.COLUMN_HABIT_TITLE + " = " + "\'" + habitTitle + "\'" +  " AND " + Contract.Calendar.COLUMN_DATE  + " = " + "\'" + convertTimeToUnixTimestamp(calendar) + "\'", null);
    }





    public void deleteHabit(String habitTitle){
        // Gets the data repository in write mode
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(Contract.Habits.TABLE_NAME, Contract.Habits.COLUMN_HABIT_TITLE + " LIKE \'" + habitTitle + "\'", null);
    }

    public int getDateCountForHabit(String habitTitle){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Contract.Calendar.TABLE_NAME +
                " WHERE " + Contract.Calendar.COLUMN_HABIT_TITLE + " LIKE \'" + habitTitle + "\'";

        Cursor cursor = database.rawQuery(query, null);
        return cursor.getCount();
    }

    public int getSuccessDateCountForHabit(String habitTitle){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Contract.Calendar.TABLE_NAME +
                " WHERE " + Contract.Calendar.COLUMN_HABIT_TITLE + " LIKE \'" + habitTitle + "\'" +
                " AND " + Contract.Calendar.COLUMN_STATE + " = 1";
        Cursor cursor = database.rawQuery(query, null);
        return cursor.getCount();
    }

    private long convertTimeToUnixTimestamp(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis()/1000L;
    }

}
