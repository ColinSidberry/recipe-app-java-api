package config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Configuration class for MongoDB database connection.
 * This class provides a centralized way to connect to the MongoDB database
 * using environment variables for configuration.
 */
public class MongoConfig {
  private static final Dotenv dotenv = Dotenv.load();

  /**
   * Retrieves a connection to the MongoDB database.
   * This method reads connection details from environment variables and
   * establishes a connection to the MongoDB database.
   *
   * @return A MongoDatabase instance connected to the configured database,
   *         or null if the connection fails
   */
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
