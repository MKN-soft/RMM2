package teamproject.rmm2.models;

/**
 * Class define day of habit, have data and variable, which points on habit done or to do.
 *
 */
public class HabitDay {
    private int year;
    private int monthOfYear; // 0 - 11
    private int dayOfMonth; // 1 - 31
    private boolean done;

    public HabitDay(int y, int m, int d) {
        year = y;
        monthOfYear = m;
        dayOfMonth = d;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return monthOfYear;
    }

    public int getDay() {
        return dayOfMonth;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean val) {
        done = val;
    }
}
