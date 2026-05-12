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
import servicios.BaseDeDatos.ConsultasSQL;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

public class Ciclo implements interpolaridadDeDatos, Serializable {

    private static final long serialVersionUID = 1L;

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

        int codigoGenerado = ConsultasEspecificas.leerCodigoBDD("ciclo");

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
    
    public Ciclo(String cadena[]){
        this.codigo = Integer.parseInt(cadena[0]) ;
        this.denominacion = cadena[1];
        this.familiaProfesional =cadena[2];
        this.nivel = cadena[3];
        this.horas = Integer.parseInt(cadena[4]);
        this.añoCurriculum = Integer.parseInt(cadena[5]);
    }

    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================

    public int getCodigo() {
        return codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public String getFamiliaProfesional() {
        return familiaProfesional;
    }

    public String getNivel() {
        return nivel;
    }

    public int getHoras() {
        return horas;
    }

    public int getAñoCurriculum() {
        return añoCurriculum;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    public void setDenominacion(String denominacion) {
        if (!Validadores.validarTextoNoVacio(denominacion)) {
            throw new IllegalArgumentException("La denominación no puede estar vacía");
        }

        this.denominacion = denominacion;
    }

    public void setFamiliaProfesional(String familiaProfesional) {
        if (!Validadores.validarTextoNoVacio(familiaProfesional)) {
            throw new IllegalArgumentException("La familia profesional no puede estar vacía");
        }

        this.familiaProfesional = familiaProfesional;
    }

    public void setNivel(String nivel) {
        if (!Validadores.validarNivel(nivel)) {
            throw new IllegalArgumentException("El nivel no puede estar vacío");
        }

        this.nivel = nivel;
    }

    public void setHoras(int horas) {
        if (!Validadores.validarHorasCiclo(horas)) {
            throw new IllegalArgumentException("Las horas del ciclo deben ser mayores que 0");
        }

        this.horas = horas;
    }

    public void setAñoCurriculum(int añoCurriculum) {
        if (!Validadores.validarAñoCurriculum(añoCurriculum)) {
            throw new IllegalArgumentException("El año del currículum no es válido");
        }

        this.añoCurriculum = añoCurriculum;
    }

    // =========================================================
    // ==================== VALIDACIONES =======================
    // =========================================================

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

    private void cargarDesdeLineas(ArrayList<String> temp) {

        GestionBaseDeDatos.listaCiclo.clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Ciclo ciclo = Ciclo.obtenerLineas(linea);
                GestionBaseDeDatos.listaCiclo.add(ciclo);
            }
        }

        for (Ciclo ciclo : GestionBaseDeDatos.listaCiclo) {
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

        for (Modulo modulo : GestionBaseDeDatos.listaModulo) {
            if (modulo.getCodigo_ciclo() == this.codigo) {
                modulosDelCiclo.add(modulo);
            }
        }

        return modulosDelCiclo;
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    @Override
    public void saveToCSV() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".csv")) {
            GestionFicheros.saveToTxtCsvJson(this.toCSV(), Config.ficheroCiclo, ".csv");
        }
    }

    @Override
    public void saveToJSON() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".json")) {
            GestionFicheros.saveToTxtCsvJson(this.toJSON(), Config.ficheroCiclo, ".json");
        }
    }

    @Override
    public void saveToTXT() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".txt")) {
            GestionFicheros.saveToTxtCsvJson(this.toTXT(), Config.ficheroCiclo, ".txt");
        }
    }

    @Override
    public void saveToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroCiclo, ".dat")) {
            GestionFicheros.saveToBinario(Config.ficheroCiclo, GestionBaseDeDatos.listaCiclo);
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".csv")) {
            ArrayList<String> temp = GestionFicheros.loadTxtCsv(Config.ficheroCiclo, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".json")) {

            GestionBaseDeDatos.listaCiclo.clear();

            ArrayList<String> temp = GestionFicheros.loadJson(Config.ficheroCiclo);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Ciclo ciclo = (Ciclo) GestionFicheros.fromJson(string, Ciclo.class);

                    ciclo.validarObjeto();

                    GestionBaseDeDatos.listaCiclo.add(ciclo);
                }
            }

            for (Ciclo ciclo : GestionBaseDeDatos.listaCiclo) {
                System.out.println(ciclo);
            }
        }
    }

    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".dat")) {
            ArrayList<String> temp = GestionFicheros.loadBinario(Config.ficheroCiclo);
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".txt")) {
            ArrayList<String> temp = GestionFicheros.loadTxtCsv(Config.ficheroCiclo, ".txt");
            cargarDesdeLineas(temp);
        }
    }

    
    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    @Override
    public String toCSV() {
        return codigo + ";"
                + denominacion + ";"
                + familiaProfesional + ";"
                + nivel + ";"
                + horas + ";"
                + añoCurriculum;
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