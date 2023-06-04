package tkachgeek.config.yaml.data;

public class DatabaseConnection {
  String url = "jdbc:mysql://localhost:3306/db_name?useSSL=false";
  String user = "root";
  String password = "root";
  
  public DatabaseConnection() {
  }
  
  public String getUrl() {
    return url;
  }
  
  public String getUser() {
    return user;
  }
  
  public String getPassword() {
    return password;
  }
}
