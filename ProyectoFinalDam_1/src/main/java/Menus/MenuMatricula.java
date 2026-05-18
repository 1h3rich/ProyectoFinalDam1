package menus;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import Utils.GsonUtils;
import com.google.gson.Gson;
import excepciones.YaImportadoException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import modelos.Matricula;
import servicios.BaseDeDatos.ConsultasSQL;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

/**
 * Menú de gestión completa de la tabla matricula.
 * Incluye operaciones CRUD, exportación/importación de ficheros
 * y visualización de datos insertados durante la sesión.
 *
 * Estados válidos: Activa, No activa.
 *
 * @author 1DAM
 */
public class MenuMatricula {

    /** Lista de matrículas insertadas durante la ejecución actual. */
    private static final ArrayList<Matricula> matriculasSesion = new ArrayList<>();

    /** Flags que indican si ya se ha importado desde cada formato. */
    private static boolean importadoTxt  = false;
    private static boolean importadoCsv  = false;
    private static boolean importadoBin  = false;
    private static boolean importadoJson = false;

    // =========================================================
    // =================== MENÚ PRINCIPAL ======================
    // =========================================================

    /**
     * Muestra el menú de gestión de matrículas y gestiona la navegación.
     *
     * @param teclado Scanner compartido con el Main para la entrada de datos.
     */
    public static void mostrarMenu(Scanner teclado) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n========================================");
            System.out.println("       GESTIÓN DE MATRÍCULAS           ");
            System.out.println("========================================");
            System.out.println("1. Insertar matrícula");
            System.out.println("2. Actualizar matrícula por código");
            System.out.println("3. Eliminar matrícula por código");
            System.out.println("4. Consultar matrícula por código");
            System.out.println("5. Consultar todas las matrículas (ordenado por estado)");
            System.out.println("6. Exportar tabla");
            System.out.println("7. Importar tabla");
            System.out.println("8. Ver datos insertados en esta sesión");
            System.out.println("9. <- Volver al menú anterior");
            System.out.print("Elija una opción (1-9): ");

            try {
                int opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1 -> insertarMatricula(teclado);
                    case 2 -> actualizarMatricula(teclado);
                    case 3 -> eliminarMatricula(teclado);
                    case 4 -> consultarPorCodigo(teclado);
                    case 5 -> consultarTodos();
                    case 6 -> mostrarMenuExportar(teclado);
                    case 7 -> mostrarMenuImportar(teclado);
                    case 8 -> verDatosSesion();
                    case 9 -> volver = true;
                    default -> System.out.println("[ERROR] Opción no válida. Introduzca un número entre 1 y 9.");
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
     * Solicita los datos de una nueva matrícula por teclado, la inserta en la base
     * de datos y la añade a la colección de sesión.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void insertarMatricula(Scanner teclado) {
        System.out.println("\n--- INSERTAR MATRÍCULA ---");

        try {
            System.out.print("Código del alumno: ");
            int codigoAlumno = teclado.nextInt();

            System.out.print("Año académico (ej. 2024): ");
            int añoAcademico = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Estado (Activa/No activa): ");
            String estado = teclado.nextLine().trim();

            System.out.print("Importe (ej. 350.00): ");
            double importe = teclado.nextDouble();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigoAlumno)) {
                System.out.println("[ERROR] El código del alumno debe ser mayor que 0.");
                return;
            }
            if (!Validadores.validarAñoAcademico(añoAcademico)) {
                System.out.println("[ERROR] El año académico no es válido (rango 1900-3000).");
                return;
            }
            if (!Validadores.validarEstado(estado)) {
                System.out.println("[ERROR] El estado debe ser exactamente 'Activa' o 'No activa'.");
                return;
            }
            if (!Validadores.validarImporte(importe)) {
                System.out.println("[ERROR] El importe no puede ser negativo.");
                return;
            }

            String[] entradas = {
                String.valueOf(codigoAlumno),
                String.valueOf(añoAcademico),
                estado,
                String.valueOf(importe)
            };

            GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_MATRICULA, entradas);

            Matricula matricula = new Matricula(codigoAlumno, añoAcademico, estado, importe);
            matriculasSesion.add(matricula);
            SesionDatos.listaMatriculas.add(matricula);

