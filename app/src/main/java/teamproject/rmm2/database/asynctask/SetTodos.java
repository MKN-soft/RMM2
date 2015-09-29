package teamproject.rmm2.database.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import teamproject.rmm2.database.DbHelper;

/**
 * Created by MSI on 2015-09-22.
 */
public class SetTodos extends AsyncTask<Void, Void, Void> {

    private Context context;
    private DbHelper dbHelper;

    public SetTodos(Context context) {
        this.context = context;
        dbHelper = new DbHelper(this.context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //dbHelper.setTodos();

        return null;
    }
}
