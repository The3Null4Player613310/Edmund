package com.thenullplayer.ai.edmund;
import java.io.IOException;
import java.net.*;

class TCPServer implements Runnable
{
    private static final int DEFAULT_PORT_TCP = 6432;
    static volatile boolean isRunning = true;
    private ServerSocket serviceSocket;

    static void manager()
    {
        new Thread( new TCPServer()).start();
    }

    private TCPServer(){}

    public void run()
    {
        try
        {
            serviceSocket = new ServerSocket(TCPServer.DEFAULT_PORT_TCP);
            serviceSocket.setSoTimeout(1000);
            while(isRunning && Main.isRunning)
            {
                try
                {
                    Socket workerSocket = serviceSocket.accept();
                    if (workerSocket != null)
                        new Thread(new ChatWorker(workerSocket)).start();
                }
                catch (SocketTimeoutException e)
                {
                    //e.printStackTrace();
                }
            }
            serviceSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            TCPServer.isRunning = false;
        }
    }
}