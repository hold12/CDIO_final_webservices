package dto;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int userId;
	private String firstname;
	private String lastname;
	private String initials;
	private String password;
    private boolean isActive;
    private List<Role> roles;

    public User() {}

    public User(int userId, String firstname, String lastname, String initials, String password, boolean isActive) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.initials = initials;
		this.password = password;
        this.isActive = isActive;
        this.roles = new ArrayList<>();
    }

	public User(int userId, String firstname, String lastname, String initials, String password, boolean isActive, List<Role> roles) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.initials = initials;
		this.password = password;
		this.isActive = isActive;
		this.roles = roles;
	}

	public User(int userId) {
		this.userId = userId;
		this.firstname = "";
		this.lastname = "";
		this.initials = "";
		this.password = "";
		this.isActive = true;
	}

    public User(User user) {
    	this.userId = user.getUserId();
    	this.firstname = user.getFirstname();
    	this.lastname = user.getLastname();
    	this.initials = user.getInitials();
    	this.password = user.getPassword();
        this.isActive = user.isActive();
    }
    
    public int getUserId() { return userId; }
	public void setUserId(int userId) { this.userId = userId; }
	public String getFirstname() { return firstname; }
	public void setFirstname(String firstname) { this.firstname = firstname; }
	public String getLastname() { return this.lastname; }
	public void setLastname(String lastname) { this.lastname = lastname; }
	public String getInitials() { return initials; }
	public void setInitials(String initials) { this.initials = initials; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
    public boolean isActive() {
        return isActive;
    }
    public void setIsActive(boolean isActive) {
    	this.isActive = isActive;
    }


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (userId != user.userId) return false;
		if (isActive != user.isActive) return false;
		if (!firstname.equals(user.firstname)) return false;
		if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
		if (!initials.equals(user.initials)) return false;
		if (!password.equals(user.password)) return false;
		return roles.equals(user.roles);
	}

	public String toString() {
        return userId + "\t" + firstname + "\t" + lastname + "\t" + initials + "\t" + password + "\t" + isActive;
    }
}
