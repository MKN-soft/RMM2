package teamproject.rmm2.decorators;

import android.graphics.Color;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import teamproject.rmm2.models.CalendarRow;
import teamproject.rmm2.models.CalendarRow;

/**
 * Class used to mark on the calendar habits(red circle ).
 */
public class HabitDecorator implements DayViewDecorator {
    List<CalendarRow> definedCalendarDay;

    public HabitDecorator(List<CalendarRow> habDefs) {
         definedCalendarDay = habDefs;
    }

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        for (CalendarRow habDef :  definedCalendarDay) {
            if (compare(habDef, calendarDay) == true) {
               // Log.i("RMM2", "jest ta data");
                if (habDef.getState() == 0) {
                   // Log.i("RMM2", "jest zrobiona!!!!!");
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        // dayViewFacade.addSpan(new StyleSpan(Typeface.BOLD));
        // dayViewFacade.addSpan(new RelativeSizeSpan(1.4f));

        dayViewFacade.addSpan(new DotSpan(5, Color.RED));
    }

    private boolean compare(CalendarRow hd, CalendarDay cDay) {
        long timestamp = hd.getTime();
        //Log.i("RMM2", "Sprawdzam czy to tu");
        Date d = new Date((long)timestamp*1000);
        Calendar myCal = new GregorianCalendar();
        myCal.setTime(d);
        int year = myCal.get(Calendar.YEAR);
        int day = myCal.get(Calendar.DAY_OF_MONTH);
        if (day == cDay.getDay()) {
           // Log.i("RMM2", "Po dniu!!! zrobiony");
           // Log.i("RMM2", "d.getDay(!!! : "+ d.getDay());
            //Log.i("RMM2", "cDay.getDay()!!! : "+ cDay.getDay());
            //Log.i("RMM2", "d.getMonth()!!! : "+ d.getMonth());
           // Log.i("RMM2", "cDay.getMonth()!!! : "+ (cDay.getMonth() - 1));
           // Log.i("RMM2", "year!!! : "+ year);
            //Log.i("RMM2", "cDay.getYear()!!! : "+ cDay.getYear());
           // Log.i("RMM2", "Po dniu!!!");
         //if ((d.getMonth() - 1) == cDay.getMonth()) {//month - 1
            if ((d.getMonth()) == cDay.getMonth()) {//month - 1
                //Log.i("RMM2", "Po miesiącu!!!");
                if (year == cDay.getYear()) {// if (d.getYear() == cDay.getYear()) {
                    //Log.i("RMM2", "Jest niezrobiony!!!");

                    return true;
                }
            }
        }


        //   Log.d("RMM", " " + hd.getDay() + " " + cDay.getDay() + " " + hd.getMonth() + " " + cDay.getMonth());
        return false;
    }
}