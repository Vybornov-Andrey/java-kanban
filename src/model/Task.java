package model;

import java.util.ArrayList;
import java.util.Objects;

public class Task {
    private String titleTask;
    private String description;
    private int id;
    private TaskStatus status;

    public Task(String titleTask, String description, int id) {
        this.titleTask = titleTask;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.add(this);
        return allTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(titleTask, task.titleTask) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titleTask, description, id);
    }

    @Override
    public String toString() {
        return titleTask + " - " + status;
    }
}

