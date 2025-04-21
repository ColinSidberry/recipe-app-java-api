package models.interfaces;

public interface RecipeModel extends BaseModel {
    String getTitle();
    void setTitle(String title);
    String getDescription();
    void setDescription(String description);
    int getPrepTime();
    void setPrepTime(int prepTime);
    int getCookTime();
    void setCookTime(int cookTime);
    int getServings();
    void setServings(int servings);
    String getDifficulty();
    void setDifficulty(String difficulty);
    String getUserId();
    void setUserId(String userId);
    String getCategoryId();
    void setCategoryId(String categoryId);
}
