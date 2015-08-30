package teamproject.rmm2.models;

/**
 * Created by Marcin on 2015-08-20.
 */
public class CalendarRow {
    private String habit;
    private long time;
    private int state;  // 1 == success, 0 == neutral, -1 == failure

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

    public int getState() {
        return state;
    }
}