            System.out.println("[OK] Matrícula insertada correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Código, año e importe deben ser numéricos.");
            teclado.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /**
     * Solicita el código de una matrícula existente y los nuevos valores de sus campos,
     * y actualiza el registro en la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void actualizarMatricula(Scanner teclado) {
        System.out.println("\n--- ACTUALIZAR MATRÍCULA ---");

        try {
            System.out.print("Código de la matrícula a actualizar: ");
            int codigo = teclado.nextInt();

            System.out.print("Nuevo código del alumno: ");
            int codigoAlumno = teclado.nextInt();

            System.out.print("Nuevo año académico: ");
            int añoAcademico = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Nuevo estado (Activa/No activa): ");
            String estado = teclado.nextLine().trim();

            System.out.print("Nuevo importe: ");
            double importe = teclado.nextDouble();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código de la matrícula debe ser mayor que 0.");
                return;
            }
            if (!Validadores.validarCodigoPositivo(codigoAlumno)) {
                System.out.println("[ERROR] El código del alumno debe ser mayor que 0.");
                return;
            }
            if (!Validadores.validarAñoAcademico(añoAcademico)) {
                System.out.println("[ERROR] El año académico no es válido (rango 1900-3000).");
                return;
            }
            if (!Validadores.validarEstado(estado)) {
                System.out.println("[ERROR] El estado debe ser exactamente 'Activa' o 'No activa'.");
                return;
            }
            if (!Validadores.validarImporte(importe)) {
                System.out.println("[ERROR] El importe no puede ser negativo.");
                return;
            }

            String[] entradas = {
                String.valueOf(codigoAlumno),
                String.valueOf(añoAcademico),
                estado,
                String.valueOf(importe),
                String.valueOf(codigo)
            };

            GestionBaseDeDatos.actualizarFila(ConsultasSQL.UPDATE_MATRICULA, entradas);
            System.out.println("[OK] Matrícula actualizada correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Los valores numéricos deben ser enteros o decimales.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita el código de una matrícula y la elimina de la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void eliminarMatricula(Scanner teclado) {
        System.out.println("\n--- ELIMINAR MATRÍCULA ---");

        try {
            System.out.print("Código de la matrícula a eliminar: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.print("¿Confirma la eliminación de la matrícula con código " + codigo + "? (s/n): ");
            String confirmacion = teclado.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                GestionBaseDeDatos.eliminarFila(ConsultasSQL.DELETE_MATRICULA, new String[]{String.valueOf(codigo)});
                System.out.println("[OK] Matrícula eliminada correctamente.");
            } else {
                System.out.println("[INFO] Eliminación cancelada.");
            }

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] El código debe ser un número entero.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita el código de una matrícula y muestra sus datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void consultarPorCodigo(Scanner teclado) {
        System.out.println("\n--- CONSULTAR MATRÍCULA POR CÓDIGO ---");

        try {
            System.out.print("Código de la matrícula: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.println("\n" + formatearCabecera(ConsultasSQL.SELECT_MATRICULA_POR_CODIGO));
            GestionBaseDeDatos.realizarConsultaSQL(
                    ConsultasSQL.SELECT_MATRICULA_POR_CODIGO,
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
     * Muestra todas las matrículas ordenadas por estado de forma ascendente.
     */
    private static void consultarTodos() {
        System.out.println("\n--- TODAS LAS MATRÍCULAS (ordenado por estado ASC) ---");
        System.out.println(formatearCabecera(ConsultasSQL.SELECT_MATRICULA_TODOS));
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SELECT_MATRICULA_TODOS, new String[0], true, false);
    }

    // =========================================================
    // ==================== EXPORTAR ===========================
    // =========================================================

    /**
     * Submenú de exportación de la tabla matricula a distintos formatos de fichero.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuExportar(Scanner teclado) {
        System.out.println("\n--- EXPORTAR TABLA MATRÍCULA ---");
        System.out.println("1. Exportar a TXT  (separador ;)");
        System.out.println("2. Exportar a CSV  (separador :)");
        System.out.println("3. Exportar a Binario");
        System.out.println("4. Exportar a JSON");
        System.out.println("5. <- Volver");
        System.out.print("Elija una opción (1-5): ");

        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();
            cargarMatriculasDesdeBD();

            switch (opcion) {
                case 1 -> exportarATxt();
                case 2 -> exportarACsv();
                case 3 -> exportarABinario();
                case 4 -> exportarAJson();
                case 5 -> { /* volver */ }
                default -> System.out.println("[ERROR] Opción no válida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Debe introducir un número entero.");
            teclado.nextLine();
        }
    }

    /** Escribe la lista de matrículas en un fichero TXT con separador ";". */
    private static void exportarATxt() {
        exportarAFicheroTexto(Config.ficheroMatricula + ".txt", false);
    }

    /** Escribe la lista de matrículas en un fichero CSV con separador ":". */
    private static void exportarACsv() {
        exportarAFicheroTexto(Config.ficheroMatricula + ".csv", true);
    }

    /**
     * Escribe la lista de matrículas en un fichero de texto, usando ";" o ":" como separador.
     *
     * @param rutaFichero  Ruta del fichero de destino.
     * @param usarDosPuntos true para usar ":" como separador (CSV), false para ";" (TXT).
     */
    private static void exportarAFicheroTexto(String rutaFichero, boolean usarDosPuntos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaFichero, false))) {
            for (Matricula m : SesionDatos.listaMatriculas) {
                pw.println(usarDosPuntos ? m.toCSV() : m.toTXT());
            }
            System.out.println("[OK] Exportados " + SesionDatos.listaMatriculas.size()
                    + " registros a: " + rutaFichero);
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar: " + e.getMessage());
        }
    }

    /** Serializa la lista de matrículas en un fichero binario (.dat). */
    private static void exportarABinario() {
        GestionFicheros.guardarToBinario(Config.ficheroMatricula, SesionDatos.listaMatriculas);
        System.out.println("[OK] Exportados " + SesionDatos.listaMatriculas.size()
                + " registros a: " + Config.ficheroMatricula + ".dat");
    }

    /** Escribe la lista de matrículas en un fichero JSON, un objeto por línea. */
    private static void exportarAJson() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Config.ficheroMatricula + ".json", false))) {
            for (Matricula m : SesionDatos.listaMatriculas) {
                pw.println(m.toJSON());
            }
            System.out.println("[OK] Exportados " + SesionDatos.listaMatriculas.size()
                    + " registros a: " + Config.ficheroMatricula + ".json");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar a JSON: " + e.getMessage());
        }
    }

    // =========================================================
    // ==================== IMPORTAR ===========================
    // =========================================================

    /**
     * Submenú de importación de la tabla matricula desde distintos formatos de fichero.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuImportar(Scanner teclado) {
        System.out.println("\n--- IMPORTAR TABLA MATRÍCULA ---");
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
                case 1 -> importarDesdeTxt();
                case 2 -> importarDesdeCsv();
                case 3 -> importarDesdeBinario();
                case 4 -> importarDesdeJson();
                case 5 -> { /* volver */ }
                default -> System.out.println("[ERROR] Opción no válida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Debe introducir un número entero.");
            teclado.nextLine();
        } catch (YaImportadoException e) {
            System.out.println("[AVISO] " + e.getMessage());
        }
    }

    /**
     * Importa matrículas desde un fichero TXT (separador ";") e inserta
     * cada registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde TXT en esta sesión.
     */
    private static void importarDesdeTxt() throws YaImportadoException {
        if (importadoTxt) {
            throw new YaImportadoException("La tabla matricula ya fue importada desde TXT en esta sesión.");
        }
 
        ArrayList<String> lineas = GestionFicheros.leerTxtCsv(Config.ficheroMatricula, ".txt");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero TXT está vacío o no existe.");
            return;
        }
 
        int contadorImportados = 0;
 
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Matricula matricula = Matricula.obtenerLineas(linea);
 
                // Orden según INSERT_MATRICULA_CON_CODIGO:
                // codigo, codigo_alumno, anio_academico, estado, importe
                String[] entradas = {
                    String.valueOf(matricula.getCodigo()),
                    String.valueOf(matricula.getCodigo_alumno()),
                    String.valueOf(matricula.getAño_academico()),
                    matricula.getEstado(),
                    String.valueOf(matricula.getImporte())
                };
 
                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_MATRICULA_CON_CODIGO[1], entradas)) {
                    SesionDatos.listaMatriculas.add(matricula);
                    contadorImportados++;
                }
            }
        }

        importadoTxt = true;
        System.out.println("[OK] Importadas " + contadorImportados + " matrículas desde TXT.");
    }
 
    /**
     * Importa matrículas desde un fichero CSV (separador ":") e inserta
     * cada registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde CSV en esta sesión.
     */
    private static void importarDesdeCsv() throws YaImportadoException {
        if (importadoCsv) {
            throw new YaImportadoException("La tabla matricula ya fue importada desde CSV en esta sesión.");
        }
 
        ArrayList<String> lineas = GestionFicheros.leerTxtCsv(Config.ficheroMatricula, ".csv");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero CSV está vacío o no existe.");
            return;
        }
 
        int contadorImportados = 0;
 
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Matricula matricula = Matricula.obtenerLineas(linea.replace(":", ";"));
 
                String[] entradas = {
                    String.valueOf(matricula.getCodigo()),
                    String.valueOf(matricula.getCodigo_alumno()),
                    String.valueOf(matricula.getAño_academico()),
                    matricula.getEstado(),
                    String.valueOf(matricula.getImporte())
                };
 
                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_MATRICULA_CON_CODIGO[1], entradas)) {
                    SesionDatos.listaMatriculas.add(matricula);
                    contadorImportados++;
                }
            }
        }

        importadoCsv = true;
        System.out.println("[OK] Importadas " + contadorImportados + " matrículas desde CSV.");
    }
 
    /**
     * Importa matrículas desde un fichero binario (.dat) e inserta cada
     * registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde Binario en esta sesión.
     */
    private static void importarDesdeBinario() throws YaImportadoException {
        if (importadoBin) {
            throw new YaImportadoException("La tabla matricula ya fue importada desde Binario en esta sesión.");
        }
 
        int contadorImportados = 0;
 
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Config.ficheroMatricula + ".dat"))) {
 
            @SuppressWarnings("unchecked")
            ArrayList<Matricula> lista = (ArrayList<Matricula>) ois.readObject();
 
            if (lista == null || lista.isEmpty()) {
                System.out.println("[INFO] El fichero binario está vacío o no existe.");
                return;
            }
 
            for (Matricula matricula : lista) {
                String[] entradas = {
                    String.valueOf(matricula.getCodigo()),
                    String.valueOf(matricula.getCodigo_alumno()),
                    String.valueOf(matricula.getAño_academico()),
                    matricula.getEstado(),
                    String.valueOf(matricula.getImporte())
                };
 
                GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_MATRICULA_CON_CODIGO, entradas);
                SesionDatos.listaMatriculas.add(matricula);
                contadorImportados++;
            }
 
        } catch (java.io.FileNotFoundException e) {
            System.out.println("[INFO] El fichero binario no existe: " + Config.ficheroMatricula + ".dat");
            return;
        } catch (java.io.IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] Error al leer el fichero binario: " + e.getMessage());
            return;
        }
 
        importadoBin = true;
        System.out.println("[OK] Importadas " + contadorImportados + " matrículas desde Binario.");
    }
 
    /**
     * Importa matrículas desde un fichero JSON e inserta cada registro
     * en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde JSON en esta sesión.
     */
    private static void importarDesdeJson() throws YaImportadoException {
        if (importadoJson) {
            throw new YaImportadoException("La tabla matricula ya fue importada desde JSON en esta sesión.");
        }
 
        ArrayList<String> lineas = GestionFicheros.leerJson(Config.ficheroMatricula);
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero JSON está vacío o no existe.");
            return;
        }
 
        int contadorImportados = 0;
        Gson gson = GsonUtils.get();

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Matricula matricula = gson.fromJson(linea, Matricula.class);

                String[] entradas = {
                    String.valueOf(matricula.getCodigo()),
                    String.valueOf(matricula.getCodigo_alumno()),
                    String.valueOf(matricula.getAño_academico()),
                    matricula.getEstado(),
                    String.valueOf(matricula.getImporte())
                };

                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_MATRICULA_CON_CODIGO[1], entradas)) {
                    SesionDatos.listaMatriculas.add(matricula);
                    contadorImportados++;
                }
            }
        }

        importadoJson = true;
        System.out.println("[OK] Importadas " + contadorImportados + " matrículas desde JSON.");
    }

    // =========================================================
    // =================== SESIÓN ==============================
    // =========================================================

    /**
     * Muestra en consola las matrículas insertadas durante la sesión actual.
     */
    private static void verDatosSesion() {
        System.out.println("\n--- MATRÍCULAS INSERTADAS EN ESTA SESIÓN ---");
        if (matriculasSesion.isEmpty()) {
            System.out.println("[INFO] No se han insertado matrículas en esta sesión.");
        } else {
            System.out.println(formatearCabecera(ConsultasSQL.SELECT_MATRICULA_TODOS));
            for (Matricula m : matriculasSesion) {
                System.out.println(m.toString());
            }
            System.out.println("Total insertadas: " + matriculasSesion.size());
        }
    }

    // =========================================================
    // ==================== AUXILIARES =========================
    // =========================================================

    /**
     * Carga todas las matrículas de la base de datos en la lista en memoria.
     */
    private static void cargarMatriculasDesdeBD() {
        SesionDatos.listaMatriculas.clear();
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SAVE_MATRICULA_TODOS, new String[0], false, true);
    }

    public static int exportar(String formato) {
        SesionDatos.listaMatriculas.clear();
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SAVE_MATRICULA_TODOS, new String[0], false, true);
        if (SesionDatos.listaMatriculas.isEmpty()) return 0;

        String ruta = Config.rutaFichero(Config.ficheroMatricula, formato);
        if ("BINARIO".equals(formato)) {
            GestionFicheros.guardarToBinario(ruta, SesionDatos.listaMatriculas);
        } else {
            String ext = "CSV".equals(formato) ? ".csv" : "JSON".equals(formato) ? ".json" : ".txt";
            try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + ext, false))) {
                for (Matricula mat : SesionDatos.listaMatriculas) {
                    pw.println("CSV".equals(formato) ? mat.toCSV() : "JSON".equals(formato) ? mat.toJSON() : mat.toTXT());
                }
            } catch (IOException e) {
                System.out.println("[ERROR] No se pudo exportar matrículas: " + e.getMessage());
                return 0;
            }
        }
        return SesionDatos.listaMatriculas.size();
    }

    public static int importar(String formato) throws Exception {
        String ruta = Config.rutaFichero(Config.ficheroMatricula, formato);
        int contador = 0;

        if ("BINARIO".equals(formato)) {
            if (!Validadores.comprobarFicheroLectura(ruta, ".dat")) {
                throw new Exception("El fichero " + ruta + ".dat no existe o está vacío.");
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta + ".dat"))) {
                @SuppressWarnings("unchecked")
                java.util.Collection<Matricula> lista = (java.util.Collection<Matricula>) ois.readObject();
                if (lista == null || lista.isEmpty()) return 0;
                for (Matricula mat : lista) {
                    String[] p = { String.valueOf(mat.getCodigo()), String.valueOf(mat.getCodigo_alumno()),
                        String.valueOf(mat.getAño_academico()), mat.getEstado(),
                        String.valueOf(mat.getImporte()) };
                    if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_MATRICULA_CON_CODIGO[1], p)) {
                        SesionDatos.registrarMatricula(mat, false); contador++;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new Exception("Error al leer binario de matrículas: " + e.getMessage());
            }
            return contador;
        }

        ArrayList<String> lineas = "JSON".equals(formato)
                ? GestionFicheros.leerJson(ruta)
                : GestionFicheros.leerTxtCsv(ruta, "TXT".equals(formato) ? ".txt" : ".csv");
        if (lineas == null || lineas.isEmpty()) {
            throw new Exception("El fichero de matrículas está vacío o no existe.");
        }
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;
            try {
                String[] partes;
                if ("JSON".equals(formato)) {
                    Matricula mat = GestionFicheros.toJson(linea, Matricula.class);
                    partes = new String[]{ String.valueOf(mat.getCodigo()), String.valueOf(mat.getCodigo_alumno()),
                        String.valueOf(mat.getAño_academico()), mat.getEstado(),
                        String.valueOf(mat.getImporte()) };
                } else {
                    partes = ("CSV".equals(formato) ? linea.replace(":", ";") : linea).split(";", -1);
                }
                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_MATRICULA_CON_CODIGO[1], partes)) {
                    SesionDatos.registrarMatricula(new Matricula(partes), false); contador++;
                }
            } catch (Exception e) {
                System.out.println("[AVISO] Línea de matrícula omitida: " + e.getMessage());
            }
        }
        return contador;
    }

    /**
     * Construye una cabecera con los nombres de columna del array de consulta.
     *
     * @param datosConsulta Array de consulta (columnas en índices 1..n).
     * @return Cadena formateada con separador de guiones.
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
