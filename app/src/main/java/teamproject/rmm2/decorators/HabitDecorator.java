package teamproject.rmm2.decorators;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Date;
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
                if (habDef.getState() == 0) {
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
        Date d = new Date(timestamp);
        if (d.getDay() == cDay.getDay()) {
            if ((d.getMonth()-1) == cDay.getMonth()) {
                if (d.getYear() == cDay.getYear()) {
                    return true;
                }
            }
        }


        //   Log.d("RMM", " " + hd.getDay() + " " + cDay.getDay() + " " + hd.getMonth() + " " + cDay.getMonth());
        return false;
    }
}