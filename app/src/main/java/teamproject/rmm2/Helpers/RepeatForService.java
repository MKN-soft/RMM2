package teamproject.rmm2.Helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.GregorianCalendar;

import teamproject.rmm2.MainActivity;
import teamproject.rmm2.services.SynchronizationService;

/**
 * Created by MSI on 2015-09-08.
 *
 * Setting repeat in time for specific service.
 */
public class RepeatForService {

    private Context context;

    public RepeatForService(Context context) {
        this.context = context;
    }

    /**
     * Getting specific notification and set running time.
     *
     * @param service
     */
    public void NotificationsHandler(Class<?> service) {
//        // Get time NOW
//        Calendar currentCalendar = new GregorianCalendar();
//        currentCalendar.setTimeInMillis(System.currentTimeMillis());
//
//        // Set run BackgroundService on 23:59
//        // 86400000 ms - ONE DAY
//
//        // Set schedule for Service
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.DAY_OF_YEAR, currentCalendar.get(Calendar.DAY_OF_YEAR));
//        calendar.set(Calendar.HOUR_OF_DAY, AppConfig.NOTIFICATION_HOUR);
//        calendar.set(Calendar.MINUTE, AppConfig.NOTIFICATION_MINUTE);
//        calendar.set(Calendar.SECOND, currentCalendar.get(Calendar.SECOND));
//        calendar.set(Calendar.MILLISECOND, currentCalendar.get(Calendar.MILLISECOND));
//        calendar.set(Calendar.DATE, currentCalendar.get(Calendar.DATE));
//        calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
//
//        // Set intent for Service
//        Intent intent = new Intent(context.getApplicationContext(), service);
//
//        // When we click on notification, we move to MainActivity
//        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, intent, 0);
//
//        // Alarm Manager
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Set repeat - per day
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AppConfig.NOTIFICATION_REPEAT, pendingIntent);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), SynchronizationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, AppConfig.NOTIFICATION_HOUR);
        calendar.set(Calendar.MINUTE, AppConfig.NOTIFICATION_MINUTE);


        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AppConfig.NOTIFICATION_REPEAT, pendingIntent);



//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 3);
//        Intent intent = new Intent(context.getApplicationContext(), service);
//        PendingIntent pintent = PendingIntent.getService(context.getApplicationContext(), 0, intent, 0);
//        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 3600, pintent);
    }

}
