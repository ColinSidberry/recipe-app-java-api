package daos.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import daos.interfaces.InstructionRepository;
import models.impl.InstructionModelImpl;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB implementation of the Instruction repository.
 * This class provides CRUD operations for Instruction entities using MongoDB as the backend.
 * It includes additional methods for querying instructions by recipe ID.
 */
public class MongoInstructionRepositoryImpl implements InstructionRepository {
    private final MongoCollection<Document> collection;

    /**
     * Creates a new instance of the instruction repository.
     * 
     * @param database The MongoDB database instance to use for operations
     */
    public MongoInstructionRepositoryImpl(MongoDatabase database) {
        this.collection = database.getCollection("instructions");
    }

    /**
     * Saves a new instruction to the database.
     * If the instruction already has an ID, it will be updated instead of creating a new record.
     * 
     * @param instruction The instruction to save
     * @return The saved instruction with its ID set
     */
    public InstructionModelImpl save(InstructionModelImpl instruction) {
        Document doc = new Document("recipeId", instruction.getRecipeId())
                .append("stepNumber", instruction.getStepNumber())
                .append("description", instruction.getDescription());

        collection.insertOne(doc);
        instruction.setId(doc.getObjectId("_id").toHexString());
        return instruction;
    }

    /**
     * Retrieves an instruction by its ID.
     * 
     * @param id The ID of the instruction to retrieve
     * @return An Optional containing the instruction if found, or empty if not found
     */
    public Optional<InstructionModelImpl> findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return Optional.empty();

        InstructionModelImpl instruction = new InstructionModelImpl();
        instruction.setId(doc.getObjectId("_id").toHexString());
        instruction.setRecipeId(doc.getString("recipeId"));
        instruction.setStepNumber(doc.getInteger("stepNumber"));
        instruction.setDescription(doc.getString("description"));
        return Optional.of(instruction);
    }

    /**
     * Retrieves all instructions from the database.
     * 
     * @return A list of all instructions in the database
     */
    public List<InstructionModelImpl> findAll() {
        List<InstructionModelImpl> instructions = new ArrayList<>();
        for (Document doc : collection.find()) {
            InstructionModelImpl instruction = new InstructionModelImpl();
            instruction.setId(doc.getObjectId("_id").toHexString());
            instruction.setRecipeId(doc.getString("recipeId"));
            instruction.setStepNumber(doc.getInteger("stepNumber"));
            instruction.setDescription(doc.getString("description"));
            instructions.add(instruction);
        }
        return instructions;
    }

    /**
     * Retrieves all instructions for a specific recipe.
     * 
     * @param recipeId The ID of the recipe to retrieve instructions for
     * @return A list of instructions for the specified recipe
     */
    public List<InstructionModelImpl> findByRecipeId(String recipeId) {
        List<InstructionModelImpl> instructions = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("recipeId", recipeId))) {
            InstructionModelImpl instruction = new InstructionModelImpl();
            instruction.setId(doc.getObjectId("_id").toHexString());
            instruction.setRecipeId(recipeId);
            instruction.setStepNumber(doc.getInteger("stepNumber"));
            instruction.setDescription(doc.getString("description"));
            instructions.add(instruction);
        }
        return instructions;
    }

    /**
     * Deletes an instruction by its ID.
     * 
     * @param id The ID of the instruction to delete
     */
    public void delete(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    /**
     * Deletes all instructions for a specific recipe.
     * 
     * @param recipeId The ID of the recipe whose instructions to delete
     */
    public void deleteByRecipeId(String recipeId) {
        collection.deleteMany(Filters.eq("recipeId", recipeId));
    }
}
