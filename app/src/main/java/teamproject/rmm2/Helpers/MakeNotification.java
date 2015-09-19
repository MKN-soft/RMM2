package teamproject.rmm2.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import teamproject.rmm2.R;

/**
 * Created by MSI on 2015-09-08.
 */
public class MakeNotification {

    public MakeNotification(Context context, Class<?> activity, int icon, String title, String text, int notification_id) {
        // Make notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(text);
        int NOTIFICATION_ID = notification_id;

        Intent targetIntent = new Intent(context, activity);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }

}
