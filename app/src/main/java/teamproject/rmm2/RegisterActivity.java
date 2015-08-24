package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
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


public class RegisterActivity extends MyActivityTemplate {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnRegister;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        btnRegister = (Button) findViewById(R.id.btnMakeMe);
        btnLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Check if logged
        if (sessionManager.getKEY_IS_LOGGEDIN()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //Validation formula
        registerViews();

        // btnLogin event click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });

        // btnRegister event click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
            }
        });

    }

    private void registerViews() {
        editEmail = (EditText) findViewById(R.id.email);
        editEmail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validate.isEmailAddress(editEmail, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editPassword = (EditText) findViewById(R.id.password);
        editPassword.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validate.isPassword(editPassword, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void onClickRegister() {
        if (checkValidation()) {
            // if Validation is ok
            submitForm();
        }
        else {
            // something went wrong
            Toast.makeText(RegisterActivity.this, "Form contains error!", Toast.LENGTH_LONG).show();
        }
    }

    private void submitForm() {
        // Build list params
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("tag", AppConfig.TAG_REGISTER));
        list.add(new BasicNameValuePair("username", editEmail.getText().toString()));
        list.add(new BasicNameValuePair("password", editPassword.getText().toString()));

        ConnectionTask connectionManager = new ConnectionTask(this, list);
        connectionManager.execute();
    }

    private boolean checkValidation() {
        boolean result = true;

        if (!Validate.isEmailAddress(editEmail, true)) {
            result = false;
        }
        if (!Validate.isPassword(editPassword, true)) {
            result = false;
        }

        return result;
    }

    private void onClickLogin() {
        Intent intent = new Intent(RegisterActivity.this, LauncherActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected Context getContext() { return this.getApplicationContext(); }
}
