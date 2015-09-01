package teamproject.rmm2.models;

/**
 * Created by Marcin on 2015-08-20.
 */
public class HabitRow {
    private String title;
    private String description;
    private int frequency;
    private int imageId;
    private long creationDate;
    private int series;

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

    public void setImageId(int imageid) {
        this.imageId = imageid;
    }

    public void setSeries(int series) {
        this.series = series;
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
        return imageId;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public int getImageId() {
        return imageId;
    }

    public int getSeries() {
        return series;
    }
}
