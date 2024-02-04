package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void shouldGetEpicId() {
        int epicId = 123;
        Subtask subtask = new Subtask("Тестовая Подзадача", "Описание", 1, epicId);
        assertEquals(epicId, subtask.getEpicId(), "getEpicId должен возвращать правильный epicId");
    }

    @Test
    void shouldSetEpicId() {
        Subtask subtask = new Subtask("Тестовая Подзадача", "Описание", 1, 123);
        int newEpicId = 456;
        subtask.setEpicId(newEpicId);
        assertEquals(newEpicId, subtask.getEpicId(), "setEpicId должен обновлять epicId");
    }

    @Test
    void shouldEqualsAndHashCode() {
        Subtask subtask1 = new Subtask("Тестовая Подзадача", "Описание", 1, 123);
        Subtask subtask2 = new Subtask("Тестовая Подзадача", "Описание", 1, 123);

        assertTrue(subtask1.equals(subtask2) && subtask2.equals(subtask1),
                "equals должен быть симметричным");
        assertEquals(subtask1.hashCode(), subtask2.hashCode(),
                "hashCode должен быть согласован с equals");
    }

    @Test
    void shouldNotEquals() {
        Subtask subtask1 = new Subtask("Тестовая Подзадача", "Описание", 1, 123);
        Subtask subtask2 = new Subtask("Другая Подзадача", "Описание", 2, 456);

        assertFalse(subtask1.equals(subtask2) || subtask2.equals(subtask1),
                "Подзадачи с разными данными не должны быть равными");
    }


}