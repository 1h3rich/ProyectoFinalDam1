package modelos;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import com.google.gson.Gson;
import interfaces.interpolaridadDeDatos;
import java.io.Serializable;
import java.util.ArrayList;
import servicios.Ficheros.GestionFicheros;

public class LineaMatricula implements interpolaridadDeDatos, Serializable {

    private static final long serialVersionUID = 1L; //Esto es para poder importar los datos de binario a base de datos sin problemas

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================
    private int cod_matricula;
    private int cod_modulo;
    private int repeticion;
    private double cal_primera;
    private double cal_segunda;

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================
    /**
     * Creación manual desde objetos.
     *
     * Se usa cuando ya tienes una matrícula y un módulo creados.
     *
     * @param matricula
     * @param modulo
     * @param repeticion
     * @param cal_primera
     * @param cal_segunda
     */
    public LineaMatricula(Matricula matricula,
            Modulo modulo,
            int repeticion,
            double cal_primera,
            double cal_segunda) {

        if (matricula == null) {
            throw new IllegalArgumentException("La matrícula no puede ser null");
        }

        if (modulo == null) {
            throw new IllegalArgumentException("El módulo no puede ser null");
        }

        validarDatos(
                matricula.getCodigo(),
                modulo.getCodigo(),
                repeticion,
                cal_primera,
                cal_segunda
        );

        this.cod_matricula = matricula.getCodigo();
        this.cod_modulo = modulo.getCodigo();
        this.repeticion = repeticion;
        this.cal_primera = cal_primera;
        this.cal_segunda = cal_segunda;
    }

    /**
     * Creación desde base de datos / fichero.
     *
     * Se usa cuando ya tienes directamente los códigos.
     *
     * @param cod_matricula
     * @param cod_modulo
     * @param repeticion
     * @param cal_primera
     * @param cal_segunda
     */
    public LineaMatricula(int cod_matricula,
            int cod_modulo,
            int repeticion,
            double cal_primera,
            double cal_segunda) {

        validarDatos(
                cod_matricula,
                cod_modulo,
                repeticion,
                cal_primera,
                cal_segunda
        );

        this.cod_matricula = cod_matricula;
        this.cod_modulo = cod_modulo;
        this.repeticion = repeticion;
        this.cal_primera = cal_primera;
        this.cal_segunda = cal_segunda;
    }

    public LineaMatricula(String cadena[]) {
        this.cod_matricula = Integer.parseInt(cadena[0]);
        this.cod_modulo = Integer.parseInt(cadena[1]);
        this.repeticion = Integer.parseInt(cadena[2]);
        if (cadena[3] == null) {
            this.cal_primera = 0;
        } else {
            this.cal_primera = Double.parseDouble(cadena[3]);
        }
        if (cadena[4] == null) {
            this.cal_segunda = 0;
        } else {
            this.cal_segunda = Double.parseDouble(cadena[4]);
        }

    }

    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================
    public int getCod_matricula() {
        return cod_matricula;
    }

