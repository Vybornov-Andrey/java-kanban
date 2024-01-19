public class Subtask extends Task {
    private int epicId;

    public Subtask(String titleTask, String description, int id, int epicId) {
        super(titleTask, description, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}

