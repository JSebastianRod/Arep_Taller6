package edu.escuelaing.arep.app;

import static spark.Spark.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

public class LogService {

    public static void main(String... args) {
        port(getPort());

        staticFiles.location("/public");
        get("/log", (req, res) -> {
            String val = req.queryParams("value");
            return getLogMessage(val);
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
    // Juan Pablo Daza Pinzon me ayudo con estos metodos
    private static String getLogMessage(String value) {
        saveValue(value);
        Gson gson = new Gson();
        return gson.toJson(getDBValues());
    }

    private static List<Document> getDBValues() {
        MongoClient mongoClient = MongoClients.create("mongodb://db:27017");
        MongoDatabase database = mongoClient.getDatabase("arep-logs");
        MongoCollection<Document> collection = database.getCollection("logs");
        List<Document> documents = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().limit(10).sort(Sorts.descending("date")).iterator()) {
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }
        } catch (Exception e) {
            System.out.println("Unable to reach data");
        }

        mongoClient.close();
        return documents;
    }

    private static void saveValue(String value) {
        MongoClient mongoClient = MongoClients.create("mongodb://db:27017");
        MongoDatabase database = mongoClient.getDatabase("arep-logs");
        MongoCollection<Document> collection = database.getCollection("logs");

        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        Document document = new Document("string", value).append("date", currentDate);

        collection.insertOne(document);
        mongoClient.close();
    }

}