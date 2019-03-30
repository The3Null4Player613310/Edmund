package com.thenullplayer.ai.edmund;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class QuestsActivity extends AppCompatActivity
{
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests);
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        ListView questsList = (ListView) findViewById(R.id.quests_view);
        View footerView = inflater.inflate(R.layout.list_footer,questsList,false);
        QuestsListAdapter questsAdapter = new QuestsListAdapter(this);
        questsList.addFooterView(footerView);
        questsList.setAdapter(questsAdapter);
    }
}
