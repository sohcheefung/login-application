package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText eRegName;
    private EditText eRegPassword;
    private Button eRegister;
    private TextView toLogin;

    public static Credentials credentials;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        eRegName = findViewById(R.id.etRegName);
        eRegPassword = findViewById(R.id.etRegPassword);
        eRegister = findViewById(R.id.btnRegister);
        toLogin = findViewById(R.id.tvLogin);

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });

        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regUsername = eRegName.getText().toString();
                String regPassword = eRegPassword.getText().toString();

                if(validate(regUsername, regPassword)){
                    credentials = new Credentials(regUsername, regPassword);

                    /* Store the credentials*/
                    sharedPreferencesEditor.putString("Username", regUsername);
                    sharedPreferencesEditor.putString("Password", regPassword);

                    /* Commits the changes and adds them to the file*/
                    sharedPreferencesEditor.apply();

                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    Toast.makeText(RegistrationActivity.this , "Registration Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate(String username, String password){

        if(username.isEmpty()|| password.length() < 8){
            Toast.makeText(RegistrationActivity.this , "Please enter all the details, password should be at least 8 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}