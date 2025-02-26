/*
################################################################
#Edmund: APLSystem.java
#Copyright © 2017-2025 Allison Munn
#FULL COPYRIGHT NOTICE IS IN README
################################################################
*/

package com.thenullplayer.ai.edmund;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import java.io.*;

public class APLSystem implements Runnable
{
    private String prompt;
    private boolean verbose;
    private static final String[] botId = {
            "83dcad851e345e3c",
            "86289ef30e345c10",
            "87ff1fbece37b79f",
            "8c3111918e34ddce",
            "8ad83d9f6e34da9f",//5
            "92a17c090e345816",
            "935a0a567e34523c",
            "94023160ee3425e0",
            "94ade513be34ddcf",
            "9750f915fe3619ae",//10
            "97fcd7450e3442d9",
            "98bb77e1ce34244a",
            "99d674d62e342605",
            "9a6afa571e37bb1d",
            "9e129a956e3548fd",//15
            "a24817d97e345c18",
            "a49104941e378378",
            "a66718a38e345c15",
            "a847934aae3456cb",
            "ab3992075e37ba81",//20
            "ac28c6669e36b194",
            "ac4a0f5c0e377942",
            "af2153a04e345c1f",
            "af875acc8e345fec",
            "b0a6a41a5e345c23",//25
            "b0dafd24ee35a477",
            "b1e9139eee362838",
            "b4fa092c8e361beb",
            "b54a7c1d7e368331",
            "b693735f8e345c22",//30
            "b8d616e35e36e881",
            "b8e200d62e3459ea",
            "bd536f6c9e344e02",
            "c07fc49ade34ddcd",
            "c39a3375ae34d985",//35
            "c406f0d66e345c27",
            "c4592e67ee345c1d",
            "c46b6e338e345aa1",
            "c867aeea4e345ad2",
            "c9c4b9bf6e345c25",//40
            "ca2d26f6ee345c1e",
            "cb6110ed0e35ba05",
            "cd44746d1e3755a1",
            "cf7aa84b0e34555c",
            "d3a87e77fe377a56",//45
            "dbf443e58e345c14",
            "dc1d07a34e345c11",
            "df9c8acfde345d97",
            "e1350fb18e345a6e",
            "e18842b12e344e02",//50
            "e365655dbe351ac7",
            "e6b3d89abe37ba83",
            "e904a1fbae34ddc1",
            "ea373c261e3458c6",
            "eeb070e74e366473",//55
            "efbf0b2b5e34242b",
            "ef90e5c1be347e01",
            "efc39100ce34d038",
            "f00247f93e342298",
            "f0962253ee345b71",//60
            "fbf7c682ee37785d",
            "f5d922d97e345aa1",
            "ffdec4afbe345f75"};

    static void learn(String promptIn,boolean verboseIn)
    {
        new Thread(new APLSystem(promptIn,verboseIn)).start();
    }

    static void learn(File fileIn,boolean verboseIn) throws IOException
    {
        if(!(fileIn.isFile()))
            return;
        BufferedReader br = new BufferedReader(new FileReader(fileIn));
        String line;
        do
        {
            line = br.readLine();
            if (line != null);
                learn(line,verboseIn);
        }while(line != null);
    }

    private APLSystem(String promptIn,boolean verboseIn)
    {
        prompt = Edmund.cleanInput(promptIn);
        verbose = verboseIn;
    }

    //wip
    public void run()
    {
        for (int rep = 0; rep < 10; rep++)
        {
            try
            {
                ChatterBotFactory factory = new ChatterBotFactory();
                ChatterBot[] bot = new ChatterBot[botId.length];
                ChatterBotSession[][] botSession = new ChatterBotSession[bot.length][bot.length];
                int numConv = ((bot.length * (bot.length - 1)) >> 1);
                String[] msg = new String[numConv];
                //String[][] msgOld = new String[2][numConv];
                boolean[] isConvAcitve = new boolean[numConv];

                for(int i = 0; i < numConv; i++)
                {
                    isConvAcitve[i] = true;
                }

                for (int i = 0; i < bot.length; i++)
                {
                    bot[i] = factory.create(ChatterBotType.PANDORABOTS, botId[i]);
                }

                for (int i = 0; i < bot.length; i++)
                {
                    for (int j = 0; j < i; j++)
                    {
                        botSession[i][j] = bot[i].createSession();
                        botSession[j][i] = bot[j].createSession();
                    }
                }

                for (int i = 0; i < msg.length; i++)
                {
                    msg[i] = prompt;
                }


                if(verbose) System.out.println("APLSystem: \"" + prompt + "\" starting");
                int turn = (int) Math.round(Math.random()) - 1;
                int conv = -1;
                boolean running = true;
                while (running)// && Main.isRunning)
                {
                    turn = (turn + 1) % 2;
                    for (int i = 0; i < bot.length; i++)
                    {
                        for (int j = 0; j < i; j++)
                        {
                            conv = (conv + 1) % msg.length;
                            try
                            {
                                if(isConvAcitve[conv])
                                {
                                    //msgOld[((turn + 1) % 2)][conv] = msg[conv];
                                    String msgOld = msg[conv];

                                    if (turn == 0)
                                    {
                                        msg[conv] = Edmund.cleanInput(botSession[i][j].think(msg[conv]));
                                        if(verbose) System.out.println("(bot:" +(i+1)+ " > bot"+(j+1)+") "+ msg[conv]);
                                    }
                                    else
                                    {
                                        msg[conv] = Edmund.cleanInput(botSession[j][i].think(msg[conv]));
                                        if(verbose) System.out.println("(bot:" +(j+1)+ " > bot"+(i+1)+") "+ msg[conv]);
                                    }


                                    //if(msg[conv].equalsIgnoreCase(msgOld[turn][conv]))
                                    //{
                                    //    isConvAcitve[conv] = false;
                                    //}

                                    String path = System.getProperty("user.dir") + File.separator + "data" + File.separator;
                                    String filename = msgOld + ".txt";//[((turn + 1) % 2)][conv]
                                    //System.out.println(path + filename);

                                    if (Counter.countWords(path + filename, msg[conv]) == 0 && filename.length() < 255)
                                    {
                                        //System.out.println("Saving: "+msg[conv]+" in "+path+filename);
                                        try
                                        {
                                            new File(path).mkdirs();
                                            FileWriter fwriter = new FileWriter((path + filename), (new File(path + filename)).isFile());//WIP
                                            PrintWriter pwriter = new PrintWriter(fwriter);
                                            pwriter.println(msg[conv]);
                                            //print to file here
                                            pwriter.close();
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                    else
                                    {
                                        isConvAcitve[conv] = false;
                                    }

                                    if (msg[conv].equalsIgnoreCase(""))
                                    {
                                        isConvAcitve[conv] = false;
                                    }

                                    Thread.sleep(msg[conv].length());
                                }
                            }
                            catch (Exception e)
                            {
                                //e.printStackTrace();
                                isConvAcitve[conv] = false;
                            }
                        }
                    }

                    running = false;
                    for(int i = 0; i < numConv; i++)
                    {
                        running = running || isConvAcitve[i];
                    }
                    if(verbose) System.out.println("(STEP)");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("APLSystem: \"" + prompt + "\" done");
    }
}
