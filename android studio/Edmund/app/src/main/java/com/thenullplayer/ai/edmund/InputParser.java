package com.thenullplayer.ai.edmund;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TheNullPlayer on 2017-06-27.
 */

public class InputParser
{

    //commands
    //open --opens activity if available
    Pattern open = Pattern.compile("^[Oo]pen[:space:]*");
    //toggle -- toggles state of item
    Pattern toggle = Pattern.compile("^[Tt]oggle[:space:]*");

    //instance variables
    private MainActivity activity;
    private String userID;
    private Edmund edmund;

    public InputParser(Edmund edmundIn,AppCompatActivity activityIn,String userIDIn)
    {
        activity = (MainActivity) activityIn;
        edmund = edmundIn;
        userID = userIDIn;
    }

    public void parseInput(String input)
    {
        String output;
        output = parseCommand(input);
        if(output == null)
            parseDialogue(input);
        else
        {
            edmund.respond(input);
        }
    }

    private String parseCommand(String input)
    {
        //pattern detection
        Matcher m = open.matcher(input);
        if (m.find())
        {
            String activityName = input.substring(m.end(), input.length());
            if (activityName.equalsIgnoreCase("stats"))
                activity.startActivity(new Intent(activity, StatsActivity.class));
            else if (activityName.equalsIgnoreCase("quests"))
                activity.startActivity(new Intent(activity, QuestsActivity.class));
            else if (activityName.equalsIgnoreCase("map"))
                activity.startActivity(new Intent(activity, MapsActivity.class));
            else if (activityName.equalsIgnoreCase("friends"))
                activity.startActivity(new Intent(activity, FriendsActivity.class));
            else if (activityName.equalsIgnoreCase("messages"))
                activity.startActivity(new Intent(activity, MessagesActivity.class));
            else if (activityName.equalsIgnoreCase("settings"))
                activity.startActivity(new Intent(activity, SettingsActivity.class));
            else
                return null;
            return "Opening " + activityName;
        }
        return null;
    }

    private void parseDialogue(String input)
    {
        new QueryTask().execute(input);
    }


    private class QueryTask extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... input) {
            Socket querySocket;
            try
            {
                querySocket = new Socket(InetAddress.getByName("192.168.137.1"), 6432);
                //querySocket.setTcpNoDelay(true);
                PrintWriter outputWriter = new PrintWriter(querySocket.getOutputStream(),true);
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(querySocket.getInputStream()));
                outputWriter.println(userID+":"+input[0]);
                //think about using multiple strings for recognition....
                String response = null;
                do
                {
                    response = inputReader.readLine();
                }
                while (response==null);
                outputWriter.close();
                inputReader.close();
                querySocket.close();
                return response;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                String[] response = activity.getResources().getStringArray(R.array.edmund_network_timeout);
                return response[(new Random()).nextInt(response.length)];
            }
        }

        protected void onPostExecute(String response) {
            edmund.respond(response);
            activity.micButton.setEnabled(true);
        }
    }
}
