package aplicacion;

import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Aplicación de consola para registrar e imprimir una nómina mensual.
 * Conceptos explicados con más detalle en ../evidencia/Explicacion.txt.
 */
public class Main {
    private static final String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private static final ArrayList<Empleado> empleados = new ArrayList<>();
    private static final Scanner entrada = new Scanner(System.in);
    private static int numeroMes;

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("          CÁLCULO DE NÓMINA");
        System.out.println("======================================");
        numeroMes = leerEnteroEnRango("Ingrese el mes a trabajar (1-12): ", 1, 12);

        int opcion;
        do {
            limpiarPantalla();
            mostrarMenuPrincipal();
            opcion = leerEnteroEnRango("Seleccione una opción: ", 1, 3);

            switch (opcion) {
                case 1:
                    ingresarNomina();
                    break;
                case 2:
                    imprimirNomina();
                    break;
                case 3:
                    limpiarPantalla();
                    System.out.println("Gracias por utilizar el sistema de nómina. ¡Hasta pronto!");
                    break;
                default:
                    // La validación de leerEnteroEnRango impide llegar aquí.
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);

        entrada.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("======================================");
        System.out.println("       NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println("======================================");
        System.out.println("1 - Ingreso de nómina");
        System.out.println("2 - Impresión de nómina");
        System.out.println("3 - Salir");
        System.out.println();
    }

    private static void ingresarNomina() {
        boolean continuar;
        do {
            limpiarPantalla();
            System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
            System.out.println("INGRESO DE EMPLEADO A NÓMINA");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println();
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");
            int tipo = leerEnteroEnRango("Seleccione el tipo de empleado: ", 1, 2);

            limpiarPantalla();
            System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
            if (tipo == 1) {
                registrarEmpleadoFijo();
            } else {
                registrarEmpleadoPorHora();
            }

            System.out.println();
            System.out.println("Empleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println();
            System.out.println("¿Desea ingresar otro empleado?");
            System.out.println("1 - Sí, ingresar otro empleado");
            System.out.println("2 - Volver al menú principal");
            int respuesta = leerEnteroEnRango("Seleccione una opción: ", 1, 2);
            continuar = respuesta == 1;
        } while (continuar);
    }

    private static void registrarEmpleadoFijo() {
        System.out.println("INGRESO DE EMPLEADO FIJO");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double sueldoMensual = leerDoubleNoNegativo("Sueldo mensual (Q): ");
        double bonificacion = leerDoubleNoNegativo("Bonificación de este mes (Q): ");

        // Se crea y guarda el objeto solo después de validar todos los datos.
        Empleado empleado = new EmpleadoFijo(dpi, nombre, sueldoMensual, bonificacion);
        empleados.add(empleado);
        System.out.printf("Pago de %s = Q %.2f%n", empleado.getNombre(), empleado.calcularPago());
    }

    private static void registrarEmpleadoPorHora() {
        System.out.println("INGRESO DE EMPLEADO POR HORA");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double pagoPorHora = leerDoubleNoNegativo("Pago por hora (Q): ");
        double horasTrabajadas = leerDoubleNoNegativo("Horas trabajadas este mes: ");

        Empleado empleado = new EmpleadoPorHora(dpi, nombre, pagoPorHora, horasTrabajadas);
        empleados.add(empleado);
        System.out.printf("Pago de %s = Q %.2f%n", empleado.getNombre(), empleado.calcularPago());
    }

    private static void imprimirNomina() {
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println();

        double totalNomina = 0.0;
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados en la nómina de este mes");
        } else {
            System.out.printf("%-18s %-22s %-12s %12s%n", "DPI", "Nombre", "Tipo", "Pago (Q)");
            System.out.println("---------------------------------------------------------------------");

            // Polimorfismo: el objeto concreto determina su propio cálculo.
            for (Empleado empleado : empleados) {
                double pago = empleado.calcularPago();
                totalNomina += pago;
                System.out.printf("%-18s %-22s %-12s %12.2f%n",
                        empleado.getDpi(), empleado.getNombre(),
                        empleado.obtenerTipo(), pago);
            }
        }

        System.out.println();
        System.out.println("Total de trabajadores: " + empleados.size());
        System.out.printf("Total de nómina: Q %.2f%n", totalNomina);
        System.out.println();
        System.out.print("Presione Enter para volver al menú principal...");
        entrada.nextLine();
    }

    private static String leerDpi() {
        while (true) {
            System.out.print("DPI (13 dígitos): ");
            String dpi = entrada.nextLine().trim();
            if (dpi.matches("\\d{13}")) {
                return dpi;
            }
            System.out.println("Error: el DPI debe contener exactamente 13 dígitos.");
        }
    }

    private static String leerNombre() {
        while (true) {
            System.out.print("Nombre: ");
            String nombre = entrada.nextLine().trim();
            if (!nombre.isEmpty()) {
                return nombre;
            }
            System.out.println("Error: el nombre no puede estar vacío ni contener solo espacios.");
        }
    }

    private static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ex) {
                // Se informa el error abajo y se vuelve a solicitar el dato.
            }
            System.out.println("Error: ingrese un número entero entre " + minimo + " y " + maximo + ".");
        }
    }

    private static double leerDoubleNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (Double.isFinite(valor) && valor >= 0.0) {
                    return valor;
                }
            } catch (NumberFormatException ex) {
                // Se informa el error abajo y se vuelve a solicitar el dato.
            }
            System.out.println("Error: ingrese un número finito mayor o igual a 0.");
        }
    }

    private static void limpiarPantalla() {
        // Compatible con consolas de IDE que no permiten ejecutar clear/cls.
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}
