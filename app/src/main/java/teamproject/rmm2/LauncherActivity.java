package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import teamproject.rmm2.Helpers.AppConfig;
import teamproject.rmm2.Helpers.ConnectionTask;
import teamproject.rmm2.Helpers.Validate;

public class LauncherActivity extends MyActivityTemplate {

    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set UI (via super)
        super.onCreate(savedInstanceState);

        //TODO Is user already logged?
        if(isLogged() == true){
            //NOT my first time here!

            //Hooking activity
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        // Else it's  users first time here so user has to sign in or sign up


        //Validation formula
        registerViews();


        // Set action to button
        Button loginBtn = (Button) findViewById(R.id.button_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });


    }

    /**
     * onClick for button_login.
     * Set in xml layout file.
     */
    private void onClickSignIn(){
        if(checkValidation()){
            //if Validation is ok
            submitForm();
        }
        else{
            //Something went wrong...
            Toast.makeText(LauncherActivity.this, "Form contains error!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * submits form to phone memory (in SharedPreferences)
     */
    private void submitForm() {
        // Build list params
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("tag", AppConfig.TAG_LOGIN));
        list.add(new BasicNameValuePair("username", editTextLogin.getText().toString()));
        list.add(new BasicNameValuePair("password", editTextPassword.getText().toString()));

        ConnectionTask connectionTask = new ConnectionTask(this, list);
        connectionTask.execute();
    }

    /**
     * checks if password and username are valid
     * @return boolean - if it's valid or not
     */
    private boolean checkValidation() {
        boolean result = true;

        if(!Validate.isEmailAddress(editTextLogin, true)){
            result = false;
        }
        if(!Validate.isPassword(editTextPassword, true)) {
            result = false;
        }

        return result;
    }

    /**
     * Validates (on the run) if password and username are correct (not too short, not too long, correct symbols etc.)
     */
    private void registerViews() {
        //TODO MR doesn't know what the fuck exactly this is supposed to do
        editTextLogin = (EditText) findViewById(R.id.editText_username);
        editTextLogin.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validate.isEmailAddress(editTextLogin, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validate.isPassword(editTextPassword, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * gets layout resource id because parent class needs it for constructor (onCreate) to work properly (check it's NON-abstract method)
     * @return layout id
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }

    /**
     * returns boolean from Shared preferences
     * @return boolean telling if user was logged in earlier
     */
    public boolean isLogged() {
        return sessionManager.getKEY_IS_LOGGEDIN();
    }

}
