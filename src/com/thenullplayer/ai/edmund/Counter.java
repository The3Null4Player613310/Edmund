package com.thenullplayer.ai.edmund;
import java.io.*;
class Counter
{
    public static int countLines(String filename) throws IOException
    {   
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try 
        {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) 
            {
                empty = false;
                for (int i = 0; i < readChars; ++i) 
                {
                    if (c[i] == '\n') 
                    {
                        ++count;
                    }
                }
                for(int i=0;i<c.length;i++)//wip
                {
                    c[i] = -1;
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public static int countWords(String filename,String input) throws IOException
    {
        input = Edmund.cleanInput(input);
        if(!((new File(filename)).isFile()))
            return 0;
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try
        {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            while ((readChars = is.read(c)) != -1)
            {
                int level = 0;
                for (int i = 0; i < readChars; ++i)
                {
                    if(level<input.length())
                    {
                        if (c[i] == input.charAt(level))
                        {
                            ++level;
                        }
                        else
                        {
                            level=0;
                        }
                    }
                    else if((c[i]=='\r')||(c[i]=='\n')||c[i]==-1)
                    {
                        ++count;
                        level=0;
                    }
                    else
                    {
                        level=0;
                    }
                }
                for(int i=0;i<c.length;i++)
                {
                    c[i] = -1;
                }
            }
            return count;
        } finally {
            is.close();
        }
    }
}