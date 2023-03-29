package com.cst2335.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
/**
 * This class represents a profile of a person.
 * It will store their login information and download history.
 */

//implement both the "checker" and the saved prefs in this class.
//As soon as they create a profile, (onclick create), it will have to check the requirements and then
// save it into saved prefs. Use if else statement.
public class Profile_Activity extends AppCompatActivity {
    private static final String PREFS_NAME = "name";
    private static final String PREFS_EMAIL = "email";
    private static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Instantiations and variables
        SharedPreferences preferences = getSharedPreferences("profile_data", Context.MODE_PRIVATE);

        //TODO: Launch another activity by using onclick. Refer to Profile Lab.

        //TODO: Implement password verification.

        //TODO: Implement toast messages (alerts/warnings) if the password has not passed.

        //TODO: Create a shared pref and create a profile display for MAIN_ACTIVITY to display the user's name.
    }
}