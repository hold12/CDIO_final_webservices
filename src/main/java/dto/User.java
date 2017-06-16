package dto;

import config.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {
	private int userId;
	private String firstname;
	private String lastname;
	private String initials;
	private String password;
    private boolean active;
    private List<Role> roles;

    public User() {}

    public User(int userId, String firstname, String lastname, String initials, String password, boolean active) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.initials = initials;
		this.password = password;
        this.active = active;
        this.roles = new ArrayList<>();
    }

	public User(int userId, String firstname, String lastname, String initials, String password, boolean active, List<Role> roles) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.initials = initials;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}

	public User(int userId) {
		this.userId = userId;
		this.firstname = "";
		this.lastname = "";
		this.initials = "";
		this.password = "";
		this.active = true;
	}

    public User(User user) {
    	this.userId = user.getUserId();
    	this.firstname = user.getFirstname();
    	this.lastname = user.getLastname();
    	this.initials = user.getInitials();
    	this.password = user.getPassword();
        this.active = user.isActive();
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
        return active;
    }
    public void setIsActive(boolean isActive) {
    	this.active = isActive;
    }
    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }
    public boolean hasPermission(Permission permission) {
    	boolean userHasPermission = false;
    	for (Role r : this.roles) {
    		for (String s : r.getPermissions()) { // TODO use map instead - performance
				if (s.toLowerCase().equals(permission.toString()))
    				userHasPermission = true;
			}
		}
		return userHasPermission;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (userId != user.userId) return false;
		if (active != user.active) return false;
		if (!firstname.equals(user.firstname)) return false;
		if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
		if (!initials.equals(user.initials)) return false;
		if (!password.equals(user.password)) return false;
		return roles.equals(user.roles);
	}

	public String toString() {
        return userId + "\t" + firstname + "\t" + lastname + "\t" + initials + "\t" + password + "\t" + active;
    }

	public String generatePassword() {
		final int minLen = 8;
		final int maxLen = 20;
		final int noOfCAPSAlpha = 1;
		final int noOfDigits = 1;
		final int noOfSplChars = 1;
		final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		final String NUM = "0123456789";
		final String SPECIAL_CHARS = ".-_+!?=";

		Random rnd = new Random();
		int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
		char[] pswd = new char[len];
		int index;

		for (int i = 0; i < noOfCAPSAlpha; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
		}

		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
		}

		for (int i = 0; i < noOfSplChars; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = SPECIAL_CHARS.charAt(rnd.nextInt(SPECIAL_CHARS.length()));
		}

		for (int i = 0; i < len; i++) {
			if (pswd[i] == 0) {
				pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
			}
		}

		return new String(pswd);
	}

	private int getNextIndex(Random rnd, int len, char[] pswd) {
		int index;
		while (pswd[index = rnd.nextInt(len)] != 0) ;
		return index;
	}

}
