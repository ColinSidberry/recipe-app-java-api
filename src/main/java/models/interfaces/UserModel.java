package models.interfaces;

public interface UserModel extends BaseModel {
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    String getEmail();
    void setEmail(String email);
}
