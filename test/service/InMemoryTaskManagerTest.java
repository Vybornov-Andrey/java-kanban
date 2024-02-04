package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    @Test
    void shouldCreateTask() {
        String title = "Задача №1";
        String description = "Описание задачи";
        Task task = taskManager.createTask(title, description);

        assertNotNull(task, "Созданная задача не должна быть null");
        assertEquals(title, task.getTitleTask(), "Заголовок задачи неверный");
        assertEquals(description, task.getDescription(), "Описание задачи неверное");
        assertEquals(TaskStatus.NEW, task.getStatus(), "Статус задачи должен быть NEW");
    }

    @Test
    void shouldCreateSubtask() {
        Task task = taskManager.createTask("Задача", "Описание задачи");
        String subtaskTitle = "Подзадача";
        String subtaskDescription = "Описание подзадачи";

        taskManager.createSubtask(new Subtask(subtaskTitle, subtaskDescription, task.getId(), 1));

        assertEquals(1, taskManager.getAllTasks().size(), "Должна быть создана одна задача");
    }

    @Test
    void shouldUpdateTask() {
        Task task = taskManager.createTask("Задача", "Описание задачи");
        Task updatedTask = new Task("Обновленная задача", "Обновленное описание", task.getId());

        taskManager.updateTask(updatedTask);

        assertEquals(updatedTask, taskManager.getTaskId(task.getId()), "Задача должна быть обновлена");
    }

    @Test
    void shouldRemoveTaskId() {
        Task task = taskManager.createTask("Задача", "Описание задачи");
        int taskId = task.getId();

        taskManager.removeTaskId(taskId);

        assertNull(taskManager.getTaskId(taskId), "Задача должна быть удалена");
    }

    @Test
    void shouldUpdateEpicStatus() {
        Epic epic = new Epic("Эпик", "Описание эпика", 1);
        taskManager.updateEpicStatus(epic);

        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    void shouldGetHistory() {
        Task task = taskManager.createTask("Задача", "Описание задачи");

        assertEquals(1, taskManager.getHistory().size(), "История должна содержать одну запись");
        assertEquals(task, taskManager.getHistory().get(0), "Неверная запись в истории");
    }
}