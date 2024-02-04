package service;

import model.Task;

import java.util.List;

public interface HistoryManager {

    void addTaskInHistoryList(Task task);

    List<Task> getHistory();
}

