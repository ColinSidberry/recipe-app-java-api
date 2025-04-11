package config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;

public class MongoConfig {
  private static final Dotenv dotenv = Dotenv.load();

  public static MongoDatabase getDatabase() {
    String uri = dotenv.get("MONGO_URI");
    String dbName = dotenv.get("MONGO_DB_NAME");

    try { // NOTE: automatically call .close -> AutoCloseable was extended
      MongoClient client = MongoClients.create(uri);
      MongoDatabase db = client.getDatabase(dbName);
      System.out.println("✅ Successfully connected to Database: " + dbName);
      return db;
    } catch (Exception e) {
      System.out.println("Unable to create db connection. Error: " + e.getMessage());
      return null;
    }
  }
}
