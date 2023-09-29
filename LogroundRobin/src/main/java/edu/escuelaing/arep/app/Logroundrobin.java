package edu.escuelaing.arep.app;

import static spark.Spark.*;

import java.io.IOException;

public class Logroundrobin {

    public static void main(String... args) {
        port(getPort());

        staticFiles.location("/public");
        get("/log",(req, res) -> {
            String val = req.queryParams("value");
            return logMessage(val);
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }


    private static String logMessage(String val) throws IOException{
        return HttpRemoteCaller.remoteLogCall(val);
    }


}