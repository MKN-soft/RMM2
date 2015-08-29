package teamproject.rmm2.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents habit. It's a habit model
 */
public class Habit implements Serializable {

    //TODO think about data needed in object.
    //TODO implement all setters
    //TODO date-done pairs

    private static final long serialVersionUID = 1L;

    String id;
    String title;
    String description;
    String date;
    String frequency;
    Drawable image; //image change to int?
    String notes;
    int series;

    List<HabitDay> definedHabits = new ArrayList<HabitDay>();

    public Habit() {
    }

    public Habit(String title, String description, String frequency, Drawable image, String notes, String date, int series) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.image = image;
        this.notes = notes;
        this.date = date;
        this.series = series;
    }

    public List<HabitDay> getHabitDefinitions() {
        return definedHabits;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Drawable getImage() {
        return this.image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

}
