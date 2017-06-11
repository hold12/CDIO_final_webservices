package dto;

import java.util.List;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
public class Role {
    private int role_id;
    private String role_name;
    private List<Permission> permissions;

    public Role() { }

    public int getRole_id() { return role_id; }
    public void setRole_id(int role_id) { this.role_id = role_id; }
    public String getRole_name() { return role_name; }
    public void setRole_name(String role_name) { this.role_name = role_name; }
    public List<Permission> getPermissions() { return permissions; }
    public void setPermissions(List<Permission> permissions) { this.permissions = permissions; }
}
