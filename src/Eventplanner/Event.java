package Eventplanner;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {
    private String nombreEvento;
    private LocalDate fecha;
    private Prioridad priority;
    private ArrayList<EventTask> tasks;

    public Event(String nombreEvento, LocalDate fecha,Prioridad priority) {
        this.nombreEvento = nombreEvento;
        this.fecha = fecha;
        this.priority = priority;
        this.tasks = new ArrayList<>();
    }

    public void addTask(EventTask tarea) {
        tasks.add(tarea);
    }
    public String getNombreEvento() {return nombreEvento;}

    public LocalDate getFecha() {return fecha;}

    public Prioridad getPriority() {return priority;}

    public ArrayList<EventTask> getTasks() {
        return tasks;
    }


    @Override
    public String toString() {
        return "Evento: " + nombreEvento + ", Fecha: " + fecha + ", Prioridad: " + priority + ", Tareas: " + tasks.size();
    }
}

