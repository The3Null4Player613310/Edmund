package com.thenullplayer.ai.edmund;

import java.io.File;

public class Cleaner
{
    public static void clean()
    {
        String path = System.getProperty("user.dir") + File.separator + "data";
        File data = new File(path);
        File[] files = data.listFiles();
        for (File file : files)
        {
            System.out.println(file);
        }
    }
}
