package menus;

import java.util.InputMismatchException;
import java.util.Scanner;
import servicios.BaseDeDatos.ConsultasEjercicios;
import servicios.BaseDeDatos.GestionBaseDeDatos;

/**
 * Menú de consultas multitabla del proyecto centro_formacion.
 * Agrupa las 9 consultas SQL requeridas por el enunciado del proyecto,
 * algunas de las cuales solicitan parámetros al usuario (nivel de ciclo,
 * curso de módulo, año académico, denominación de ciclo).
 *
 * @author 1DAM
 */
public class MenuConsultas {

    // =========================================================
    // =================== MENÚ PRINCIPAL ======================
    // =========================================================

    /**
     * Muestra el submenú de consultas multitabla y gestiona la navegación.
     *
     * @param teclado Scanner compartido con el Main para la entrada de datos.
     */
    public static void mostrarMenu(Scanner teclado) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n========================================");
            System.out.println("        CONSULTAS MULTITABLA            ");
            System.out.println("========================================");
            System.out.println("1.  Módulos y ciclos a los que pertenecen");
            System.out.println("2.  Alumnos matriculados por nivel y curso");
            System.out.println("3.  Módulos en los que está matriculado un alumno (por año académico)");
            System.out.println("4.  Calificaciones de alumnos (por ciclo, curso y año académico)");
            System.out.println("5.  Importe total recaudado por ciclo y año académico");
            System.out.println("6.  Total de créditos ECTS y horas matriculadas por alumno");
            System.out.println("7.  Alumnos con calificación primera pendiente (nula)");
            System.out.println("8.  Módulos con más de 3 alumnos repetidores");
            System.out.println("9.  Alumnos sin matrícula o con matrícula anulada");
            System.out.println("10. <- Volver al menú principal");
            System.out.print("Elija una consulta (1-10): ");

            try {
                int opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1  -> ejecutarConsulta1();
                    case 2  -> ejecutarConsulta2(teclado);
                    case 3  -> ejecutarConsulta3(teclado);
                    case 4  -> ejecutarConsulta4(teclado);
                    case 5  -> ejecutarConsulta5();
                    case 6  -> ejecutarConsulta6();
                    case 7  -> ejecutarConsulta7();
                    case 8  -> ejecutarConsulta8();
                    case 9  -> ejecutarConsulta9();
                    case 10 -> volver = true;
                    default -> System.out.println("[ERROR] Opción fuera de rango (1-10).");
                }
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Debe introducir un número entero.");
                teclado.nextLine();
            }
        }
    }

    // =========================================================
    // ================ EJECUCIÓN DE CONSULTAS =================
    // =========================================================

    /**
     * Consulta 1: Denominación, familia profesional y nivel de todos los ciclos,
     * con los nombres y horas de sus módulos. Ordenado por denominación ASC.
     */
    private static void ejecutarConsulta1() {
        System.out.println("\n--- CONSULTA 1: Módulos y sus ciclos ---");
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasEjercicios.datosConsulta1, new String[0], true, false);
    }

    /**
     * Consulta 2: Nombre y horas de módulos que pertenecen a un nivel de ciclo
     * y se imparten en un curso dados, con el número de alumnos matriculados.
     * Ordenado por nombre del módulo ASC.
     *
     * @param teclado Scanner para leer nivel de ciclo y curso de módulo.
     */
    private static void ejecutarConsulta2(Scanner teclado) {
        System.out.println("\n--- CONSULTA 2: Alumnos matriculados por nivel de ciclo y curso ---");

        System.out.print("Introduce el nivel del ciclo (básico/medio/superior): ");
        String nivelCiclo = teclado.nextLine().trim();

        System.out.print("Introduce el curso del módulo (primero/segundo): ");
        String cursoModulo = teclado.nextLine().trim();

        GestionBaseDeDatos.realizarConsultaSQL(
                ConsultasEjercicios.datosConsulta2,
                new String[]{nivelCiclo, cursoModulo},
                true, false
        );
    }

    /**
     * Consulta 3: Nombre y fecha de nacimiento de todos los alumnos, con los
     * módulos en los que están matriculados en un año académico dado.
     * Ordenado por nombre del alumno ASC.
     *
     * @param teclado Scanner para leer el año académico.
     */
    private static void ejecutarConsulta3(Scanner teclado) {
        System.out.println("\n--- CONSULTA 3: Módulos de alumnos por año académico ---");

        System.out.print("Introduce el año académico (ej. 2024): ");
        String añoAcademico = teclado.nextLine().trim();

        GestionBaseDeDatos.realizarConsultaSQL(
                ConsultasEjercicios.datosConsulta3,
                new String[]{añoAcademico},
                true, false
        );
    }

    /**
     * Consulta 4: Nombre y fecha de nacimiento de alumnos matriculados en un curso
     * dado de un ciclo dado para un año académico dado, con calificaciones primera
     * y segunda de sus módulos. Ordenado por nombre del alumno ASC.
     *
     * @param teclado Scanner para leer denominación del ciclo, curso y año académico.
     */
    private static void ejecutarConsulta4(Scanner teclado) {
        System.out.println("\n--- CONSULTA 4: Calificaciones de alumnos ---");

        System.out.print("Introduce la denominación del ciclo: ");
        String denominacion = teclado.nextLine().trim();

        System.out.print("Introduce el curso del módulo (primero/segundo): ");
        String curso = teclado.nextLine().trim();

        System.out.print("Introduce el año académico (ej. 2024): ");
        String añoAcademico = teclado.nextLine().trim();

        GestionBaseDeDatos.realizarConsultaSQL(
                ConsultasEjercicios.datosConsulta4,
                new String[]{denominacion, curso, añoAcademico},
                true, false
        );
    }

    /**
     * Consulta 5: Denominación, familia profesional y nivel de todos los ciclos,
     * con el importe total de matrículas por año académico.
     * Ordenado por denominación ASC.
     */
    private static void ejecutarConsulta5() {
        System.out.println("\n--- CONSULTA 5: Importe total recaudado por ciclo y año académico ---");
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasEjercicios.datosConsulta5, new String[0], true, false);
    }

    /**
     * Consulta 6: Nombre del alumno y año académico, con el total de créditos ECTS
     * y total de horas matriculadas. Ordenado por total de créditos DESC.
     */
    private static void ejecutarConsulta6() {
        System.out.println("\n--- CONSULTA 6: Créditos ECTS y horas totales por alumno ---");
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasEjercicios.datosConsulta6, new String[0], true, false);
    }

    /**
     * Consulta 7: Nombre del alumno, nombre del módulo y año académico donde
     * la calificación primera es nula. Ordenado por año académico ASC.
     */
    private static void ejecutarConsulta7() {
        System.out.println("\n--- CONSULTA 7: Alumnos con calificación primera pendiente ---");
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasEjercicios.datosConsulta7, new String[0], true, false);
    }

    /**
     * Consulta 8: Nombre del módulo y denominación del ciclo con más de 3 alumnos
     * cuya repetición sea superior a 1.
     */
    private static void ejecutarConsulta8() {
        System.out.println("\n--- CONSULTA 8: Módulos con más de 3 alumnos repetidores ---");
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasEjercicios.datosConsulta8, new String[0], true, false);
    }

    /**
     * Consulta 9: Nombre, teléfono y correo de alumnos sin matrícula o con
     * matrícula en estado "anulada". Ordenado por nombre del alumno ASC.
     */
    private static void ejecutarConsulta9() {
        System.out.println("\n--- CONSULTA 9: Alumnos sin matrícula o con matrícula anulada ---");
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasEjercicios.datosConsulta9, new String[0], true, false);
    }
}
