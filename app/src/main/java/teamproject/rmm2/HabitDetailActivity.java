package teamproject.rmm2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import teamproject.rmm2.models.HabitRow;

public class HabitDetailActivity extends MyActivityTemplate {

    private HabitRow item;
    private TextView title, description, frequency, date;
    private ImageView image;
    private Button editHabit, deleteHabit, schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Find habit in database
        item = dbHelper.getHabit(sessionManager.getHabitTitle());

        // View on data Habit
        title = (TextView) findViewById(R.id.habitTitle);
        description = (TextView) findViewById(R.id.habitDescription);
        frequency = (TextView) findViewById(R.id.habitFrequency);
        date = (TextView) findViewById(R.id.habitDate);
        image = (ImageView) findViewById(R.id.habitImage);

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        frequency.setText(Integer.toString(item.getFrequency()));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_habit_detail;
    }

    @Override
    protected Context getContext() { return this.getApplicationContext(); }

}
