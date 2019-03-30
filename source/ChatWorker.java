import java.net.*;
import java.io.*;

public class ChatWorker implements Runnable
{

    Socket clientSocket;
    BufferedReader inputStream;
    PrintWriter outputStream;
    Edmund edmund;
    

    public ChatWorker(Socket socket)
    {
        clientSocket = socket;
        edmund = new Edmund();
        try
        {
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
        }
        catch(IOException ie)
        {
            ie.printStackTrace();
            Server.isRunning = false;
        }
    }

    public void run()
    {
        String data = null;
        do
        {
            try
            {
                data = inputStream.readLine();
            }
            catch(IOException ie)
            {
                ie.printStackTrace();
                Server.isRunning = false;
            }

            if(!(data == null))
            {
                System.out.println(data);
                outputStream.println(data);
                String output = edmund.parseInput(data);
                System.out.println(output);
                outputStream.println(output);
            }
        }while((!(data == null)) && Server.isRunning);

        //close socket here
        try
        {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}