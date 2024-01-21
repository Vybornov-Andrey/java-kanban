import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private static int taskCounter = 1;

    private Map<Integer, Task> taskMap;
    private Map<Integer, Subtask> subtaskMap;
    private Map<Integer, Epic> epicMap;

    public TaskManager() {
        this.taskMap = new HashMap<>();
        this.subtaskMap = new HashMap<>();
        this.epicMap = new HashMap<>();
    }

    public Task createTask(String titleTask, String description) {
        Task newTask = new Task(titleTask, description, newId());
        taskMap.put(newTask.getId(), newTask);
        return newTask;
    }

    public void createSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
        taskMap.put(subtask.getId(), subtask);
    }

    public int newId() {
        return ++taskCounter;
    }

    public HashSet<Task> getAllTasks() {
        HashSet<Task> allTasks = new HashSet<>(taskMap.values());
        for (Epic epic : epicMap.values()) {
            allTasks.addAll(epic.getSubtasks());
        }
        return allTasks;
    }

    public void clearArrayList() {
        taskMap.clear();
        subtaskMap.clear();
        epicMap.clear();
    }

    public Task getTaskId(int taskId) {
        return taskMap.get(taskId);
    }

    public void createTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void updateTask(Task updatedTask) {
        int taskId = updatedTask.getId();
        taskMap.put(taskId, updatedTask);
    }

    public void updateSubtask(Subtask updatedSubtask) {
        int subtaskId = updatedSubtask.getId();
        subtaskMap.put(subtaskId, updatedSubtask);
    }

    public void removeTaskId(int taskId) {
        taskMap.remove(taskId);
        subtaskMap.remove(taskId);
        epicMap.remove(taskId);
    }

    public List<Subtask> getSubtasksEpic(Epic epic) {
        List<Subtask> epicSubtasks = new ArrayList<>();

        for (Subtask subtask : subtaskMap.values()) {
            if (subtask.getEpicId() == epic.getId()) {
                epicSubtasks.add(subtask);
            }
        }

        return epicSubtasks;
    }

    public void updateEpicStatus(Epic epic) {
        ArrayList<Subtask> subtasksEpic = (ArrayList<Subtask>) getSubtasksEpic(epic);

        if (subtasksEpic.isEmpty() || allSubtasksStatusNew(subtasksEpic)) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allSubtasksStatusDone(subtasksEpic)) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    private boolean allSubtasksStatusNew(ArrayList<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.NEW) {
                return false;
            }
        }
        return true;
    }

    private boolean allSubtasksStatusDone(ArrayList<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                return false;
            }
        }
        return true;
    }
}

