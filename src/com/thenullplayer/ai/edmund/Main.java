/*
################################################################
#Edmund: Main.java
#Copyright © 2017-2025 Allison Munn
#FULL COPYRIGHT NOTICE IS IN README
################################################################
*/

package com.thenullplayer.ai.edmund;

import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {

    //global variables
    private static final Scanner kb = new Scanner(System.in);
    static volatile boolean isRunning = true;
    static volatile boolean isVerbose = false;

    //main method
    public static void main(String[] args)
    {

        //modifiers
        for(int i=0;i<args.length;i++)
        {
            if (args[i].equalsIgnoreCase("-v"))
                isVerbose = true;
        }

        //functions
        for(int i=0;i<args.length;i++)
        {
            if(args[i].equalsIgnoreCase("-G"))
                GUI.start();
            if(args[i].equalsIgnoreCase("-S"))
                TCPServer.manager();
            if(args[i].equalsIgnoreCase("-C"))
                Cleaner.clean();
            if(args[i].equalsIgnoreCase("-M"))
                Grapher.graph();
            if(args[i].equalsIgnoreCase("-L") && (i<(args.length-1)))
            {
                try
                {
                    File file = new File(args[i + 1]);
                    //APLSystem.learn(file,isVerbose); //DEPRECATED
                    return;
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        System.out.print("\f");

        String prompt = "hello";
        Edmund edmund = new Edmund(prompt);
        String input;
        String output = prompt;

        //generate output
        System.out.println("Edmund: " + output);
        //Voice.speak(output); //DEPRECATED

        do
        {
            //get input
            System.out.print("User: ");
            input = kb.nextLine();
            input = input.trim();

            //check for commands
            if((input.length()>0) && input.charAt(0)=='!')
            {
                switch(input.substring(1,input.length()))
                {
                    case "learn":
                        System.out.println("System: please enter a prompt");
                        System.out.print("User: ");
                        //APLSystem.learn(kb.nextLine(),isVerbose); //DEPRECATED
                        System.out.println("System: fetching data");
                        break;
                    case "speech":
                        //Voice.manager(); //DEPRECATED
                        if(Voice.isRunning)
                            System.out.println("System: speech enabled");
                        else
                            System.out.println("System: speech disabled");
                        break;
                    case "server":
                        TCPServer.manager();
                        if(TCPServer.isRunning)
                            System.out.println("System: server enabled");
                        else
                            System.out.println("System: server disabled");
                        break;
                    default:
                        System.out.println("System: invalid command");
                }
            }
            else
            {
                output = edmund.parseInput(input);

                //generate output
                System.out.println("Edmund: " + output);
                Voice.speak(output);
            }
        }
        while (!((input.equalsIgnoreCase("goodbye") || input.equalsIgnoreCase("good bye") || input.equalsIgnoreCase("bye"))
                || (output.equalsIgnoreCase("goodbye") || output.equalsIgnoreCase("good bye") || output.equalsIgnoreCase("bye")))); //allow bot to say goodbye
        TCPServer.isRunning = false;
        isRunning = false;

    }
}
