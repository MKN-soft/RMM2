package teamproject.rmm2.models;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

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
}
