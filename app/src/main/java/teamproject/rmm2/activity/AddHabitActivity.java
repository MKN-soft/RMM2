package teamproject.rmm2.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import teamproject.rmm2.LogicBase;
import teamproject.rmm2.R;
import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.models.Habit;


/**
 *Class add new habit to list of habits
 */

public class AddHabitActivity extends ActionBarActivity {

    //TODO: adding habit to list

    protected DbHelper dbHelper;
    public ImageView habitImage;
    public int position = LogicBase.getLastPosition();
    public int position2 = LogicBase.getLastPosition();
    public int additional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        //database access
        this.dbHelper = new DbHelper(this);

        Button addHabitButton = (Button) findViewById(R.id.addHabitButton);
        Button chooseImage = (Button) findViewById(R.id.chooseImage);

        final EditText habitName = (EditText) findViewById(R.id.addHabitName);
        final EditText habitDescription = (EditText) findViewById(R.id.addHabitDescription);
        final EditText habitFrequency = (EditText) findViewById(R.id.addHabitFrequency);
        final EditText habitNotes = (EditText) findViewById(R.id.addHabitNotes);

        //TODO delete following 4 lines - testing purposes
        habitName.setText("a");
        habitDescription.setText("a");
        habitFrequency.setText("1");

        final Drawable image = getResources().getDrawable(R.mipmap.ic_question_mark);
        habitImage = (ImageView) findViewById(R.id.addHabitImage);

        //final int position = LogicBase.getLastPosition();
//        position = LogicBase.getLastPosition();

//        habitImage.setImageDrawable(image);

        String[] elements = {"Day", "Week", "Month"};

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> series = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);

        spinner.setAdapter(series);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {


                switch ((int) position)
                {
                    case 0:
                        additional = 1; //Day
                        break;
                    case 1:
                        additional = 7;//week
                        break;
                    case 2:
                        additional = 30;//month
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddHabitActivity.this, AddImageActivity.class);
                startActivity(intent);

                onResume();

            }
        });
        //after click on button create new habit(1) and add to list(2)
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dbHelper.getHabit(habitName.getText().toString()) == null){
                    dbHelper.insertHabit(habitName.getText().toString(), habitDescription.getText().toString(),
                            Integer.parseInt(habitFrequency.getText().toString()), 0, System.currentTimeMillis());
                }
                else{
                    //TODO what happens if there is such habit already?
                }


                /*//int position2 = LogicBase.getLastPosition();
                position2 = LogicBase.getLastPosition();
                // check that habit exist  by image add
                if (position == position2) {// habit does not exist
                    Date date = new Date();
                    String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                    Habit newHabit = new Habit(habitName.getText().toString(), habitDescription.getText().toString(), habitFrequency.getText().toString(), image, habitNotes.getText().toString(), date1, additional);
                    //2
                    LogicBase.addHabit(newHabit);
                    finish();

                } else { // habit exist, change data therein
                    Habit habit = LogicBase.getHabitAt(position2);

                    habit.setTitle(habitName.getText().toString());
                    //description
                    habit.setDescription(habitDescription.getText().toString());
                    //frequency
                    habit.setFrequency(habitFrequency.getText().toString());
                    //notes
                    habit.setNotes(habitNotes.getText().toString());
                    //exchange old version habit on edit version

                    habit.setSeries(additional);

                    LogicBase.setHabitAt(position2, habit);


                    int position3 = position + 1;
                    int position4 = LogicBase.getLastPosition();
                    while (position3 != position4) {
                        LogicBase.removeHabitItemAt(position + 1);
                        position3++;
                    }

                    finish();

                }

*/
            }
        });

    }

    /*protected void onResume() {
        super.onResume();
        int position4 = LogicBase.getLastPosition();
        if (position != position4) {
            int posPlus = LogicBase.getLastPosition();
            Habit habit = LogicBase.getHabitAt(posPlus);
            habitImage.setImageDrawable(habit.getImage());

        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        int position3 = position + 1;
        int position4 = LogicBase.getLastPosition();
        while (position3 <= position4) {
            LogicBase.removeHabitItemAt(position + 1);
            position3++;
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addHabit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
