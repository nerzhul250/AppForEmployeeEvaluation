package com.usoft.dtos.jira;

public class Priority {
	
	private int id;
	private String description;
	private String iconUrl;
	private String name;
	
	public Priority() {
		
	}

	public Priority(int id, String description, String iconUrl, String name) {
		super();
		this.id = id;
		this.description = description;
		this.iconUrl = iconUrl;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
