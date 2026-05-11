package modelos;

import Config.Config;
import Utils.Validadores;
import com.google.gson.Gson;
import interfaces.interpolaridadDeDatos;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import servicios.BaseDeDatos.ConsultasEspecificas;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

public class Modulo implements interpolaridadDeDatos, Serializable {

    private static final long serialVersionUID = 1L;

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================

    private final int codigo;
    private int codigo_ciclo;
    private String nombre;
    private String curso;
    private int creditos_ects;
    private int horas;

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================

    /**
     * Creación manual de nuevo módulo.
     *
     * @param codigo_ciclo
     * @param nombre
     * @param curso
     * @param creditos_ects
     * @param horas
     */
    public Modulo(int codigo_ciclo,
                  String nombre,
                  String curso,
                  int creditos_ects,
                  int horas) {

        int codigoGenerado = ConsultasEspecificas.leerCodigoBDD("modulo");

        if (!Validadores.validarCodigoPositivo(codigoGenerado)) {
            throw new IllegalArgumentException("El código generado del módulo debe ser mayor que 0");
        }

        validarDatos(
                codigo_ciclo,
                nombre,
                curso,
                creditos_ects,
                horas
        );

        this.codigo = codigoGenerado;
        this.codigo_ciclo = codigo_ciclo;
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
    }

    /**
     * Creación desde base de datos / fichero.
     *
     * @param codigo
     * @param codigo_ciclo
     * @param nombre
     * @param curso
     * @param creditos_ects
     * @param horas
     */
    public Modulo(int codigo,
                  int codigo_ciclo,
                  String nombre,
                  String curso,
                  int creditos_ects,
                  int horas) {

        if (!Validadores.validarCodigoPositivo(codigo)) {
            throw new IllegalArgumentException("El código del módulo debe ser mayor que 0");
        }

        validarDatos(
                codigo_ciclo,
                nombre,
                curso,
                creditos_ects,
                horas
        );

        this.codigo = codigo;
        this.codigo_ciclo = codigo_ciclo;
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
    }

    public Modulo(String cadena[]){
        this.codigo = Integer.parseInt(cadena[0]) ;
        this.codigo_ciclo = Integer.parseInt(cadena[1]) ;
        this.nombre = cadena[2] ;
        this.curso = cadena[3];
        this.creditos_ects = Integer.parseInt(cadena[4]);
        this.horas = Integer.parseInt(cadena[5]);
       
    }
    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================

    public int getCodigo() {
        return codigo;
    }

