package com.thenullplayer.ai.edmund;

import java.net.*;
import java.io.*;

public class TCPWorker implements Runnable
{

    ServerSocket serviceSocket;

    public TCPWorker()
    {

    }

    public void run()
    {
        try
        {
            serviceSocket = new ServerSocket(Server.DEFAULT_PORT_TCP);
            while(Server.isRunning)
            {
                new Thread( new ChatWorker(serviceSocket.accept())).start();
            }
        }
        catch( Exception ex )
        {
            System.out.println("Problem creating socket on port: " + Server.DEFAULT_PORT_TCP);
            ex.printStackTrace();
            Server.isRunning = false;
        }

        //close socket here
        try
        {
            serviceSocket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}