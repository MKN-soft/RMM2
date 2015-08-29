package teamproject.rmm2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import teamproject.rmm2.LogicBase;
import teamproject.rmm2.R;
import teamproject.rmm2.models.Habit;


/**
 * Class edit data habit.
 */

public class EditHabitActivity extends ActionBarActivity {
    public ImageView habitImage;
    public int additional;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        Button editHabitButton = (Button) findViewById(R.id.editHabitButton);
        Button editImageButton = (Button) findViewById(R.id.editImageButton);

        int position = LogicBase.getPosition();
        Habit habit = LogicBase.getHabitAt(position);

        final EditText habitName = (EditText) findViewById(R.id.editHabitName);
        final EditText habitDescription = (EditText) findViewById(R.id.editHabitDescription);
        final EditText habitFrequency = (EditText) findViewById(R.id.editHabitFrequency);
        final EditText habitNotes = (EditText) findViewById(R.id.editHabitNotes);
        habitImage = (ImageView) findViewById(R.id.editHabitImage);

        additional = habit.getSeries();

        habitName.setText(habit.getTitle());
        habitDescription.setText(habit.getDescription());
        habitFrequency.setText(habit.getFrequency());
        habitNotes.setText(habit.getNotes());
        habitImage.setImageDrawable(habit.getImage());

        String[] elements = {"No change", "Day", "Week", "Month"};
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> series = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);

        spinner2.setAdapter(series);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                //Toast.makeText(EditHabitActivity.this, "Wybrano opcję " + (id + 1), Toast.LENGTH_SHORT).show();


                switch ((int) position) {
                    case 0:
                        Toast.makeText(EditHabitActivity.this, "No change ", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        additional = 1; //Day
                        break;
                    case 2:
                        additional = 7;//Week
                        break;
                    case 3:
                        additional = 30;//Month
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        editImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditHabitActivity.this, EditImageActivity.class);
                startActivity(intent);

                onResume();


            }
        });

        editHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //give position (from file HomeFragment)
                int position = LogicBase.getPosition();
                //give habit from position
                Habit habit = LogicBase.getHabitAt(position);
                //rewrite habitName on new
                habit.setTitle(habitName.getText().toString());
                //description
                habit.setDescription(habitDescription.getText().toString());
                //frequency
                habit.setFrequency(habitFrequency.getText().toString());
                //notes
                habit.setNotes(habitNotes.getText().toString());
                //exchange old version habit on edit version
                habit.setSeries(additional);

                LogicBase.setHabitAt(position, habit);
                // LogicBase.refreshList();

                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        int position2 = LogicBase.getPosition();
        Habit habit2 = LogicBase.getHabitAt(position2);
        habitImage.setImageDrawable(habit2.getImage());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
