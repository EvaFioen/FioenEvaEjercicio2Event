package Eventplanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.DateTimeException;

public class Main {
    //variables globales
    Scanner input = new Scanner(System.in);
    ArrayList<Event> eventos = new ArrayList<>();
    boolean out = false;

    public static void main(String[] args) {
        Main programa = new Main();
        programa.inicio();
    }

    public void inicio() {
        System.out.println("Bienvenido a la aplicación de planificación de eventos.");
        do {
            System.out.println("Elige una opción:");
            System.out.println("[1] Añadir evento");
            System.out.println("[2] Borrar evento");
            System.out.println("[3] Listar eventos");
            System.out.println("[4] Marcar/desmarcar tarea de un evento como completada");
            System.out.println("[5] Salir");
            if (input.hasNext()) {
                int opcion = input.nextInt();
                input.nextLine();
                switch (opcion) {
                    case 1:
                        anadirEvento();
                        break;
                    case 2:
                        borrarEvento();
                        break;
                    case 3:
                        listarEventos();
                        break;
                    case 4:
                        marcarTareas();
                        break;
                    case 5:
                        System.out.println("Gracias por usar Event Planner. ¡Hasta pronto!");
                        out = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione una opción del 1 al 5.");
                        break;
                }
            } else {
                System.out.println("The option chosen is not correct. Try again");
                input.nextLine();
            }
        } while (!out);
    }

    /**
     * Este metodo sirve para añidir un evento donde pide por consola al usuario el nombre del evento.
     * Despues pide al usuario la fecha.
     * Como ultimo pide al usuaraio si quiere añadir una tarea.
     */

    public void anadirEvento() {
        String nombre = getStringFromConsole("Nombre del Evento: ");
        int anio = obtenerNumero("Introduce el año: ");
        int mes = obtenerNumero("Introduce el mes: ");
        int dia = obtenerNumero("Introduce el día: ");

        LocalDate fecha = LocalDate.of(anio, mes, dia);
        if (esFechaValida(dia, mes, anio)) {
            Prioridad prioridad = Prioridad.determinarPrioridad(fecha);
            Event event = new Event(nombre, fecha, prioridad);
            eventos.add(event);
            System.out.println("Evento añadido con éxito. Prioridad: " + prioridad);
            System.out.println(" ");
            String respuesta;
            do {
                respuesta = getStringFromConsole("¿Quieres añadir tareas al evento? (s/n)").toLowerCase().trim();
                if (respuesta.equals("s")) {
                    String descripcionTarea = getStringFromConsole("Introduce la descripción de la tarea: ");
                    EventTask nuevaTarea = new EventTask(descripcionTarea); // Crear nueva tarea
                    event.addTask(nuevaTarea); // Añadir la tarea al evento
                    System.out.println("Tarea añadida con éxito.");
                } else if (!respuesta.equals("n")) {
                    System.out.println("Respuesta no válida. Por favor, responde con 's' o 'n'.");
                }
            } while (!respuesta.equals("n"));
        }
    }

    /**
     * Este metodo sirve para borrar un evento donde tenemos que introducir el nombre exacto.
     * Si no hay eventos, se muestra por consola.
     * Si el usuario ha introducido mal el nombre no se va a encontrar el evento. Tiene que el nombre tal cual como se ha creado.
     */
    public void borrarEvento() {
        String nombreEvento = getStringFromConsole("Introduce el nombre del evento que deseas borrar: ");
        boolean eventoEncontrado = false;

        if (eventos.isEmpty()) {
            System.out.println("No hay eventos programados para borrar.");
            return;
        }
        for (int i = 0; i < eventos.size(); i++) {
            Event evento = eventos.get(i); // Obtener el evento en la posición i
            if (evento.getNombreEvento().equals(nombreEvento)) {
                eventos.remove(i); // Eliminar el evento de la lista
                System.out.println("El evento '" + nombreEvento + "' ha sido borrado exitosamente.");
                eventoEncontrado = true; // Marcar que se encontró el evento
                break; // Salir del bucle
            }
        }
        if (!eventoEncontrado) {
            System.out.println("No se encontró ningún evento con el título: " + nombreEvento);
        }
    }

