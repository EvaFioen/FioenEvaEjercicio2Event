package Eventplanner;

import java.time.LocalDate;

public enum Prioridad {
    HIGH,  // Alta prioridad
    MEDIUM, // Media prioridad
    LOW;    // Baja prioridad

    public static Prioridad determinarPrioridad(LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate dosMesesDesdeAhora = fechaActual.plusMonths(3);
        LocalDate cuatroMesesDesdeAhora = fechaActual.plusMonths(5);

        if (fecha.isAfter(fechaActual) && fecha.isBefore(dosMesesDesdeAhora)) {
            return HIGH; // Prioridad alta si está dentro de los próximos 3 meses
        } else if (fecha.isAfter(dosMesesDesdeAhora) && fecha.isBefore(cuatroMesesDesdeAhora)) {
            return MEDIUM; // Prioridad media si está entre 3 y 5 meses
        } else {
            return LOW; // Prioridad baja para fechas más lejanas
        }
    }
}