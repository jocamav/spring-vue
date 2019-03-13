package com.example.springvue.dto;

public class TodoDTO {

    private long id;
    private String content;
    private boolean done;

    public TodoDTO() {

    }

    public TodoDTO(long id, String content) {
        this.id = id;
        this.content = content;
        this.done = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
    
    
}
