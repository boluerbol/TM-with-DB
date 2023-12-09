package com.example.taskmanage;

import java.time.LocalDate;
import java.util.Date;

public class HomeworkTask implements Task{

    private String taskType;
    private String taskName;
    private String description;
    private boolean completed;
    private Priority priority;
    private LocalDate deadline;

    @Override
    public void createTask(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.description = taskDescription;
        this.completed = false;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void setTaskDescription(String taskDescription) {
        this.description = taskDescription;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public void markAsComplete() {
        this.completed = true;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void setDeadline(LocalDate date) {
        this.deadline = date;
    }

    public String getTaskName() {
        return taskName;
    }

    @Override
    public String getTaskDescription() {
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        String azar;
        if (this.completed){
            azar = "completed";
        }
        else
        {
            azar = "not completed";
        }

        return this.taskType + ": " + this.taskName + " " + this.deadline + " " + azar;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public int getTaskId() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void markAsIncomplete() {
        this.completed = false;
    }

    public boolean getCompleted()
    {
        return completed;
    }
}
