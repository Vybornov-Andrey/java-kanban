package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface TaskManager {
    Task createTask(String titleTask, String description);

    void createSubtask(Subtask subtask);

    int newId();

    HashSet<Task> getAllTasks();

    void clearArrayList();

    Task getTaskId(int taskId);

    void createTask(Task task);

    void updateTask(Task updatedTask);

    void updateSubtask(Subtask updatedSubtask);

    void removeTaskId(int taskId);

    List<Subtask> getSubtasksEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();
}
