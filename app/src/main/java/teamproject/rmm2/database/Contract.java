package teamproject.rmm2.database;

import android.provider.BaseColumns;


/**
 * Created by Marcin on 2015-07-21.
 * Contract class for SQLite. Contract class explicitly specifies the layout of schema in a systematic and self-documenting way.
 */
 final public class Contract {
    //TODO schema and stuff with databases, it MIGHT not yet be finished (MR)
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
    static abstract class Habits{
        public static final String TABLE_NAME = "habits";
        public static final String COLUMN_HABIT_TITLE = "title";
        public static final String COLUMN_HABIT_DESCRIPTION = "description";
        public static final String COLUMN_HABIT_FREQUENCY = "frequency";
        public static final String COLUMN_IMAGE_ID = "image_number";
        public static final String COLUMN_CREATION_DATE = "creation_date";
        public static final String COLUMN_SERIES = "series";
    }

    static abstract class Calendar{
        public static final String TABLE_NAME = "calendar";
        public static final String COLUMN_HABIT_TITLE = "habitid";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATE = "state";
    }

    static abstract class States{
        public static final String TABLE_NAME = "states";
        public static final String COLUMN_STATE = "habitstate";
    }

    //aux constants
    static final String COMMA_SEP = ",";
    static final String TEXT_TYPE = " TEXT";
    static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String FOREIGN_KEY = " FOREIGN KEY";
    private static final String REFERENCES = " REFERENCES ";

    /*
    Constants used in SQL for HABITS table
     */
    static final String SQL_CREATE_HABITS =
            "CREATE TABLE IF NOT EXISTS " + Habits.TABLE_NAME +
                    " (" +
                    Habits.COLUMN_HABIT_TITLE + TEXT_TYPE+ PRIMARY_KEY + COMMA_SEP +
                    Habits.COLUMN_HABIT_DESCRIPTION + TEXT_TYPE  + COMMA_SEP +
                    Habits.COLUMN_HABIT_FREQUENCY + INTEGER_TYPE +
                    Habits.COLUMN_IMAGE_ID + INTEGER_TYPE +
                    Habits.COLUMN_CREATION_DATE + INTEGER_TYPE +
                    Habits.COLUMN_SERIES + INTEGER_TYPE +
                    " )";

    static final String SQL_DELETE_HABITS =
            "DROP TABLE IF EXISTS " + Habits.TABLE_NAME;

    /*
   Constants used in SQL for STATE (of habit) table
    */
    static final String SQL_CREATE_STATES =
            "CREATE TABLE IF NOT EXISTS " + States.TABLE_NAME +
                    " (" +
                    States.COLUMN_STATE + TEXT_TYPE + PRIMARY_KEY +
                    " )";
    static final String SQL_DELETE_STATES =
            "DROP TABLE IF EXISTS " + States.TABLE_NAME;

    /*
    Constants used in SQL for CALENDAR table
     */
    static final String SQL_CREATE_CALENDAR =
            "CREATE TABLE IF NOT EXISTS " + Calendar.TABLE_NAME +
                    " (" +
                    Calendar.COLUMN_DATE + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    Calendar.COLUMN_HABIT_TITLE + TEXT_TYPE + COMMA_SEP +
                    Calendar.COLUMN_STATE + INTEGER_TYPE  +  COMMA_SEP +
                    FOREIGN_KEY + "(" + Calendar.COLUMN_STATE + ")" + REFERENCES +   States.TABLE_NAME + "(" + States.COLUMN_STATE + ")" + COMMA_SEP +
                    FOREIGN_KEY + "(" + Calendar.COLUMN_HABIT_TITLE + ")" + REFERENCES +  Habits.TABLE_NAME + "(" + Habits.COLUMN_HABIT_TITLE + ")" + COMMA_SEP +
                    "UNIQUE(" + Calendar.COLUMN_DATE + COMMA_SEP + Calendar.COLUMN_HABIT_TITLE + ")" +
            " )";

    static final String SQL_DELETE_CALENDAR =
            "DROP TABLE IF EXISTS " + Calendar.TABLE_NAME;

}
