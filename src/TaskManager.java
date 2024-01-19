import java.util.ArrayList;
import java.util.HashSet;

public class TaskManager {
    private static int taskCounter = 1;

    private ArrayList<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public Task createTask(String titleTask, String description) {
        Task newTask = new Task(titleTask, description, newId());
        tasks.add(newTask);
        return newTask;
    }

    public void createSubtask(Subtask subtask) {
        tasks.add(subtask);
    }

    public int newId() {
        taskCounter = taskCounter + 1;
        return taskCounter;
    }

    public HashSet<Task> getAllTasks() {
        HashSet<Task> allTasks = new HashSet<>();
        for (Task task : tasks) {
            allTasks.add(task);
            if (task instanceof Epic) {
                allTasks.addAll(((Epic) task).getSubtasks());
            }
        }
        return allTasks;
    }

    public void clearArrayList() {
        tasks.clear();
    }

    public Task getTaskId(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        System.out.println("Нет задач с таким: " + taskId);
        return null;
    }

    public void createTask(Task task) {
        tasks.add(task);
    }

    public void updateTask(Task updatedTask) {
        int taskId = updatedTask.getId();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId() == taskId) {
                tasks.set(i, updatedTask);
                break;
            }
        }
    }

    public void updateSubtask(Subtask updatedSubtask) {
        int subtaskId = updatedSubtask.getId();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId() == subtaskId) {
                tasks.set(i, updatedSubtask);
                break;
            }
        }
    }

    public void removeTaskId(int taskId) {
        ArrayList<Task> tasksRemoveId = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getId() == taskId) {
                tasksRemoveId.add(task);
                break;
            }
        }
        tasks.removeAll(tasksRemoveId);
    }

    public ArrayList<Subtask> getSubtasksEpic(Epic epic) {
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getClass().equals(Subtask.class)) {
                Subtask subtask = (Subtask) task;
                if (subtask.getEpicId() == epic.getId()) {
                    epicSubtasks.add(subtask);
                }
            }
        }
        return epicSubtasks;
    }

    public void updateEpicStatus(Epic epic) {
        ArrayList<Subtask> subtasksEpic = getSubtasksEpic(epic);

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

