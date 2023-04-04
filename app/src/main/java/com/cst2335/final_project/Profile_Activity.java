package com.cst2335.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cocktaildatabase_loveleen.R;

/**
 * This class represents a profile of a person.
 * It will store their login information and download history.
 * @author Elizabeth Quach, Jacob Stoppa
 *
 * Workload division:
 * The file was created by Liz and the code was implemented by Jacob.
 */

//implement both the "checker" and the saved prefs in this class.
//As soon as they create a profile, (onclick create), it will have to check the requirements and then
// save it into saved prefs. Use if else statement.
public class Profile_Activity extends AppCompatActivity {
    private static final String PREFS_NAME = "name";
    private static final String PREFS_EMAIL = "email";
    private static final String PASSWORD = "password";

    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Instantiations and variables
        SharedPreferences preferences = getSharedPreferences("profile_data", Context.MODE_PRIVATE);
        //Instantiate views and variables
        nameInput = findViewById(R.id.profile_name);
        emailInput = findViewById(R.id.profile_email);
        passwordInput = findViewById(R.id.profile_password);
        loginButton = findViewById(R.id.profile_login);

        //TODO: Launch another activity by using onclick. Refer to Profile Lab.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a new profile if all requirements are met
                if (checkRequirements()) {
                    //Save profile to shared preferences
                    saveProfile();
                    //Launch MainActivity
                    Intent intent = new Intent(Profile_Activity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        //TODO: Implement password verification.
        passwordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = passwordInput.getText().toString();
                    if (TextUtils.isEmpty(password) || password.length() < 8) {
                        Toast.makeText(Profile_Activity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //TODO: Implement toast messages (alerts/warnings) if the password has not passed.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequirements()) {
                    String password = passwordInput.getText().toString();
                    if (password.length() < 8) {
                        Toast.makeText(Profile_Activity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        saveProfile();
                        Intent intent = new Intent(Profile_Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        //Create a shared pref and create a profile display for MAIN_ACTIVITY to display the user's name.
        String name = preferences.getString(PREFS_NAME, "");
        if (!name.equals("")) {
            Toast.makeText(Profile_Activity.this, "Welcome back ", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to check if requirements are met
    private boolean checkRequirements() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Profile_Activity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //TODO: Create a shared pref and create a profile display for MAIN_ACTIVITY to display the user's name.
    private void saveProfile() {
        SharedPreferences preferences = getSharedPreferences("profile_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_NAME, nameInput.getText().toString());
        editor.putString(PREFS_EMAIL, emailInput.getText().toString());
        editor.putString(PASSWORD, passwordInput.getText().toString());
        editor.apply();
    }
}