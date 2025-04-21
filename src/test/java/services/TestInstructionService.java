package services;

import models.impl.InstructionModelImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.helpers.TestInstructionRepository;
import services.impl.InstructionServiceImpl;
import services.interfaces.BaseService;

import static org.junit.jupiter.api.Assertions.*;

public class TestInstructionService {
    private final TestInstructionRepository repository = new TestInstructionRepository();
    private BaseService<InstructionModelImpl> service;

    @BeforeEach
    public void setUp() {
        service = new InstructionServiceImpl(repository);
        repository.clear();
    }

    @AfterEach
    public void tearDown() {
        repository.clear();
    }

    @Test
    void testCreateInstruction() {
        var instruction = new InstructionModelImpl();
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction description");

        var created = service.create(instruction);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(1, created.getStepNumber());
        assertEquals("Test instruction description", created.getDescription());
    }

    @Test
    void testGetInstruction() {
        var instruction = new InstructionModelImpl();
        instruction.setId("test-instruction");
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction description");
        service.create(instruction);

        var retrieved = service.getById("test-instruction");
        assertNotNull(retrieved);
        assertEquals(1, retrieved.getStepNumber());
        assertEquals("Test instruction description", retrieved.getDescription());
    }

    @Test
    void testGetAllInstructions() {
        var instruction1 = new InstructionModelImpl();
        instruction1.setId("instruction1");
        instruction1.setRecipeId("test-recipe");
        instruction1.setStepNumber(1);
        instruction1.setDescription("First instruction");
        service.create(instruction1);

        var instruction2 = new InstructionModelImpl();
        instruction2.setId("instruction2");
        instruction2.setRecipeId("test-recipe");
        instruction2.setStepNumber(2);
        instruction2.setDescription("Second instruction");
        service.create(instruction2);

        var instructions = service.getAll();
        assertNotNull(instructions);
        assertEquals(2, instructions.size());
        assertEquals(1, instructions.get(0).getStepNumber());
        assertEquals("First instruction", instructions.get(0).getDescription());
        assertEquals(2, instructions.get(1).getStepNumber());
        assertEquals("Second instruction", instructions.get(1).getDescription());
    }

    @Test
    void testDeleteInstruction() {
        var instruction = new InstructionModelImpl();
        instruction.setId("test-instruction");
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction description");
        service.create(instruction);

        service.delete("test-instruction");
        var retrieved = service.getById("test-instruction");
        assertNull(retrieved);
    }
}
