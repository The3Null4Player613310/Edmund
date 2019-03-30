package com.thenullplayer.ai.edmund;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity
{
    //instance variables
    TextView firstNameText;
    TextView lastNameText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //find view dynamic views
        firstNameText = (TextView) findViewById(R.id.first_name);
        lastNameText = (TextView) findViewById(R.id.last_name);
    }

    @Override
    protected void onStart()
    {
        firstNameText.setText(sharedPreferences.getString("first_name",""));
        lastNameText.setText(sharedPreferences.getString("last_name",""));

        super.onStart();
    }
}
