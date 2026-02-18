import java.util.ArrayList;
import java.util.Scanner;

class Trabajador {

    String codigo;
    String nombre;
    String apellido;
    int ingreso;

    double sueldo;
    double prestamo;

    double afp;
    double sfs;
    double afpEmpresa;
    double sfsEmpresa;
    double isr;
    double totalDesc;
    double neto;

    public Trabajador(String nombre, String apellido, int ingreso,
                      double sueldo, double prestamo, String codigo) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.ingreso = ingreso;
        this.sueldo = sueldo;
        this.prestamo = prestamo;
        this.codigo = codigo;

        calcular();
    }

    private void calcular() {

        afp = sueldo * 0.0287;
        sfs = sueldo * 0.0304;

        afpEmpresa = sueldo * 0.0710;
        sfsEmpresa = sueldo * 0.0709;

        double anual = (sueldo - afp - sfs) * 12;
        isr = calcularISR(anual) / 12;

        totalDesc = prestamo + afp + sfs + isr;
        neto = sueldo - totalDesc;
    }
private double calcularISR(double anual) {

    if (anual <= 416220.00) {
        return 0;
    } 
    else if (anual <= 624329.00) {
        return (anual - 416220.00) * 0.15;
    } 
    else if (anual <= 867123.00) {
        return 31216.00 + (anual - 624329.00) * 0.20;
    } 
    else {
        return 79776.00 + (anual - 867123.00) * 0.25;
    }
}


    public void actualizar(double nuevoSueldo) {
        this.sueldo = nuevoSueldo;
        calcular();
    }
}

public class GestionPagos2026 {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Trabajador> lista = new ArrayList<>();

    public static void main(String[] args) {

        int op;

        do {
            System.out.println("\n======= SISTEMA DE GESTIÓN DE PAGOS =======");
            System.out.println("1. Agregar trabajador");
            System.out.println("2. Modificar sueldo");
            System.out.println("3. Mostrar tabla nómina");
            System.out.println("4. Salir");
            System.out.print("Seleccione: ");

            op = input.nextInt();

            switch (op) {
                case 1 -> agregar();
                case 2 -> modificar();
                case 3 -> mostrarTabla();
                case 4 -> System.out.println("Programa finalizado.");
                default -> System.out.println("Opción incorrecta.");
            }

        } while (op != 4);
    }

    static void agregar() {

        input.nextLine();

        System.out.print("Nombre: ");
        String nom = input.nextLine();

        System.out.print("Apellido: ");
        String ape = input.nextLine();

        System.out.print("Año ingreso: ");
        int anio = input.nextInt();

        System.out.print("Sueldo mensual: ");
        double sueldo = input.nextDouble();

        System.out.print("Descuento préstamo: ");
        double prest = input.nextDouble();

        String codigo = nom.substring(0, 1).toUpperCase()
                + ape.substring(0, 1).toUpperCase()
                + anio;

        lista.add(new Trabajador(nom, ape, anio, sueldo, prest, codigo));

        System.out.println("Trabajador agregado correctamente. Código: " + codigo);
    }

    static void mostrarTabla() {

        if (lista.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }

        System.out.println("\n========== EMPRESA MARKET GAMING ==========");
        System.out.println("Valores en RD$ - Nomina Febrero 2026");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        String linea = "+----------+--------------+--------------+--------------+--------------+--------------+--------------+--------------+----------------+----------------+";
        System.out.println(linea);

        System.out.printf("| %-8s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-14s | %-14s |\n",
                "CODIGO", "NOMBRE", "APELLIDO",
                "SUELDO", "AFP", "SFS", "ISR",
                "PREST.", "T.DESC", "NETO");

        System.out.println(linea);

        for (Trabajador t : lista) {

            System.out.printf("| %-8s | %-12s | %-12s | RD$ %8.2f | RD$ %8.2f | RD$ %8.2f | RD$ %8.2f | RD$ %8.2f | RD$ %10.2f | RD$ %10.2f |\n",
                    t.codigo,
                    t.nombre,
                    t.apellido,
                    t.sueldo,
                    t.afp,
                    t.sfs,
                    t.isr,
                    t.prestamo,
                    t.totalDesc,
                    t.neto);

            System.out.println(linea);
        }
    }

    static void modificar() {

        input.nextLine();

        System.out.print("Código del trabajador: ");
        String cod = input.nextLine();

        for (Trabajador t : lista) {

            if (t.codigo.equalsIgnoreCase(cod)) {

                System.out.print("Nuevo sueldo: ");
                double nuevo = input.nextDouble();

                t.actualizar(nuevo);
                System.out.println("Sueldo actualizado correctamente.");
                return;
            }
        }

        System.out.println("Código no encontrado.");
    }
}
