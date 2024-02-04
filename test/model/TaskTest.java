package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;

    @BeforeEach
    void beforeEach() {
        task = new Task("Тестовая Задача", "Описание", 1);
    }

    @Test
    void shouldGetTitleTask() {
        String titleTask = "Тестовая Задача";
        assertEquals(titleTask, task.getTitleTask(), "getTitleTask должен возвращать правильный заголовок задачи");
    }

    @Test
    void shouldGetDescription() {
        String description = "Описание тестовой задачи";
        Task task = new Task("Тестовая Задача", description, 1);
        assertEquals(description, task.getDescription(), "getDescription должен возвращать правильное описание задачи");
    }

    @Test
    void shouldGetId() {
        int id = 123;
        Task task = new Task("Тестовая Задача", "Описание", id);
        assertEquals(id, task.getId(), "getId должен возвращать правильный ID задачи");
    }

    @Test
    void shouldGetStatus() {
        assertEquals(TaskStatus.NEW, task.getStatus(), "Новая задача должна иметь статус NEW");
    }

    @Test
    void shouldSetStatus() {
        TaskStatus newStatus = TaskStatus.IN_PROGRESS;
        task.setStatus(newStatus);
        assertEquals(newStatus, task.getStatus(), "setStatus должен обновлять статус задачи");
    }

    @Test
    void shouldGetAllTasks() {
        ArrayList<Task> allTasks = task.getAllTasks();
        assertEquals(1, allTasks.size(), "getAllTasks должен возвращать список с одной задачей");
        assertTrue(allTasks.contains(task), "Возвращенный список должен содержать задачу");
    }

    @Test
    void shouldEqualsAndHashCode() {
        Task sameTask = new Task("Тестовая Задача", "Описание", 1);

        assertTrue(task.equals(sameTask) && sameTask.equals(task),
                "equals должен быть симметричным");
        assertEquals(task.hashCode(), sameTask.hashCode(),
                "hashCode должен быть согласован с equals");
    }

    @Test
    void shouldNotEquals() {
        Task differentTask = new Task("Другая Задача", "Описание", 2);

        assertFalse(task.equals(differentTask) || differentTask.equals(task),
                "Задачи с разными данными не должны быть равными");
    }

}