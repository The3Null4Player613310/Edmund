package com.thenullplayer.ai.edmund;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class FriendsActivity extends AppCompatActivity
{
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        ListView friendsList = (ListView) findViewById(R.id.friends_view);
        View footerView = inflater.inflate(R.layout.list_footer,friendsList,false);
        FriendsListAdapter friendsAdapter = new FriendsListAdapter(this);
        friendsList.addFooterView(footerView);
        friendsList.setAdapter(friendsAdapter);
    }
}
