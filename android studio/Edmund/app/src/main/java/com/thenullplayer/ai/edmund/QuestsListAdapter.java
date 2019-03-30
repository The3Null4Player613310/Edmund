package com.thenullplayer.ai.edmund;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by TheNullPlayer on 2017-07-30.
 */

public class QuestsListAdapter extends BaseAdapter implements ListAdapter
{
    private ArrayList<QuestsListItem> quests;
    private AppCompatActivity activity;
    private LayoutInflater inflater;

    public QuestsListAdapter(AppCompatActivity activityIn)
    {
        activity = activityIn;
        inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        quests = new ArrayList<>();
        //remove names?
        quests.add( new QuestsListItem("Barbecue on beach!!!","time for fun in the sun!","John"));
        quests.add( new QuestsListItem("Car pool","its better for the environment.","Mary"));
        quests.add( new QuestsListItem("Join in for a great bash","having a party just for the fun any one up.","Dave"));
        quests.add( new QuestsListItem("Meeting out by campus","we are going to meet up for all interested.","Jane"));
        quests.add( new QuestsListItem("Cleaning up graffiti","a group of us will be cleaning up graffiti.","Jenny"));
        quests.add( new QuestsListItem("Creek Cleanup","Any one willing to join us as we clean up the creek.","Bill"));
        quests.add( new QuestsListItem("Protest","Any one willing to put some time aside for a protest.","Mark"));
        quests.add( new QuestsListItem("Barbecue on beach!!!","time for fun in the sun!","John"));
        quests.add( new QuestsListItem("Car pool","its better for the environment.","Mary"));
        quests.add( new QuestsListItem("Join in for a great bash","having a party just for the fun any one up.","Dave"));
        quests.add( new QuestsListItem("Meeting out by campus","we are going to meet up for all interested.","Jane"));
        quests.add( new QuestsListItem("Cleaning up graffiti","a group of us will be cleaning up graffiti.","Jenny"));
        quests.add( new QuestsListItem("Creek Cleanup","Any one willing to join us as we clean up the creek.","Bill"));
        quests.add( new QuestsListItem("Protest","Any one willing to put some time aside for a protest.","Mark"));
        quests.add( new QuestsListItem("Barbecue on beach!!!","time for fun in the sun!","John"));
        quests.add( new QuestsListItem("Car pool","its better for the environment.","Mary"));
        quests.add( new QuestsListItem("Join in for a great bash","having a party just for the fun any one up.","Dave"));
        quests.add( new QuestsListItem("Meeting out by campus","we are going to meet up for all interested.","Jane"));
        quests.add( new QuestsListItem("Cleaning up graffiti","a group of us will be cleaning up graffiti.","Jenny"));
        quests.add( new QuestsListItem("Creek Cleanup","Any one willing to join us as we clean up the creek.","Bill"));
        quests.add( new QuestsListItem("Protest","Any one willing to put some time aside for a protest.","Mark"));
        quests.add( new QuestsListItem("Barbecue on beach!!!","time for fun in the sun!","John"));
        quests.add( new QuestsListItem("Car pool","its better for the environment.","Mary"));
        quests.add( new QuestsListItem("Join in for a great bash","having a party just for the fun any one up.","Dave"));
        quests.add( new QuestsListItem("Meeting out by campus","we are going to meet up for all interested.","Jane"));
        quests.add( new QuestsListItem("Cleaning up graffiti","a group of us will be cleaning up graffiti.","Jenny"));
        quests.add( new QuestsListItem("Creek Cleanup","Any one willing to join us as we clean up the creek.","Bill"));
        quests.add( new QuestsListItem("Protest","Any one willing to put some time aside for a protest.","Mark"));

    }

    @Override
    public int getCount()
    {
        return quests.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
            convertView = inflater.inflate(R.layout.item_quest,parent,false);
        TextView title = (TextView)convertView.findViewById(R.id.quest_title);
        title.setText(quests.get(position).title);
        TextView subTitle = (TextView)convertView.findViewById(R.id.quest_subtitle);
        subTitle.setText(quests.get(position).subTitle);
        ToggleButton joinButton = (ToggleButton)convertView.findViewById(R.id.join_button);
        joinButton.setOnCheckedChangeListener(null);
        joinButton.setChecked(quests.get(position).joined);
        joinButton.setOnCheckedChangeListener(new JoinButtonOnCheckedChangeListener(position));
        return convertView;
    }

    @Override
    public Object getItem(int position)
    {
        return quests.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return super.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position)
    {
        return super.isEnabled(position);
    }

    private class JoinButtonOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener
    {
        private int position;

        public JoinButtonOnCheckedChangeListener(int positionIn)
        {
            position=positionIn;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            quests.get(position).joined=isChecked;//wip
            System.out.println(isChecked);
        }
    }

    private class QuestsListItem
    {
        public String title;
        public String subTitle;
        public String name;
        public boolean joined;
        QuestsListItem(String titleIn,String subTitleIn,String nameIn)
        {
            title=titleIn;
            subTitle=subTitleIn;
            name=nameIn;
        }
    }

}
