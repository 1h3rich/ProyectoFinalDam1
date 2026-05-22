package Main;

import java.util.InputMismatchException;
import java.util.Scanner;
import Menus.*;
import pantallas.PantallaPrincipal;
import servicios.BaseDeDatos.GestionBaseDeDatos;

/**
 * Clase principal de la aplicación de gestión del centro de formación.
 *
 * Arranca la conexión con la base de datos, presenta el menú principal y delega
 * en cada submenú especializado (Alumno, Ciclo, Módulo, Matrícula, Línea de
 * Matrícula y Consultas Multitabla).
 *
 * El Scanner se crea aquí y se comparte con todos los submenús para evitar que
 * se cierre prematuramente.
 *
 * @author 1DAM
 */
public class ProyectoFinalDam1 {

    /** Clase de utilidad; no instanciable. */
    private ProyectoFinalDam1() {}

    /**
     * Scanner compartido con todos los submenús de la aplicación.
     */
    private static final Scanner teclado = new Scanner(System.in);

    // =========================================================
    // ========================= MAIN ==========================
    // =========================================================
    /**
     * Punto de entrada de la aplicación. Conecta con la base de datos y lanza
     * el bucle del menú principal.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        pantallas.PantallaPrincipal pantallaPrincipal = new PantallaPrincipal();
        pantallaPrincipal.setVisible(true);
        mostrarBienvenida();

        GestionBaseDeDatos.vincularBDD();

        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();

            try {
                int opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1 ->
                        MenuAlumno.mostrarMenu(teclado);
                    case 2 ->
                        MenuCiclo.mostrarMenu(teclado);
                    case 3 ->
                        MenuModulo.mostrarMenu(teclado);
                    case 4 ->
                        MenuMatricula.mostrarMenu(teclado);
                    case 5 ->
                        MenuLineaMatricula.mostrarMenu(teclado);
                    case 6 ->
                        MenuConsultas.mostrarMenu(teclado);
                    case 7 -> {
                        salir = true;
                        System.out.println("\n¡Hasta pronto! Cerrando la aplicación...");
                    }
                    default ->
                        System.out.println("[ERROR] Opción no válida. Introduzca un número entre 1 y 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Debe introducir un número entero.");
                teclado.nextLine();
            }
        }

        GestionBaseDeDatos.cerrarBDD();
        teclado.close();
    }

    // =========================================================
    // ==================== AUXILIARES =========================
    // =========================================================
    /**
     * Muestra la pantalla de bienvenida al arrancar la aplicación.
     */
    private static void mostrarBienvenida() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN - CENTRO FORMACIÓN  ║");
        System.out.println("║              base de datos:              ║");
        System.out.println("║             centro_formacion             ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Imprime en consola el menú principal con todas las opciones disponibles.
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("\n==========================================");
        System.out.println("            MENÚ PRINCIPAL               ");
        System.out.println("==========================================");
        System.out.println("1. Gestión de Alumnos");
        System.out.println("2. Gestión de Ciclos");
        System.out.println("3. Gestión de Módulos");
        System.out.println("4. Gestión de Matrículas");
        System.out.println("5. Gestión de Líneas de Matrícula");
        System.out.println("6. Consultas Multitabla");
        System.out.println("7. Salir");
        System.out.print("Elija una opción (1-7): ");
    }
}
