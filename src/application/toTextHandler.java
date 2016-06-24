package application;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class toTextHandler{
	@SuppressWarnings("deprecation")
	public static void sendPOST(){
		String URLPath = "https://stream.watsonplatform.net/speech-to-text/api/v1/recognize?continuous=true";
		String FilePath = "/Users/Alan/Desktop/MakeMoveDestroy-flac.flac";
        HttpURLConnection conn = null;
        DataInputStream inStream = null;

        try{
            //------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(FilePath));
            // open a URL connection to the Servlet 
            URL url = new URL(URLPath);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty ("Authorization", "Basic ZGRhOWFhZTUtOGNiMy00NDBkLTk5MTUtNjk5ZTU4Mjg3N2RlOnNBS3pZa2FTNGFPeQ==");
            conn.setRequestProperty("Content-Type", "audio/flac");
            conn.setRequestProperty("Transfer-Encoding", "chunked");
            
            /*Write the audio on bufferI/O*/
            File file = new File(FilePath);
            byte[] buffer = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(buffer);
            fis.close();
            OutputStream os = conn.getOutputStream();
            os.write(buffer);
            os.close();
            
            
         fileInputStream.close();

         //System.out.println(FilePath + " uploaded to from " + URLPath);
        }catch (MalformedURLException ex){
            System.out.println(" CLIENT REQUEST:"+ex);
        }catch (IOException ioe){
            System.out.println(" CLIENT REQUEST:"+ioe);
        }
        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream ( conn.getInputStream() );
            String str;
            while (( str = inStream.readLine()) != null){
                System.out.println("Server response is: " + str);
                //System.out.println("");
            }
            inStream.close();
        }catch (IOException ioex){
            System.out.println("From (ServerResponse): " + ioex);
        }
  }
}