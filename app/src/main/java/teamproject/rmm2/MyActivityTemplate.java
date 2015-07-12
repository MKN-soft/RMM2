package teamproject.rmm2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import teamproject.rmm2.Helpers.SessionManager;

/**
 * Created by Marcin on 2015-07-12.
 */
public abstract class MyActivityTemplate extends Activity {

    protected SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sessionManager = new SessionManager(getContext());

        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    protected abstract Context getContext();

}