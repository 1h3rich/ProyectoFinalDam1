package menus;

import Config.Config;
import Utils.Validadores;
import excepciones.YaImportadoException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import modelos.Ciclo;
import servicios.BaseDeDatos.ConsultasSQL;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

/**
 * Menú de gestión completa de la tabla ciclo.
 * Incluye operaciones CRUD, exportación/importación de ficheros
 * y visualización de datos insertados durante la sesión.
 *
 * @author 1DAM
 */
public class MenuCiclo {

    /** Lista de ciclos insertados durante la ejecución actual. */
    private static final ArrayList<Ciclo> ciclosSesion = new ArrayList<>();

    /** Flags que indican si ya se ha importado desde cada formato. */
    private static boolean importadoTxt  = false;
    private static boolean importadoCsv  = false;
    private static boolean importadoBin  = false;
    private static boolean importadoJson = false;

    // =========================================================
    // =================== MENÚ PRINCIPAL ======================
    // =========================================================

    /**
     * Muestra el menú de gestión de ciclos y gestiona la navegación.
     *
     * @param teclado Scanner compartido con el Main para la entrada de datos.
     */
    public static void mostrarMenu(Scanner teclado) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n========================================");
            System.out.println("         GESTIÓN DE CICLOS             ");
            System.out.println("========================================");
            System.out.println("1. Insertar ciclo");
            System.out.println("2. Actualizar ciclo por código");
            System.out.println("3. Eliminar ciclo por código");
            System.out.println("4. Consultar ciclo por código");
            System.out.println("5. Consultar todos los ciclos (ordenado por denominación)");
            System.out.println("6. Exportar tabla");
            System.out.println("7. Importar tabla");
            System.out.println("8. Ver datos insertados en esta sesión");
            System.out.println("9. <- Volver al menú anterior");
            System.out.print("Elija una opción (1-9): ");

            try {
                int opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1 -> insertarCiclo(teclado);
                    case 2 -> actualizarCiclo(teclado);
                    case 3 -> eliminarCiclo(teclado);
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
     * Solicita los datos de un nuevo ciclo por teclado, lo inserta en la base
     * de datos y lo añade a la colección de sesión.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void insertarCiclo(Scanner teclado) {
        System.out.println("\n--- INSERTAR CICLO ---");

        try {
            System.out.print("Denominación: ");
            String denominacion = teclado.nextLine().trim();

            System.out.print("Familia profesional: ");
            String familiaProfesional = teclado.nextLine().trim();

            System.out.print("Nivel (basico/medio/superior): ");
            String nivel = teclado.nextLine().trim();

            System.out.print("Horas totales: ");
            int horas = teclado.nextInt();

            System.out.print("Año del currículum: ");
            int añoCurriculum = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarTextoNoVacio(denominacion)
                    || !Validadores.validarTextoNoVacio(familiaProfesional)
                    || !Validadores.validarNivel(nivel)
                    || !Validadores.validarHorasCiclo(horas)
                    || !Validadores.validarAñoCurriculum(añoCurriculum)) {
                System.out.println("[ERROR] Datos inválidos. Revise los campos introducidos.");
                return;
            }

            String[] entradas = {
                denominacion,
                familiaProfesional,
                nivel,
                String.valueOf(horas),
                String.valueOf(añoCurriculum)
            };

            GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_CICLO, entradas);

            Ciclo ciclo = new Ciclo(denominacion, familiaProfesional, nivel, horas, añoCurriculum);
            ciclosSesion.add(ciclo);
            GestionBaseDeDatos.listaCiclo.add(ciclo);

            System.out.println("[OK] Ciclo insertado correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Las horas y el año deben ser números enteros.");
            teclado.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /**
     * Solicita el código de un ciclo existente y los nuevos valores de sus campos,
     * y actualiza el registro en la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void actualizarCiclo(Scanner teclado) {
        System.out.println("\n--- ACTUALIZAR CICLO ---");

        try {
            System.out.print("Código del ciclo a actualizar: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Nueva denominación: ");
            String denominacion = teclado.nextLine().trim();

            System.out.print("Nueva familia profesional: ");
            String familiaProfesional = teclado.nextLine().trim();

            System.out.print("Nuevo nivel (basico/medio/superior): ");
            String nivel = teclado.nextLine().trim();

            System.out.print("Nuevas horas totales: ");
            int horas = teclado.nextInt();

            System.out.print("Nuevo año del currículum: ");
            int añoCurriculum = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)
                    || !Validadores.validarTextoNoVacio(denominacion)
                    || !Validadores.validarTextoNoVacio(familiaProfesional)
                    || !Validadores.validarNivel(nivel)
                    || !Validadores.validarHorasCiclo(horas)
                    || !Validadores.validarAñoCurriculum(añoCurriculum)) {
                System.out.println("[ERROR] Datos inválidos.");
                return;
            }

            String[] entradas = {
                denominacion,
                familiaProfesional,
                nivel,
                String.valueOf(horas),
                String.valueOf(añoCurriculum),
                String.valueOf(codigo)
            };

            GestionBaseDeDatos.actualizarFila(ConsultasSQL.UPDATE_CICLO, entradas);
            System.out.println("[OK] Ciclo actualizado correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Código, horas y año deben ser números enteros.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita el código de un ciclo y lo elimina de la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void eliminarCiclo(Scanner teclado) {
        System.out.println("\n--- ELIMINAR CICLO ---");

        try {
            System.out.print("Código del ciclo a eliminar: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.print("¿Confirma la eliminación del ciclo con código " + codigo + "? (s/n): ");
            String confirmacion = teclado.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                GestionBaseDeDatos.eliminarFila(ConsultasSQL.DELETE_CICLO, new String[]{String.valueOf(codigo)});
                System.out.println("[OK] Ciclo eliminado correctamente.");
            } else {
                System.out.println("[INFO] Eliminación cancelada.");
            }

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] El código debe ser un número entero.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita el código de un ciclo y muestra sus datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void consultarPorCodigo(Scanner teclado) {
        System.out.println("\n--- CONSULTAR CICLO POR CÓDIGO ---");

        try {
            System.out.print("Código del ciclo: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.println("\n" + formatearCabecera(ConsultasSQL.SELECT_CICLO_POR_CODIGO));
            GestionBaseDeDatos.realizarConsultaSQL(
                    ConsultasSQL.SELECT_CICLO_POR_CODIGO,
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
     * Muestra todos los ciclos ordenados por denominación de forma ascendente.
     */
    private static void consultarTodos() {
        System.out.println("\n--- TODOS LOS CICLOS (ordenado por denominación ASC) ---");
        System.out.println(formatearCabecera(ConsultasSQL.SELECT_CICLO_TODOS));
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SELECT_CICLO_TODOS, new String[0], true, false);
    }

