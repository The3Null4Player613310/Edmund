/*
################################################################
#Edmund: ChatWorker.java
#Copyright © 2017-2025 Allison Munn
#FULL COPYRIGHT NOTICE IS IN README
################################################################
*/

package com.thenullplayer.ai.edmund;

import java.net.*;
import java.io.*;
import java.util.*;

class ChatWorker implements Runnable
{

    private final Socket clientSocket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private Edmund edmund;
    private static final HashMap<String, Edmund> sessions = new HashMap<String, Edmund>();
    
    public ChatWorker(Socket socket)
    {
        clientSocket = socket;
        try
        {
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
        }
        catch(IOException ie)
        {
            ie.printStackTrace();
        }
    }

    public void run()
    {
        String data=null;
        while(data==null)
        {
            try
            {
                data = inputStream.readLine();
            }
            catch(IOException ie)
            {
                ie.printStackTrace();
            }
        }

        //WIP
        String[] message = data.split(":");
        String id = message[0];
        String input = message[1]; 
        //put checks here
        
        if(sessions.get(id)==null)
            sessions.put(id,new Edmund("hello"));

        edmund = sessions.get(id);
        
        String output = edmund.parseInput(input);
        //System.out.println(output);

        outputStream.println(output);

        if((input.equalsIgnoreCase("goodbye") || input.equalsIgnoreCase("good bye") || input.equalsIgnoreCase("bye")) 
        || (output.equalsIgnoreCase("goodbye") || output.equalsIgnoreCase("good bye") || output.equalsIgnoreCase("bye")))
            sessions.remove(id);

        //WIP
        

        //close socket here
        try
        {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}