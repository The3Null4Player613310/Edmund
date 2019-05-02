package com.thenullplayer.ai.edmund;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class Voice
{
    public static boolean isRunning = false;

    static void manager()
    {
        isRunning = !isRunning;
    }

    static void speak(String string)
    {
        if(!isRunning)
            return;

        string = string.replaceAll("'", "");
        string = string.replaceAll("[^a-zA-Z]+", " ");
        string = string.toLowerCase();
        string = string.trim();

        String[] array = string.split(" ");
        for (String word : array)
        {
            say(word);
            try
            {
                Thread.sleep(0);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static void say(String word)
    {
        try
        {
            AudioInputStream in = null;
            int attempt = 0;
            do
            {
                URL url = getURL(word);
                try
                {
                    in = AudioSystem.getAudioInputStream(url);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                attempt++;
            }while((in==null) && (attempt <= 3));

            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);
            // Play now.
            rawplay(decodedFormat, din);
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //Handle exception.
        }
    }

    private static void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException, LineUnavailableException
    {
        byte[] data = new byte[4096];
        SourceDataLine line = getLine(targetFormat);
        if (line != null)
        {
            // Start
            line.start();
            int nBytesRead = 0, nBytesWritten = 0;
            while (nBytesRead != -1)
            {
                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
            }
            // Stop
            line.drain();
            line.stop();
            line.close();
            din.close();
        }
    }

    private static SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
    {
        SourceDataLine res;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }

    private static URL getURL(String word)
    {
        int choice = ((int) Math.floor(Math.random() * 7));
        choice=0; //wip
        //System.err.println(choice);
        URL url = null;
        String wordf;
        String word1;
        String word3;
        String word5;
        try
        {
            switch (choice)
            {
                case 0:
                    wordf = word;
                    url = new URL("http://mary.dfki.de:59125/process?INPUT_TYPE=TEXT&OUTPUT_TYPE=AUDIO&INPUT_TEXT=" + wordf + "&LOCALE=en_GB&AUDIO=WAVE_FILE");
                    break;
                case 1:
                    wordf = word + "--_us_1";
                    url = new URL("http://ssl.gstatic.com/dictionary/static/sounds/20160317/" + wordf + ".mp3");
                    break;
                case 2:
                    wordf = word;
                    url = new URL("http://www.howjsay.com/mp3/" + wordf + ".mp3");
                    break;
                case 3:
                    wordf = "en_us_" + word;
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("https://www.collinsdictionary.com/sounds/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
                case 4:
                    wordf = word;
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("http://dictionary.cambridge.org/media/english/us_pron/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
                case 5:
                    wordf = word;
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("http://dictionary.cambridge.org/media/english/uk_pron/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
                case 6:
                    wordf = word.toUpperCase() + "_British_English_pronunciation";
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("http://www.macmillandictionary.com/media/british/uk_pron/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }
}