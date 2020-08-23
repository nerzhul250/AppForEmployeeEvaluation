package com.usoft.dtos.jira;

public class UserJira {
	
	private String key;
	private String name;
	private String emailAddress;
	private String displayName;
	private boolean active;
	private String img48x48;
	
	public UserJira() {
		
	}

	public UserJira(String key, String name, String emailAddress, String displayName, boolean active, String img48x48) {
		super();
		this.key = key;
		this.name = name;
		this.emailAddress = emailAddress;
		this.displayName = displayName;
		this.active = active;
		this.img48x48 = img48x48;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getImg48x48() {
		return img48x48;
	}

	public void setImg48x48(String img48x48) {
		this.img48x48 = img48x48;
	}
	

}
