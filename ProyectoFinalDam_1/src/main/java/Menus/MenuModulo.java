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
import modelos.Modulo;
import servicios.BaseDeDatos.ConsultasSQL;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

/**
 * Menú de gestión completa de la tabla modulo.
 * Incluye operaciones CRUD, exportación/importación de ficheros
 * y visualización de datos insertados durante la sesión.
 *
 * @author 1DAM
 */
public class MenuModulo {

    /** Lista de módulos insertados durante la ejecución actual. */
    private static final ArrayList<Modulo> modulosSesion = new ArrayList<>();

    /** Flags que indican si ya se ha importado desde cada formato. */
    private static boolean importadoTxt  = false;
    private static boolean importadoCsv  = false;
    private static boolean importadoBin  = false;
    private static boolean importadoJson = false;

    // =========================================================
    // =================== MENÚ PRINCIPAL ======================
    // =========================================================

    /**
     * Muestra el menú de gestión de módulos y gestiona la navegación.
     *
     * @param teclado Scanner compartido con el Main para la entrada de datos.
     */
    public static void mostrarMenu(Scanner teclado) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n========================================");
            System.out.println("         GESTIÓN DE MÓDULOS            ");
            System.out.println("========================================");
            System.out.println("1. Insertar módulo");
            System.out.println("2. Actualizar módulo por código");
            System.out.println("3. Eliminar módulo por código");
            System.out.println("4. Consultar módulo por código");
            System.out.println("5. Consultar todos los módulos (ordenado por nombre)");
            System.out.println("6. Exportar tabla");
            System.out.println("7. Importar tabla");
            System.out.println("8. Ver datos insertados en esta sesión");
            System.out.println("9. <- Volver al menú anterior");
            System.out.print("Elija una opción (1-9): ");

