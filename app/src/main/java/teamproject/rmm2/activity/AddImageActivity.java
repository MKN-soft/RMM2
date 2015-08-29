package teamproject.rmm2.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import teamproject.rmm2.LogicBase;
import teamproject.rmm2.R;
import teamproject.rmm2.models.Habit;


/**
 * Class add image to new habit.
 */
public class AddImageActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_image);

        ImageButton Image1 = (ImageButton) findViewById(R.id.image1);
        ImageButton Image2 = (ImageButton) findViewById(R.id.image2);
        ImageButton Image3 = (ImageButton) findViewById(R.id.image3);
        ImageButton Image4 = (ImageButton) findViewById(R.id.image4);
        ImageButton Image5 = (ImageButton) findViewById(R.id.image5);
        ImageButton Image6 = (ImageButton) findViewById(R.id.image6);
        ImageButton Image7 = (ImageButton) findViewById(R.id.image7);
        ImageButton Image8 = (ImageButton) findViewById(R.id.image8);
        ImageButton Image9 = (ImageButton) findViewById(R.id.image9);

        Image1.setImageResource(R.mipmap.ic_communities);
        Image2.setImageResource(R.mipmap.ic_home);
        Image3.setImageResource(R.mipmap.ic_launcher);
        Image4.setImageResource(R.mipmap.ic_pages);
        Image5.setImageResource(R.mipmap.ic_people);
        Image6.setImageResource(R.mipmap.ic_photos);
        Image7.setImageResource(R.mipmap.ic_running);
        Image8.setImageResource(R.mipmap.ic_whats_hot);
        Image9.setImageResource(R.mipmap.ic_question_mark);

        Image1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_communities);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_home);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_launcher);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_pages);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_people);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_photos);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_running);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_whats_hot);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });

        Image9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date date = new Date();
                String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

                Drawable image = getResources().getDrawable(R.mipmap.ic_question_mark);
                Habit habit = new Habit("title", "description", "frequency", image, "notes", date1, 0);
                LogicBase.addHabit(habit);

                //onResume();
                finish();
            }
        });
    }
}
