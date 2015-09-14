package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import teamproject.rmm2.models.HabitRow;

public class HabitDetailActivity extends MyActivityTemplate {

    private HabitRow item;
    private TextView title, description, frequency, date, periodicity;
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
        periodicity = (TextView) findViewById(R.id.habitPeriodicity);

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        frequency.setText(Integer.toString(item.getFrequency()));
        periodicity.setText(periodicityToString(item.getPeriod()));

        deleteHabit = (Button) findViewById(R.id.deleteHabit);
        editHabit = (Button) findViewById(R.id.editHabit);

        deleteHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete habit from database
                dbHelper.deleteHabit(sessionManager.getHabitTitle());

                // Back to Main Activity
                Intent intent = new Intent(HabitDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to edit habit activity
                Intent intent = new Intent(HabitDetailActivity.this, EditHabitActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public String periodicityToString(int periodicity) {
        String temp;

        if (periodicity == 1)
            return "Day";
        else if (periodicity == 7)
            return "Week";
        else if (periodicity == 30)
            return "Month";
        else
            return "Error";
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_habit_detail;
    }

    @Override
    protected Context getContext() { return this.getApplicationContext(); }

}
