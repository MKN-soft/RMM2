package teamproject.rmm2.models;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Calendar;

/**
 * Created by Marcin on 2015-08-20.
 */
public class HabitRow {
    private Drawable icon;
    private int id;
    private String title;
    private String description;
    private int frequency;
    private int period;
    private Calendar creationDate;
    private Calendar lastUpdateDate;

    private String notes;

    public HabitRow() {

    }

    public HabitRow(Drawable icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }
    public void setCreationDate(long creationDateUnixTimestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(creationDateUnixTimestamp*1000);
        this.creationDate = calendar;
    }

    public void setLastUpdateDate(Calendar lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDateUnixTimestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastUpdateDateUnixTimestamp*1000);
        this.lastUpdateDate = calendar;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getId() {
        return id;
    }

    public int getPeriod() {
        return period;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Calendar getLastUpdateDate() {
        return lastUpdateDate;
    }
}
