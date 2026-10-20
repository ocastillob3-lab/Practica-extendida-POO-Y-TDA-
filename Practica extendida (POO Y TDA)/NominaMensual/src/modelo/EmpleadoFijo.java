package modelo;

/** Empleado con sueldo mensual y bonificación. */
public class EmpleadoFijo extends Empleado {
    private double sueldoMensual;
    private double bonificacion;

    public EmpleadoFijo(String dpi, String nombre,
                        double sueldoMensual, double bonificacion) {
        super(dpi, nombre);
        this.sueldoMensual = sueldoMensual;
        this.bonificacion = bonificacion;
    }

    public double getSueldoMensual() {
        return sueldoMensual;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    @Override
    public double calcularPago() {
        return sueldoMensual + bonificacion;
    }

    @Override
    public String obtenerTipo() {
        return "Fijo";
    }
}
