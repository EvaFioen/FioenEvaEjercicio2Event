package Eventplanner;

public class EventTask{
    private final String descripcion;
    private boolean isCompleted;

    public EventTask(String descripcion){
        this.descripcion = descripcion;
        this.isCompleted = false;
    }
    public void completeTask() {
        isCompleted = true;
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "Tarea: " + descripcion + " - Estado tarea: " + (isCompleted ? "Completada" : "Sin completar");
    }
}
