package service;

import model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;

    private final LinkedList<Task> lastTasksList = new LinkedList<>();

    @Override
    public void addTaskInHistoryList(Task task) {
        if (task == null) {
            return;
        } else if (lastTasksList.size() == MAX_HISTORY_SIZE) {
            lastTasksList.removeFirst();
        }
        lastTasksList.addLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return new LinkedList<>(lastTasksList);
    }
}