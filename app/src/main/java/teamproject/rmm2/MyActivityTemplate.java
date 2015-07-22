package teamproject.rmm2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import teamproject.rmm2.Helpers.SessionManager;
import teamproject.rmm2.database.DbHelper;

/**
 * Created by Marcin on 2015-07-12.
 * Activity template, all activities inherit after this one.
 */
public abstract class MyActivityTemplate extends Activity {

    protected SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creates SessionManager object that helps with managing
        this.sessionManager = new SessionManager(getContext());

        //creates DbHelper to access database
        DbHelper DbHelper = new DbHelper(getContext());

        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    protected abstract Context getContext();

}