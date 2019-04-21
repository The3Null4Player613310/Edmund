package com.thenullplayer.ai.edmund;

public class ServerWorker implements Runnable
{

    public ServerWorker()
    {
        
    }

    public void run()
    {
        new Thread( new UDPWorker()).start();
        new Thread( new TCPWorker()).start();
    }
}