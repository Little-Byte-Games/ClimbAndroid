package com.littlebyte.climb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import io.swagger.client.api.AccountApi;
import io.swagger.client.model.ApplicationUser;

public class MainActivity extends AppCompatActivity {
    private AccountApi accountApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountApi = new AccountApi();
        accountApi.setBasePath("http://192.168.196.1:45455");
    }

    public void onRegisterClick(View view) {
        final TextView mTextView = findViewById(R.id.textView);
        mTextView.setText("registering...");

        final EditText emailInput = findViewById(R.id.emailInput);
        final String email = emailInput.getText().toString();

        final EditText passwordInput = findViewById(R.id.passwordInput);
        final String password = passwordInput.getText().toString();

        final EditText confirmInput = findViewById(R.id.confirmInput);
        final String confirm = confirmInput.getText().toString();

        accountApi.accountRegister(email, password, confirm, new Listener<ApplicationUser>() {
                    @Override
                    public void onResponse(ApplicationUser response) {
                        mTextView.setText("Welcome " + response.getEmail());
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("Couldn't register!");

                    }
                }
        );
    }

    public void onLoginClick(View view) {
        final TextView mTextView = findViewById(R.id.textView);
        mTextView.setText("logging in...");

        final EditText emailInput = findViewById(R.id.emailInput);
        final String email = emailInput.getText().toString();

        final EditText passwordInput = findViewById(R.id.passwordInput);
        final String password = passwordInput.getText().toString();

        accountApi.accountLogIn(email, password, new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTextView.setText("Logged In");
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("Couldn't log in!");
                    }
                }
        );
    }
}
