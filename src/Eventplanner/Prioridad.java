package Eventplanner;

import java.time.LocalDate;

public enum Prioridad {
    HIGH,  // Alta prioridad
    MEDIUM, // Media prioridad
    LOW;    // Baja prioridad

    /**
     * Este metodo es para determinar si el evento añadido tiene alta prioridad o no, dependiendo de la fecha actual.
     * Tiene en cuenta tambien si esta dentro de 3,5 o mas meses para determinar el HIGH,MEDIUM o LOW.
     * @param fecha dependiendo del año, mes y dia introducido por el usuario se calcula el metodo
     * @return nos devuelve si es prioridad HIGH, MEDIUM, LOW dependiendo de la fecha.
     */
    public static Prioridad determinarPrioridad(LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate dosMesesDesdeAhora = fechaActual.plusMonths(3);
        LocalDate cuatroMesesDesdeAhora = fechaActual.plusMonths(5);

        if (fecha.isAfter(fechaActual) && fecha.isBefore(dosMesesDesdeAhora)) {
            return HIGH; // Prioridad alta si está dentro de los próximos 3 meses
        } else if (fecha.isAfter(dosMesesDesdeAhora) && fecha.isBefore(cuatroMesesDesdeAhora)) {
            return MEDIUM; // Prioridad media si está entre 3 y 5 meses
        } else {
            return LOW;
        }
    }
}