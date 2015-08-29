package teamproject.rmm2.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import teamproject.rmm2.LogicBase;
import teamproject.rmm2.R;
import teamproject.rmm2.models.Habit;


/**
 * Displays habit details
 */
public class HabitDetailActivity extends Activity {

    public TextView title, description, notes, frequency;
    public ImageView habitImage;
    String additional = "Nothing";
    private Button _editHabit, _deleteHabit, schedule;
    private Context selfContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        selfContext = this;

        //View on data Habit
        title = (TextView) findViewById(R.id.habitTitle);
        description = (TextView) findViewById(R.id.habitDescription);
        notes = (TextView) findViewById(R.id.habitNotes);
        frequency = (TextView) findViewById(R.id.habitFrequency);
        TextView date = (TextView) findViewById(R.id.habitDate);
        habitImage = (ImageView) findViewById(R.id.habitImage);

        int position = LogicBase.getPosition();
        Habit habit = LogicBase.getHabitAt(position);
        title.setText(habit.getTitle());
        description.setText(habit.getDescription());
        notes.setText(habit.getNotes());

        int series = habit.getSeries();
        if (series == 1) {
            additional = "Days";
            if (habit.getFrequency().equals("1"))
                additional = "Day";
        } else if (series == 7) {
            additional = "Weeks";
            if (habit.getFrequency().equals("1"))
                additional = "Week";
        } else if (series == 30) {
            additional = "Months";
            if (habit.getFrequency().equals("1"))
                additional = "Month";
        }


        frequency.setText("Your frequency: " + habit.getFrequency() + " " + additional);
        date.setText("Created: " + habit.getDate());

        habitImage.setImageDrawable(habit.getImage());

        //LogicBase.setHabitAt(position, habit);

        //

        _editHabit = (Button) findViewById(R.id.editHabit);
        _deleteHabit = (Button) findViewById(R.id.deleteHabit);
        schedule = (Button) findViewById(R.id.Schedule);

        schedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HabitDetailActivity.this, ScheduleActivity.class);
                startActivity(intent);

                //finish();


            }
        });

        _editHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Hooking Activity
                Intent intent = new Intent(HabitDetailActivity.this, EditHabitActivity.class);
                startActivity(intent);

                //LogicBase.refreshList();
                //finish();
                onResume();


            }
        });

        // after click remove code create dialogMessage with two button- positive and negative
        _deleteHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Hooking Activity
                //Intent intent = new Intent(HabitDetailActivity.this, DeleteHabitActivity.class);
                //startActivity(intent);
                // remove this item from list

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                int position = LogicBase.getPosition();
                                LogicBase.removeHabitItemAt(position);
                                finish(); // ends current activity and go to previous
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(selfContext);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
    }

    public void onResume() {
        super.onResume();
        int position2 = LogicBase.getPosition();
        Habit habit2 = LogicBase.getHabitAt(position2);
        habitImage.setImageDrawable(habit2.getImage());
        title.setText(habit2.getTitle());
        description.setText(habit2.getDescription());
        notes.setText(habit2.getNotes());

        int series = habit2.getSeries();
        if (series == 1) {
            additional = "Days";
            if (habit2.getFrequency().equals("1"))
                additional = "Day";
        } else if (series == 7) {
            additional = "Weeks";
            if (habit2.getFrequency().equals("1"))
                additional = "Week";
        } else if (series == 30) {
            additional = "Months";
            if (habit2.getFrequency().equals("1"))
                additional = "Month";
        }
        frequency.setText("Your frequency: " + habit2.getFrequency() + " " + additional);
    }


}
