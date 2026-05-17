package menus;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import com.google.gson.Gson;
import excepciones.YaImportadoException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import modelos.Alumno;
import servicios.BaseDeDatos.ConsultasSQL;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

/**
 * Menú de gestión completa de la tabla alumno. Incluye operaciones CRUD,
 * exportación/importación de ficheros y visualización de datos insertados
 * durante la sesión.
 *
 * @author 1DAM
 */
public class MenuAlumno {

    /**
     * Formato de fecha usado en la entrada de datos por teclado.
     */
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Lista de alumnos insertados durante la ejecución actual.
     */
    private static final ArrayList<Alumno> alumnosSesion = new ArrayList<>();

    /**
     * Flags que indican si ya se ha importado desde cada formato (evita doble
     * importación).
     */
    private static boolean importadoTxt = false;
    private static boolean importadoCsv = false;
    private static boolean importadoBin = false;
    private static boolean importadoJson = false;

    // =========================================================
    // =================== MENÚ PRINCIPAL ======================
    // =========================================================
    /**
     * Muestra el menú de gestión de alumnos y gestiona la navegación.
     *
     * @param teclado Scanner compartido con el Main para la entrada de datos.
     */
    public static void mostrarMenu(Scanner teclado) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n========================================");
            System.out.println("        GESTIÓN DE ALUMNOS             ");
            System.out.println("========================================");
            System.out.println("1. Insertar alumno");
            System.out.println("2. Actualizar alumno por código");
            System.out.println("3. Eliminar alumno por código");
            System.out.println("4. Consultar alumno por código");
            System.out.println("5. Consultar todos los alumnos (ordenado por nombre)");
            System.out.println("6. Exportar tabla");
            System.out.println("7. Importar tabla");
            System.out.println("8. Ver datos insertados en esta sesión");
            System.out.println("9. <- Volver al menú anterior");
            System.out.print("Elija una opción (1-9): ");

