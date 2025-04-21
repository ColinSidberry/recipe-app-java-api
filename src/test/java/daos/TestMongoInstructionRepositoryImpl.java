package daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.InstructionModelImpl;
import daos.helpers.TestMongoRepositoryHelper;
import daos.impl.MongoInstructionRepositoryImpl;
import daos.interfaces.InstructionRepository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestMongoInstructionRepositoryImpl {
    private InstructionRepository mongoRepo;

    @BeforeEach
    void setup() {
        mongoRepo = new MongoInstructionRepositoryImpl(TestMongoRepositoryHelper.getDatabase());
        TestMongoRepositoryHelper.clearCollection("instructions");
    }

    @AfterEach
    void cleanup() {
        TestMongoRepositoryHelper.clearCollection("instructions");
    }

    @Test
    void testSave() {
        InstructionModelImpl instruction = new InstructionModelImpl();
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction");

        InstructionModelImpl saved = mongoRepo.save(instruction);

        assertNotNull(saved.getId());
        assertEquals(1, saved.getStepNumber());
        assertEquals("Test instruction", saved.getDescription());
    }

    @Test
    void testFindById_found() {
        InstructionModelImpl instruction = new InstructionModelImpl();
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction");
        InstructionModelImpl saved = mongoRepo.save(instruction);

        Optional<InstructionModelImpl> result = mongoRepo.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getStepNumber());
        assertEquals("Test instruction", result.get().getDescription());
    }

    @Test
    void testFindById_notFound() {
        // Generate a random ObjectId that doesn't exist in the database
        String nonExistentId = new ObjectId().toString();
        Optional<InstructionModelImpl> result = mongoRepo.findById(nonExistentId);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        InstructionModelImpl instruction1 = new InstructionModelImpl();
        instruction1.setRecipeId("recipe-1");
        instruction1.setStepNumber(1);
        instruction1.setDescription("Instruction 1");
        mongoRepo.save(instruction1);

        InstructionModelImpl instruction2 = new InstructionModelImpl();
        instruction2.setRecipeId("recipe-2");
        instruction2.setStepNumber(1);
        instruction2.setDescription("Instruction 2");
        mongoRepo.save(instruction2);

        List<InstructionModelImpl> instructions = mongoRepo.findAll();
        assertEquals(2, instructions.size());
    }

    @Test
    void testDelete() {
        InstructionModelImpl instruction = new InstructionModelImpl();
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction");
        InstructionModelImpl saved = mongoRepo.save(instruction);

        mongoRepo.delete(saved.getId());

        Optional<InstructionModelImpl> result = mongoRepo.findById(saved.getId());
        assertFalse(result.isPresent());
    }

    @Test
    void testGetByRecipeId() {
        InstructionModelImpl instruction1 = new InstructionModelImpl();
        instruction1.setRecipeId("test-recipe");
        instruction1.setStepNumber(1);
        instruction1.setDescription("Instruction 1");
        mongoRepo.save(instruction1);

        InstructionModelImpl instruction2 = new InstructionModelImpl();
        instruction2.setRecipeId("test-recipe");
        instruction2.setStepNumber(2);
        instruction2.setDescription("Instruction 2");
        mongoRepo.save(instruction2);

        List<InstructionModelImpl> instructions = mongoRepo.findByRecipeId("test-recipe");
        assertEquals(2, instructions.size());
        assertEquals("Instruction 1", instructions.get(0).getDescription());
        assertEquals(1, instructions.get(0).getStepNumber());
        assertEquals("Instruction 2", instructions.get(1).getDescription());
        assertEquals(2, instructions.get(1).getStepNumber());
    }

    @Test
    void testGetByRecipeId_notFound() {
        List<InstructionModelImpl> instructions = mongoRepo.findByRecipeId("nonexistent");
        assertTrue(instructions.isEmpty());
    }
}
