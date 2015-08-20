package teamproject.rmm2.models;

/**
 * Created by Marcin on 2015-08-20.
 */
public class Date{
    private String habit;
    private String time;
    private String state;

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHabit() {
        return habit;
    }

    public String getTime() {
        return time;
    }

    public String getState() {
        return state;
    }
}
