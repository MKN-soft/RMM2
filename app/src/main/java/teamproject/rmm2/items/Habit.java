package teamproject.rmm2.items;

import android.graphics.drawable.Drawable;

/**
 * Created by MSI on 2015-09-03.
 */
public class Habit {

    public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description

    public Habit(Drawable icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

}
