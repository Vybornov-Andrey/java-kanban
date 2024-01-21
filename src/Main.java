

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("Создание задач");
        Task firstTask = taskManager.createTask("Задача № 1", "Описание задачи № 1");
        Task secondTask = taskManager.createTask("Задача № 2", "Описание задачи № 2");
        Task thirdTask = taskManager.createTask("Задача № 3", "Описание задачи № 3");

        System.out.println("\nСоздание эпика и подзадач");
        Epic firstEpic = new Epic("Эпик № 1", "Описание задачи типа эпик № 1", taskManager.newId());
        Subtask firstSubtask = new Subtask("Подзадача № 1", "Описание подзадачи № 1", taskManager.newId(), firstEpic.getId());
        taskManager.createSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask("Подзадача № 2", "Описание подзадачи № 2", taskManager.newId(), firstEpic.getId());
        taskManager.createSubtask(secondSubtask);
        Subtask thirdSubtask = new Subtask("Подзадача № 3", "Описание подзадачи № 3", taskManager.newId(), firstEpic.getId());
        taskManager.createSubtask(thirdSubtask);

        System.out.println("\nЭпик с подзадачами");
        firstEpic.addSubtask(firstSubtask.getId());
        firstEpic.addSubtask(secondSubtask.getId());
        firstEpic.addSubtask(thirdSubtask.getId());
        System.out.println(firstEpic);

        System.out.println("\nДобавление задач и эпика в менеджер");
        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);
        taskManager.createTask(thirdTask);
        taskManager.createTask(firstEpic);

        System.out.println("\nСписок всех задач и их статусы:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task.getTitleTask() + " - " + task.getStatus());
        }

        System.out.println("\nОбновление статуса задачи");
        firstTask.setStatus(TaskStatus.IN_PROGRESS);
        thirdSubtask.setStatus(TaskStatus.DONE);
        taskManager.updateEpicStatus(firstEpic);
        taskManager.updateTask(firstTask);
        taskManager.updateSubtask(thirdSubtask);


        System.out.println("\nСписок задач после обновления и их статусы");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task.getTitleTask() + " - " + task.getStatus());
        }

        System.out.println("\nПроверяем, все ли подзадачи в эпике завершены");
        System.out.println(firstEpic.epicComplete(taskManager));

        System.out.println("\nУдаление задачи по идентификатору");
        taskManager.removeTaskId(secondTask.getId());

        System.out.println("\nСписок задач после удаления и их статусы");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task.getTitleTask() + " - " + task.getStatus());
        }

        System.out.println("\nУдаление всех задач");
        taskManager.clearArrayList();
        System.out.println(taskManager.getAllTasks());
    }
}

