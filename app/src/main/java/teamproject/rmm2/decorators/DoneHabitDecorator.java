package teamproject.rmm2.decorators;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.List;

import teamproject.rmm2.models.HabitDay;

/**
 *  Class used to mark on the calendar habits(green circle ).
 */
public class DoneHabitDecorator implements DayViewDecorator {
    List<HabitDay> definedHabits;

    public DoneHabitDecorator(List<HabitDay> habDefs) {
        definedHabits = habDefs;
    }

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        for (HabitDay habDef : definedHabits) {
            if (compare(habDef, calendarDay) == true) {
                if (habDef.isDone()) {
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

        dayViewFacade.addSpan(new DotSpan(5, Color.GREEN));
    }

    private boolean compare(HabitDay hd, CalendarDay cDay) {
        if (hd.getDay() == cDay.getDay()) {
            if ((hd.getMonth()-1) == cDay.getMonth()) {
                if (hd.getYear() == cDay.getYear()) {
                    return true;
                }
            }
        }

        //   Log.d("RMM", " " + hd.getDay() + " " + cDay.getDay() + " " + hd.getMonth() + " " + cDay.getMonth());
        return false;
    }
}

