package modelos;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;

import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;
import interfaces.InterpolaridadDeDatos;

public class Ciclo implements InterpolaridadDeDatos, Serializable, Comparable<Ciclo> {

    private static final long serialVersionUID = 1L; //Esto es para poder importar los datos de binario a base de datos sin problemas

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================
    private final int codigo;
    private String denominacion;
    private String familiaProfesional;
    private String nivel;
    private int horas;
    private int añoCurriculum;

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================
    /**
     * Creación manual de nuevo ciclo.
     *
     * @param denominacion
     * @param familiaProfesional
     * @param nivel
     * @param horas
     * @param añoCurriculum
     */
    public Ciclo(String denominacion,
            String familiaProfesional,
            String nivel,
            int horas,
            int añoCurriculum) {

        int codigoGenerado = GestionBaseDeDatos.obtenerUltimoCodigo("ciclo") + 1;

        if (!Validadores.validarCodigoPositivo(codigoGenerado)) {
            throw new IllegalArgumentException("El código generado del ciclo debe ser mayor que 0");
        }

        validarDatos(
                denominacion,
                familiaProfesional,
                nivel,
                horas,
                añoCurriculum
        );

        this.codigo = codigoGenerado;
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.nivel = nivel;
        this.horas = horas;
        this.añoCurriculum = añoCurriculum;
    }

    /**
     * Creación desde base de datos / fichero.
     *
     * @param codigo
     * @param denominacion
     * @param familiaProfesional
     * @param nivel
     * @param horas
     * @param añoCurriculum
     */
    public Ciclo(int codigo,
            String denominacion,
            String familiaProfesional,
            String nivel,
            int horas,
            int añoCurriculum) {

        if (!Validadores.validarCodigoPositivo(codigo)) {
            throw new IllegalArgumentException("El código del ciclo debe ser mayor que 0");
        }

        validarDatos(
                denominacion,
                familiaProfesional,
                nivel,
                horas,
                añoCurriculum
        );

        this.codigo = codigo;
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.nivel = nivel;
        this.horas = horas;
        this.añoCurriculum = añoCurriculum;
    }

    /**
     * Creación rápida desde un array de cadenas (tabla Swing, ResultSet ya convertido, etc.).
     * Orden esperado: [0]=codigo, [1]=denominacion, [2]=familia_profesional, [3]=nivel, [4]=horas, [5]=anio_curriculo.
     * No realiza validaciones — úsalo solo con datos que ya vienen de la BD.
     *
     * @param cadena Array de cadenas con los datos del ciclo.
     */
    public Ciclo(String cadena[]) {
        this.codigo = Integer.parseInt(cadena[0]);
        this.denominacion = cadena[1];
        this.familiaProfesional = cadena[2];
        this.nivel = cadena[3];
        this.horas = Integer.parseInt(cadena[4]);
        this.añoCurriculum = Integer.parseInt(cadena[5]);
    }

    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================

    /** @return Código único del ciclo (clave primaria). */
    public int getCodigo() {
        return codigo;
    }

    /** @return Nombre oficial del ciclo formativo. */
    public String getDenominacion() {
        return denominacion;
    }

    /** @return Familia profesional a la que pertenece el ciclo. */
    public String getFamiliaProfesional() {
        return familiaProfesional;
    }

    /** @return Nivel del ciclo (básico, medio o superior). */
    public String getNivel() {
        return nivel;
    }

    /** @return Número total de horas lectivas del ciclo. */
    public int getHoras() {
        return horas;
    }

