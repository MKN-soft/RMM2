package teamproject.rmm2.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import teamproject.rmm2.MainActivity;

/**
 * Created by Marcin on 2015-07-15.
 * Checks internet connection
 */
public class ConnectionTask extends AsyncTask<Void, Void, Void> {
    private JSONObject json;
    private Context context;
    private List<NameValuePair> list;
    private JSONParser jParser;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private String successMsg = "Success!";

    public ConnectionTask(Context context, List<NameValuePair> list) {
        this.context = context;
        this.list = list;
        this.jParser = new JSONParser();
        this.sessionManager = new SessionManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.list = list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setMessage("Connecting...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        IsConnected isConnected = new IsConnected(context);
        if (isConnected.check()) {
            json = jParser.getJSONFromUrl(AppConfig.URL_API, list);
        }
        else {
            jParser.setError("No internet connection!");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        progressDialog.dismiss();
        progressDialog.cancel();

        String salt = " ";
        String username = " ";
        String tag = " ";

        boolean error = jParser.getError();
        if (!error) {
            try {
                tag = json.getString("tag");

                if (!tag.toString().equals(AppConfig.TAG_SYNCHRO.toString())) {
                    salt = json.getString("salt");
                    username = json.getString("username");

                    // Create login session
                    sessionManager.setLogin(true, salt, username);

                    // Launch User Activity
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    // Toast success
                    Toast.makeText(context, successMsg, Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {
            // error in login. Get the error message
            String errorMsg = jParser.getErrorMsg();
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
        }
    }

}
