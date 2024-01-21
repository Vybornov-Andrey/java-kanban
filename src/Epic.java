import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Epic extends Task {
    private Set<Integer> subtaskIds;

    public Epic(String titleTask, String description, int id) {
        super(titleTask, description, id);
        this.subtaskIds = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtaskIds +
                '}';
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public boolean epicComplete(TaskManager taskManager) {
        for (int subtaskId : subtaskIds) {
            Subtask subtask = (Subtask) taskManager.getTaskId(subtaskId);
            if (subtask == null || subtask.getStatus() != TaskStatus.DONE) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }
}

