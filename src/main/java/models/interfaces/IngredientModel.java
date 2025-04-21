package models.interfaces;

public interface IngredientModel extends BaseModel {
    String getUnit();
    void setUnit(String unit);
    String getName();
    void setName(String name);
}
