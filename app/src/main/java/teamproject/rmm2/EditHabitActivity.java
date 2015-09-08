package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import teamproject.rmm2.models.HabitRow;

public class EditHabitActivity extends MyActivityTemplate {

    private int additional;
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
        //TODO Set image??

        String[] elements = {"No change", "Day", "Week", "Month"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> series = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);
        spinner.setAdapter(series);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int id, long position) {
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

        editHabit = (Button) findViewById(R.id.editHabitButton);
        editHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO make functionality
                dbHelper.editHabit(getContext(), habitDescription.getText().toString(), Integer.parseInt(habitFrequency.getText().toString()));

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
