package edu.escuelaing.arep.app;

/**
 * Hello world!
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRemoteCaller {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String[] LOG_SERVERS = new String[]{
        "https://localhost:4568","https://localhost:4569", "https://localhost:4570"
    };

    private static int currentServer = 0;

    public static String remoteLogCall(String message) throws IOException{
        return remoteHttpCall(LOG_SERVERS[currentServer] + "/log?value?=" + message);
    }

    public static String remoteHttpCall(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
            return "404 error";
        }
        System.out.println("GET DONE");
        rotateRoundrobinServer();
        return response.toString();
    }

    public static void rotateRoundrobinServer(){
        currentServer = (currentServer + 1) % 3;
    }

} 