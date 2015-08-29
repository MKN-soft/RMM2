package teamproject.rmm2.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CalendarView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.util.List;

import teamproject.rmm2.LogicBase;
import teamproject.rmm2.R;
import teamproject.rmm2.decorators.DoneHabitDecorator;
import teamproject.rmm2.decorators.HabitDecorator;
import teamproject.rmm2.models.Habit;
import teamproject.rmm2.models.HabitDay;


/**
 *  Class calendar, which takes a list of day from habit and means them on calendar.
 *  It used also to add habits done or to do.
 */
public class CalendarActivity extends Activity {
    CalendarView calendar;

    Habit currentHabit;

    private Context selfContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        MaterialCalendarView calendar = (MaterialCalendarView) findViewById(R.id.calendarView);

        selfContext = this;


        int position = LogicBase.getPosition();
        currentHabit = LogicBase.getHabitAt(position);

        final List<HabitDay> definedHabits = currentHabit.getHabitDefinitions();
        //definedHabits.add(new HabitDefinition(2015, 4, 5)); // 4 - may

        HabitDecorator habDecor = new HabitDecorator(definedHabits);
        DoneHabitDecorator doneHabDecor = new DoneHabitDecorator(definedHabits);
        calendar.addDecorator(doneHabDecor);
        calendar.addDecorator(habDecor);

        calendar.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(MaterialCalendarView materialCalendarView, final CalendarDay calendarDay) {
                //Intent intent = new Intent(CalendarActivity.this, DayActivity.class);
                //startActivity(intent);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                boolean found = false;
                                for (HabitDay hab : currentHabit.getHabitDefinitions()) {
                                    if (hab.getYear() == calendarDay.getYear() && hab.getMonth() == calendarDay.getMonth() && hab.getDay() == calendarDay.getDay()) {
                                        hab.setDone(true);
                                        found = true;
                                    }
                                }


                                if (found == false) {
                                    HabitDay HabitDef = new HabitDay(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
                                    HabitDef.setDone(true);
                                    definedHabits.add(HabitDef);
                                }

                                onResume();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                HabitDay HabitDef = new HabitDay(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
                                HabitDef.setDone(false);
                                definedHabits.add(HabitDef);

                                onResume();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(selfContext);
                builder.setMessage("Set Habit:").setPositiveButton("Done", dialogClickListener)
                        .setNegativeButton("To do", dialogClickListener).show();


            }
        });
    }

    public void onResume() {
        super.onResume();
        this.onCreate(null);
    }

}


/*
*   xmlns:app="http://schemas.android.com/apk/res-auto"
        app:mcv_showOtherDates="boolean"
        app:mcv_arrowColor="color"
        app:mcv_selectionColor="color"
        app:mcv_headerTextAppearance="style"
        app:mcv_dateTextAppearance="style"
        app:mcv_weekDayTextAppearance="style"
        app:mcv_weekDayLabels="array"
        app:mcv_monthLabels="array"
        app:mcv_tileSize="dimension"
        app:mcv_firstDayOfWeek="enum"
* */