package teamproject.rmm2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import teamproject.rmm2.Helpers.SessionManager;

/**
 * Created by Marcin on 2015-07-12.
 * Activity template, all activities inherit after this one.
 */
public abstract class MyActivityTemplate extends Activity {

    protected SessionManager sessionManager;
    protected abstract int getLayoutResourceId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sessionManager = new SessionManager(getApplicationContext()); //creates SessionManager object that helps with managing

        setContentView(getLayoutResourceId());
    }

}