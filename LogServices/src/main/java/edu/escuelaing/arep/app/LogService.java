package edu.escuelaing.arep.app;

import static spark.Spark.*;

public class LogService {

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
        return 4568;
    }

    private static String logMessage(String val){
        return """
                {
                    "m1" : "mensaje1"
                    "m2" : "mensaje2"
                    "m3" : "mensaje3"
                }
                """;

    }


}