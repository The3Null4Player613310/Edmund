package com.thenullplayer.ai.edmund;

import java.io.File;

public class Grapher
{
    public static void graph(){
        String path = System.getProperty("user.dir") + File.separator + "data";
        File data = new File(path);
        File[] files = data.listFiles();
        for (File file : files)
        {
            System.out.println(file);
        }
    }
}
