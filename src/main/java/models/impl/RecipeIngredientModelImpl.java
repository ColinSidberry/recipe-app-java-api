package models.impl;

import models.interfaces.RecipeIngredientModel;

public class RecipeIngredientModelImpl implements RecipeIngredientModel {
  private String id;
  private String recipeId;
  private String ingredientId;
  private double quantity;
  private String unit;

  public RecipeIngredientModelImpl() {}
  
  public RecipeIngredientModelImpl(String id, String recipeId, String ingredientId, double quantity, String unit) {
    this.id = id;
    this.recipeId = recipeId;
    this.ingredientId = ingredientId;
    this.quantity = quantity;
    this.unit = unit;
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }


  public String getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(String recipeId) {
    this.recipeId = recipeId;
  }

  public String getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(String ingredientId) {
    this.ingredientId = ingredientId;
  }
}