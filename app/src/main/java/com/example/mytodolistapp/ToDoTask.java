package com.example.mytodolistapp;

public class ToDoTask {
    private int id;
    private String text;
    private String date;
    private boolean complete;

    public ToDoTask(int id, String text, String date, boolean complete) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.complete = complete;
    }

    public String getText() { return text; }
    public String getDate() { return  date; }
    public void setDate(String date) { this.date = date; }
    public boolean isComplete() { return complete; }
    public int getId() { return id; }
}