    /** @return Año de publicación del currículum del ciclo. */
    public int getAñoCurriculum() {
        return añoCurriculum;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    /**
     * Actualiza la denominación del ciclo tras validar que no esté vacía.
     *
     * @param denominacion Nueva denominación del ciclo.
     * @throws IllegalArgumentException si la denominación es nula o vacía.
     */
    public void setDenominacion(String denominacion) {
        if (!Validadores.validarTextoNoVacio(denominacion)) {
            throw new IllegalArgumentException("La denominación no puede estar vacía");
        }

        this.denominacion = denominacion;
    }

    /**
     * Actualiza la familia profesional tras validar que no esté vacía.
     *
     * @param familiaProfesional Nueva familia profesional.
     * @throws IllegalArgumentException si es nula o vacía.
     */
    public void setFamiliaProfesional(String familiaProfesional) {
        if (!Validadores.validarTextoNoVacio(familiaProfesional)) {
            throw new IllegalArgumentException("La familia profesional no puede estar vacía");
        }

        this.familiaProfesional = familiaProfesional;
    }

    /**
     * Actualiza el nivel del ciclo tras validar que no esté vacío.
     *
     * @param nivel Nuevo nivel del ciclo.
     * @throws IllegalArgumentException si es nulo o vacío.
     */
    public void setNivel(String nivel) {
        if (!Validadores.validarNivel(nivel)) {
            throw new IllegalArgumentException("El nivel no puede estar vacío");
        }

        this.nivel = nivel;
    }

    /**
     * Actualiza el número de horas del ciclo tras validar que sea mayor que 0.
     *
     * @param horas Nuevas horas totales del ciclo.
     * @throws IllegalArgumentException si las horas son 0 o negativas.
     */
    public void setHoras(int horas) {
        if (!Validadores.validarHorasCiclo(horas)) {
            throw new IllegalArgumentException("Las horas del ciclo deben ser mayores que 0");
        }

        this.horas = horas;
    }

    /**
     * Actualiza el año del currículum tras validar que esté en el rango [1900, 3000].
     *
     * @param añoCurriculum Nuevo año del currículum.
     * @throws IllegalArgumentException si el año está fuera del rango válido.
     */
    public void setAñoCurriculum(int añoCurriculum) {
        if (!Validadores.validarAñoCurriculum(añoCurriculum)) {
            throw new IllegalArgumentException("El año del currículum no es válido");
        }

        this.añoCurriculum = añoCurriculum;
    }

    // =========================================================
    // ==================== VALIDACIONES =======================
    // =========================================================

    /**
     * Valida todos los campos de un ciclo antes de asignarlos.
     * Lanza IllegalArgumentException en el primer campo inválido encontrado.
     *
     * @param denominacion     Nombre oficial del ciclo.
     * @param familiaProfesional Familia profesional.
     * @param nivel            Nivel del ciclo.
     * @param horas            Horas totales.
     * @param añoCurriculum    Año del currículum.
     */
    private static void validarDatos(String denominacion,
            String familiaProfesional,
            String nivel,
            int horas,
            int añoCurriculum) {

        if (!Validadores.validarTextoNoVacio(denominacion)) {
            throw new IllegalArgumentException("La denominación no puede estar vacía");
        }

        if (!Validadores.validarTextoNoVacio(familiaProfesional)) {
            throw new IllegalArgumentException("La familia profesional no puede estar vacía");
        }

        if (!Validadores.validarNivel(nivel)) {
            throw new IllegalArgumentException("El nivel no puede estar vacío");
        }

        if (!Validadores.validarHorasCiclo(horas)) {
            throw new IllegalArgumentException("Las horas del ciclo deben ser mayores que 0");
        }

        if (!Validadores.validarAñoCurriculum(añoCurriculum)) {
            throw new IllegalArgumentException("El año del currículum no es válido");
        }
    }

    /**
     * Valida todos los campos del objeto actual.
     * Se usa al deserializar desde JSON para garantizar que los datos siguen siendo correctos.
     *
     * @throws IllegalArgumentException si cualquier campo no supera su validación.
     */
    private void validarObjeto() {
        if (!Validadores.validarCodigoPositivo(this.codigo)) {
            throw new IllegalArgumentException("El código del ciclo debe ser mayor que 0");
        }

        validarDatos(
                this.denominacion,
                this.familiaProfesional,
                this.nivel,
                this.horas,
                this.añoCurriculum
        );
    }

    // =========================================================
    // ===================== MÉTODOS ===========================
    // =========================================================
    /**
     * Este metodo es para poder añadir datos al TreeSet, en este caso ordenados
     * por codigo
     *
     * @param otro
     * @return
     */
    @Override
    public int compareTo(Ciclo otro) {
        return Integer.compare(this.codigo, otro.codigo);
    }

    /**
     * Parsea una línea de texto con formato CSV (separador ";") y devuelve el Ciclo correspondiente.
     *
     * @param linea Cadena con 6 campos: codigo;denominacion;familia;nivel;horas;anio_curriculo.
     * @return Ciclo construido con los datos de la línea.
     * @throws IllegalArgumentException si la línea no tiene exactamente 6 campos.
     */
    public static Ciclo obtenerLineas(String linea) {
        String[] partes = linea.split(";", -1);

        if (partes.length != 6) {
            throw new IllegalArgumentException("Línea inválida para Ciclo: " + linea);
        }

        int tempCodigo = Integer.parseInt(partes[0]);
        String tempDenominacion = partes[1];
        String tempFamiliaProfesional = partes[2];
        String tempNivel = partes[3];
        int tempHoras = Integer.parseInt(partes[4]);
        int tempAñoCurriculum = Integer.parseInt(partes[5]);

        return new Ciclo(
                tempCodigo,
                tempDenominacion,
                tempFamiliaProfesional,
                tempNivel,
                tempHoras,
                tempAñoCurriculum
        );
    }

    /**
     * Reemplaza la lista de ciclos en sesión con los objetos parseados desde las líneas de texto.
     *
     * @param temp Lista de cadenas, cada una con los datos de un ciclo en formato CSV.
     */
    private void cargarDesdeLineas(ArrayList<String> temp) {

        SesionDatos.listaCiclos.clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Ciclo ciclo = Ciclo.obtenerLineas(linea);
                SesionDatos.listaCiclos.add(ciclo);
            }
        }

