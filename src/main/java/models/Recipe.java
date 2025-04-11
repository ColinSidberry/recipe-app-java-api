package models;

import java.time.LocalDateTime;
import java.util.List;

public class Recipe {
  private String id;
  private String userId;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private List<RecipeIngredient> ingredients;
  private List<Instruction> instructions;
  private String imageUrl;
  private Category category;

  public Recipe() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public List<RecipeIngredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<RecipeIngredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Instruction> getInstructions() {
    return instructions;
  }

  public void setInstructions(List<Instruction> instructions) {
    this.instructions = instructions;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}