    public int getCodigo_ciclo() {
        return codigo_ciclo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCurso() {
        return curso;
    }

    public int getCreditos_ects() {
        return creditos_ects;
    }

    public int getHoras() {
        return horas;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    public void setCodigo_ciclo(int codigo_ciclo) {
        if (!Validadores.validarCodigoPositivo(codigo_ciclo)) {
            throw new IllegalArgumentException("El código del ciclo debe ser mayor que 0");
        }

        this.codigo_ciclo = codigo_ciclo;
    }

    public void setNombre(String nombre) {
        if (!Validadores.validarTextoNoVacio(nombre)) {
            throw new IllegalArgumentException("El nombre del módulo no puede estar vacío");
        }

        this.nombre = nombre;
    }

    public void setCurso(String curso) {
        if (!Validadores.validarCurso(curso)) {
            throw new IllegalArgumentException("El curso no puede estar vacío");
        }

        this.curso = curso;
    }

    public void setCreditos_ects(int creditos_ects) {
        if (!Validadores.validarCreditosEcts(creditos_ects)) {
            throw new IllegalArgumentException("Los créditos ECTS deben ser mayores que 0");
        }

        this.creditos_ects = creditos_ects;
    }

    public void setHoras(int horas) {
        if (!Validadores.validarHorasModulo(horas)) {
            throw new IllegalArgumentException("Las horas del módulo deben ser mayores que 0");
        }

        this.horas = horas;
    }

    // =========================================================
    // ==================== VALIDACIONES =======================
    // =========================================================

    private static void validarDatos(int codigo_ciclo,
                                     String nombre,
                                     String curso,
                                     int creditos_ects,
                                     int horas) {

        if (!Validadores.validarCodigoPositivo(codigo_ciclo)) {
            throw new IllegalArgumentException("El código del ciclo debe ser mayor que 0");
        }

        if (!Validadores.validarTextoNoVacio(nombre)) {
            throw new IllegalArgumentException("El nombre del módulo no puede estar vacío");
        }

        if (!Validadores.validarCurso(curso)) {
            throw new IllegalArgumentException("El curso no puede estar vacío");
        }

        if (!Validadores.validarCreditosEcts(creditos_ects)) {
            throw new IllegalArgumentException("Los créditos ECTS deben ser mayores que 0");
        }

        if (!Validadores.validarHorasModulo(horas)) {
            throw new IllegalArgumentException("Las horas del módulo deben ser mayores que 0");
        }
    }

    private void validarObjeto() {
        if (!Validadores.validarCodigoPositivo(this.codigo)) {
            throw new IllegalArgumentException("El código del módulo debe ser mayor que 0");
        }

        validarDatos(
                this.codigo_ciclo,
                this.nombre,
                this.curso,
                this.creditos_ects,
                this.horas
        );
    }

    // =========================================================
    // ===================== MÉTODOS ===========================
    // =========================================================

    public static Modulo obtenerLineas(String linea) {
        String[] partes = linea.split(";", -1);

        if (partes.length != 6) {
            throw new IllegalArgumentException("Línea inválida para Modulo: " + linea);
        }

        int tempCodigo = Integer.parseInt(partes[0]);
        int tempCodigoCiclo = Integer.parseInt(partes[1]);
        String tempNombre = partes[2];
        String tempCurso = partes[3];
        int tempCreditosEcts = Integer.parseInt(partes[4]);
        int tempHoras = Integer.parseInt(partes[5]);

        return new Modulo(
                tempCodigo,
                tempCodigoCiclo,
                tempNombre,
                tempCurso,
                tempCreditosEcts,
                tempHoras
        );
    }

    private void cargarDesdeLineas(ArrayList<String> temp) {

        GestionBaseDeDatos.listaModulo.clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Modulo modulo = Modulo.obtenerLineas(linea);
                GestionBaseDeDatos.listaModulo.add(modulo);
            }
        }

        for (Modulo modulo : GestionBaseDeDatos.listaModulo) {
            System.out.println(modulo);
        }
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    @Override
    public void saveToCSV() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".csv")) {
            GestionFicheros.saveToTxtCsvJson(this.toCSV(), Config.ficheroModulo, ".csv");
        }
    }

    @Override
    public void saveToJSON() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".json")) {
            GestionFicheros.saveToTxtCsvJson(this.toJSON(), Config.ficheroModulo, ".json");
        }
    }

    @Override
    public void saveToTXT() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".txt")) {
            GestionFicheros.saveToTxtCsvJson(this.toTXT(), Config.ficheroModulo, ".txt");
        }
    }

    @Override
    public void saveToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".dat")) {
            GestionFicheros.saveToBinario(Config.ficheroModulo, GestionBaseDeDatos.listaModulo);
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".csv")) {
            ArrayList<String> temp = GestionFicheros.loadTxtCsv(Config.ficheroModulo, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".json")) {

            GestionBaseDeDatos.listaModulo.clear();

            ArrayList<String> temp = GestionFicheros.loadJson(Config.ficheroModulo);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Modulo modulo = (Modulo) GestionFicheros.fromJson(string, Modulo.class);

                    modulo.validarObjeto();

                    GestionBaseDeDatos.listaModulo.add(modulo);
                }
            }

            for (Modulo modulo : GestionBaseDeDatos.listaModulo) {
                System.out.println(modulo);
            }
        }
    }

    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".dat")) {
            ArrayList<String> temp = GestionFicheros.loadBinario(Config.ficheroModulo);
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".txt")) {
            ArrayList<String> temp = GestionFicheros.loadTxtCsv(Config.ficheroModulo, ".txt");
            cargarDesdeLineas(temp);
        }
    }

    // =========================================================
    // ======================= SQL =============================
    // =========================================================

    public static Modulo SqlToObj(ResultSet rs) throws SQLException {
        return new Modulo(
                rs.getInt("codigo"),
                rs.getInt("codigo_ciclo"),
                rs.getString("nombre"),
                rs.getString("curso"),
                rs.getInt("creditos_ects"),
                rs.getInt("horas")
        );
    }

    public static void ObjToSql() {
    }

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    @Override
    public String toCSV() {
        return codigo + ";"
                + codigo_ciclo + ";"
                + nombre + ";"
                + curso + ";"
                + creditos_ects + ";"
                + horas;
    }

    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }

    @Override
    public String toTXT() {
        return toCSV();
    }

    // =========================================================
    // ===================== TO STRING =========================
    // =========================================================

    @Override
    public String toString() {
        return "Modulo{"
                + "codigo=" + codigo
                + ", codigo_ciclo=" + codigo_ciclo
                + ", nombre='" + nombre + '\''
                + ", curso='" + curso + '\''
                + ", creditos_ects=" + creditos_ects
                + ", horas=" + horas
                + '}';
    }
}