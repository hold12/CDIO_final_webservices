package dto;

import java.util.Arrays;
import java.util.List;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
public class Role {
    private int role_id;
    private String role_name;
    private List<String> permissions;

    public Role() { }

	public Role(int role_id) {
		this.role_id = role_id;
	}

    public Role(String role_name, String[] permissions) {
        this.role_name = role_name;
        this.permissions = Arrays.asList(permissions);
    }
    public Role(int role_id, String role_name, List<String> permissions) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.permissions = permissions;
    }
    public Role(String role_name) {
        this.role_name = role_name;
    }

    public int getRole_id() { return role_id; }
    public void setRole_id(int role_id) { this.role_id = role_id; }
    public String getRole_name() { return role_name; }
    public void setRole_name(String role_name) { this.role_name = role_name; }
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    public void clearPermissions() { this.permissions.clear(); }
}
