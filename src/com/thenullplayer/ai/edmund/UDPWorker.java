package com.thenullplayer.ai.edmund;

import java.net.*;
import java.io.*;

public class UDPWorker implements Runnable
{
    MulticastSocket socket;
    DatagramPacket packetIn;
    DatagramPacket packetOut;
    InetAddress group;

    public UDPWorker()
    {
    }

    public void run()
    {
        try
        {
            socket = new MulticastSocket(Server.DEFAULT_PORT_UDP);
            group = InetAddress.getByName(Server.DEFAULT_GROUP);
            socket.joinGroup(group);
            packetIn = new DatagramPacket (new byte[1024], 1024);
            while(Server.isRunning)
            {
                socket.receive(packetIn);
                //System.out.println(packetIn.getOffset() + ":" + packetIn.getLength()); //Debug
                String msg = new String(packetIn.getData(), packetIn.getOffset(), packetIn.getLength());
                System.out.println("Received from: " + packetIn.getAddress() + ":" + packetIn.getPort() + "\n" + msg);
                if(msg.equalsIgnoreCase("Ping"))
                {
                    msg = "Pong";
                    packetOut = new DatagramPacket ( msg.getBytes(), msg.getBytes().length, packetIn.getAddress(), Server.DEFAULT_PORT_UDP); 
                    socket.send(packetOut);
                }
            }
        }
            catch( Exception ex)
        {
            System.out.println("Problem creating socket on port: " + Server.DEFAULT_PORT_UDP);
            ex.printStackTrace();
            Server.isRunning = false;
        }

        //close socket here
        try
        {
            socket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}