            try {
                int opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1 ->
                        insertarAlumno(teclado);
                    case 2 ->
                        actualizarAlumno(teclado);
                    case 3 ->
                        eliminarAlumno(teclado);
                    case 4 ->
                        consultarPorCodigo(teclado);
                    case 5 ->
                        consultarTodos();
                    case 6 ->
                        mostrarMenuExportar(teclado);
                    case 7 ->
                        mostrarMenuImportar(teclado);
                    case 8 ->
                        verDatosSesion();
                    case 9 ->
                        volver = true;
                    default ->
                        System.out.println("[ERROR] Opción no válida. Introduzca un número entre 1 y 9.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Debe introducir un número entero.");
                teclado.nextLine();
            }
        }
    }

    // =========================================================
    // ========================= CRUD ==========================
    // =========================================================
    /**
     * Solicita los datos de un nuevo alumno por teclado, lo inserta en la base
     * de datos y lo añade a la colección de sesión.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void insertarAlumno(Scanner teclado) {
        System.out.println("\n--- INSERTAR ALUMNO ---");

        try {
            System.out.print("Nombre: ");
            String nombre = teclado.nextLine().trim();

            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            String fechaStr = teclado.nextLine().trim();
            LocalDate fechaNacimiento = LocalDate.parse(fechaStr, FORMATO_FECHA);

            System.out.print("Domicilio (tipo vía, nombre, número, localidad, provincia): ");
            String domicilio = teclado.nextLine().trim();

            System.out.print("Teléfono (9 dígitos): ");
            String telefono = teclado.nextLine().trim();

            System.out.print("Correo electrónico: ");
            String correo = teclado.nextLine().trim();

            if (!Validadores.validarTextoNoVacio(nombre)
                    || !Validadores.validarTextoNoVacio(domicilio)
                    || !Validadores.validarTelefono(telefono)
                    || !Validadores.validarCorreo(correo)) {
                System.out.println("[ERROR] Datos inválidos. Compruebe el teléfono (9 dígitos) y el correo.");
                return;
            }

            String[] entradas = {
                nombre,
                fechaNacimiento.toString(),
                domicilio,
                telefono,
                correo
            };

            GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_ALUMNO, entradas);

            Alumno alumno = new Alumno(nombre, fechaNacimiento, domicilio, telefono, correo);
            alumnosSesion.add(alumno);
            SesionDatos.listaAlumnos.add(alumno);

            System.out.println("[OK] Alumno insertado correctamente.");

        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] Formato de fecha incorrecto. Use dd/MM/yyyy.");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /**
     * Solicita el código de un alumno existente y los nuevos valores de sus
     * campos, y actualiza el registro en la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void actualizarAlumno(Scanner teclado) {
        System.out.println("\n--- ACTUALIZAR ALUMNO ---");

        try {
            System.out.print("Código del alumno a actualizar: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Nuevo nombre: ");
            String nombre = teclado.nextLine().trim();

            System.out.print("Nueva fecha de nacimiento (dd/MM/yyyy): ");
            String fechaStr = teclado.nextLine().trim();
            LocalDate fechaNacimiento = LocalDate.parse(fechaStr, FORMATO_FECHA);

            System.out.print("Nuevo domicilio: ");
            String domicilio = teclado.nextLine().trim();

            System.out.print("Nuevo teléfono (9 dígitos): ");
            String telefono = teclado.nextLine().trim();

            System.out.print("Nuevo correo electrónico: ");
            String correo = teclado.nextLine().trim();

            if (!Validadores.validarCodigoPositivo(codigo)
                    || !Validadores.validarTextoNoVacio(nombre)
                    || !Validadores.validarTextoNoVacio(domicilio)
                    || !Validadores.validarTelefono(telefono)
                    || !Validadores.validarCorreo(correo)) {
                System.out.println("[ERROR] Datos inválidos.");
                return;
            }

            String[] entradas = {
                nombre,
                fechaNacimiento.toString(),
                domicilio,
                telefono,
                correo,
                String.valueOf(codigo)
            };

            GestionBaseDeDatos.actualizarFila(ConsultasSQL.UPDATE_ALUMNO, entradas);
            System.out.println("[OK] Alumno actualizado correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] El código debe ser un número entero.");
            teclado.nextLine();
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] Formato de fecha incorrecto. Use dd/MM/yyyy.");
        }
    }

    /**
     * Solicita el código de un alumno y lo elimina de la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void eliminarAlumno(Scanner teclado) {
        System.out.println("\n--- ELIMINAR ALUMNO ---");

        try {
            System.out.print("Código del alumno a eliminar: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.print("¿Confirma la eliminación del alumno con código " + codigo + "? (s/n): ");
            String confirmacion = teclado.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                GestionBaseDeDatos.eliminarFila(ConsultasSQL.DELETE_ALUMNO, new String[]{String.valueOf(codigo)});
                System.out.println("[OK] Alumno eliminado correctamente.");
            } else {
                System.out.println("[INFO] Eliminación cancelada.");
            }

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] El código debe ser un número entero.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita el código de un alumno y muestra sus datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void consultarPorCodigo(Scanner teclado) {
        System.out.println("\n--- CONSULTAR ALUMNO POR CÓDIGO ---");

        try {
            System.out.print("Código del alumno: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.println("\n" + formatearCabecera(ConsultasSQL.SELECT_ALUMNO_POR_CODIGO));
            GestionBaseDeDatos.realizarConsultaSQL(
                    ConsultasSQL.SELECT_ALUMNO_POR_CODIGO,
                    new String[]{String.valueOf(codigo)},
                    true,
                    false
            );

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] El código debe ser un número entero.");
            teclado.nextLine();
        }
    }

    /**
     * Muestra todos los alumnos ordenados por nombre de forma ascendente.
     */
    private static void consultarTodos() {
        System.out.println("\n--- TODOS LOS ALUMNOS (ordenado por nombre ASC) ---");
        System.out.println(formatearCabecera(ConsultasSQL.SELECT_ALUMNO_TODOS));
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SELECT_ALUMNO_TODOS, new String[0], true, false);
    }

    // =========================================================
    // ==================== EXPORTAR ===========================
    // =========================================================
    /**
     * Submenú de exportación de la tabla alumno a distintos formatos de
     * fichero.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuExportar(Scanner teclado) {
        System.out.println("\n--- EXPORTAR TABLA ALUMNO ---");
        System.out.println("1. Exportar a TXT  (separador ;)");
        System.out.println("2. Exportar a CSV  (separador :)");
        System.out.println("3. Exportar a Binario");
        System.out.println("4. Exportar a JSON");
        System.out.println("5. <- Volver");
        System.out.print("Elija una opción (1-5): ");

        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();

            cargarAlumnosDesdeBD();

            switch (opcion) {
                case 1 ->
                    exportarATxt();
                case 2 ->
                    exportarACsv();
                case 3 ->
                    exportarABinario();
                case 4 ->
                    exportarAJson();
                case 5 -> {
                    /* volver */ }
                default ->
                    System.out.println("[ERROR] Opción no válida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Debe introducir un número entero.");
            teclado.nextLine();
        }
    }

    /**
     * Exporta todos los alumnos a un fichero TXT con separador ";".
     * Sobreescribe el fichero si ya existe.
     */
    private static void exportarATxt() {
        exportarAFicheroTexto(Config.ficheroAlumno + ".txt", false);
    }

    /**
     * Exporta todos los alumnos a un fichero CSV con separador ":".
     * Sobreescribe el fichero si ya existe.
     */
    private static void exportarACsv() {
        exportarAFicheroTexto(Config.ficheroAlumno + ".csv", true);
    }

    /**
     * Escribe la lista de alumnos en un fichero de texto.
     *
     * @param rutaFichero Ruta completa del fichero de salida.
     * @param usarDosPuntos true para usar ":" como separador (CSV); false para
     * ";" (TXT).
     */
    private static void exportarAFicheroTexto(String rutaFichero, boolean usarDosPuntos) {
        try ( PrintWriter pw = new PrintWriter(new FileWriter(rutaFichero, false))) {
            for (Alumno alumno : SesionDatos.listaAlumnos) {
                String linea = alumno.toCSV();
                if (usarDosPuntos) {
                    linea = linea.replace(";", ":");
                }
                pw.println(linea);
            }
            System.out.println("[OK] Exportados " + SesionDatos.listaAlumnos.size()
                    + " registros a: " + rutaFichero);
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar: " + e.getMessage());
        }
    }

    /**
     * Exporta todos los alumnos a un fichero binario (.dat).
     */
    private static void exportarABinario() {
        GestionFicheros.guardarToBinario(Config.ficheroAlumno, SesionDatos.listaAlumnos);
        System.out.println("[OK] Exportados " + SesionDatos.listaAlumnos.size()
                + " registros a: " + Config.ficheroAlumno + ".dat");
    }

    /**
     * Exporta todos los alumnos a un fichero JSON (un objeto JSON por línea).
     */
    private static void exportarAJson() {
        try ( PrintWriter pw = new PrintWriter(new FileWriter(Config.ficheroAlumno + ".json", false))) {
            for (Alumno alumno : SesionDatos.listaAlumnos) {
                pw.println(alumno.toJSON());
            }
            System.out.println("[OK] Exportados " + SesionDatos.listaAlumnos.size()
                    + " registros a: " + Config.ficheroAlumno + ".json");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar a JSON: " + e.getMessage());
        }
    }

    // =========================================================
    // ==================== IMPORTAR ===========================
    // =========================================================
    /**
     * Submenú de importación de la tabla alumno desde distintos formatos de
     * fichero. Lanza YaImportadoException si el formato seleccionado ya fue
     * importado.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuImportar(Scanner teclado) {
        System.out.println("\n--- IMPORTAR TABLA ALUMNO ---");
        System.out.println("1. Importar desde TXT");
        System.out.println("2. Importar desde CSV");
        System.out.println("3. Importar desde Binario");
        System.out.println("4. Importar desde JSON");
        System.out.println("5. <- Volver");
        System.out.print("Elija una opción (1-5): ");

        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 ->
                    importarDesdeTxt();
                case 2 ->
                    importarDesdeCsv();
                case 3 ->
                    importarDesdeBinario();
                case 4 ->
                    importarDesdeJson();
                case 5 -> {
                    /* volver */ }
                default ->
                    System.out.println("[ERROR] Opción no válida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Debe introducir un número entero.");
            teclado.nextLine();
        } catch (YaImportadoException e) {
            System.out.println("[AVISO] " + e.getMessage());
        }
    }

    /**
     * Importa alumnos desde un fichero TXT (separador ";") e inserta cada
     * registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde TXT en esta
     * sesión.
     */
    private static void importarDesdeTxt() throws YaImportadoException {
        if (importadoTxt) {
            throw new YaImportadoException("La tabla alumno ya fue importada desde TXT en esta sesión.");
        }

        ArrayList<String> lineas = GestionFicheros.leerTxtCsv(Config.ficheroAlumno, ".txt");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero TXT está vacío o no existe.");
            return;
        }

        int contadorImportados = 0;

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Alumno alumno = Alumno.obtenerLineas(linea);

                String[] entradas = {
                    String.valueOf(alumno.getCodigo()),
                    alumno.getNombre(),
                    String.valueOf(alumno.getFechaNacimiento()),
                    alumno.getDomicilio(),
                    alumno.getTelefono(),
                    alumno.getCorreo()
                };

                GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_ALUMNO_CON_CODIGO, entradas);
                SesionDatos.listaAlumnos.add(alumno);
                contadorImportados++;
            }
        }

        importadoTxt = true;
        System.out.println("[OK] Importados " + contadorImportados + " alumnos desde TXT.");
    }

    /**
     * Importa alumnos desde un fichero CSV (separador ":") e inserta cada
     * registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde CSV en esta
     * sesión.
     */
    private static void importarDesdeCsv() throws YaImportadoException {
        if (importadoCsv) {
            throw new YaImportadoException("La tabla alumno ya fue importada desde CSV en esta sesión.");
        }

        ArrayList<String> lineas = GestionFicheros.leerTxtCsv(Config.ficheroAlumno, ".csv");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero CSV está vacío o no existe.");
            return;
        }

        int contadorImportados = 0;

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                // Normaliza el separador ":" a ";" para reutilizar obtenerLineas
                Alumno alumno = Alumno.obtenerLineas(linea.replace(":", ";"));

                String[] entradas = {
                    String.valueOf(alumno.getCodigo()),
                    alumno.getNombre(),
                    String.valueOf(alumno.getFechaNacimiento()),
                    alumno.getDomicilio(),
                    alumno.getTelefono(),
                    alumno.getCorreo()
                };

                GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_ALUMNO_CON_CODIGO, entradas);
                SesionDatos.listaAlumnos.add(alumno);
                contadorImportados++;
            }
        }

        importadoCsv = true;
        System.out.println("[OK] Importados " + contadorImportados + " alumnos desde CSV.");
    }

    /**
     * Importa alumnos desde un fichero binario (.dat) e inserta cada registro
     * en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde Binario en esta
     * sesión.
     */
    private static void importarDesdeBinario() throws YaImportadoException {
        if (importadoBin) {
            throw new YaImportadoException("La tabla alumno ya fue importada desde Binario en esta sesión.");
        }

        int contadorImportados = 0;

        try ( ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Config.ficheroAlumno + ".dat"))) {

            @SuppressWarnings("unchecked")
            ArrayList<Alumno> lista = (ArrayList<Alumno>) ois.readObject();

            if (lista == null || lista.isEmpty()) {
                System.out.println("[INFO] El fichero binario está vacío o no existe.");
                return;
            }

            for (Alumno alumno : lista) {
                String[] entradas = {
                    String.valueOf(alumno.getCodigo()),
                    alumno.getNombre(),
                    String.valueOf(alumno.getFechaNacimiento()),
                    alumno.getDomicilio(),
                    alumno.getTelefono(),
                    alumno.getCorreo()
                };

                GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_ALUMNO_CON_CODIGO, entradas);
                SesionDatos.listaAlumnos.add(alumno);
                contadorImportados++;
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("[INFO] El fichero binario no existe: " + Config.ficheroAlumno + ".dat");
            return;
        } catch (java.io.IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] Error al leer el fichero binario: " + e.getMessage());
            return;
        }

        importadoBin = true;
        System.out.println("[OK] Importados " + contadorImportados + " alumnos desde Binario.");
    }

    /**
     * Importa alumnos desde un fichero JSON e inserta cada registro en la base
     * de datos.
     *
     * @throws YaImportadoException si ya fue importado desde JSON en esta
     * sesión.
     */
    private static void importarDesdeJson() throws YaImportadoException {
        if (importadoJson) {
            throw new YaImportadoException("La tabla alumno ya fue importada desde JSON en esta sesión.");
        }

        ArrayList<String> lineas = GestionFicheros.leerJson(Config.ficheroAlumno);
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero JSON está vacío o no existe.");
            return;
        }

        int contadorImportados = 0;
        Gson gson = new Gson();

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Alumno alumno = gson.fromJson(linea, Alumno.class);

                String[] entradas = {
                    String.valueOf(alumno.getCodigo()),
                    alumno.getNombre(),
                    String.valueOf(alumno.getFechaNacimiento()),
                    alumno.getDomicilio(),
                    alumno.getTelefono(),
                    alumno.getCorreo()
                };

                GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_ALUMNO_CON_CODIGO, entradas);
                SesionDatos.listaAlumnos.add(alumno);
                contadorImportados++;
            }
        }

        importadoJson = true;
        System.out.println("[OK] Importados " + contadorImportados + " alumnos desde JSON.");
    }

    // =========================================================
    // =================== SESIÓN ==============================
    // =========================================================
    /**
     * Muestra en consola los alumnos insertados durante la sesión actual.
     */
    private static void verDatosSesion() {
        System.out.println("\n--- ALUMNOS INSERTADOS EN ESTA SESIÓN ---");
        if (alumnosSesion.isEmpty()) {
            System.out.println("[INFO] No se han insertado alumnos en esta sesión.");
        } else {
            System.out.println(formatearCabecera(ConsultasSQL.SELECT_ALUMNO_TODOS));
            for (Alumno alumno : alumnosSesion) {
                System.out.println(alumno.toString());
            }
            System.out.println("Total insertados: " + alumnosSesion.size());
        }
    }

    // =========================================================
    // ==================== AUXILIARES =========================
    // =========================================================
    /**
     * Carga todos los alumnos de la base de datos en la lista en memoria.
     */
    private static void cargarAlumnosDesdeBD() {
        SesionDatos.listaAlumnos.clear();
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SAVE_ALUMNO_TODOS, new String[0], false, true);
    }

    /**
     * Construye una cabecera legible a partir de los nombres de columna
     * definidos en el array de datos de la consulta.
     *
     * @param datosConsulta Array con la SQL en [0] y columnas en [1..n].
     * @return Cadena formateada con los nombres de columna separados por " | ".
     */
    private static String formatearCabecera(String[] datosConsulta) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < datosConsulta.length; i++) {
            sb.append(String.format("%-22s", datosConsulta[i].toUpperCase()));
        }
        sb.append("\n").append("-".repeat(sb.length()));
        return sb.toString();
    }
}
