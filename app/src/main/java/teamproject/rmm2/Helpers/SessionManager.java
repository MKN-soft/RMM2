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
    private final String PREF_NAME = "RMM2";

    //Shared preferences keys
    private final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private final String KEY_IS_SALT = "salt";
    private final String KEY_PASSWORD = "password";
    private final String KEY_USERNAME = "username";

    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
    }

    public SharedPreferences getPrefs(){
        return sharedPreferences;
    }

    public String getPREF_NAME(){
        return PREF_NAME;
    }

    public String getKEY_IS_LOGGEDIN(){
        return KEY_IS_LOGGEDIN;
    }

    public String getKEY_IS_SALT(){
        return KEY_IS_SALT;
    }

    public String getKEY_PASSWORD(){
        return KEY_PASSWORD;
    }

    public String getKEY_USERNAME(){
        return KEY_USERNAME;
    }

    //save user name to SharedPref
    public void setSavedUsername(String username){
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedUserName() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public void setSavedPassword(String pass) {
        editor.putString(KEY_PASSWORD, pass);
        editor.commit();
    }

    public void setUserLoggedIn(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLogin);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}
