package modelos;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import com.google.gson.Gson;
import interfaces.interpolaridadDeDatos;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;

public class Matricula implements interpolaridadDeDatos, Serializable {

    

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================

    private final int codigo;
    private int codigo_alumno;
    private int año_academico;
    private String estado;
    private double importe;

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================

    /**
     * Creación manual de nueva matrícula.
     *
     * @param codigo_alumno
     * @param año_academico
     * @param estado
     * @param importe
     */
    public Matricula(int codigo_alumno,
                     int año_academico,
                     String estado,
                     double importe) {

        validarDatos(codigo_alumno, año_academico, estado, importe);
         int codigoGenerado = GestionBaseDeDatos.obtenerUltimoCodigo("matricula") + 1;
        
        this.codigo = codigoGenerado;
        this.codigo_alumno = codigo_alumno;
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
    }

    /**
     * Creación desde base de datos / fichero.
     *
     * @param codigo
     * @param codigo_alumno
     * @param año_academico
     * @param estado
     * @param importe
     */
    public Matricula(int codigo,
                     int codigo_alumno,
                     int año_academico,
                     String estado,
                     double importe) {

        validarDatos(codigo_alumno, año_academico, estado, importe);

        this.codigo = codigo;
        this.codigo_alumno = codigo_alumno;
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
    }
    
    public Matricula(String cadena[]){
        this.codigo = Integer.parseInt(cadena[0]) ;
        this.codigo_alumno = Integer.parseInt(cadena[1]) ;
        this.año_academico = Integer.parseInt(cadena[2]) ;
        this.estado = cadena[3];
        this.importe = Double.parseDouble(cadena[4]);
       
    }

    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================

    public int getCodigo() {
        return codigo;
    }

    public int getCodigo_alumno() {
        return codigo_alumno;
    }

    public int getAño_academico() {
        return año_academico;
    }

    public String getEstado() {
        return estado;
    }

    public double getImporte() {
        return importe;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    public void setCodigo_alumno(int codigo_alumno) {
        if (!Validadores.validarCodigoPositivo(codigo_alumno)) {
            throw new IllegalArgumentException("El código del alumno debe ser mayor que 0");
        }

        this.codigo_alumno = codigo_alumno;
    }

    public void setAño_academico(int año_academico) {
        if (!Validadores.validarAñoAcademico(año_academico)) {
            throw new IllegalArgumentException("El año académico no es válido");
        }

        this.año_academico = año_academico;
    }

    public void setEstado(String estado) {
        if (!Validadores.validarTextoNoVacio(estado)) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }

        this.estado = estado;
    }

    public void setImporte(double importe) {
        if (!Validadores.validarImporte(importe)) {
            throw new IllegalArgumentException("El importe no puede ser negativo");
        }

        this.importe = importe;
    }

    // =========================================================
    // ==================== VALIDACIONES =======================
    // =========================================================

    private static void validarDatos(int codigo_alumno,
                                     int año_academico,
                                     String estado,
                                     double importe) {

        if (!Validadores.validarCodigoPositivo(codigo_alumno)) {
            throw new IllegalArgumentException("El código del alumno debe ser mayor que 0");
        }

        if (!Validadores.validarAñoAcademico(año_academico)) {
            throw new IllegalArgumentException("El año académico no es válido");
        }

        if (!Validadores.validarTextoNoVacio(estado)) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }

        if (!Validadores.validarImporte(importe)) {
            throw new IllegalArgumentException("El importe no puede ser negativo");
        }
    }

    private void validarObjeto() {
        validarDatos(this.codigo_alumno, this.año_academico, this.estado, this.importe);
    }

    // =========================================================
    // ===================== MÉTODOS ===========================
    // =========================================================

    public static Matricula obtenerLineas(String linea) {
        String[] partes = linea.split(";", -1);

        if (partes.length != 5) {
            throw new IllegalArgumentException("Línea inválida para Matricula: " + linea);
        }

        int tempCodigo = Integer.parseInt(partes[0]);
        int tempCodigoAlumno = Integer.parseInt(partes[1]);
        int tempAñoAcademico = Integer.parseInt(partes[2]);
        String tempEstado = partes[3];
        double tempImporte = Double.parseDouble(partes[4]);

        return new Matricula(
                tempCodigo,
                tempCodigoAlumno,
                tempAñoAcademico,
                tempEstado,
                tempImporte
        );
    }

    private void cargarDesdeLineas(ArrayList<String> temp) {

        SesionDatos.getMatriculas().clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Matricula matricula = Matricula.obtenerLineas(linea);
                SesionDatos.getMatriculas().add(matricula);
            }
        }

        for (Matricula matricula : SesionDatos.getMatriculas()) {
            System.out.println(matricula);
        }
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroMatricula, ".csv");
        }
    }

    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroMatricula, ".json");
        }
    }

    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroMatricula, ".txt");
        }
    }

    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroMatricula, SesionDatos.getMatriculas());
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroMatricula, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroMatricula, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroMatricula, ".json")) {

            SesionDatos.getMatriculas().clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroMatricula);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Matricula matricula = (Matricula) GestionFicheros.toJson(string, Matricula.class);

                    matricula.validarObjeto();

                    SesionDatos.getMatriculas().add(matricula);
                }
            }

            for (Matricula matricula : SesionDatos.getMatriculas()) {
                System.out.println(matricula);
            }
        }
    }

    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroMatricula, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroMatricula);
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroMatricula, ".txt")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroMatricula, ".txt");
            cargarDesdeLineas(temp);
        }
    }

   

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    @Override
    public String toCSV() {
        return codigo + ";"
                + codigo_alumno + ";"
                + año_academico + ";"
                + estado + ";"
                + importe;
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
        return "Matricula{"
                + "codigo=" + codigo
                + ", codigo_alumno=" + codigo_alumno
                + ", año_academico=" + año_academico
                + ", estado='" + estado + '\''
                + ", importe=" + importe
                + '}';
    }
}