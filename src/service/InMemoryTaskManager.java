package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private static int taskCounter = 1;

    private Map<Integer, Task> taskMap;
    private Map<Integer, Subtask> subtaskMap;
    private Map<Integer, Epic> epicMap;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.taskMap = new HashMap<>();
        this.subtaskMap = new HashMap<>();
        this.epicMap = new HashMap<>();
    }

    @Override
    public Task createTask(String titleTask, String description) {
        Task newTask = new Task(titleTask, description, newId());
        taskMap.put(newTask.getId(), newTask);
        historyManager.addTaskInHistoryList(newTask);
        return newTask;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
        taskMap.put(subtask.getId(), subtask);
    }

    @Override
    public int newId() {
        return ++taskCounter;
    }

    @Override
    public HashSet<Task> getAllTasks() {
        HashSet<Task> allTasks = new HashSet<>(taskMap.values());
        for (Epic epic : epicMap.values()) {
            allTasks.addAll(epic.getAllTasks());
        }
        return allTasks;
    }

    @Override
    public void clearArrayList() {
        taskMap.clear();
        subtaskMap.clear();
        epicMap.clear();
    }

    @Override
    public Task getTaskId(int taskId) {
        return taskMap.get(taskId);
    }

    @Override
    public void createTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task updatedTask) {
        int taskId = updatedTask.getId();
        taskMap.put(taskId, updatedTask);
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        int subtaskId = updatedSubtask.getId();
        subtaskMap.put(subtaskId, updatedSubtask);
    }

    @Override
    public void removeTaskId(int taskId) {
        taskMap.remove(taskId);
        subtaskMap.remove(taskId);
        epicMap.remove(taskId);
    }

    @Override
    public List<Subtask> getSubtasksEpic(Epic epic) {
        List<Subtask> epicSubtasks = new ArrayList<>();

        for (Subtask subtask : subtaskMap.values()) {
            if (subtask.getEpicId() == epic.getId()) {
                epicSubtasks.add(subtask);
            }
        }

        return epicSubtasks;
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        List<Subtask> subtasksEpic = getSubtasksEpic(epic);

        if (subtasksEpic.isEmpty() || allSubtasksStatusNew(subtasksEpic)) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allSubtasksStatusDone(subtasksEpic)) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private boolean allSubtasksStatusNew(List<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.NEW) {
                return false;
            }
        }
        return true;
    }

    private boolean allSubtasksStatusDone(List<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                return false;
            }
        }
        return true;
    }
}