    /**
     * Este metodo nos sirve para listar los eventos y toda la información que tiene.
     */
    public void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos programados.");
        } else {
            System.out.println("Eventos programados:");
            for (int i = 0; i < eventos.size(); i++) {
                Event evento = eventos.get(i);
                System.out.println((i + 1) + ". " + evento.getNombreEvento() + " - Fecha: " + evento.getFecha() + " - Prioridad: " + evento.getPriority() + " - Tarea: " + evento.getTasks());
            }
        }
    }

    /**
     * Este metodo nos sirve para marcar o desmarcar una tarea si esta completa o no.
     * Primero el usuario tiene que introducir el nombre del evento.
     * Se muestra las tareas para que el usuario puede eligir entre tareas.
     */

    public void marcarTareas(){
        String nombreEvento = getStringFromConsole("Introduce el nombre del evento que deseas marcar/desmarcar la tarea: ");
        boolean eventoEncontrado = false;
        Event eventoSeleccionado = null; // Variable para almacenar el evento encontrado

        // Verificar si hay eventos programados
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos programados.");
            return;
        }
        // Buscar el evento por nombre
        for (Event evento : eventos) {
            if (evento.getNombreEvento().equals(nombreEvento)) {
                eventoEncontrado = true;
                eventoSeleccionado = evento; // Almacenar el evento encontrado
                break;
            }
        }
        // Si no se encontró el evento
        if (!eventoEncontrado) {
            System.out.println("No se encontró ningún evento con el título: " + nombreEvento);
            return; // Salir si no se encuentra el evento
        }

        // Obtener la lista de tareas del evento
        ArrayList<EventTask> tareas = eventoSeleccionado.getTasks();

        if (tareas == null || tareas.isEmpty()) {
            System.out.println("No hay tareas para este evento.");
            return; // Salir si no hay tareas
        }

        System.out.println("Tareas para el evento '" + nombreEvento + "':");
        for (int i = 0; i < tareas.size(); i++) {
            System.out.println((i + 1) + ". " + tareas.get(i));
        }
        // Solicitar al usuario que elija una tarea
        int tareaNombre = obtenerNumero("Introduce el número de la tarea que deseas marcar/desmarcar (0 para cancelar): ");
        if (tareaNombre < 1 || tareaNombre > tareas.size()) {
            System.out.println("Operación cancelada o número de tarea no válido.");
            return; // Salir si la entrada es inválida
        }

        // Marcar o desmarcar la tarea
        EventTask tareaSeleccionada = tareas.get(tareaNombre - 1);
        if (tareaSeleccionada.isCompleted()) {
            tareaSeleccionada.completeTask(); // Completar la tarea
            System.out.println("La tarea ha sido marcada como completada.");
        } else {
            tareaSeleccionada.setCompleted(true); // Desmarcar la tarea
            System.out.println("La tarea ha sido desmarcada.");
        }
    }

    //Metodo para pedir al usuario un texto (String)
    public String getStringFromConsole(String mensaje) {
        Scanner input = new Scanner(System.in);
        String texto;
        do {
            System.out.println(mensaje);
            texto = input.nextLine();
        } while (texto.isBlank());
        return texto;
    }
    //Metodo para validar si la fecha es correcta
    public static boolean esFechaValida(int dia, int mes, int anio) {
        LocalDate fechaActual = LocalDate.now(); // Obtener la fecha actual

        try {
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            if (fecha.isBefore(fechaActual)) { // Comprobar si la fecha es anterior a la actual
                System.out.println("La fecha no puede ser anterior a la fecha actual.");
                return false;
            }
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
    //Metodo para pedir al usuario un numero
    public int obtenerNumero(String mensaje) {
        System.out.print(mensaje);
        while (!input.hasNextInt()) {
            System.out.println("Por favor, ingresa un número válido.");
            input.next();
        }
        return input.nextInt();
    }
}
