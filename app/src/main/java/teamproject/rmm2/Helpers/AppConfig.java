package teamproject.rmm2.Helpers;

import android.app.AlarmManager;

/**
Class for Application settings.
 */
public class AppConfig {
    // Server user API URL
    //public static final String URL_API = "http://lubiekokosy.pl/php/android.php";
    public static final String URL_API = "https://serwer1538500.home.pl/lubiekokosy/php/android.php";

    public static final String TAG_LOGIN = "login";
    public static final String TAG_REGISTER = "register";
    public static final String TAG_SYNCHRO = "synchro";

    // Notification settings
    public static int NOTIFICATION_HOUR = 23;
    public static int NOTIFICATION_MINUTE = 45;
    public static long NOTIFICATION_REPEAT = AlarmManager.INTERVAL_DAY;
}
