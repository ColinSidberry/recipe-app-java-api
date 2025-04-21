package models.interfaces;

public interface CategoryModel extends BaseModel {
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
}
