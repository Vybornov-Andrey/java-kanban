package service;


import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Задача 1", "Описание задачи 1", 1);
        task2 = new Task("Задача 2", "Описание задачи 2", 2);
        task3 = new Task("Задача 3", "Описание задачи 3", 3);
    }

    @Test
    void addTaskToHistory() {
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size(), "Задача должна быть добавлена в историю");
    }

    @Test
    void removeTaskFromHistory() {
        historyManager.add(task1);
        historyManager.remove(task1.getId());
        assertTrue(historyManager.getHistory().isEmpty(), "Задача должна быть удалена из истории");
    }

    @Test
    void getEntireHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        assertEquals(3, historyManager.getHistory().size(), "История должна содержать все добавленные задачи");
    }

    @Test
    void removeTaskOnReAddition() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи");
        assertEquals(task2, history.get(0), "Первой задачей в истории должна быть задача 2");
        assertEquals(task1, history.get(1), "Второй задачей в истории должна быть задача 1");
    }
}