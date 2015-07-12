package teamproject.rmm2.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marcin on 2015-07-12.
 */
public class SessionManager {

    // Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private final String PREF_NAME = "ProjektZespolowy";

    //private final String KEY_IS_.... = ".....";

    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
    }

}
