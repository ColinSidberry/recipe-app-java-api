package models.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import models.interfaces.RecipeModel;

public class RecipeModelImpl implements RecipeModel {
  private String id;
  private String userId;
  private String categoryId;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private List<RecipeIngredientModelImpl> ingredients;
  private List<InstructionModelImpl> instructions;
  private String imageUrl;
  private CategoryModelImpl category;
  private int prepTime;
  private int cookTime;
  private int servings;
  private String difficulty;

  public RecipeModelImpl() {}

  public RecipeModelImpl(String id, String userId, String categoryId, String title, String description, LocalDateTime createdAt, List<RecipeIngredientModelImpl> ingredients, List<InstructionModelImpl> instructions, String imageUrl, CategoryModelImpl category, int prepTime, int cookTime, int servings, String difficulty) {
    this.id = id;
    this.userId = userId;
    this.categoryId = categoryId;
    this.title = title;
    this.description = description;
    this.createdAt = createdAt;
    this.ingredients = ingredients;
    this.instructions = instructions;
    this.imageUrl = imageUrl;
    this.category = category;
    this.prepTime = prepTime;
    this.cookTime = cookTime;
    this.servings = servings;
    this.difficulty = difficulty;
  }

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

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
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

  public List<RecipeIngredientModelImpl> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<RecipeIngredientModelImpl> ingredients) {
    this.ingredients = ingredients;
  }

  public List<InstructionModelImpl> getInstructions() {
    return instructions;
  }

  public void setInstructions(List<InstructionModelImpl> instructions) {
    this.instructions = instructions;
  }

  public int getPrepTime() {
    return prepTime;
  }

  public void setPrepTime(int prepTime) {
    this.prepTime = prepTime;
  }

  public int getCookTime() {
    return cookTime;
  }

  public void setCookTime(int cookTime) {
    this.cookTime = cookTime;
  }

  public int getServings() {
    return servings;
  }

  public void setServings(int servings) {
    this.servings = servings;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public CategoryModelImpl getCategory() {
    return category;
  }

  public void setCategory(CategoryModelImpl category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RecipeModelImpl that = (RecipeModelImpl) o;
    return prepTime == that.prepTime &&
        cookTime == that.cookTime &&
        servings == that.servings &&
        Objects.equals(id, that.id) &&
        Objects.equals(userId, that.userId) &&
        Objects.equals(categoryId, that.categoryId) &&
        Objects.equals(title, that.title) &&
        Objects.equals(description, that.description) &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(ingredients, that.ingredients) &&
        Objects.equals(instructions, that.instructions) &&
        Objects.equals(imageUrl, that.imageUrl) &&
        Objects.equals(category, that.category) &&
        Objects.equals(difficulty, that.difficulty);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, userId, categoryId, title, description, createdAt,
        ingredients != null ? ingredients.stream().map(RecipeIngredientModelImpl::getId).collect(Collectors.toList()) : null,
        instructions != null ? instructions.stream().map(InstructionModelImpl::getId).collect(Collectors.toList()) : null,
        imageUrl, category, prepTime, cookTime, servings, difficulty
    );
  }

  @Override
  public String toString() {
    return String.format(
        "RecipeModelImpl{" +
        "id=%s, " +
        "userId=%s, " +
        "title=%s, " +
        "description=%s, " +
        "createdAt=%s, " +
        "category=%s" +
        "}",
        id,
        userId,
        title,
        description,
        createdAt,
        category
    );
  }
}