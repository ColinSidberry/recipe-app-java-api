package models.impl;

import models.interfaces.InstructionModel;

public class InstructionModelImpl implements InstructionModel {
    private String id;
    private String recipeId;
    private int stepNumber;
    private String description;

    public InstructionModelImpl() {}

    public InstructionModelImpl(String id, String recipeId, int stepNumber, String description) {
        this.id = id;
        this.recipeId = recipeId;
        this.stepNumber = stepNumber;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
}
