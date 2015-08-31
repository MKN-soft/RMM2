package teamproject.rmm2.models;

/**
 * Created by Marcin on 2015-08-20.
 */
public class HabitRow {
    private String title;
    private String description;
    private int frequency;
    private int imageid;
    private long creationDate;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
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

    public int getImageid() {
        return imageid;
    }

    public long getCreationDate() {
        return creationDate;
    }
}
