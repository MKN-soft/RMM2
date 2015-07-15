package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import teamproject.rmm2.login_registration.ConnectionTask;
import teamproject.rmm2.login_registration.Validate;

public class LauncherActivity extends MyActivityTemplate {

    private Button buttonSignIn;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private SharedPreferences prefs;    // how does it work? I copied it like soviet scientist (MR)

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        prefs = getSharedPreferences("rmm2", MODE_PRIVATE);
        Boolean imBackAgain = isLogged();

        //TODO Is user already logged?
        if(imBackAgain == true){
            //NOT my first time here!

            //Hooking activity
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        // Else it's  users first time here so user has to sign in or sign up

        //set UI
        super.onCreate(savedInstanceState); //we have to create activity then.

        //Validation formula
        registerViews();

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
        // Set variables
        SharedPreferences prefs = getSharedPreferences("rmm2", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", editTextLogin.getText().toString());
        editor.putString("password", editTextPassword.getText().toString());


        editor.commit();

        //It won't work until ConnectionTask class is completed
        ConnectionTask connectionTask = new ConnectionTask(this, ConnectionTask.WhichSide.LOGIN);
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
     * I have no idea what it's doing (MR)
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
        return this.getApplicationContext();
    }

    /**
     * returns boolean from SharedPreferences prefs
     * @return boolean "imBackAgain"
     */
    public boolean isLogged() {
        return prefs.getBoolean("imBackAgain", false);
    }
}
