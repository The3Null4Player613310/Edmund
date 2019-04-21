package com.thenullplayer.ai.edmund;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
    public static final int DEFAULT_PORT_UDP = 3264;
    public static final int DEFAULT_PORT_TCP = 6432;
    public static final String DEFAULT_GROUP = "225.252.32.64";
    public static volatile boolean isRunning = true;

    public static void manager()
    {
        isRunning = true;
        new Thread( new UDPWorker()).start();
        new Thread( new TCPWorker()).start();
        String input = "";
        Scanner kb = new Scanner(System.in);
        System.out.print("\f");
        System.out.println("Service: Server is now running.");
        do
        {

            System.out.print("User: ");
            input = kb.nextLine();

            isRunning = (!(((input.equalsIgnoreCase("quit")) || (input.equalsIgnoreCase("exit"))) || ((input.equalsIgnoreCase("cancle")) || (input.equalsIgnoreCase("escape")))));
        }while(isRunning);
    }
}