            try {
                int opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1 -> insertarModulo(teclado);
                    case 2 -> actualizarModulo(teclado);
                    case 3 -> eliminarModulo(teclado);
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
     * Solicita los datos de un nuevo módulo por teclado, lo inserta en la base
     * de datos y lo añade a la colección de sesión.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void insertarModulo(Scanner teclado) {
        System.out.println("\n--- INSERTAR MÓDULO ---");

        try {
            System.out.print("Código del ciclo al que pertenece: ");
            int codigoCiclo = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Nombre del módulo: ");
            String nombre = teclado.nextLine().trim();

            System.out.print("Curso (primero/segundo): ");
            String curso = teclado.nextLine().trim();

            System.out.print("Créditos ECTS: ");
            int creditosEcts = teclado.nextInt();

            System.out.print("Horas: ");
            int horas = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigoCiclo)
                    || !Validadores.validarTextoNoVacio(nombre)
                    || !Validadores.validarCurso(curso)
                    || !Validadores.validarCreditosEcts(creditosEcts)
                    || !Validadores.validarHorasModulo(horas)) {
                System.out.println("[ERROR] Datos inválidos. Revise los campos introducidos.");
                return;
            }

            String[] entradas = {
                String.valueOf(codigoCiclo),
                nombre,
                curso,
                String.valueOf(creditosEcts),
                String.valueOf(horas)
            };

            GestionBaseDeDatos.insertarDatos(ConsultasSQL.INSERT_MODULO, entradas);

            Modulo modulo = new Modulo(codigoCiclo, nombre, curso, creditosEcts, horas);
            modulosSesion.add(modulo);
            GestionBaseDeDatos.listaModulo.add(modulo);

            System.out.println("[OK] Módulo insertado correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Los valores numéricos deben ser enteros.");
            teclado.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /**
     * Solicita el código de un módulo existente y los nuevos valores de sus campos,
     * y actualiza el registro en la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void actualizarModulo(Scanner teclado) {
        System.out.println("\n--- ACTUALIZAR MÓDULO ---");

        try {
            System.out.print("Código del módulo a actualizar: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Nuevo código de ciclo: ");
            int codigoCiclo = teclado.nextInt();
            teclado.nextLine();

            System.out.print("Nuevo nombre: ");
            String nombre = teclado.nextLine().trim();

            System.out.print("Nuevo curso (primero/segundo): ");
            String curso = teclado.nextLine().trim();

            System.out.print("Nuevos créditos ECTS: ");
            int creditosEcts = teclado.nextInt();

            System.out.print("Nuevas horas: ");
            int horas = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)
                    || !Validadores.validarCodigoPositivo(codigoCiclo)
                    || !Validadores.validarTextoNoVacio(nombre)
                    || !Validadores.validarCurso(curso)
                    || !Validadores.validarCreditosEcts(creditosEcts)
                    || !Validadores.validarHorasModulo(horas)) {
                System.out.println("[ERROR] Datos inválidos.");
                return;
            }

            String[] entradas = {
                String.valueOf(codigoCiclo),
                nombre,
                curso,
                String.valueOf(creditosEcts),
                String.valueOf(horas),
                String.valueOf(codigo)
            };

            GestionBaseDeDatos.actualizarFila(ConsultasSQL.UPDATE_MODULO, entradas);
            System.out.println("[OK] Módulo actualizado correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Los valores numéricos deben ser enteros.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita el código de un módulo y lo elimina de la base de datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void eliminarModulo(Scanner teclado) {
        System.out.println("\n--- ELIMINAR MÓDULO ---");

        try {
            System.out.print("Código del módulo a eliminar: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.print("¿Confirma la eliminación del módulo con código " + codigo + "? (s/n): ");
            String confirmacion = teclado.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
                GestionBaseDeDatos.eliminarFila(ConsultasSQL.DELETE_MODULO, new String[]{String.valueOf(codigo)});
                System.out.println("[OK] Módulo eliminado correctamente.");
            } else {
                System.out.println("[INFO] Eliminación cancelada.");
            }

        } catch (InputMismatchException e) {
            System.out.println("[ERROR] El código debe ser un número entero.");
            teclado.nextLine();
        }
    }

    /**
     * Solicita el código de un módulo y muestra sus datos.
     *
     * @param teclado Scanner para leer la entrada del usuario.
     */
    private static void consultarPorCodigo(Scanner teclado) {
        System.out.println("\n--- CONSULTAR MÓDULO POR CÓDIGO ---");

        try {
            System.out.print("Código del módulo: ");
            int codigo = teclado.nextInt();
            teclado.nextLine();

            if (!Validadores.validarCodigoPositivo(codigo)) {
                System.out.println("[ERROR] El código debe ser mayor que 0.");
                return;
            }

            System.out.println("\n" + formatearCabecera(ConsultasSQL.SELECT_MODULO_POR_CODIGO));
            GestionBaseDeDatos.realizarConsultaSQL(
                    ConsultasSQL.SELECT_MODULO_POR_CODIGO,
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
     * Muestra todos los módulos ordenados por nombre de forma ascendente.
     */
    private static void consultarTodos() {
        System.out.println("\n--- TODOS LOS MÓDULOS (ordenado por nombre ASC) ---");
        System.out.println(formatearCabecera(ConsultasSQL.SELECT_MODULO_TODOS));
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SELECT_MODULO_TODOS, new String[0], true, false);
    }

    // =========================================================
    // ==================== EXPORTAR ===========================
    // =========================================================

    /**
     * Submenú de exportación de la tabla modulo a distintos formatos de fichero.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuExportar(Scanner teclado) {
        System.out.println("\n--- EXPORTAR TABLA MÓDULO ---");
        System.out.println("1. Exportar a TXT  (separador ;)");
        System.out.println("2. Exportar a CSV  (separador :)");
        System.out.println("3. Exportar a Binario");
        System.out.println("4. Exportar a JSON");
        System.out.println("5. <- Volver");
        System.out.print("Elija una opción (1-5): ");

        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();
            cargarModulosDesdeBD();

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

    private static void exportarATxt() {
        exportarAFicheroTexto(Config.ficheroModulo + ".txt", false);
    }

    private static void exportarACsv() {
        exportarAFicheroTexto(Config.ficheroModulo + ".csv", true);
    }

    private static void exportarAFicheroTexto(String rutaFichero, boolean usarDosPuntos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaFichero, false))) {
            for (Modulo modulo : GestionBaseDeDatos.listaModulo) {
                String linea = modulo.toCSV();
                if (usarDosPuntos) {
                    linea = linea.replace(";", ":");
                }
                pw.println(linea);
            }
            System.out.println("[OK] Exportados " + GestionBaseDeDatos.listaModulo.size()
                    + " registros a: " + rutaFichero);
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar: " + e.getMessage());
        }
    }

    private static void exportarABinario() {
        GestionFicheros.saveToBinario(Config.ficheroModulo, GestionBaseDeDatos.listaModulo);
        System.out.println("[OK] Exportados " + GestionBaseDeDatos.listaModulo.size()
                + " registros a: " + Config.ficheroModulo + ".dat");
    }

    private static void exportarAJson() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Config.ficheroModulo + ".json", false))) {
            for (Modulo modulo : GestionBaseDeDatos.listaModulo) {
                pw.println(modulo.toJSON());
            }
            System.out.println("[OK] Exportados " + GestionBaseDeDatos.listaModulo.size()
                    + " registros a: " + Config.ficheroModulo + ".json");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo exportar a JSON: " + e.getMessage());
        }
    }

    // =========================================================
    // ==================== IMPORTAR ===========================
    // =========================================================

    /**
     * Submenú de importación de la tabla modulo desde distintos formatos de fichero.
     *
     * @param teclado Scanner para leer la opción del usuario.
     */
    private static void mostrarMenuImportar(Scanner teclado) {
        System.out.println("\n--- IMPORTAR TABLA MÓDULO ---");
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

    private static void importarDesdeTxt() throws YaImportadoException {
        if (importadoTxt) {
            throw new YaImportadoException("La tabla modulo ya fue importada desde TXT en esta sesión.");
        }
        ArrayList<String> lineas = GestionFicheros.loadTxtCsv(Config.ficheroModulo, ".txt");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero TXT está vacío o no existe.");
            return;
        }
        GestionBaseDeDatos.listaModulo.clear();
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                GestionBaseDeDatos.listaModulo.add(Modulo.obtenerLineas(linea));
            }
        }
        importadoTxt = true;
        System.out.println("[OK] Importados " + GestionBaseDeDatos.listaModulo.size() + " módulos desde TXT.");
    }

    private static void importarDesdeCsv() throws YaImportadoException {
        if (importadoCsv) {
            throw new YaImportadoException("La tabla modulo ya fue importada desde CSV en esta sesión.");
        }
        ArrayList<String> lineas = GestionFicheros.loadTxtCsv(Config.ficheroModulo, ".csv");
        if (lineas == null || lineas.isEmpty()) {
            System.out.println("[INFO] El fichero CSV está vacío o no existe.");
            return;
        }
        GestionBaseDeDatos.listaModulo.clear();
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                GestionBaseDeDatos.listaModulo.add(Modulo.obtenerLineas(linea.replace(":", ";")));
            }
        }
        importadoCsv = true;
        System.out.println("[OK] Importados " + GestionBaseDeDatos.listaModulo.size() + " módulos desde CSV.");
    }

    private static void importarDesdeBinario() throws YaImportadoException {
        if (importadoBin) {
            throw new YaImportadoException("La tabla modulo ya fue importada desde Binario en esta sesión.");
        }
        Modulo instancia = new Modulo(1, "placeholder", "primero", 1, 1);
        instancia.objFromBinario();
        importadoBin = true;
        System.out.println("[OK] Importación desde binario completada.");
    }

    private static void importarDesdeJson() throws YaImportadoException {
        if (importadoJson) {
            throw new YaImportadoException("La tabla modulo ya fue importada desde JSON en esta sesión.");
        }
        Modulo instancia = new Modulo(1, "placeholder", "primero", 1, 1);
        instancia.objFromJSON();
        importadoJson = true;
        System.out.println("[OK] Importación desde JSON completada.");
    }

    // =========================================================
    // =================== SESIÓN ==============================
    // =========================================================

    /**
     * Muestra en consola los módulos insertados durante la sesión actual.
     */
    private static void verDatosSesion() {
        System.out.println("\n--- MÓDULOS INSERTADOS EN ESTA SESIÓN ---");
        if (modulosSesion.isEmpty()) {
            System.out.println("[INFO] No se han insertado módulos en esta sesión.");
        } else {
            System.out.println(formatearCabecera(ConsultasSQL.SELECT_MODULO_TODOS));
            for (Modulo modulo : modulosSesion) {
                System.out.println(modulo.toString());
            }
            System.out.println("Total insertados: " + modulosSesion.size());
        }
    }

    // =========================================================
    // ==================== AUXILIARES =========================
    // =========================================================

    /**
     * Carga todos los módulos de la base de datos en la lista en memoria.
     */
    private static void cargarModulosDesdeBD() {
        GestionBaseDeDatos.listaModulo.clear();
        GestionBaseDeDatos.realizarConsultaSQL(ConsultasSQL.SAVE_MODULO_TODOS, new String[0], false, true);
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
