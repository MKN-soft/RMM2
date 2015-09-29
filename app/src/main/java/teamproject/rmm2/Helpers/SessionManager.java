package teamproject.rmm2.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marcin on 2015-07-12.
 *
 * Session manager holding any values that not need in hold in database.
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
    private final String KEY_USERNAME = "username";
    private final String KEY_IS_HABIT_TITLE = "habitTitle";

    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
    }

    public boolean getKEY_IS_LOGGEDIN(){
        return this.sharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getHabitTitle() {
        return this.sharedPreferences.getString(KEY_IS_HABIT_TITLE, "");
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

    public void setUserLoggedIn(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLogin);
        editor.commit();
    }

    public void setLogin(boolean isLoggedIn, String salt, String username) {
        this.editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        this.editor.putString(KEY_IS_SALT, salt);
        this.editor.putString(KEY_USERNAME, username);

        // commit changes
        editor.commit();
    }

    public void setHabitTitle(String habitTitle) {
        this.editor.putString(KEY_IS_HABIT_TITLE, habitTitle);

        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

}
