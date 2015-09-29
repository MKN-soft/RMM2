package teamproject.rmm2.models;

/**
 * Created by Marcin on 2015-08-20.
 * Represents row in CALENDAR table in sqLite database RMM2.db
 */
public class CalendarRow {
    private int id;
    private String habit;
    private long time;
    private int state;

    public void setId(int id) {
        this.id = id;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getHabit() {
        return habit;
    }

    public long getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public int getState() {
        return state;
    }
}
