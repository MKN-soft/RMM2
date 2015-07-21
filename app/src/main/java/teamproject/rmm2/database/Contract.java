package teamproject.rmm2.database;

import android.provider.BaseColumns;


/**
 * Created by Marcin on 2015-07-21.
 * Contract class for SQLite. Contract class explicitly specifies the layout of schema in a systematic and self-documenting way.
 */
public final class Contract {
    //TODO schema and stuff with databases, these are just foundatiosn and example (MR)
    /*
     an empty constructor to prevent someone from accidentally instantiating the contract class
     */
    public Contract() {}

    /*By implementing the BaseColumns interface, your inner class can inherit a primary key field called _ID
that some Android classes such as cursor adaptors will expect it to have. It's not required,
but this can help your database work harmoniously with the Android framework.*/

    /**
     * Inner class that defines the table contents
     */
    public static abstract class Habits implements BaseColumns{
        public static final String TABLE_NAME = "habits";
        public static final String COLUMN_HABIT_ID = "habitid";
        public static final String COLUMN_HABIT_TITLE = "title";
        public static final String COLUMN_HABIT_DESCRIPTION = "description";
        public static final String COLUMN_HABIT_FREQUENCY = "frequency";
    }

    public static abstract class Calendar implements BaseColumns{
        public static final String TABLE_NAME = "dates";
        public static final String COLUMN_HABIT_ID = "habitid";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATE = "state";
    }

    //aux constant
    private static final String COMMA_SEP = ",";

    /*
    Constants used in SQL for HABITS table
     */
    //private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_CREATE_HABITS =
            "CREATE TABLE " + Habits.TABLE_NAME +
                    " (" +
                    Habits._ID + " INTEGER PRIMARY KEY," +
                    Habits.COLUMN_HABIT_ID + COMMA_SEP +
                    Habits.COLUMN_HABIT_TITLE + COMMA_SEP +
                    Habits.COLUMN_HABIT_DESCRIPTION + COMMA_SEP +
                    Habits.COLUMN_HABIT_FREQUENCY +COMMA_SEP+
                    " )";
    private static final String SQL_DELETE_HABITS =
            "DROP TABLE IF EXISTS " + Habits.TABLE_NAME;


    /*
    Constants used in SQL for CALENDAR table
     */
    private static final String SQL_CREATE_CALENDAR =
            "CREATE TABLE " + Habits.TABLE_NAME +
                    " (" +
                    Calendar._ID + " INTEGER PRIMARY KEY," +
                    Calendar.COLUMN_HABIT_ID + COMMA_SEP +
                    Calendar.COLUMN_DATE + COMMA_SEP +
                    Calendar.COLUMN_STATE + COMMA_SEP +
                    " )";

    private static final String SQL_DELETE_CALENDAR =
            "DROP TABLE IF EXISTS " + Calendar.TABLE_NAME;



}
