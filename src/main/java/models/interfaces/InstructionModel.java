package models.interfaces;

public interface InstructionModel extends BaseModel {
    int getStepNumber();
    void setStepNumber(int stepNumber);
    String getDescription();
    void setDescription(String description);
}