        for (Ciclo ciclo : SesionDatos.listaCiclos) {
            System.out.println(ciclo);
        }
    }

    /**
     * Devuelve los módulos que pertenecen a este ciclo.
     *
     * La relación correcta está en Modulo.codigo_ciclo.
     */
    public ArrayList<Modulo> obtenerModulosDelCiclo() {
        ArrayList<Modulo> modulosDelCiclo = new ArrayList<>();

        for (Modulo modulo : SesionDatos.listaModulos) {
            if (modulo.getCodigo_ciclo() == this.codigo) {
                modulosDelCiclo.add(modulo);
            }
        }

        return modulosDelCiclo;
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    /** Serializa este ciclo en formato CSV y lo añade al fichero ciclo.csv. */
    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroCiclo, ".csv");
        }
    }

    /** Serializa este ciclo en formato JSON y lo añade al fichero ciclo.json. */
    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroCiclo, ".json");
        }
    }

    /** Serializa este ciclo en formato TXT (mismo separador que CSV) y lo añade al fichero ciclo.txt. */
    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroCiclo, ".txt");
        }
    }

    /** Guarda la lista completa de ciclos en sesión como objeto serializado binario (ciclo.dat). */
    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroCiclo, SesionDatos.listaCiclos);
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    /** Lee ciclo.csv y reemplaza la lista en sesión con los objetos deserializados. */
    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroCiclo, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    /** Lee ciclo.json, valida cada objeto y reemplaza la lista en sesión. */
    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".json")) {

            SesionDatos.listaCiclos.clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroCiclo);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Ciclo ciclo = (Ciclo) GestionFicheros.toJson(string, Ciclo.class);

                    ciclo.validarObjeto();

                    SesionDatos.listaCiclos.add(ciclo);
                }
            }

            for (Ciclo ciclo : SesionDatos.listaCiclos) {
                System.out.println(ciclo);
            }
        }
    }

    /** Lee ciclo.dat (binario serializado) y reemplaza la lista en sesión. */
    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroCiclo);
            cargarDesdeLineas(temp);
        }
    }

    /** Lee ciclo.txt (formato CSV con ";") y reemplaza la lista en sesión. */
    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".txt")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroCiclo, ".txt");
            cargarDesdeLineas(temp);
        }
    }

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    /**
     * Serializa el ciclo como línea CSV con separador ";".
     *
     * @return Cadena en formato: codigo;denominacion;familia;nivel;horas;anio_curriculo.
     */
    @Override
    public String toCSV() {
        return codigo + ":"
                + denominacion + ":"
                + familiaProfesional + ":"
                + nivel + ":"
                + horas + ":"
                + añoCurriculum;
    }

    /** @return Cadena JSON con todos los campos del ciclo. */
    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }

    /** @return Cadena idéntica a {@link #toCSV()}. */
    @Override
    public String toTXT() {
        return codigo + ";"
                + denominacion + ";"
                + familiaProfesional + ";"
                + nivel + ";"
                + horas + ";"
                + añoCurriculum;
    }

    // =========================================================
    // ===================== TO STRING =========================
    // =========================================================

    /** @return Representación legible del ciclo para depuración y logs. */
    @Override
    public String toString() {
        return "Ciclo{"
                + "codigo=" + codigo
                + ", denominacion='" + denominacion + '\''
                + ", familiaProfesional='" + familiaProfesional + '\''
                + ", nivel='" + nivel + '\''
                + ", horas=" + horas
                + ", añoCurriculum=" + añoCurriculum
                + '}';
    }
}
