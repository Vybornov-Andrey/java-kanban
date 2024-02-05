package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    // Проверяем, что taskManager не null и taskManager является экземпляром InMemoryTaskManager
    @Test
    void shouldDefaultTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertInstanceOf(InMemoryTaskManager.class, taskManager);
    }


    // Проверяем, что historyManager не null и historyManager является экземпляром InMemoryHistoryManager
    @Test
    void shouldDefaultHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
        assertTrue(historyManager instanceof InMemoryHistoryManager);
    }

}