package teamproject.rmm2.services;

import android.app.IntentService;
import android.content.Intent;

import teamproject.rmm2.Helpers.MakeNotification;
import teamproject.rmm2.MainActivity;
import teamproject.rmm2.R;
import teamproject.rmm2.database.DbHelper;

/**
 * Created by MSI on 2015-09-19.
 */
public class HabitService extends IntentService {

    public HabitService() {
        super("");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public HabitService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Build frequency for habit
        buildFrequency();

        // Notification
        notification();
    }

    private void buildFrequency() {
        DbHelper dbHelper = new DbHelper(getApplicationContext());

        // TODO Build frequency for habits in database

    }

    private void notification() {
        // Make notification
        MakeNotification makeNotification = new MakeNotification(getApplicationContext(), MainActivity.class, R.mipmap.ic_test,
                getApplicationContext().getResources().getString(R.string.notification_habit_frequency_title), getApplicationContext().getResources().getString(R.string.notification_habit_frequency_description), 1);
    }

}