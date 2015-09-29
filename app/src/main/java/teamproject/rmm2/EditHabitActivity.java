package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import teamproject.rmm2.models.HabitRow;

public class EditHabitActivity extends MyActivityTemplate {

    private int habitPeriodicity;
    private HabitRow item;
    private Button editHabit;
    private TextView habitName;
    private EditText habitDescription, habitFrequency;
    private ImageView habitImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get habit from database
        item = dbHelper.getHabit(sessionManager.getHabitTitle());

        habitName = (TextView) findViewById(R.id.habitName);
        habitDescription = (EditText) findViewById(R.id.habitDescription);
        habitFrequency = (EditText) findViewById(R.id.habitFrequency);
        habitImage = (ImageView) findViewById(R.id.habitImage);

        habitName.setText(item.getTitle());
        habitDescription.setText(item.getDescription());
        habitFrequency.setText(Integer.toString(item.getFrequency()));
        habitPeriodicity = item.getPeriod();
        //TODO Set image??

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.edit_periodicity_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        break;
                    case 1:
                        habitPeriodicity = 1;
                        break;
                    case 2:
                        habitPeriodicity = 7;
                        break;
                    case 3:
                        habitPeriodicity = 30;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editHabit = (Button) findViewById(R.id.editHabitButton);
        editHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.editHabit(getContext(), habitDescription.getText().toString(), Integer.parseInt(habitFrequency.getText().toString()), habitPeriodicity);

                Intent intent = new Intent(EditHabitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_edit_habit;
    }

    @Override
    protected Context getContext() { return this.getApplicationContext(); }
}