    // =========================================================
    // ==================== EXPORTAR ===========================
    // =========================================================

    /**
     * Submenú de exportación de la tabla ciclo a distintos formatos de fichero.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuExportar(Scanner teclado) {
        System.out.println("\n--- EXPORTAR TABLA CICLO ---");
        System.out.println("1. Exportar a TXT  (separador ;)");
        System.out.println("2. Exportar a CSV  (separador :)");
        System.out.println("3. Exportar a Binario");
        System.out.println("4. Exportar a JSON");
        System.out.println("5. <- Volver");
        System.out.print("Elija una opción (1-5): ");

        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();
            cargarCiclosDesdeBD();

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

    /**
     * Exporta todos los ciclos a un fichero TXT con separador ";".
     */
    private static void exportarATxt() {
        exportarAFicheroTexto(Config.ficheroCiclo + ".txt", false);
    }

    /**
     * Exporta todos los ciclos a un fichero CSV con separador ":".
     */
    private static void exportarACsv() {
        exportarAFicheroTexto(Config.ficheroCiclo + ".csv", true);
    }

    /**
     * Escribe la lista de ciclos en un fichero de texto (sobreescribe si existe).
     *
     * @param rutaFichero Ruta completa del fichero de salida.
     * @param usarDosPuntos true para usar ":" como separador (CSV); false para ";" (TXT).
     */
    private static void exportarAFicheroTexto(String rutaFichero, boolean usarDosPuntos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaFichero, false))) {
            for (Ciclo ciclo : GestionBaseDeDatos.listaCiclo) {
                String linea = ciclo.toCSV();
                if (usarDosPuntos) {
                    linea = linea.replace(";", ":");
                }
                pw.println(linea);
            }
            System.out.println("[OK] Exportados " + GestionBaseDeDatos.listaCiclo.size()
                    + " registros a: " + rutaFichero);
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar: " + e.getMessage());
        }
    }

    /**
     * Exporta todos los ciclos a un fichero binario (.dat).
     */
    private static void exportarABinario() {
        GestionFicheros.saveToBinario(Config.ficheroCiclo, GestionBaseDeDatos.listaCiclo);
        System.out.println("[OK] Exportados " + GestionBaseDeDatos.listaCiclo.size()
                + " registros a: " + Config.ficheroCiclo + ".dat");
    }

    /**
     * Exporta todos los ciclos a un fichero JSON (un objeto por línea).
     */
    private static void exportarAJson() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Config.ficheroCiclo + ".json", false))) {
            for (Ciclo ciclo : GestionBaseDeDatos.listaCiclo) {
                pw.println(ciclo.toJSON());
            }
            System.out.println("[OK] Exportados " + GestionBaseDeDatos.listaCiclo.size()
                    + " registros a: " + Config.ficheroCiclo + ".json");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar a JSON: " + e.getMessage());
        }
    }

    // =========================================================
    // ==================== IMPORTAR ===========================
    // =========================================================

    /**
     * Submenú de importación de la tabla ciclo desde distintos formatos de fichero.
     * Lanza YaImportadoException si el formato seleccionado ya fue importado.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuImportar(Scanner teclado) {
        System.out.println("\n--- IMPORTAR TABLA CICLO ---");
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
     * Importa ciclos desde un fichero TXT con separador ";".
     *
     * @throws YaImportadoException si ya fue importado desde TXT en esta sesión.
     */
    private static void importarDesdeTxt() throws YaImportadoException {
        if (importadoTxt) {
            throw new YaImportadoException("La tabla ciclo ya fue importada desde TXT en esta sesión.");
        }
        ArrayList<String> lineas = GestionFicheros.loadTxtCsv(Config.ficheroCiclo, ".txt");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero TXT está vacío o no existe.");
            return;
        }
        GestionBaseDeDatos.listaCiclo.clear();
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                GestionBaseDeDatos.listaCiclo.add(Ciclo.obtenerLineas(linea));
            }
        }
        importadoTxt = true;
        System.out.println("[OK] Importados " + GestionBaseDeDatos.listaCiclo.size() + " ciclos desde TXT.");
    }

    /**
     * Importa ciclos desde un fichero CSV con separador ":".
     *
     * @throws YaImportadoException si ya fue importado desde CSV en esta sesión.
     */
    private static void importarDesdeCsv() throws YaImportadoException {
        if (importadoCsv) {
            throw new YaImportadoException("La tabla ciclo ya fue importada desde CSV en esta sesión.");
        }
        ArrayList<String> lineas = GestionFicheros.loadTxtCsv(Config.ficheroCiclo, ".csv");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero CSV está vacío o no existe.");
            return;
        }
        GestionBaseDeDatos.listaCiclo.clear();
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                GestionBaseDeDatos.listaCiclo.add(Ciclo.obtenerLineas(linea.replace(":", ";")));
            }
        }
        importadoCsv = true;
        System.out.println("[OK] Importados " + GestionBaseDeDatos.listaCiclo.size() + " ciclos desde CSV.");
    }

    /**
     * Importa ciclos desde un fichero binario (.dat).
     *
     * @throws YaImportadoException si ya fue importado desde binario en esta sesión.
     */
    private static void importarDesdeBinario() throws YaImportadoException {
        if (importadoBin) {
            throw new YaImportadoException("La tabla ciclo ya fue importada desde Binario en esta sesión.");
        }
        Ciclo instancia = new Ciclo("placeholder", "placeholder", "medio", 1, 2000);
        instancia.objFromBinario();
        importadoBin = true;
        System.out.println("[OK] Importación desde binario completada.");
    }

    /**
     * Importa ciclos desde un fichero JSON.
     *
     * @throws YaImportadoException si ya fue importado desde JSON en esta sesión.
     */
    private static void importarDesdeJson() throws YaImportadoException {
        if (importadoJson) {
            throw new YaImportadoException("La tabla ciclo ya fue importada desde JSON en esta sesión.");
        }
        Ciclo instancia = new Ciclo("placeholder", "placeholder", "medio", 1, 2000);
        instancia.objFromJSON();
        importadoJson = true;
        System.out.println("[OK] Importación desde JSON completada.");
    }

    // =========================================================
    // =================== SESIÓN ==============================
    // =========================================================

    /**
     * Muestra en consola los ciclos insertados durante la sesión actual.
     */
    private static void verDatosSesion() {
        System.out.println("\n--- CICLOS INSERTADOS EN ESTA SESIÓN ---");
        if (ciclosSesion.isEmpty()) {
            System.out.println("[INFO] No se han insertado ciclos en esta sesión.");
        } else {
            System.out.println(formatearCabecera(ConsultasSQL.SELECT_CICLO_TODOS));
            for (Ciclo ciclo : ciclosSesion) {
                System.out.println(ciclo.toString());
            }
            System.out.println("Total insertados: " + ciclosSesion.size());
        }
    }

    // =========================================================
    // ==================== AUXILIARES =========================
    // =========================================================

     /**
     * Carga todos los ciclos de la base de datos en la lista en memoria.
     */
    private static void cargarCiclosDesdeBD() {
        GestionBaseDeDatos.listaCiclo.clear();
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SAVE_CICLO_TODOS, new String[0], false, true);
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
