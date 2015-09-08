package teamproject.rmm2.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import teamproject.rmm2.Helpers.MakeNotification;
import teamproject.rmm2.MainActivity;
import teamproject.rmm2.MyActivityTemplate;

/**
 * Created by MSI on 2015-09-08.
 */
public class SynchronizationService extends IntentService {

    public SynchronizationService() {
        super("");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SynchronizationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Synchronization
        synchronization();

        // Notification
        notification();

    }

    private void synchronization() {
        //TODO Make synchronization (statistics)
    }

    private void notification() {
        // Make notification
        MakeNotification makeNotification = new MakeNotification(getApplicationContext(), MainActivity.class);
    }

}
