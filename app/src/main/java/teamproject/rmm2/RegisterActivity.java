package teamproject.rmm2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class RegisterActivity extends MyActivityTemplate {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnRegister;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // set UI
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);

        btnRegister = (Button) findViewById(R.id.btnMakeMe);
        btnLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Check if logged
        if (sessionManager.getKEY_IS_LOGGEDIN()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

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

    private void onClickRegister() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            registerUser(email, password);
        }
        else
            // Empty data
            Toast.makeText(getApplicationContext(), "Please enter data!", Toast.LENGTH_LONG).show();
    }

    private void registerUser(String email, String password) {
        // Build list params
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("tag", AppConfig.TAG_REGISTER));
        list.add(new BasicNameValuePair("username", email));
        list.add(new BasicNameValuePair("password", password));

        ConnectionTask connectionManager = new ConnectionTask(this, list);
        connectionManager.execute();
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
