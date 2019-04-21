package com.thenullplayer.ai.edmund;

import java.util.*;
import java.io.*;

public class Main {


    //global varibles
    static Scanner kb = new Scanner(System.in);

    //main method
    public static void main(String[] args) throws IOException
    {
        String input = "";
        boolean prompt = true;

        do
        {
            if(prompt)
            {
                System.out.print("\f");
                System.out.println("Service: Hello and welcome please enter a command.");
                prompt = false;
            }

            System.out.print("User: ");
            input = kb.nextLine();

            if(input.equalsIgnoreCase("client"))
            {
                edmundClient();
                prompt = true;
            }
            else if(input.equalsIgnoreCase("server"))
            {
                edmundServer();
                prompt = true;
            }

        }while(!(((input.equalsIgnoreCase("quit")) || (input.equalsIgnoreCase("exit"))) || ((input.equalsIgnoreCase("cancle")) || (input.equalsIgnoreCase("escape")))));
    }

    //client version of chat bot
    private static void edmundClient()
    {

        System.out.print("\f");

        String prompt = "hello";
        Edmund edmund = new Edmund(prompt);
        String input = "";
        String output = prompt;
        System.out.println("Edmund: " + output);

        do
        {
            //get input
            System.out.print("User: ");
            input = kb.nextLine();


            output = edmund.parseInput(input);

            //generate output
            System.out.println("Edmund: " + output);
        }while(!(((input.equalsIgnoreCase("goodbye")) || (input.equalsIgnoreCase("good bye"))) || ((output.equalsIgnoreCase("goodbye")) || (output.equalsIgnoreCase("good bye"))))); //allow bot to say goodbye
    }

    //server version of chat bot
    private static void edmundServer()
    {
        Server.manager();
    }
}

