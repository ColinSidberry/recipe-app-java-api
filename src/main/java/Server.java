import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import controllers.RecipeController;
import daos.impl.*;
import daos.interfaces.BaseRepository;
import models.impl.*;
import services.interfaces.*;
import services.impl.RecipeServiceImpl;
import services.impl.UserServiceImpl;
import services.impl.CategoryServiceImpl;
import services.impl.IngredientServiceImpl;
import services.impl.InstructionServiceImpl;
import services.impl.RecipeIngredientServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.TypeAdapter;

/**
 * Main server class for the Recipe API application.
 * This class sets up and manages the HTTP server for handling recipe-related operations.
 * It provides RESTful endpoints for recipe management using MongoDB as the backend.
 */
public class Server {
    private static final int PORT = 8080;
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
            @Override
            public void write(JsonWriter out, LocalDateTime value) throws IOException {
                out.value(value != null ? value.toString() : null);
            }

            @Override
            public LocalDateTime read(JsonReader in) throws IOException {
                String str = in.nextString();
                return str != null ? LocalDateTime.parse(str) : null;
            }
        })
        .create();
    private static RecipeController recipeController;

    /**
     * Main entry point for the application.
     * Sets up the MongoDB connection, initializes the recipe controller,
     * and starts the HTTP server to listen for incoming requests.
     *
     * @param args Command line arguments (not used)
     * @throws IOException If there's an error setting up the server or database
     */
    public static void main(String[] args) throws IOException {
        // Setup MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("recipedb");

        // Setup repositories
        MongoRecipeRepositoryImpl recipeRepo = new MongoRecipeRepositoryImpl(database);
        MongoInstructionRepositoryImpl instructionRepo = new MongoInstructionRepositoryImpl(database);
        MongoRecipeIngredientRepositoryImpl recipeIngredientRepo = new MongoRecipeIngredientRepositoryImpl(database);
        BaseRepository<UserModelImpl> userRepo = new MongoUserRepositoryImpl(database);
        BaseRepository<CategoryModelImpl> categoryRepo = new MongoCategoryRepositoryImpl(database);
        BaseRepository<IngredientModelImpl> ingredientRepo = new MongoIngredientRepositoryImpl(database);

        // Setup services
        UserService userService = new UserServiceImpl(userRepo);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepo);
        IngredientService ingredientService = new IngredientServiceImpl(ingredientRepo);

        // Setup services
        BaseService<RecipeModelImpl> recipeService = new RecipeServiceImpl(recipeRepo, userService, categoryService, ingredientService);
        InstructionService instructionService = new InstructionServiceImpl(instructionRepo);
        RecipeIngredientService recipeIngredientService = new RecipeIngredientServiceImpl(recipeIngredientRepo);

        // Setup controllers
        recipeController = new RecipeController(recipeService, instructionService, recipeIngredientService);

        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Setup endpoints
        server.createContext("/api/recipes", new RecipeHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("Server started on port " + PORT);
    }

    /**
     * HTTP handler for recipe-related endpoints.
     * This class implements the HttpHandler interface to handle HTTP requests
     * for recipe operations, including GET and POST methods.
     */
    static class RecipeHandler implements HttpHandler {
        @Override
        /**
         * Handles incoming HTTP requests for recipe operations.
         * Supports GET (retrieve all recipes) and POST (create new recipe) methods.
         * 
         * @param exchange The HTTP exchange containing the request and response
         * @throws IOException If there's an error processing the request
         */
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            int statusCode = 200;

            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        List<RecipeModelImpl> recipes = recipeController.getAllRecipes();
                        response = gson.toJson(recipes);
                        break;

                    case "POST":
                        // Read the request body
                        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                             BufferedReader br = new BufferedReader(isr)) {
                            String requestBody = br.lines()
                                .collect(Collectors.joining("\n"));
                            
                            // Parse JSON to Recipe object
                            RecipeModelImpl recipe = gson.fromJson(requestBody, RecipeModelImpl.class);
                            
                            // Create recipe
                            RecipeModelImpl createdRecipe = recipeController.createRecipe(recipe);
                            response = gson.toJson(createdRecipe);
                            statusCode = 201;
                        } catch (IOException e) {
                            response = "Error reading request body: " + e.getMessage();
                            statusCode = 500;
                        }
                        break;

                    default:
                        statusCode = 405; // Method Not Allowed
                        response = "Method not allowed";
                }
            } catch (Exception e) {
                statusCode = 500;
                response = "Internal Server Error: " + e.getMessage();
            }

            // Set response headers
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);

            // Write response
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }}
