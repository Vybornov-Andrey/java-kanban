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

    private final Map<Integer, Task> taskMap;
    private final Map<Integer, Subtask> subtaskMap;
    private final Map<Integer, Epic> epicMap;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        this.taskMap = new HashMap<>();
        this.subtaskMap = new HashMap<>();
        this.epicMap = new HashMap<>();
    }

    @Override
    public Task createTask(String titleTask, String description) {
        Task newTask = new Task(titleTask, description, newId());
        taskMap.put(newTask.getId(), newTask);
        historyManager.add(newTask);
        return newTask;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
        taskMap.put(subtask.getId(), subtask);

        if (subtask.getEpicId() != 0) { // Убрано ненужное приведение типа и проверка instanceof
            int epicId = subtask.getEpicId();
            Epic epic = epicMap.get(epicId);
            if (epic != null) {
                epic.addSubtask(subtask.getId());
            }
        }
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
    public HashSet<Epic> getAllEpics() {
        HashSet<Epic> allEpics = new HashSet<>();

        for (Task task : taskMap.values()) {
            if (task instanceof Epic) {
                allEpics.add((Epic) task);
            }
        }

        return allEpics;
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

    private void updateEpicStatusForSubtask(Subtask subtask) {
        if (subtask.getEpicId() != 0) { // Убрано ненужное приведение типа и проверка instanceof
            int epicId = subtask.getEpicId();
            Epic epic = epicMap.get(epicId);
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        int subtaskId = updatedSubtask.getId();
        subtaskMap.put(subtaskId, updatedSubtask);
        updateEpicStatusForSubtask(updatedSubtask);
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

        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtaskMap.get(subtaskId);
            if (subtask != null) {
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
