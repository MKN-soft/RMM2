package teamproject.rmm2;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.CalendarView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import teamproject.rmm2.R;
import teamproject.rmm2.decorators.DoneHabitDecorator;
import teamproject.rmm2.decorators.HabitDecorator;
//import teamproject.rmm2.models.Habit;
//import teamproject.rmm2.models.HabitDay;
import teamproject.rmm2.models.CalendarRow;
import teamproject.rmm2.models.HabitRow;


import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

/**
 * Created by Natka on 2015-09-09.
 */
public class CalendarActivity extends MyActivityTemplate {
        CalendarView calendar;

        HabitRow currentHabit;


        private Context selfContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_calendar);
            MaterialCalendarView calendar = (MaterialCalendarView) findViewById(R.id.calendarView);

            selfContext = this;

            currentHabit = dbHelper.getHabit(sessionManager.getHabitTitle());

           // final List<HabitDay> definedHabits = currentHabit.getHabitDefinitions();
            //definedHabits.add(new HabitDefinition(2015, 4, 5)); // TO NIE

            final List<CalendarRow> calDay = dbHelper.getAllDates();
               int i = 0;
            final List<CalendarRow> currentDay = new ArrayList<CalendarRow>();
            for(CalendarRow c: calDay){
               // Log.i("RMM2", "tytul habitu tej daty:"+ c.getHabit().toString());
                //Log.i("RMM2", "Tytul habitu tego kalendarza:"+currentHabit.getTitle().toString());

                //if(c.getHabit().equals(sessionManager.getHabitTitle()))
                if(c.getHabit().toString().equals(currentHabit.getTitle().toString()))
                {
                    //Log.i("RMM2", "jestem!");
                    currentDay.add(c);
                    Log.i("RMM2", "To jest tytul nawyku "+ i + "daty: "+c.getHabit());
                    long timestamp = c.getTime();
                    //Log.i("RMM2", "Sprawdzam czy to tu2222");
                    //Date d = new Date((long)timestamp*1000);
                    Calendar ca =  Calendar.getInstance();
                    Date d = new Date((long)timestamp*1000);
                    ca.setTime(d);
                    int year1 = ca.get(Calendar.YEAR);
                    int month1 = ca.get(Calendar.MONTH); // Note: zero based!
                    int day1 = ca.get(Calendar.DAY_OF_MONTH);
                    Log.i("RMM2", "To jest data "+ i + "daty: "+day1 + " "+ month1 +" "+ year1);
                    Log.i("RMM2", "To jest state "+ i + "daty: "+c.getState());
                }
                i++;
            }
            Log.i("RMM2", "To jest jakis log:"+calDay.size());
            Log.i("RMM2", "To jest jakis log2:"+currentDay.size());
            //Log.i("RMM2", "To jest i: "+ i);

            /*HabitDecorator habDecor = new HabitDecorator(definedHabits);
            DoneHabitDecorator doneHabDecor = new DoneHabitDecorator(definedHabits);
            calendar.addDecorator(doneHabDecor);
            calendar.addDecorator(habDecor);
           */
            HabitDecorator habDecor = new HabitDecorator(currentDay);
            DoneHabitDecorator doneHabDecor = new DoneHabitDecorator(currentDay);
            calendar.addDecorator(doneHabDecor);
            calendar.addDecorator(habDecor);

            Date today = Calendar.getInstance().getTime();
            //int y = Integer.parseInt(str);
            //check future or past
            SimpleDateFormat todayDay = new SimpleDateFormat("dd");
            String tD = todayDay.format(today);
            final int tDay = Integer.parseInt(tD);

            SimpleDateFormat todayMonth = new SimpleDateFormat("MM");
            String tM = todayMonth.format(today);
            final int tMonth = Integer.parseInt(tM) - 1;

            SimpleDateFormat todayYear = new SimpleDateFormat("yyyy");
            String tY = todayYear.format(today);
            final int tYear = Integer.parseInt(tY);


            calendar.setOnDateChangedListener(new OnDateChangedListener() {
                @Override
                public void onDateChanged(MaterialCalendarView materialCalendarView, final CalendarDay calendarDay) {
                    //Intent intent = new Intent(CalendarActivity.this, DayActivity.class);
                    //startActivity(intent);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:/*
                                    boolean found = false;
                                    if(tYear > calendarDay.getYear()){
                                        for (HabitDay hab : currentHabit.getHabitDefinitions()) {
                                            if (hab.getYear() == calendarDay.getYear() && (hab.getMonth()-1) == calendarDay.getMonth() && hab.getDay() == calendarDay.getDay()) {
                                                hab.setDone(true);
                                                found = true;


                                            }
                                        }
                                    }else if(tYear == calendarDay.getYear()) {
                                        if (tMonth > calendarDay.getMonth()){
                                            for (HabitDay hab : currentHabit.getHabitDefinitions()) {
                                                if (hab.getYear() == calendarDay.getYear() && (hab.getMonth()-1) == calendarDay.getMonth() && hab.getDay() == calendarDay.getDay()) {
                                                    hab.setDone(true);
                                                    found = true;


                                                }
                                            }
                                        }else if( tMonth == calendarDay.getMonth()) {
                                            if (tDay > calendarDay.getDay() || tDay == calendarDay.getDay()) {
                                                for (HabitDay hab : currentHabit.getHabitDefinitions()) {
                                                    if (hab.getYear() == calendarDay.getYear() && (hab.getMonth()-1) == calendarDay.getMonth() && hab.getDay() == calendarDay.getDay()) {
                                                        hab.setDone(true);
                                                        found = true;


                                                    }
                                                }

                                            }
                                        }
                                    }


                                    if (found == false) {
                                        if(tYear > calendarDay.getYear()){
                                            HabitDay HabitDef = new HabitDay(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
                                            HabitDef.setDone(true);
                                            definedHabits.add(HabitDef);
                                        }else if(tYear == calendarDay.getYear()) {
                                            if (tMonth > calendarDay.getMonth()){
                                                HabitDay HabitDef = new HabitDay(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
                                                HabitDef.setDone(true);
                                                definedHabits.add(HabitDef);
                                            }else if( tMonth == calendarDay.getMonth()) {
                                                if (tDay > calendarDay.getDay() || tDay == calendarDay.getDay()) {
                                                    HabitDay HabitDef = new HabitDay(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
                                                    HabitDef.setDone(true);
                                                    definedHabits.add(HabitDef);

                                                    Log.v("CalendarActivity", " " + calendarDay.getMonth());
                                                }
                                            }
                                        }
                                    }

                                    onResume();
                                    break;*/

                                    boolean found = false;
                                    if(tYear > calendarDay.getYear()){
                                        for (CalendarRow hab : currentDay) {
                                            long timestamp = hab.getTime();
                                            Date d = new Date((long)timestamp*1000);
                                            Calendar myCal = new GregorianCalendar();
                                            myCal.setTime(d);
                                            int year = myCal.get(Calendar.YEAR);
                                            int month = myCal.get(Calendar.MONTH);
                                            int day = myCal.get(Calendar.DAY_OF_MONTH);
                                            if (year == calendarDay.getYear() && month == (calendarDay.getMonth() + 1) && day == calendarDay.getDay()) {
                                               // hab.setState(1);
                                               // Calendar calendar = Calendar.getInstance();
                                               // calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());
                                               // dbHelper.insertDate(calendar,sessionManager.getHabitTitle(), 1);
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());
                                                dbHelper.updateDate(calendar, currentHabit.getTitle().toString(), 1);

                                                Log.i("RMM2", "znaleziony do zrobienia ");
                                                found = true;


                                            }
                                        }
                                    }else if(tYear == calendarDay.getYear()) {
                                        if (tMonth > calendarDay.getMonth()){
                                            for (CalendarRow hab : currentDay) {
                                                long timestamp = hab.getTime();
                                                Date d = new Date((long)timestamp*1000);
                                                Calendar myCal = new GregorianCalendar();
                                                myCal.setTime(d);
                                                int year = myCal.get(Calendar.YEAR);
                                                int month = myCal.get(Calendar.MONTH);
                                                int day = myCal.get(Calendar.DAY_OF_MONTH);
                                                if (year == calendarDay.getYear() && month == (calendarDay.getMonth() + 1) && day == calendarDay.getDay()) {
                                                   // hab.setState(1);
                                                    //Calendar calendar = Calendar.getInstance();
                                                    //calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());
                                                    //dbHelper.insertDate(calendar,sessionManager.getHabitTitle(), 1);
                                                    Calendar calendar = Calendar.getInstance();
                                                    calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());
                                                    dbHelper.updateDate(calendar, currentHabit.getTitle().toString(), 1);

                                                    Log.i("RMM2", "znaleziony do zrobienia 2 ");
                                                    found = true;


                                                }
                                            }
                                        }else if( tMonth == calendarDay.getMonth()) {
                                            if (tDay > calendarDay.getDay() || tDay == calendarDay.getDay()) {
                                                for (CalendarRow hab : currentDay) {
                                                    long timestamp = hab.getTime();
                                                    Date d = new Date((long)timestamp*1000);
                                                    Calendar myCal = new GregorianCalendar();
                                                    myCal.setTime(d);
                                                    int year = myCal.get(Calendar.YEAR);
                                                    int month = myCal.get(Calendar.MONTH);
                                                    int day = myCal.get(Calendar.DAY_OF_MONTH);
                                                    if (year == calendarDay.getYear() && month == (calendarDay.getMonth() + 1) && day == calendarDay.getDay()) {
                                                       // hab.setState(1);
                                                        //Calendar calendar = Calendar.getInstance();
                                                        //calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());
                                                        //dbHelper.insertDate(calendar,sessionManager.getHabitTitle(), 1);
                                                        Calendar calendar = Calendar.getInstance();
                                                        calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());
                                                        dbHelper.updateDate(calendar, currentHabit.getTitle().toString(), 1);

                                                        Log.i("RMM2", "znaleziony do zrobienia 3 ");
                                                        found = true;


                                                    }
                                                }

                                            }
                                        }
                                    }


                                    if (found == false) {
                                        if(tYear > calendarDay.getYear()){
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());

                                            try {
                                                Log.i("RMM2", "Tu jak nie znaleziony do zrobienia 1");
                                                dbHelper.insertDate(calendar,sessionManager.getHabitTitle(), 1);
                                                }
                                            catch (SQLiteConstraintException e) {
                                                Toast.makeText(getContext(), "Duplicate record!", Toast.LENGTH_SHORT).show();
                                                Log.d("RMM2",e.getMessage());
                                                return;
                                            }
                                            catch(Exception e){
                                                Toast.makeText(getContext(), "Unexpected exception!", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }else if(tYear == calendarDay.getYear()) {
                                            if (tMonth > calendarDay.getMonth()){
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());

                                                try {
                                                    Log.i("RMM2", "Tu jak nie znaleziony do zrobienia 2");
                                                    dbHelper.insertDate(calendar,sessionManager.getHabitTitle(), 1);
                                                }
                                                catch (SQLiteConstraintException e) {
                                                    Toast.makeText(getContext(), "Duplicate record!", Toast.LENGTH_SHORT).show();
                                                    Log.d("RMM2",e.getMessage());
                                                    return;
                                                }
                                                catch(Exception e){
                                                    Toast.makeText(getContext(), "Unexpected exception!", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }else if( tMonth == calendarDay.getMonth()) {
                                                if (tDay > calendarDay.getDay() || tDay == calendarDay.getDay()) {
                                                    Calendar calendar = Calendar.getInstance();
                                                    calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());

                                                    try {
                                                        Log.i("RMM2", "Tu jak nie znaleziony do zrobienia3");
                                                        dbHelper.insertDate(calendar,sessionManager.getHabitTitle(), 1);
                                                    }
                                                    catch (SQLiteConstraintException e) {
                                                        Toast.makeText(getContext(), "Duplicate record!", Toast.LENGTH_SHORT).show();
                                                        Log.d("RMM2",e.getMessage());
                                                        return;
                                                    }
                                                    catch(Exception e){
                                                        Toast.makeText(getContext(), "Unexpected exception!", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }

                                                    Log.v("CalendarActivity", " " + calendarDay.getMonth());
                                                }
                                            }
                                        }
                                    }

                                    onResume();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                   /* HabitDay HabitDef = new HabitDay(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
                                    HabitDef.setDone(false);
                                    definedHabits.add(HabitDef);

                                    System.out.println(calendarDay.getYear() +" "+ calendarDay.getMonth() + " "+ calendarDay.getDay());

                                    onResume();
                                    break;*/

                                    //CalendarRow HabitDay = new CalendarRow();
                                    //HabitDay.setHabit(currentHabit.getTitle());
                                    //HabitDay.setState(0);
                                    //Date d = new Date(calendarDay.getYear(), (calendarDay.getMonth()+1),calendarDay.getDay()); // -1
                                    Calendar calendar = Calendar.getInstance();
                                    int day2 = calendar.get(Calendar.DAY_OF_MONTH);
                                    int month = calendar.get(Calendar.MONTH);
                                    //if(month == 12)
                                     //   calendar.set(calendarDay.getYear(), 11, calendarDay.getDay());

                                    calendar.set(calendarDay.getYear(), (calendarDay.getMonth() + 1), calendarDay.getDay());

                                   // d.getTime();
                                   // HabitDay.setTime(d.getTime());
                                    //calDay.add(HabitDay);

                                    //Log.i("RMM2", "To : "+calendarDay.getYear()+" "+ (calendarDay.getMonth()+1)+" "+calendarDay.getDay());

                                    try {
                                        Log.i("RMM2","dodanie do zrobienia");
                                        dbHelper.insertDate(calendar,sessionManager.getHabitTitle(), 0);
                                        int year = calendar.get(Calendar.YEAR);
                                        //int month = calendar.get(Calendar.MONTH); // Note: zero based!
                                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                                       // Log.i("RMM2", "To jest tytul nawyku " + "daty: "+sessionManager.getHabitTitle());
                                        //Log.i("RMM2", "To jest data " + "daty: "+calendar.getTime() + " "+ calendar.toString());

                                        //Log.i("RMM2", "/n To jest data " +year+" "+month+" " +day);
                                       // Log.i("RMM2", "To jest state " + "daty: "+c.getState());
                                        //dbHelper.close();
                                        //dbHelper.insertDate(d.getTime(),currentHabit.getTitle().toString(), 0);
                                        //dbHelper.insertDate(HabitDay.getTime(),HabitDay.getHabit().toString(), HabitDay.getState());
                                    }
                                    catch (SQLiteConstraintException e) {
                                        Toast.makeText(getContext(), "Duplicate record!", Toast.LENGTH_SHORT).show();
                                        Log.d("RMM2",e.getMessage());
                                        return;
                                    }
                                    catch(Exception e){
                                        Toast.makeText(getContext(), "Unexpected exception!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected Context getContext() {
        return this.getApplicationContext();
    }


    public void onResume() {
            super.onResume();
            this.onCreate(null);
        }



    }

