package com.thenullplayer.ai.edmund;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by TheNullPlayer on 2017-07-30.
 */

public class FriendsListAdapter extends BaseAdapter implements ListAdapter
{
    private ArrayList<FriendsListItem> friends;
    private AppCompatActivity activity;
    private LayoutInflater inflater;

    public FriendsListAdapter(AppCompatActivity activityIn)
    {
        activity = activityIn;
        inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        friends = new ArrayList<>();
        friends.add( new FriendsListItem("James","Klein",1129));
        friends.add( new FriendsListItem("Harry","Smith",1081));
        friends.add( new FriendsListItem("Drew","Newton",928));
        friends.add( new FriendsListItem("John","Augustine",876));
        friends.add( new FriendsListItem("jenny","Smith",612));
        friends.add( new FriendsListItem("lenny","Augustine",544));
        friends.add( new FriendsListItem("benny","jacobs",543));
        friends.add( new FriendsListItem("jacobi","Klein",145));
        friends.add( new FriendsListItem("Harry","Newton",245));
        friends.add( new FriendsListItem("John","Klein",873));
        friends.add( new FriendsListItem("Drew","Augustine",354));
        friends.add( new FriendsListItem("Sarah","Klein",542));
        friends.add( new FriendsListItem("jenny","Newton",543));
        friends.add( new FriendsListItem("James","jacobi",146));
        friends.add( new FriendsListItem("Harry","Smith",1134));
        friends.add( new FriendsListItem("Drew","Klein",2566));
        friends.add( new FriendsListItem("James","Augustine",2565));
        friends.add( new FriendsListItem("Harry","Klein",6543));
        friends.add( new FriendsListItem("jenny","Klein",1025));
        friends.add( new FriendsListItem("Sarah","Augustine",1432));
        friends.add( new FriendsListItem("Jacobi","Augustine",8239));
    }

    @Override
    public int getCount()
    {
        return friends.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
            convertView = inflater.inflate(R.layout.item_friend,parent,false);
        TextView firstName = (TextView)convertView.findViewById(R.id.friend_first_name);
        firstName.setText(friends.get(position).firstName);
        TextView lastName = (TextView)convertView.findViewById(R.id.friend_last_name);
        lastName.setText(friends.get(position).lastName);
        TextView rep = (TextView)convertView.findViewById(R.id.friend_rep);
        rep.setText("Rep: "+friends.get(position).rep);
        return convertView;
    }

    @Override
    public Object getItem(int position)
    {
        return friends.get(position);
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

    private class FriendsListItem
    {
        public String firstName;
        public String lastName;
        public int rep;
        FriendsListItem(String firstNameIn,String lastNameIn,int repIn)
        {
            firstName=firstNameIn;
            lastName=lastNameIn;
            rep=repIn;
        }
    }
}
