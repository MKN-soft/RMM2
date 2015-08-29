package teamproject.rmm2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import teamproject.rmm2.R;


/**
 * Class schedule having menu to calendar and statistic.
 */
public class ScheduleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        Button calendar = (Button) findViewById(R.id.Calendar);


        calendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ScheduleActivity.this, CalendarActivity.class);
                startActivity(intent);

                finish();

            }
        });


    }

}
