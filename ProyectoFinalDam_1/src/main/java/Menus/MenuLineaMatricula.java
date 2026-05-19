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
import modelos.LineaMatricula;
import servicios.BaseDeDatos.ConsultasSQL;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

/**
 * Menú de gestión completa de la tabla linea_matricula. Incluye operaciones
 * CRUD, exportación/importación de ficheros y visualización de datos insertados
 * durante la sesión.
 *
 * La clave primaria de linea_matricula es compuesta: (codigo_matricula,
 * codigo_modulo), por lo que las operaciones de consulta, actualización y
 * eliminación solicitan ambos valores al usuario.
 *
 * Las calificaciones se introducen como valores entre 0.0 y 10.0. Si aún no hay
 * calificación, el usuario puede introducir 0.0.
 *
 * @author 1DAM
 */
public class MenuLineaMatricula {

    /**
     * Lista de líneas de matrícula insertadas durante la ejecución actual.
     */
    private static final ArrayList<LineaMatricula> lineasSesion = new ArrayList<>();

    /**
     * Flags que indican si ya se ha importado desde cada formato.
     */
    private static boolean importadoTxt = false;
    private static boolean importadoCsv = false;
    private static boolean importadoBin = false;
    private static boolean importadoJson = false;

    // =========================================================
    // =================== MENÚ PRINCIPAL ======================
    // =========================================================
    /**
     * Muestra el menú de gestión de líneas de matrícula y gestiona la
     * navegación.
     *
     * @param teclado Scanner compartido con el Main para la entrada de datos.
     */
    public static void mostrarMenu(Scanner teclado) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n========================================");
            System.out.println("    GESTIÓN DE LÍNEAS DE MATRÍCULA      ");
            System.out.println("========================================");
            System.out.println("1. Insertar línea de matrícula");
            System.out.println("2. Actualizar línea de matrícula");
            System.out.println("3. Eliminar línea de matrícula");
            System.out.println("4. Consultar línea de matrícula por código");
            System.out.println("5. Consultar todas las líneas (ordenado por cod. matrícula)");
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
                        insertarLineaMatricula(teclado);
                    case 2 ->
                        actualizarLineaMatricula(teclado);
                    case 3 ->
                        eliminarLineaMatricula(teclado);
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
     * Solicita los datos de una nueva línea de matrícula por teclado, la
     * inserta en la base de datos y la añade a la colección de sesión.
     *
     * Los campos calificacion_primera y calificacion_segunda admiten 0.0 como
     * valor provisional (aún sin calificar).
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void insertarLineaMatricula(Scanner teclado) {
        System.out.println("\n--- INSERTAR LÍNEA DE MATRÍCULA ---");

        try {
            System.out.print("Código de matrícula: ");
            int codMatricula = teclado.nextInt();

            System.out.print("Código de módulo: ");
            int codModulo = teclado.nextInt();

            System.out.print("Repetición (1 = primera vez, 2 = repetidor): ");
            int repeticion = teclado.nextInt();

            System.out.print("Calificación primera convocatoria (0.0 - 10.0, o 0 si sin calificar): ");
            double calPrimera = teclado.nextDouble();

            System.out.print("Calificación segunda convocatoria (0.0 - 10.0, o 0 si sin calificar): ");
            double calSegunda = teclado.nextDouble();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codMatricula)
                    || !Validadores.validarCodigoPositivo(codModulo)
                    || !Validadores.validarRepeticion(repeticion)
                    || !Validadores.validarCalificacion(calPrimera)
                    || !Validadores.validarCalificacion(calSegunda)) {
                System.out.println("[ERROR] Datos inválidos.");
                System.out.println("        · Códigos deben ser > 0.");
                System.out.println("        · Repetición: 1 o 2.");
                System.out.println("        · Calificaciones: entre 0.0 y 10.0.");
                return;
            }

            String[] entradas = {
                String.valueOf(codMatricula),
                String.valueOf(codModulo),
                String.valueOf(repeticion),
                String.valueOf(calPrimera),
                String.valueOf(calSegunda)
            };

            GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_LINEA_MATRICULA, entradas);

            LineaMatricula lineaMatricula = new LineaMatricula(
                    codMatricula, codModulo, repeticion, calPrimera, calSegunda
            );
            lineasSesion.add(lineaMatricula);
            SesionDatos.getListaLineasMatricula().add(lineaMatricula);

            System.out.println("[OK] Línea de matrícula insertada correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Códigos y repetición deben ser enteros; calificaciones deben ser decimales.");
            teclado.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /**
     * Solicita los dos códigos que forman la clave primaria compuesta y los
     * nuevos valores de repetición y calificaciones, y actualiza el registro en
     * la BD.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void actualizarLineaMatricula(Scanner teclado) {
        System.out.println("\n--- ACTUALIZAR LÍNEA DE MATRÍCULA ---");
        System.out.println("(Indique la clave primaria compuesta de la línea a modificar)");

        try {
            System.out.print("Código de matrícula: ");
            int codMatricula = teclado.nextInt();

            System.out.print("Código de módulo: ");
            int codModulo = teclado.nextInt();

            System.out.print("Nueva repetición (1 o 2): ");
            int repeticion = teclado.nextInt();

            System.out.print("Nueva calificación primera convocatoria (0.0 - 10.0): ");
            double calPrimera = teclado.nextDouble();

            System.out.print("Nueva calificación segunda convocatoria (0.0 - 10.0): ");
            double calSegunda = teclado.nextDouble();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codMatricula)
                    || !Validadores.validarCodigoPositivo(codModulo)
                    || !Validadores.validarRepeticion(repeticion)
                    || !Validadores.validarCalificacion(calPrimera)
                    || !Validadores.validarCalificacion(calSegunda)) {
                System.out.println("[ERROR] Datos inválidos. Revise los códigos, repetición y calificaciones.");
                return;
            }

            // UPDATE linea_matricula SET repeticion=?, cal_primera=?, cal_segunda=?
            // WHERE codigo_matricula=? AND codigo_modulo=?
            String[] entradas = {
                String.valueOf(repeticion),
                String.valueOf(calPrimera),
                String.valueOf(calSegunda),
                String.valueOf(codMatricula),
                String.valueOf(codModulo)
            };

            GestionBaseDeDatos.actualizarFila(ConsultasSQL.UPDATE_LINEA_MATRICULA, entradas);
            System.out.println("[OK] Línea de matrícula actualizada correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Los valores numéricos deben ser enteros o decimales.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita los dos códigos que forman la clave primaria compuesta y elimina
     * el registro correspondiente de la base de datos, previa confirmación.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void eliminarLineaMatricula(Scanner teclado) {
        System.out.println("\n--- ELIMINAR LÍNEA DE MATRÍCULA ---");

        try {
            System.out.print("Código de matrícula: ");
            int codMatricula = teclado.nextInt();

            System.out.print("Código de módulo: ");
            int codModulo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codMatricula)
                    || !Validadores.validarCodigoPositivo(codModulo)) {
                System.out.println("[ERROR] Los códigos deben ser mayores que 0.");
                return;
            }

            System.out.print("¿Confirma la eliminación de la línea (matrícula="
                    + codMatricula + ", módulo=" + codModulo + ")? (s/n): ");
            String confirmacion = teclado.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                GestionBaseDeDatos.eliminarFila(
                        ConsultasSQL.DELETE_LINEA_MATRICULA,
                        new String[]{String.valueOf(codMatricula), String.valueOf(codModulo)}
                );
                System.out.println("[OK] Línea de matrícula eliminada correctamente.");
            } else {
                System.out.println("[INFO] Eliminación cancelada.");
            }

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Los códigos deben ser números enteros.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita los dos códigos que forman la clave primaria compuesta y muestra
     * los datos de la línea de matrícula correspondiente.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void consultarPorCodigo(Scanner teclado) {
        System.out.println("\n--- CONSULTAR LÍNEA DE MATRÍCULA POR CÓDIGO ---");

        try {
            System.out.print("Código de matrícula: ");
            int codMatricula = teclado.nextInt();

            System.out.print("Código de módulo: ");
            int codModulo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codMatricula)
                    || !Validadores.validarCodigoPositivo(codModulo)) {
                System.out.println("[ERROR] Los códigos deben ser mayores que 0.");
                return;
            }

            System.out.println("\n" + formatearCabecera(ConsultasSQL.SELECT_LINEA_MATRICULA_POR_CODIGO));
            GestionBaseDeDatos.realizarConsultaSQL(
                    ConsultasSQL.SELECT_LINEA_MATRICULA_POR_CODIGO,
                    new String[]{String.valueOf(codMatricula), String.valueOf(codModulo)},
                    true,
                    false
            );

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Los códigos deben ser números enteros.");
            teclado.nextLine();
        }
    }

    /**
     * Muestra todas las líneas de matrícula ordenadas por código de matrícula
     * ASC.
     */
    private static void consultarTodos() {
        System.out.println("\n--- TODAS LAS LÍNEAS DE MATRÍCULA (ordenado por cod. matrícula ASC) ---");
        System.out.println(formatearCabecera(ConsultasSQL.SELECT_LINEA_MATRICULA_TODOS));
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SELECT_LINEA_MATRICULA_TODOS, new String[0], true, false);
    }

    // =========================================================
    // ==================== EXPORTAR ===========================
    // =========================================================
    /**
     * Submenú de exportación de la tabla linea_matricula a distintos formatos.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuExportar(Scanner teclado) {
        System.out.println("\n--- EXPORTAR TABLA LÍNEA MATRÍCULA ---");
        System.out.println("1. Exportar a TXT  (separador ;)");
        System.out.println("2. Exportar a CSV  (separador :)");
        System.out.println("3. Exportar a Binario");
        System.out.println("4. Exportar a JSON");
        System.out.println("5. <- Volver");
        System.out.print("Elija una opción (1-5): ");

        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();

            cargarLineasDesdeBD();

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
     * Exporta todas las líneas de matrícula a un fichero TXT con separador ";".
     */
    private static void exportarATxt() {
        exportarAFicheroTexto(Config.ficheroLineaMatricula + ".txt", false);
    }

    /**
     * Exporta todas las líneas de matrícula a un fichero CSV con separador ":".
     */
    private static void exportarACsv() {
        exportarAFicheroTexto(Config.ficheroLineaMatricula + ".csv", true);
    }

    /**
     * Escribe la lista de líneas de matrícula en un fichero de texto.
     *
     * @param rutaFichero Ruta completa del fichero de salida.
     * @param usarDosPuntos true para separador ":" (CSV); false para ";" (TXT).
     */
    private static void exportarAFicheroTexto(String rutaFichero, boolean usarDosPuntos) {
        try ( PrintWriter pw = new PrintWriter(new FileWriter(rutaFichero, false))) {
            for (LineaMatricula lm : SesionDatos.getListaLineasMatricula()) {
                pw.println(usarDosPuntos ? lm.toCSV() : lm.toTXT());
            }
            System.out.println("[OK] Exportados " + SesionDatos.getListaLineasMatricula().size()
                    + " registros a: " + rutaFichero);
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar: " + e.getMessage());
        }
    }

    /**
     * Exporta todas las líneas de matrícula a un fichero binario (.dat).
     */
    private static void exportarABinario() {
        GestionFicheros.guardarToBinario(Config.ficheroLineaMatricula, SesionDatos.getListaLineasMatricula());
        System.out.println("[OK] Exportados " + SesionDatos.getListaLineasMatricula().size()
                + " registros a: " + Config.ficheroLineaMatricula + ".dat");
    }

    /**
     * Exporta todas las líneas de matrícula a un fichero JSON (un objeto por
     * línea).
     */
    private static void exportarAJson() {
        try ( PrintWriter pw = new PrintWriter(new FileWriter(Config.ficheroLineaMatricula + ".json", false))) {
            for (LineaMatricula lm : SesionDatos.getListaLineasMatricula()) {
                pw.println(lm.toJSON());
            }
            System.out.println("[OK] Exportados " + SesionDatos.getListaLineasMatricula().size()
                    + " registros a: " + Config.ficheroLineaMatricula + ".json");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar a JSON: " + e.getMessage());
        }
    }

    // =========================================================
    // ==================== IMPORTAR ===========================
    // =========================================================
    /**
     * Submenú de importación de la tabla linea_matricula desde distintos
     * formatos. Lanza YaImportadoException si el formato ya fue importado en
     * esta sesión.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuImportar(Scanner teclado) {
        System.out.println("\n--- IMPORTAR TABLA LÍNEA MATRÍCULA ---");
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
     * Importa líneas de matrícula desde un fichero TXT (separador ";") e
     * inserta cada registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde TXT en esta
     * sesión.
     */
    private static void importarDesdeTxt() throws YaImportadoException {
        if (importadoTxt) {
            throw new YaImportadoException("La tabla linea_matricula ya fue importada desde TXT en esta sesión.");
        }

        ArrayList<String> lineas = GestionFicheros.leerTxtCsv(Config.ficheroLineaMatricula, ".txt");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero TXT está vacío o no existe.");
            return;
        }

        int contadorImportados = 0;

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                LineaMatricula lineaMatricula = LineaMatricula.obtenerLineas(linea);

                // Orden según INSERT_LINEA_MATRICULA:
                // codigo_matricula, codigo_modulo, repeticion,
                // calificacion_primera, calificacion_segunda
                String[] entradas = {
                    String.valueOf(lineaMatricula.getCod_matricula()),
                    String.valueOf(lineaMatricula.getCod_modulo()),
                    String.valueOf(lineaMatricula.getRepeticion()),
                    String.valueOf(lineaMatricula.getCal_primera()),
                    String.valueOf(lineaMatricula.getCal_segunda())
                };

                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_LINEA_MATRICULA[1], entradas)) {
                    SesionDatos.getListaLineasMatricula().add(lineaMatricula);
                    contadorImportados++;
                }
            }
        }

        importadoTxt = true;
        System.out.println("[OK] Importadas " + contadorImportados + " líneas de matrícula desde TXT.");
    }

    /**
     * Importa líneas de matrícula desde un fichero CSV (separador ":") e
     * inserta cada registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde CSV en esta
     * sesión.
     */
    private static void importarDesdeCsv() throws YaImportadoException {
        if (importadoCsv) {
            throw new YaImportadoException("La tabla linea_matricula ya fue importada desde CSV en esta sesión.");
        }

        ArrayList<String> lineas = GestionFicheros.leerTxtCsv(Config.ficheroLineaMatricula, ".csv");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero CSV está vacío o no existe.");
            return;
        }

        int contadorImportados = 0;

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                LineaMatricula lineaMatricula = LineaMatricula.obtenerLineas(linea.replace(":", ";"));

                String[] entradas = {
                    String.valueOf(lineaMatricula.getCod_matricula()),
                    String.valueOf(lineaMatricula.getCod_modulo()),
                    String.valueOf(lineaMatricula.getRepeticion()),
                    String.valueOf(lineaMatricula.getCal_primera()),
                    String.valueOf(lineaMatricula.getCal_segunda())
                };

                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_LINEA_MATRICULA[1], entradas)) {
                    SesionDatos.getListaLineasMatricula().add(lineaMatricula);
                    contadorImportados++;
                }
            }
        }

        importadoCsv = true;
        System.out.println("[OK] Importadas " + contadorImportados + " líneas de matrícula desde CSV.");
    }

    /**
     * Importa líneas de matrícula desde un fichero binario (.dat) e inserta
     * cada registro en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde Binario en esta
     * sesión.
     */
    private static void importarDesdeBinario() throws YaImportadoException {
        if (importadoBin) {
            throw new YaImportadoException("La tabla linea_matricula ya fue importada desde Binario en esta sesión.");
        }

        int contadorImportados = 0;

        try ( ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Config.ficheroLineaMatricula + ".dat"))) {

            @SuppressWarnings("unchecked")
            ArrayList<LineaMatricula> lista = (ArrayList<LineaMatricula>) ois.readObject();

            if (lista == null || lista.isEmpty()) {
                System.out.println("[INFO] El fichero binario está vacío o no existe.");
                return;
            }

            for (LineaMatricula lineaMatricula : lista) {
                String[] entradas = {
                    String.valueOf(lineaMatricula.getCod_matricula()),
                    String.valueOf(lineaMatricula.getCod_modulo()),
                    String.valueOf(lineaMatricula.getRepeticion()),
                    String.valueOf(lineaMatricula.getCal_primera()),
                    String.valueOf(lineaMatricula.getCal_segunda())
                };

                GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_LINEA_MATRICULA, entradas);
                SesionDatos.getListaLineasMatricula().add(lineaMatricula);
                contadorImportados++;
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("[INFO] El fichero binario no existe: " + Config.ficheroLineaMatricula + ".dat");
            return;
        } catch (java.io.IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] Error al leer el fichero binario: " + e.getMessage());
            return;
        }

        importadoBin = true;
        System.out.println("[OK] Importadas " + contadorImportados + " líneas de matrícula desde Binario.");
    }

    /**
     * Importa líneas de matrícula desde un fichero JSON e inserta cada registro
     * en la base de datos.
     *
     * @throws YaImportadoException si ya fue importado desde JSON en esta
     * sesión.
     */
    private static void importarDesdeJson() throws YaImportadoException {
        if (importadoJson) {
            throw new YaImportadoException("La tabla linea_matricula ya fue importada desde JSON en esta sesión.");
        }

        ArrayList<String> lineas = GestionFicheros.leerJson(Config.ficheroLineaMatricula);
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero JSON está vacío o no existe.");
            return;
        }

        int contadorImportados = 0;
        Gson gson = GsonUtils.get();

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                LineaMatricula lineaMatricula = gson.fromJson(linea, LineaMatricula.class);

                String[] entradas = {
                    String.valueOf(lineaMatricula.getCod_matricula()),
                    String.valueOf(lineaMatricula.getCod_modulo()),
                    String.valueOf(lineaMatricula.getRepeticion()),
                    String.valueOf(lineaMatricula.getCal_primera()),
                    String.valueOf(lineaMatricula.getCal_segunda())
                };

                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_LINEA_MATRICULA[1], entradas)) {
                    SesionDatos.getListaLineasMatricula().add(lineaMatricula);
                    contadorImportados++;
                }
            }
        }

        importadoJson = true;
        System.out.println("[OK] Importadas " + contadorImportados + " líneas de matrícula desde JSON.");
    }

    // =========================================================
    // =================== SESIÓN ==============================
    // =========================================================
    /**
     * Muestra en consola las líneas de matrícula insertadas durante la sesión
     * actual.
     */
    private static void verDatosSesion() {
        System.out.println("\n--- LÍNEAS DE MATRÍCULA INSERTADAS EN ESTA SESIÓN ---");
        if (lineasSesion.isEmpty()) {
            System.out.println("[INFO] No se han insertado líneas de matrícula en esta sesión.");
        } else {
            System.out.println(formatearCabecera(ConsultasSQL.SELECT_LINEA_MATRICULA_TODOS));
            for (LineaMatricula lm : lineasSesion) {
                System.out.println(lm.toString());
            }
            System.out.println("Total insertadas: " + lineasSesion.size());
        }
    }

    // =========================================================
    // ==================== AUXILIARES =========================
    // =========================================================
    /**
     * Carga todas las líneas de matrícula de la base de datos en la lista en
     * memoria.
     */
    private static void cargarLineasDesdeBD() {
        SesionDatos.getListaLineasMatricula().clear();
        GestionBaseDeDatos.realizarConsultaSQL(
                ConsultasSQL.SAVE_LINEA_MATRICULA_TODOS, new String[0], false, true
        );
    }

    public static int exportar(String formato) {
        SesionDatos.getListaLineasMatricula().clear();
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SAVE_LINEA_MATRICULA_TODOS, new String[0], false, true);
        if (SesionDatos.getListaLineasMatricula().isEmpty()) return 0;

        String ruta = Config.rutaFichero(Config.ficheroLineaMatricula, formato);
        if ("BINARIO".equals(formato)) {
            GestionFicheros.guardarToBinario(ruta, SesionDatos.getListaLineasMatricula());
        } else {
            String ext = "CSV".equals(formato) ? ".csv" : "JSON".equals(formato) ? ".json" : ".txt";
            try (PrintWriter pw = new PrintWriter(new FileWriter(ruta + ext, false))) {
                for (LineaMatricula lm : SesionDatos.getListaLineasMatricula()) {
                    pw.println("CSV".equals(formato) ? lm.toCSV() : "JSON".equals(formato) ? lm.toJSON() : lm.toTXT());
                }
            } catch (IOException e) {
                System.out.println("[ERROR] No se pudo exportar líneas de matrícula: " + e.getMessage());
                return 0;
            }
        }
        return SesionDatos.getListaLineasMatricula().size();
    }

    public static int importar(String formato) throws Exception {
        String ruta = Config.rutaFichero(Config.ficheroLineaMatricula, formato);
        int contador = 0;

        if ("BINARIO".equals(formato)) {
            if (!Validadores.comprobarFicheroLectura(ruta, ".dat")) {
                throw new Exception("El fichero " + ruta + ".dat no existe o está vacío.");
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta + ".dat"))) {
                @SuppressWarnings("unchecked")
                java.util.Collection<LineaMatricula> lista = (java.util.Collection<LineaMatricula>) ois.readObject();
                if (lista == null || lista.isEmpty()) return 0;
                for (LineaMatricula lm : lista) {
                    String[] p = { String.valueOf(lm.getCod_matricula()), String.valueOf(lm.getCod_modulo()),
                        String.valueOf(lm.getRepeticion()),
                        String.valueOf(lm.getCal_primera()), String.valueOf(lm.getCal_segunda()) };
                    if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_LINEA_MATRICULA[1], p)) {
                        SesionDatos.registrarLineaMatricula(lm, false); contador++;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new Exception("Error al leer binario de líneas de matrícula: " + e.getMessage());
            }
            return contador;
        }

        ArrayList<String> lineas = "JSON".equals(formato)
                ? GestionFicheros.leerJson(ruta)
                : GestionFicheros.leerTxtCsv(ruta, "TXT".equals(formato) ? ".txt" : ".csv");
        if (lineas == null || lineas.isEmpty()) {
            throw new Exception("El fichero de líneas de matrícula está vacío o no existe.");
        }
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;
            try {
                String[] partes;
                if ("JSON".equals(formato)) {
                    LineaMatricula lm = GestionFicheros.toJson(linea, LineaMatricula.class);
                    partes = new String[]{ String.valueOf(lm.getCod_matricula()), String.valueOf(lm.getCod_modulo()),
                        String.valueOf(lm.getRepeticion()),
                        String.valueOf(lm.getCal_primera()), String.valueOf(lm.getCal_segunda()) };
                } else {
                    partes = ("CSV".equals(formato) ? linea.replace(":", ";") : linea).split(";", -1);
                }
                if (GestionBaseDeDatos.insertarSinID(ConsultasSQL.INSERT_LINEA_MATRICULA[1], partes)) {
                    SesionDatos.registrarLineaMatricula(new LineaMatricula(partes), false); contador++;
                }
            } catch (Exception e) {
                System.out.println("[AVISO] Línea de línea_matrícula omitida: " + e.getMessage());
            }
        }
        return contador;
    }

    /**
     * Construye una cabecera legible a partir de los nombres de columna del
     * array de consulta.
     *
     * @param datosConsulta Array con la SQL en [0] y columnas en [1..n].
     * @return Cadena formateada con nombres de columna separados y subrayada
     * con guiones.
     */
    private static String formatearCabecera(String[] datosConsulta) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < datosConsulta.length; i++) {
            sb.append(String.format("%-25s", datosConsulta[i].toUpperCase()));
        }
        sb.append("\n").append("-".repeat(sb.length()));
        return sb.toString();
    }
}
