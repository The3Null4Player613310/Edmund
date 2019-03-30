package com.thenullplayer.ai.edmund;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

/**
 * Created by TheNullPlayer on 2017-07-30.
 */

public class MessagesListAdapter extends BaseAdapter implements ListAdapter
{
    @Override
    public int getCount()
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
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

}
