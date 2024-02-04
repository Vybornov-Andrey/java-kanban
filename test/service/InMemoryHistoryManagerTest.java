package service;

import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    // Проверяем добавление задач в историю и  корректное извлечение
    @Test
    void shouldAddTaskInHistoryList() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Задача 1", "Описание 1", 1);
        Task task2 = new Task("Задача 2", "Описание 2", 2);

        historyManager.addTaskInHistoryList(task1);
        historyManager.addTaskInHistoryList(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    // Проверяем, что задача == null не добавляется в историю
    @Test
    void shouldAddTaskInHistoryList_NullTask() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        historyManager.addTaskInHistoryList(null);

        List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size());
    }

    // Проверяем, что история хранит не более 10 последних задач
    @Test
    void shouldAddTaskInHistoryList_MaxSize() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        for (int i = 1; i <= 15; i++) {
            historyManager.addTaskInHistoryList(new Task("Задача " + i, "Описание " + i, i));
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size());
        assertEquals(6, history.get(0).getId()); // первая добавленная задача удалена (1-5, 6-15)
        assertEquals(15, history.get(9).getId()); // последняя добавленная задача присутствует
    }

    // Проверяем корректное извлечение истории
    @Test
    void shouldGetHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Задача 1", "Описание 1", 1);
        Task task2 = new Task("Задача 2", "Описание 2", 2);

        historyManager.addTaskInHistoryList(task1);
        historyManager.addTaskInHistoryList(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

}