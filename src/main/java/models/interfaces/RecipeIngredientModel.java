package models.interfaces;

public interface RecipeIngredientModel extends BaseModel {
    double getQuantity();
    void setQuantity(double quantity);
    String getRecipeId();
    void setRecipeId(String recipeId);
    String getIngredientId();
    void setIngredientId(String ingredientId);
}
