package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddHabitActivity extends MyActivityTemplate {

    private EditText habitName, habitDescription, habitFrequency;
    private int habitPeriodicity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button addHabitButton = (Button) findViewById(R.id.addHabitButton);
        //Button chooseImage = (Button) findViewById(R.id.chooseImage);

        habitName = (EditText) findViewById(R.id.addHabitName);
        habitDescription = (EditText) findViewById(R.id.addHabitDescription);
        habitFrequency = (EditText) findViewById(R.id.addHabitFrequency);

        final Drawable image = getResources().getDrawable(R.mipmap.ic_question_mark);
        //ImageView habitImage = (ImageView) findViewById(R.id.addHabitImage);

        //habitImage.setImageDrawable(image);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.periodicity_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        habitPeriodicity = 1;
                        break;
                    case 1:
                        habitPeriodicity = 7;
                        break;
                    case 2:
                        habitPeriodicity = 30;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        chooseImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO Make Choose Image Activity
//            }
//        });

        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                      dbHelper.insertHabit(habitName.getText().toString(), habitDescription.getText().toString(), Integer.parseInt(habitFrequency.getText().toString()), habitPeriodicity, Calendar.getInstance());
                }
                catch (SQLiteConstraintException e) {
                    Toast.makeText(getContext(), "Duplicate record!", Toast.LENGTH_SHORT).show();
                    return;
                }
                catch(Exception e){
                    Toast.makeText(getContext(), "Unexpected exception!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(AddHabitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddHabitActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_habit;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }

}
