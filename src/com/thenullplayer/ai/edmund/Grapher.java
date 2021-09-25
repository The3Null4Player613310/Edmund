package com.thenullplayer.ai.edmund;

import java.io.*;
import java.util.Scanner;

public class Grapher
{
    public static void graph()
    {

        File fileGraph = new File(System.getProperty("user.dir") + File.separator + "graph.dot");
        try
        {

            System.out.println("Grapher: starting");

            if(fileGraph.isFile())
            {
                fileGraph.delete();
                fileGraph.createNewFile();
            }

            FileWriter graphFileWriter = new FileWriter(fileGraph, false);
            PrintWriter graphPrintWriter = new PrintWriter(graphFileWriter);

            //start the graph
            graphPrintWriter.println("digraph {");

            String path = System.getProperty("user.dir") + File.separator + "data";
            File data = new File(path);
            File[] files = data.listFiles();
            for(File file : files)
            {
                //System.out.println(file);
                try
                {
                    if(file.isFile())
                    {
                        String fileName = file.getName();
                        String query = fileName.substring(0,fileName.length()-4);

                        System.out.println("Grapher: \"" + query + "\" starting");

                        Scanner fileReader = new Scanner(file);

                        while(fileReader.hasNextLine())
                        {
                            graphPrintWriter.println("\"" + query + "\" -> \"" + fileReader.nextLine() + "\";");
                        }

                        fileReader.close();

                        System.out.println("Grapher: \"" + query + "\" done");

                    }

                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }

            graphPrintWriter.println("}");

            graphPrintWriter.close();

            System.out.println("Grapher: done");

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
