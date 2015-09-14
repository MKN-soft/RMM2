package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AddHabitActivity extends MyActivityTemplate {

    private EditText habitName, habitDescription, habitFrequency;
    private int additional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button addHabitButton = (Button) findViewById(R.id.addHabitButton);
        Button chooseImage = (Button) findViewById(R.id.chooseImage);

        habitName = (EditText) findViewById(R.id.addHabitName);
        habitDescription = (EditText) findViewById(R.id.addHabitDescription);
        habitFrequency = (EditText) findViewById(R.id.addHabitFrequency);

        final Drawable image = getResources().getDrawable(R.mipmap.ic_question_mark);
        ImageView habitImage = (ImageView) findViewById(R.id.addHabitImage);

        habitImage.setImageDrawable(image);

        String[] elements = {"Day", "Week", "Month"};
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> series = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);

        spinner.setAdapter(series);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int id, long position) {
                switch ((int) position) {
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
                //TODO Make Choose Image Activity
            }
        });

        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
//                    TODO uncomment when setting periodicity is fixed and add as an argument to the method call below
//                    dbHelper.insertHabit(habitName.getText().toString(), habitDescription.getText().toString(), Integer.parseInt(habitFrequency.getText().toString()), );
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
    protected int getLayoutResourceId() {
        return R.layout.activity_add_habit;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }

}
