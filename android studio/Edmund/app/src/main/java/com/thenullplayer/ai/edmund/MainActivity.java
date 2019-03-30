package com.thenullplayer.ai.edmund;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.UUID;

public class MainActivity extends AppCompatActivity
{


    //instance variables
    TextView responseText;
    ImageButton micButton;
    FloatingActionButton navMenuButton;
    DrawerLayout drawerLayout;
    NavigationView navView;
    View headerView;
    TextView firstNameText;
    TextView lastNameText;
    TextView reputationText;
    String userID;
    private SharedPreferences sharedPreferences;
    private Edmund edmund;

    //****************************************************************
    //app methods
    //****************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //set vars
        responseText = (TextView) findViewById(R.id.text_view);
        micButton = (ImageButton) findViewById(R.id.mic_button);
        navMenuButton = (FloatingActionButton) findViewById(R.id.navmenu_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navigation_view);
        headerView = navView.getHeaderView(0);
        firstNameText = (TextView) headerView.findViewById(R.id.first_name);
        lastNameText = (TextView) headerView.findViewById(R.id.last_name);
        reputationText = (TextView) headerView.findViewById(R.id.reputation_text);

        //setup user id
        if (sharedPreferences.getString("user_id",null) == null)
            sharedPreferences.edit().putString("user_id",UUID.randomUUID().toString()).commit();

        edmund = new Edmund(this,sharedPreferences.getString("user_id",""));

        System.out.println(""+sharedPreferences.getString("user_id",""));

        //restore instance variables
        //if (savedInstanceState!=null)
        //    responseText.setText(savedInstanceState.getString("response_text","Hello"));

        //setup listeners
        navMenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                int id = menuItem.getItemId();
                int rep;
                switch (id)
                {
                    case R.id.stats_item:
                        startActivity(new Intent(MainActivity.this,StatsActivity.class));
                        break;
                    case R.id.quests_item:
                        startActivity(new Intent(MainActivity.this,QuestsActivity.class));
                        break;
                    case R.id.map_item:
                        startActivity(new Intent(MainActivity.this,MapsActivity.class));
                        break;
                    case R.id.friends_item:
                        startActivity(new Intent(MainActivity.this,FriendsActivity.class));
                        break;
                    case R.id.messages_item:
                        startActivity(new Intent(MainActivity.this,MessagesActivity.class));
                        break;
                    case R.id.calendar_item:
                        rep = sharedPreferences.getInt("reputation",0);
                        rep--;
                        (sharedPreferences.edit()).putInt("reputation",rep).apply();
                        reputationText.setText(getString(R.string.nav_rep_counter,rep));
                        //startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                        break;
                    case R.id.alarms_item:
                        rep = sharedPreferences.getInt("reputation",0);
                        rep++;
                        (sharedPreferences.edit()).putInt("reputation",rep).apply();
                        reputationText.setText(getString(R.string.nav_rep_counter,rep));
                        //startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                        break;
                    case R.id.settings_item:
                        startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        micButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (edmund != null)
                {
                    if(!edmund.isListening())
                    {
                        edmund.startListening();
                    }
                    else
                    {
                        edmund.stopListening();
                    }
                }
            }
        });

        //micButton.setEnabled(false);

    }

    @Override
    public void onStart()
    {

        //setup header
        String fName = sharedPreferences.getString("pref_first_name","");
        String lName = sharedPreferences.getString("pref_last_name","");
        if (fName.equalsIgnoreCase("") && lName.equalsIgnoreCase(""))
            fName="anon";
        firstNameText.setText(fName);
        lastNameText.setText(lName);
        reputationText.setText(getString(R.string.nav_rep_counter,sharedPreferences.getInt("reputation",0)));

        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        drawerLayout.closeDrawer(Gravity.START);
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        if(edmund != null)
        {
            //edmund.stopSpeaking();
            edmund.destroy();
        }
        super.onDestroy();
    }
}