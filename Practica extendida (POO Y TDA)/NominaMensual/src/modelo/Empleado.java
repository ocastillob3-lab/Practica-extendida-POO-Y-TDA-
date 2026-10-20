package modelo;

/**
 * Clase base abstracta para los empleados.
 * ABSTRACCIÓN: concentra los datos y el comportamiento común.
 */
public abstract class Empleado {
    private String dpi;
    private String nombre;

    public Empleado(String dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract double calcularPago();

    public abstract String obtenerTipo();
}
