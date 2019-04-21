package com.thenullplayer.ai.edmund;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class Voice
{
    static void speak(String string)
    {
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
            do
            {
                URL url = getURL(word);
                try
                {
                    in = AudioSystem.getAudioInputStream(url);
                }
                catch(Exception e)
                {
                    //e.printStackTrace();
                }
            }while(in==null);

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
            //e.printStackTrace();
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
        int choice = ((int) Math.floor(Math.random() * 92));
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
                    wordf = word + "--_us_1";
                    url = new URL("http://ssl.gstatic.com/dictionary/static/sounds/20160317/" + wordf + ".mp3");
                    break;
                case 1:
                    wordf = word;
                    url = new URL("http://www.howjsay.com/mp3/" + wordf + ".mp3");
                    break;
                case 2:
                    wordf = "en_us_" + word;
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("https://www.collinsdictionary.com/sounds/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
                case 3:
                    wordf = word;
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("http://dictionary.cambridge.org/media/english/us_pron/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
                case 4:
                    wordf = word;
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("http://dictionary.cambridge.org/media/english/uk_pron/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
                case 5:
                    wordf = word + "_British_English_pronunciation";
                    word1 = (wordf.length() >= 1) ? wordf.substring(0, 1) : wordf.substring(0, wordf.length());
                    word3 = (wordf.length() >= 3) ? wordf.substring(0, 3) : wordf.substring(0, wordf.length());
                    word5 = (wordf.length() >= 5) ? wordf.substring(0, 5) : wordf.substring(0, wordf.length());
                    url = new URL("http://www.macmillandictionary.com/media/british/uk_pron/" + word1 + "/" + word3 + "/" + word5 + "/" + wordf + ".mp3");
                    break;
                case 6:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Ivy/" + wordf);
                    break;
                case 7:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/ca-es/" + wordf);
                    break;
                case 8:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/zh-cn/" + wordf);
                    break;
                case 9:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/zh-hk/" + wordf);
                    break;
                case 10:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/zh-tw/" + wordf);
                    break;
                case 11:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Mads/" + wordf);
                    break;
                case 12:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Naja/" + wordf);
                    break;
                case 13:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/da-dk/" + wordf);
                    break;
                case 14:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Lotte/" + wordf);
                    break;
                case 15:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Ruben/" + wordf);
                    break;
                case 16:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/nl-nl/" + wordf);
                    break;
                case 17:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Nicole/" + wordf);
                    break;
                case 18:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Russell/" + wordf);
                    break;
                case 19:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-au/" + wordf);
                    break;
                case 20:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-ca/" + wordf);
                    break;
                case 21:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Amy/" + wordf);
                    break;
                case 22:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Brian/" + wordf);
                    break;
                case 23:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Emma/" + wordf);
                    break;
                case 24:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-gb/" + wordf);
                    break;
                case 25:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-GB_KateVoice/" + wordf);
                    break;
                case 26:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Raveena/" + wordf);
                    break;
                case 27:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-in/" + wordf);
                    break;
                case 28:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Joey/" + wordf);
                    break;
                case 29:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Justin/" + wordf);
                    break;
                case 30:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Kendra/" + wordf);
                    break;
                case 31:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Kimberly/" + wordf);
                    break;
                case 32:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Salli/" + wordf);
                    break;
                case 33:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Joanna/" + wordf);
                    break;
                case 34:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-us/" + wordf);
                    break;
                case 35:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-US_AllisonVoice/" + wordf);
                    break;
                case 36:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-US_LisaVoice/" + wordf);
                    break;
                case 37:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/en-US_MichaelVoice/" + wordf);
                    break;
                case 38:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Geraint/" + wordf);
                    break;
                case 39:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/fi-fi/" + wordf);
                    break;
                case 40:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Celine/" + wordf);
                    break;
                case 41:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Mathieu/" + wordf);
                    break;
                case 42:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/fr-fr/" + wordf);
                    break;
                case 43:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/fr-FR_ReneeVoice/" + wordf);
                    break;
                case 44:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Chantal/" + wordf);
                    break;
                case 45:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/fr-ca/" + wordf);
                    break;
                case 46:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Marlene/" + wordf);
                    break;
                case 47:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Hans/" + wordf);
                    break;
                case 48:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/de-de/" + wordf);
                    break;
                case 49:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/de-DE_BirgitVoice/" + wordf);
                    break;
                case 50:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/de-DE_DieterVoice/" + wordf);
                    break;
                case 51:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Dora/" + wordf);
                    break;
                case 52:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Karl/" + wordf);
                    break;
                case 53:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Carla/" + wordf);
                    break;
                case 54:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Giorgio/" + wordf);
                    break;
                case 55:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/it-it/" + wordf);
                    break;
                case 56:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/it-IT_FrancescaVoice/" + wordf);
                    break;
                case 57:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Mizuki/" + wordf);
                    break;
                case 58:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/ja-jp/" + wordf);
                    break;
                case 59:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/ja-JP_EmiVoice/" + wordf);
                    break;
                case 60:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/ko-kr/" + wordf);
                    break;
                case 61:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Liv/" + wordf);
                    break;
                case 62:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/nb-no/" + wordf);
                    break;
                case 63:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Jacek/" + wordf);
                    break;
                case 64:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Ewa/" + wordf);
                    break;
                case 65:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Jan/" + wordf);
                    break;
                case 66:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Maja/" + wordf);
                    break;
                case 67:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/pl-pl/" + wordf);
                    break;
                case 68:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Ricardo/" + wordf);
                    break;
                case 69:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Vitoria/" + wordf);
                    break;
                case 70:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/pt-br/" + wordf);
                    break;
                case 71:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/pt-BR_IsabelaVoice/" + wordf);
                    break;
                case 72:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Cristiano/" + wordf);
                    break;
                case 73:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Ines/" + wordf);
                    break;
                case 74:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/pt-pt/" + wordf);
                    break;
                case 75:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Tatyana/" + wordf);
                    break;
                case 76:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Maxim/" + wordf);
                    break;
                case 77:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/ru-ru/" + wordf);
                    break;
                case 78:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Carmen/" + wordf);
                    break;
                case 79:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Miguel/" + wordf);
                    break;
                case 80:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Penelope/" + wordf);
                    break;
                case 81:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Enrique/" + wordf);
                    break;
                case 82:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Conchita/" + wordf);
                    break;
                case 83:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/es-es/" + wordf);
                    break;
                case 84:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/es-ES_EnriqueVoice/" + wordf);
                    break;
                case 85:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/es-ES_LauraVoice/" + wordf);
                    break;
                case 86:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/es-US_SofiaVoice/" + wordf);
                    break;
                case 87:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/es-mx/" + wordf);
                    break;
                case 88:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Astrid/" + wordf);
                    break;
                case 89:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/sv-se/" + wordf);
                    break;
                case 90:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Filiz/" + wordf);
                    break;
                case 91:
                    wordf = word;
                    url = new URL("https://pkaudionew.herokuapp.com/Gwyneth/" + wordf);
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