    public int getCod_modulo() {
        return cod_modulo;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public double getCal_primera() {
        return cal_primera;
    }

    public double getCal_segunda() {
        return cal_segunda;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================
    public void setCod_matricula(int cod_matricula) {
        if (!Validadores.validarCodigoPositivo(cod_matricula)) {
            throw new IllegalArgumentException("El código de matrícula debe ser mayor que 0");
        }

        this.cod_matricula = cod_matricula;
    }

    public void setCod_modulo(int cod_modulo) {
        if (!Validadores.validarCodigoPositivo(cod_modulo)) {
            throw new IllegalArgumentException("El código del módulo debe ser mayor que 0");
        }

        this.cod_modulo = cod_modulo;
    }

    public void setRepeticion(int repeticion) {
        if (!Validadores.validarRepeticion(repeticion)) {
            throw new IllegalArgumentException("La repetición debe ser 1 o 2");
        }

        this.repeticion = repeticion;
    }

    public void setCal_primera(double cal_primera) {
        if (!Validadores.validarCalificacion(cal_primera)) {
            throw new IllegalArgumentException("La primera calificación debe estar entre 0 y 10");
        }

        this.cal_primera = cal_primera;
    }

    public void setCal_segunda(double cal_segunda) {
        if (!Validadores.validarCalificacion(cal_segunda)) {
            throw new IllegalArgumentException("La segunda calificación debe estar entre 0 y 10");
        }

        this.cal_segunda = cal_segunda;
    }

    // =========================================================
    // ==================== VALIDAR OBJETO =======================
    // =========================================================
    private static void validarDatos(int cod_matricula,
            int cod_modulo,
            int repeticion,
            double cal_primera,
            double cal_segunda) {

        if (!Validadores.validarCodigoPositivo(cod_matricula)) {
            throw new IllegalArgumentException("El código de matrícula debe ser mayor que 0");
        }

        if (!Validadores.validarCodigoPositivo(cod_modulo)) {
            throw new IllegalArgumentException("El código del módulo debe ser mayor que 0");
        }

        if (!Validadores.validarRepeticion(repeticion)) {
            throw new IllegalArgumentException("La repetición debe ser 1 o 2");
        }

        if (!Validadores.validarCalificacion(cal_primera)) {
            throw new IllegalArgumentException("La primera calificación debe estar entre 0 y 10");
        }

        if (!Validadores.validarCalificacion(cal_segunda)) {
            throw new IllegalArgumentException("La segunda calificación debe estar entre 0 y 10");
        }
    }

    private void validarObjeto() {
        validarDatos(
                this.cod_matricula,
                this.cod_modulo,
                this.repeticion,
                this.cal_primera,
                this.cal_segunda
        );
    }

    // =========================================================
    // ===================== MÉTODOS ===========================
    // =========================================================
    public static LineaMatricula obtenerLineas(String linea) {
        String[] partes = linea.split(";", -1);

        if (partes.length != 5) {
            throw new IllegalArgumentException("Línea inválida para LineaMatricula: " + linea);
        }

        int tempCodMatricula = Integer.parseInt(partes[0]);
        int tempCodModulo = Integer.parseInt(partes[1]);
        int tempRepeticion = Integer.parseInt(partes[2]);
        double tempCalPrimera = Double.parseDouble(partes[3]);
        double tempCalSegunda = Double.parseDouble(partes[4]);

        return new LineaMatricula(
                tempCodMatricula,
                tempCodModulo,
                tempRepeticion,
                tempCalPrimera,
                tempCalSegunda
        );
    }

    private void cargarDesdeLineas(ArrayList<String> temp) {

        SesionDatos.getLineas().clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                LineaMatricula lineaMatricula = LineaMatricula.obtenerLineas(linea);
                SesionDatos.getLineas().add(lineaMatricula);
            }
        }

        for (LineaMatricula lineaMatricula : SesionDatos.getLineas()) {
            System.out.println(lineaMatricula);
        }
    }

    // =========================================================
    // ===================== LOAD TO ===========================
    // =========================================================
    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroLineaMatricula, ".csv");
        }
    }

    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroLineaMatricula, ".json");
        }
    }

    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroLineaMatricula, ".txt");
        }
    }

    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroLineaMatricula, SesionDatos.getLineas());
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================
    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroLineaMatricula, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".json")) {

            SesionDatos.getLineas().clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroLineaMatricula);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    LineaMatricula lineaMatricula
                            = (LineaMatricula) GestionFicheros.toJson(string, LineaMatricula.class);

                    lineaMatricula.validarObjeto();

                    SesionDatos.getLineas().add(lineaMatricula);
                }
            }

            for (LineaMatricula lineaMatricula : SesionDatos.getLineas()) {
                System.out.println(lineaMatricula);
            }
        }
    }

    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroLineaMatricula);
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".txt")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroLineaMatricula, ".txt");
            cargarDesdeLineas(temp);
        }
    }

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================
    @Override
    public String toCSV() {
        return cod_matricula + ";"
                + cod_modulo + ";"
                + repeticion + ";"
                + cal_primera + ";"
                + cal_segunda;
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
        return "LineaMatricula{"
                + "cod_matricula=" + cod_matricula
                + ", cod_modulo=" + cod_modulo
                + ", repeticion=" + repeticion
                + ", cal_primera=" + cal_primera
                + ", cal_segunda=" + cal_segunda
                + '}';
    }
}
