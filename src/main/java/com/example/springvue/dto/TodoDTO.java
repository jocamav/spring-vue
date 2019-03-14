package com.example.springvue.dto;

public class TodoDTO {

    private long id;
    private String title;
    private boolean completed;

    public TodoDTO() {

    }

    public TodoDTO(long id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
    
    
}
