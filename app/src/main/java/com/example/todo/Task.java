package com.example.todo;

import java.util.GregorianCalendar;

public class Task {
    private long id;
    private String title;
    private String description;
    private int priority;
    private long dueDate;
    private boolean completed;

    public Task() {}

    public Task(String title, String description, int priority, int year, int month, int day) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        setDueDate(year, month, day);
        this.completed = false;
    }

    public Task(long id, String title, String description, int priority, long dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public long getDueDate() { return dueDate; }
    public void setDueDate(long dueDate) { this.dueDate = dueDate; }
    public void setDueDate(int year, int month, int day) {
        this.dueDate = new GregorianCalendar(year, month, day).getTimeInMillis();
    }
    public int getDueYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dueDate);
        return calendar.get(GregorianCalendar.YEAR);
    }
    public int getDueMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dueDate);
        return calendar.get(GregorianCalendar.MONTH);
    }
    public int getDueDay() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dueDate);
        return calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}