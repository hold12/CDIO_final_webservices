package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndersWOlsen on 16-06-2017.
 */
public class UserNoPerms {
    private int userId;
    private String firstname;
    private String lastname;
    private String initials;
    private String password;
    private boolean active;
    List<String> roles;

    public UserNoPerms() {
    }

    public UserNoPerms(User user) {
        this.userId = user.getUserId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.initials = user.getInitials();
        this.password = user.getPassword();
        this.active = user.isActive();
        roles = new ArrayList<>();
        for (Role role : user.getRoles())
            roles.add(role.getRole_name());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNoPerms that = (UserNoPerms) o;

        if (userId != that.userId) return false;
        if (active != that.active) return false;
        if (!firstname.equals(that.firstname)) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (!initials.equals(that.initials)) return false;
        if (!password.equals(that.password)) return false;
        return roles != null ? roles.equals(that.roles) : that.roles == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + firstname.hashCode();
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + initials.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserNoPerms{" +
                "userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", initials='" + initials + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
