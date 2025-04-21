package controllers.helpers;

import services.interfaces.InstructionService;
import models.impl.InstructionModelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestInstructionService implements InstructionService {
    private final List<InstructionModelImpl> instructions = new ArrayList<>();
    private int nextId = 1;

    public InstructionModelImpl create(InstructionModelImpl instruction) {
        instruction.setId("i" + nextId++);
        instructions.add(instruction);
        return instruction;
    }

    public List<InstructionModelImpl> getAll() {
        return new ArrayList<>(instructions);
    }

    public InstructionModelImpl getById(String id) {
        return instructions.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        instructions.removeIf(i -> i.getId().equals(id));
    }

    public List<InstructionModelImpl> getInstructionsByRecipe(String recipeId) {
        return instructions.stream()
                .filter(i -> i.getRecipeId().equals(recipeId))
                .collect(Collectors.toList());
    }

    public void deleteByRecipeId(String recipeId) {
        instructions.removeIf(i -> i.getRecipeId().equals(recipeId));
    }
}
