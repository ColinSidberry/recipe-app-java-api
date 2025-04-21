package daos.helpers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class TestMongoRepositoryHelper {
    private static final String TEST_DATABASE = "test_db";
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase(TEST_DATABASE);
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void clearCollection(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.deleteMany(new Document());
    }

    public static void dropCollection(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.drop();
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
