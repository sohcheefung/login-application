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

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText eRegName;
    private EditText eRegPassword;
    private Button eRegister;
    private TextView toLogin;

    public Credentials credentials;

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

        credentials = new Credentials();

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){

            Map<String, ?> preferencesMap = sharedPreferences.getAll();

            if(preferencesMap.size() != 0){
                credentials.loadCredentials(preferencesMap);
            }
        }

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

                    if(credentials.checkUsername(regUsername)) {
                        Toast.makeText(RegistrationActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                    }else{

                        credentials.addCredentials(regUsername,regPassword);

                        /* Store the credentials*/
                        sharedPreferencesEditor.putString(regUsername, regPassword);
                        sharedPreferencesEditor.putString("LastSavedUsername", regUsername);
                        sharedPreferencesEditor.putString("LastSavedPassword", regPassword);

                        /* Commits the changes and adds them to the file*/
                        sharedPreferencesEditor.apply();

                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        Toast.makeText(RegistrationActivity.this , "Registration Successful!", Toast.LENGTH_SHORT).show();
                    }
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