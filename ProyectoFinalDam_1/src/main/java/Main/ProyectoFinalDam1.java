package Main;

import servicios.BaseDeDatos.GestionBaseDeDatos;
import java.util.InputMismatchException;
import java.util.Scanner;
import servicios.BaseDeDatos.ConsultasEjercicios;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author isard
 */
public class ProyectoFinalDam1 {

    private static Scanner teclado = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        

        System.out.println("Proyecto Final");
        System.out.println("");

        // Pruebas Base de datos
        GestionBaseDeDatos.vincularBDD();
        boolean salir = false;

        // TODO ESTE MENU ES PARA REALIZAR PRUEBAS TODO ESTO HABRA QUE HACERLO EN SWING
        while (!salir) {
            
            System.out.println("\n========================================");
            System.out.println("       MENÚ GESTIÓN DE MATRÍCULAS       ");
            System.out.println("========================================");
            System.out.println("1. Introducir datos en la base de datos");
            System.out.println("2. Realizar consultas multitabla");
            System.out.println("3. Salir de la aplicación");
            System.out.print("Elija una opción (1-3): ");

            try {
                int opcionPrincipal = teclado.nextInt();
                teclado.nextLine();

                switch (opcionPrincipal) {
                    case 1 ->
                        System.out.println("\n[INFO] Ejecutando inserción de datos...");
                    // Aquí pondriamos para insertar los datos en la base de datos y crear los objetos correspondientes

                    case 2 ->
                        mostrarSubmenuConsultas();

                    case 3 -> {
                        salir = true;
                        System.out.println("\n¡Gracias por usar la aplicación! Saliendo...");
                    }

                    default ->
                        System.out.println("\n[ERROR] Opción no válida. Inténtelo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n[ERROR] Debe introducir un número entero.");
                teclado.nextLine();
            }
        }
        teclado.close();
    }

    /**
     * Muestra el submenú con las 9 consultas multitabla requeridas.
     */
    private static void mostrarSubmenuConsultas() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n----------------------------------------");
            System.out.println("         CONSULTAS MULTITABLA           ");
            System.out.println("----------------------------------------");
            System.out.println("1. Módulos y sus respectivos ciclos");
            System.out.println("2. Alumnos matriculados ");
            System.out.println("3. Módulos de alumnos matriculados en 2024/2025");
            System.out.println("4. Notas de alumnos ");
            System.out.println("5. Importe total recaudado por año académico");
            System.out.println("6. Total de créditos y horas matriculadas por alumno");
            System.out.println("7. Alumnos sin primera calificación asignada");
            System.out.println("8. Módulos con más de 3 alumnos repetidores ");
            System.out.println("9. Alumnos sin matrícula o con matrícula anulada");
            System.out.println("10. <- Volver al Menú Principal");
            System.out.print("Elija una consulta (1-10): ");

            try {
                int opcionConsulta = teclado.nextInt();
                teclado.nextLine();

                if (opcionConsulta >= 1 && opcionConsulta <= 9) {
                    System.out.println("\n[BD] Lanzando la Consulta " + opcionConsulta + " a la base de datos...");

                    switch (opcionConsulta) {
                        case 1 -> {
                            System.out.println("Consulta 1:");
                            String entradas[] = new String[0];
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta1, entradas, true, false);
                            System.out.println("");
                        }
                        case 2 -> {
                            String entradas[] = new String[2];
                            System.out.println("Introduce un nivel de ciclo");
                            entradas[0] = teclado.nextLine();
                            System.out.println("Introduce un curso de modulo");
                            entradas[1] = teclado.nextLine();

                            System.out.println("Consulta 2:");

                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta2, entradas, true, false);
                            System.out.println("");
                        }
                        case 3 -> {
                            String entradas[] = new String[1];
                            System.out.println("Introduce el año academico");
                            entradas[0] = teclado.next();

                            System.out.println("Consulta 3:");
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta3, entradas, true, false);
                            System.out.println("");
                        }
                        case 4 -> {
                            String entradas[] = new String[3];
                            System.out.println("Introduce la denominacion del ciclo");
                            entradas[0] = teclado.nextLine();
                            System.out.println("Introduce el curso del modulo");
                            entradas[1] = teclado.nextLine();
                            System.out.println("Introduce el anio academico");
                            entradas[2] = teclado.nextLine();

                            System.out.println("Consulta 4:");
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta4, entradas, true, false);
                        }
                        case 5 -> {
                            String entradas[] = new String[0];
                            System.out.println("Consulta 5:");
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta5, entradas, true, false);
                            System.out.println("");
                        }
                        case 6 -> {
                            String entradas[] = new String[0];
                            System.out.println("Consulta 6:");
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta6, entradas, true, false);
                            System.out.println("");
                        }
                        case 7 -> {
                            String entradas[] = new String[0];
                            System.out.println("Consulta 7:");
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta7, entradas, true, false);
                            System.out.println("");
                        }
                        case 8 -> {
                            String entradas[] = new String[0];
                            System.out.println("Consulta 8:");
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta8, entradas, true, false);
                            System.out.println("");
                        }
                        case 9 -> {
                            String entradas[] = new String[0];
                            System.out.println("Consulta 9:");
                            GestionBaseDeDatos.realizarSQL(ConsultasEjercicios.datosConsulta9, entradas, true, false);
                            System.out.println("");
                        }


                    }

                } else if (opcionConsulta == 10) {
                    volver = true; // Rompe este bucle y regresa automáticamente al menú principal
                } else {
                    System.out.println("\n[ERROR] Opción fuera de rango (1-10).");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n[ERROR] Debe introducir un número entero.");
                teclado.nextLine();
            }
        }

        System.out.println("");

        //Pruebas modelos
        //Alumno alumno_1 = new Alumno(nombre, LocalDate.MIN, domicilio, 0, correo);
        //Ciclo cilo_1 = new Ciclo(0, nombre, denominacion, familiaProfesional, 0, 0);
        //LineaMatricula lineaMatricula_1 = new LineaMatricula(0, 0, 0);
        //Matricula matricula_1 = new Matricula(0, estado, 0);
        //Modulo modulo_1 = new Modulo(nombre, curso, 0, 0);
        //Exportar datos
        //ArrayList = Coger datos de BD()
        //for
        //...convertoCSV(Objeto.toCSV)
    }
}
