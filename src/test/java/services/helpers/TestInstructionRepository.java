package services.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import daos.interfaces.InstructionRepository;
import models.impl.InstructionModelImpl;

public class TestInstructionRepository implements InstructionRepository {
    private final List<InstructionModelImpl> instructions = new ArrayList<>();

    @Override
    public InstructionModelImpl save(InstructionModelImpl instruction) {
        if (instruction.getId() == null) {
            instruction.setId(java.util.UUID.randomUUID().toString());
        }
        instructions.removeIf(i -> i.getId().equals(instruction.getId()));
        instructions.add(instruction);
        return instruction;
    }

    @Override
    public List<InstructionModelImpl> findAll() {
        return new ArrayList<>(instructions);
    }

    @Override
    public Optional<InstructionModelImpl> findById(String id) {
        return instructions.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        instructions.removeIf(i -> i.getId().equals(id));
    }

    public List<InstructionModelImpl> findByRecipeId(String recipeId) {
        return instructions.stream()
                .filter(i -> i.getRecipeId().equals(recipeId))
                .toList();
    }

    public void clear() {
        instructions.clear();
    }
}
