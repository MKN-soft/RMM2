package teamproject.rmm2.services;

import android.app.IntentService;
import android.content.Intent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import teamproject.rmm2.Helpers.AppConfig;
import teamproject.rmm2.Helpers.ConnectionTask;
import teamproject.rmm2.Helpers.MakeNotification;
import teamproject.rmm2.Helpers.SessionManager;
import teamproject.rmm2.Helpers.Statistics;
import teamproject.rmm2.Helpers.SynchronizationTask;
import teamproject.rmm2.MainActivity;
import teamproject.rmm2.R;
import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.models.HabitRow;

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
        if (synchronization())
            // Notification
            notification();
    }

    private boolean synchronization() {
        //TODO Make synchronization (statistics)
        DbHelper dbHelper = new DbHelper(getApplicationContext());

        // Get all habits
        List<HabitRow> habitRowList = dbHelper.getAllHabits();

        // Session Manager
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        // List of lists
        List<List<NameValuePair>> listLists = new ArrayList<>();

        for (int i = 0; i < habitRowList.size(); i ++) {
            Statistics statistics = new Statistics(getApplicationContext(), habitRowList.get(i).getTitle());

            Calendar creationDate = habitRowList.get(i).getCreationDate();
            Calendar updateDate = habitRowList.get(i).getLastUpdateDate();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // Build list for Connection Task
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("tag", AppConfig.TAG_SYNCHRO));
            list.add(new BasicNameValuePair("username", sessionManager.getSavedUserName()));
            list.add(new BasicNameValuePair("czy_sie_udalo", "false"));
            list.add(new BasicNameValuePair("data_wprowadzenia", format.format(creationDate.getTime())));
            list.add(new BasicNameValuePair("czestotliwosc", Integer.toString(habitRowList.get(i).getFrequency())));
            list.add(new BasicNameValuePair("kiedy_ostatnio_aktualizowano_nawyk", format.format(updateDate.getTime())));
            list.add(new BasicNameValuePair("ilosc_nawykow", Integer.toString(statistics.getIloscNawykow())));
            list.add(new BasicNameValuePair("najlepsza_passa", Float.toString(statistics.getNajlepszaPassa())));
            list.add(new BasicNameValuePair("srednia_dlugosc_ciagu", Float.toString(statistics.getSredniaDlugoscCiagu())));
            list.add(new BasicNameValuePair("procent_powodzen", Float.toString(statistics.getProcent_powodzen())));


            listLists.add(list);
        }

        SynchronizationTask synchronizationTask = new SynchronizationTask(getApplicationContext(), listLists);

        boolean bul = false;

        try {
            bul = synchronizationTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bul;
    }

    private void notification() {
        // Make notification
        MakeNotification makeNotification = new MakeNotification(getApplicationContext(), MainActivity.class, R.drawable.ic_halo,
                getApplicationContext().getResources().getString(R.string.notification_habit_title), getApplicationContext().getResources().getString(R.string.notification_habit_description), 0);
    }

}