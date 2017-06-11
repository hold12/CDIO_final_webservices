package dto;

/**
 * Created by AndersWOlsen on 11-06-2017.
 */
public class Permission {
    private int permission_id;
    private String permission_name;

    public Permission() { }

    public int getPermission_id() { return permission_id; }
    public void setPermission_id(int permission_id) { this.permission_id = permission_id; }
    public String getPermission_name() { return permission_name; }
    public void setPermission_name(String permission_name) { this.permission_name = permission_name; }
}
