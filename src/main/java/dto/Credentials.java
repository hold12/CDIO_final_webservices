package dto;

/**
 * Created by AndersWOlsen on 10-06-2017.
 */
public class Credentials {
    private int userId;
    private String password;

    public Credentials() {  }
    public Credentials( int userId, String password) { this.userId = userId; this.password = password; }
    public Credentials(User user) { this.userId = user.getUserId(); this.password = user.getPassword(); }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
