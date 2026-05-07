package modelos;

import Config.Config;
import Utils.Validadores;
import com.google.gson.Gson;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import servicios.BaseDeDatos.ConsultasEspecificas;
import servicios.BaseDeDatos.Insert;
import servicios.Ficheros.GestionFicheros;

public class Ciclo implements interfaces.interpolaridadDeDatos, Serializable {

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================
    private final int codigo;
    private String denominacion;
    private String familiaProfesional;
    private String nivel;
    private int horas;
    private int añoCurriculum;
    private HashMap<Integer, Modulo> modulos;
    public static ArrayList<Ciclo> lista = new ArrayList<>();

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================
    public Ciclo(String denominacion, String familiaProfesional, String nivel, int horas, int añoCurriculum) {
        this.codigo = ConsultasEspecificas.leerCodigoBDD("ciclo");
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.nivel = nivel;
        this.horas = horas;
        this.añoCurriculum = añoCurriculum;
        this.modulos = new HashMap<>();
    }

    public Ciclo(int codigo, String denominacion, String familiaProfesional, String nivel, int horas, int añoCurriculum) {
        this.codigo = codigo;
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.nivel = nivel;
        this.horas = horas;
        this.añoCurriculum = añoCurriculum;
        this.modulos = new HashMap<>();
    }

    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================
    public int getCodigo() { return codigo; }
    public String getDenominacion() { return denominacion; }
    public String getFamiliaProfesional() { return familiaProfesional; }
    public String getNivel() { return nivel; }
    public int getHoras() { return horas; }
    public int getAñoCurriculum() { return añoCurriculum; }
    public HashMap<Integer, Modulo> getModulos() { return modulos; }

    // =========================================================
    // ====================== SETTERS ==========================
    // =========================================================
    public void setDenominacion(String denominacion) { this.denominacion = denominacion; }
    public void setFamiliaProfesional(String familiaProfesional) { this.familiaProfesional = familiaProfesional; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public void setHoras(int horas) { this.horas = horas; }
    public void setAñoCurriculum(int añoCurriculum) { this.añoCurriculum = añoCurriculum; }

    // =========================================================
    // ======================= METODOS =========================
    // =========================================================
    public void agregarModulo(Modulo m) {
        this.modulos.put(m.getCodigo(), m);
    }

    public static Ciclo obtenerLineas(String linea) {
        String[] partes = linea.split(";");

        int cod = Integer.parseInt(partes[0]);
        String den = partes[1];
        String fam = partes[2];
        String niv = partes[3];
        int horas = Integer.parseInt(partes[4]);
        int año = Integer.parseInt(partes[5]);

        return new Ciclo(cod, den, fam, niv, horas, año);
    }

    private void cargarDesdeLineas(ArrayList<String> temp) {
        for (String linea : temp) {
            Ciclo c = obtenerLineas(linea);
            lista.add(c);
        }

        for (Ciclo c : lista) {
            System.out.println(c);
        }
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
            GestionFicheros.saveToBinario(Config.ficheroCiclo, lista);
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
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".txt")) {
            ArrayList<String> temp = GestionFicheros.loadTxtCsv(Config.ficheroCiclo, ".txt");
            cargarDesdeLineas(temp);
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
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroCiclo, ".json")) {
            ArrayList<String> temp = GestionFicheros.loadJson(Config.ficheroCiclo);

            for (String linea : temp) {
                Ciclo c = (Ciclo)GestionFicheros.fromJson(linea, Ciclo.class);
                lista.add(c);
            }

            for (Ciclo c : lista) {
                System.out.println(c);
            }
        }
    }

    // =========================================================
    // ======================= SQL =============================
    // =========================================================
    public static Ciclo SqlToObj(ResultSet rs) throws SQLException {
        return new Ciclo(
                rs.getInt("codigo"),
                rs.getString("denominacion"),
                rs.getString("familia_profesional"),
                rs.getString("nivel"),
                rs.getInt("horas"),
                rs.getInt("año_curriculum")
        );
    }

    public void ObjToSql() {
        Insert.insertarBDD(this);
    }

    // =========================================================
    // ================== CONVERTIDORES ========================
    // =========================================================
    @Override
    public String toCSV() {
        return codigo + ";" + denominacion + ";" + familiaProfesional + ";" + nivel + ";" + horas + ";" + añoCurriculum;
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
        return "Ciclo{" +
                "codigo=" + codigo +
                ", denominacion='" + denominacion + '\'' +
                ", familiaProfesional='" + familiaProfesional + '\'' +
                ", nivel='" + nivel + '\'' +
                ", horas=" + horas +
                ", añoCurriculum=" + añoCurriculum +
                '}';
    }
}