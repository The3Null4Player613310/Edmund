package com.thenullplayer.ai.edmund;
import java.util.*;
import java.io.*;
class Edmund
{
    private final ArrayList<String> queryArray = new ArrayList<String>(0);
    private final ArrayList<String> responseArray = new ArrayList<String>(0);
    private final int MAXMEM = 3;
    private String output = "";

    //set up edmund with preset prompt
    public Edmund(String prompt)
    {
        output = prompt;
        responseArray.add(output);
    }

    //parse input from user
    String parseInput(String input)
    {
        //clean input
        input = cleanInput(input);

        //fix for empty strings so they dont break his memory
        if(input.equalsIgnoreCase(""))
            return input; 

        //process input
        queryArray.add(input);

        //clean memory
        while(responseArray.size()>MAXMEM)
        {
            responseArray.remove(0);
            queryArray.remove(0);   
        }

        //store input
        storeInput(responseArray,queryArray);

        //retrieve output
        output = retrieveOutput(responseArray,queryArray);

        //fallback
        if(output.equalsIgnoreCase(""))
        {
            output = input;
            APLSystem.learn(input,false);
        }

        //process output
        responseArray.add(output);

        return output;
    }

    //parse input from user
    /*
    String parseCommand(String input,String output)
    {
        //clean input
        input = cleanInput(input);

        //fix for empty strings so they dont break his memory
        if(input.equalsIgnoreCase(""))
            return input; 

        //process input
        queryArray.add(input);

        //clean memory
        while(responseArray.size()>MAXMEM)
        {
            responseArray.remove(0);
            queryArray.remove(0);   
        }

        //store input
        storeInput(responseArray,queryArray);

        //retrieve output
        output = retrieveOutput(responseArray,queryArray);

        //fallback
        if(output.equalsIgnoreCase(""))
            output=input;

        //process output
        responseArray.add(output);

        return output;
    }
    */

    //clean input from user
    static String cleanInput(String input)
    {
        //clean input
        input = input.toLowerCase();
        input = input.replace(" + "," plus ");
        input = input.replace(" - "," minus ");
        input = input.replace(" * "," times ");
        input = input.replace(" / "," divided by ");
        String tempinput = "";
        for(int i=0;i<input.length();i++)
        {
            char curChar = input.charAt(i);
            if((97 <= curChar)&&(curChar <= 122)) //letters
            {
                tempinput += curChar; 
            }
            else if((48 <= curChar)&&(curChar <= 57)) //numbers
            {
                tempinput += curChar; 
            }
            else if(curChar == 32) //space
            {
                tempinput += curChar; 
            }
        }

        //remove extra spaces
        do
        {
            tempinput = tempinput.replaceAll("  "," ");
        }while(tempinput.contains("  "));
        input = tempinput.trim();
        return input;
    }

    //store input from user in memory banks
    private static void storeInput(ArrayList<String> responseArray,ArrayList<String> queryArray)
    {
        //store input
        for(int i=0; i<(responseArray.size()+queryArray.size()-1); i++) //make dependent on const
        {
            String path = System.getProperty("user.dir") + File.separator + "data";
            for(int j=(i); j<((responseArray.size()+queryArray.size())-2); j++)
            {
                if(j%2==0) //WIP
                    path = path + File.separator + responseArray.get(j/2); //j is not right val
                else
                    path = path + File.separator + queryArray.get(j/2);  
            }
            path += File.separator;
            String filename = (responseArray.get((responseArray.size()-1)) + ".txt");
            //System.out.println(path + filename);

            try
            {
                new File(path).mkdirs();
                FileWriter fwriter = new FileWriter((path+filename),(new File(path+filename)).isFile());//WIP
                PrintWriter pwriter = new PrintWriter(fwriter);
                pwriter.println(queryArray.get((queryArray.size()-1)));
                //print to file here
                pwriter.close();
            }
            catch(IOException e) 
            {
                e.printStackTrace();
            }

            //STORE query IN path //such as that it is added to eof
        }
        //System.out.println("inEND");
    }

    //retrieve output from memory banks
    private static String retrieveOutput(ArrayList<String> responseArray,ArrayList<String> queryArray)
    {
        //retrieve output //swap input with output
        String output="";
        int depth=(responseArray.size()+queryArray.size());
        for(int i=0; i<(depth); i++)//WIP
        {
            String path = System.getProperty("user.dir") + File.separator + "data";
            for(int j=(i); j<((depth)-1); j++)//WIP
            {
                if(j%2==0) //WIP
                    path = path + File.separator + responseArray.get(j/2); //j is not right val //works though?
                else
                    path = path + File.separator + queryArray.get(j/2);  
            }
            path += File.separator;
            String filename = (queryArray.get((queryArray.size()-1)) + ".txt");
            //System.out.println(path + filename);

            File inFile = new File(path+filename);
            if(inFile.isFile()) //check if file exists
            {
                try
                {
                    int selLine = (new Random()).nextInt(Counter.countLines(path + filename)); //assign random number here
                    int curLine = 0;
                    Scanner fileReader = new Scanner(inFile);

                    while (fileReader.hasNextLine()) 
                    {
                        if(curLine == selLine)
                        {
                            //System.out.println(selLine);
                            //System.out.println(curLine);
                            //System.out.println(path+filename);
                            output = fileReader.nextLine();
                            break;
                        }
                        else
                        {
                            curLine++;
                            fileReader.nextLine();
                        }
                    }
                    fileReader.close();
                    break;
                }
                catch(IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }
}