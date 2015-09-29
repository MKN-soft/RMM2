package teamproject.rmm2.Helpers;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import teamproject.rmm2.MainActivity;
import teamproject.rmm2.R;

/**
 * Created by MSI on 2015-09-24.
 *
 * Specific task for synchronization, very similar to ConnectionTask.
 */
public class SynchronizationTask extends AsyncTask<Void, Void, Boolean> {

    private List<List<NameValuePair>> listLists;
    private Context context;
    private JSONObject json;
    private JSONParser jParser;

    public SynchronizationTask(Context context, List<List<NameValuePair>> listLists) {
        this.context = context;
        this.listLists = listLists;
        this.jParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        IsConnected isConnected = new IsConnected(context);

        for (int i = 0; i < listLists.size(); i++) {
            List<NameValuePair> list = listLists.get(i);

            if (isConnected.check()) {
                json = jParser.getJSONFromUrl(AppConfig.URL_API, list);
            }
            else {
                jParser.setError("No internet connection!");
            }
        }

        if (jParser.getError())
            return false;
        else
            return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        // ONLY FOR TESTS!!!! Uncomment if you want
        MakeNotification makeNotification = new MakeNotification(context, MainActivity.class, R.mipmap.ic_test, "Synchronization Task TEST", jParser.getErrorMsg(), 1);
    }

}
