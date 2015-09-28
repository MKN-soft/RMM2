package teamproject.rmm2.database.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.models.HabitRow;

/**
 * Created by MSI on 2015-09-22.
 */
public class GetAllHabitsTask extends AsyncTask<Void, Void, List<HabitRow>> {
    private Context context;
    private DbHelper dbHelper;

    public GetAllHabitsTask(Context context) {
        this.context = context;
        dbHelper = new DbHelper(this.context);
    }

    @Override
    protected List<HabitRow> doInBackground(Void... params) {
        return dbHelper.getAllHabits();
    }

}
