package teamproject.rmm2.models;

/**
 * Created by Marcin on 2015-08-20.
 */
public class CalendarRow {
    private String habit;
    private long time;
    private String state;

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public void setState(String state) {
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

    public String getState() {
        return state;
    }
}
