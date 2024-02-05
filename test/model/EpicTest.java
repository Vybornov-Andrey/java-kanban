package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    private TaskManager taskManager;
    private Epic epic;

    @BeforeEach
    void beforeEach() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        taskManager = new InMemoryTaskManager();
        epic = new Epic("Эпик 1", "Описание", 1);
    }

    @Test
    void addSubtaskToEpic() {
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи", 2, epic.getId());

        taskManager.createTask(epic);
        taskManager.createSubtask(subtask);
        epic.addSubtask(subtask.getId());

        assertTrue(epic.getSubtaskIds().contains(subtask.getId()));
    }

    @Test
    void notAddSameSubtaskToEpic() {
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи", 2, epic.getId());

        taskManager.createTask(epic);
        taskManager.createSubtask(subtask);
        epic.addSubtask(subtask.getId());

        assertEquals(1, epic.getSubtaskIds().size());
    }
}

