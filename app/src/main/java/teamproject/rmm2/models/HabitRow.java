package teamproject.rmm2.models;

/**
 * Created by Marcin on 2015-08-20.
 */
public class HabitRow {
    private String title;
    private String description;
    private int frequency;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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
}
