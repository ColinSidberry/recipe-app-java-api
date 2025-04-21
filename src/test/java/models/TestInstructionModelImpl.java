package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.InstructionModelImpl;
import models.helpers.TestModelHelper;

import static org.junit.jupiter.api.Assertions.*;

class TestInstructionModelImpl {
    private InstructionModelImpl instruction;

    @BeforeEach
    void setup() {
        instruction = TestModelHelper.createInstruction();
    }

    @AfterEach
    void cleanup() {
        instruction = null;
    }

    @Test
    void testConstructor() {
        assertNotNull(instruction);
        assertEquals("Test instruction description", instruction.getDescription());
        assertEquals(1, instruction.getStepNumber());
    }

    @Test
    void testSetters() {
        instruction.setDescription("New instruction description");
        instruction.setStepNumber(2);

        assertEquals("New instruction description", instruction.getDescription());
        assertEquals(2, instruction.getStepNumber());
    }

    @Test
    void testGetters() {
        assertEquals("Test instruction description", instruction.getDescription());
        assertEquals(1, instruction.getStepNumber());
    }
}
