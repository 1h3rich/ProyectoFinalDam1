package modelos;

import Config.appConfig;
import com.google.gson.Gson;
import java.io.*;
import servicios.BaseDeDatos.Insert;
import servicios.Ficheros.GestionFicheros;

public class LineaMatricula implements interfaces.interpolaridadDeDatos {

    // ============================================================================================================================================================================
    // ===================== ATRIBUTOS ============================================================================================================================================
    // ============================================================================================================================================================================
    // IDs (persistencia)
    private int codigo_matricula;
    private int codigo_modulo;

    // Objetos (BDD / lógica)
    private Matricula ma;
    private Modulo mo;

    // Datos propios
    private int repeticion;
    private double calificacion_primera;
    private double calificacion_segunda;

    // ============================================================================================================================================================================
    // ================== CONSTRUCTORES ===========================================================================================================================================
    // ============================================================================================================================================================================
    /**
     * Constructor desde CSV / TXT / JSON (trabaja con IDs)
     */
    public LineaMatricula(int codigo_matricula,
            int codigo_modulo,
            int repeticion,
            double calificacion_primera,
            double calificacion_segunda) {

        this.codigo_matricula = codigo_matricula;
        this.codigo_modulo = codigo_modulo;
        this.repeticion = repeticion;
        this.calificacion_primera = calificacion_primera;
        this.calificacion_segunda = calificacion_segunda;
    }

    /**
     * Constructor desde BDD (trabaja con objetos)
     */
    public LineaMatricula(Matricula ma,
            Modulo mo,
            int repeticion,
            double calificacion_primera,
            double calificacion_segunda) {

        this.ma = ma;
        this.mo = mo;

        this.codigo_matricula = ma.getCodigo();
        this.codigo_modulo = mo.getCodigo();

        this.repeticion = repeticion;
        this.calificacion_primera = calificacion_primera;
        this.calificacion_segunda = calificacion_segunda;
    }

    // ============================================================================================================================================================================
    // ===================== GETTERS ==============================================================================================================================================
    // ============================================================================================================================================================================
    public int getCodigo_matricula() {
        return codigo_matricula;
    }

    public int getCodigo_modulo() {
        return codigo_modulo;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public double getCalificacion_primera() {
        return calificacion_primera;
    }

    public double getCalificacion_segunda() {
        return calificacion_segunda;
    }

    public Matricula getMatricula() {
        return ma;
    }

    public Modulo getModulo() {
        return mo;
    }

    // ============================================================================================================================================================================
    // ==================== SAVE ==================================================================================================================================================
    // ============================================================================================================================================================================
    
    /**
     * Guarda en CSV
     */
    @Override
    public void saveToCSV() {
        GestionFicheros.crearFichero(appConfig.ficheroLineaMatricula + ".csv");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroLineaMatricula + ".csv", true))) {

            bw.write(toCSV());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error CSV: " + e.getMessage());
        }
    }

    /**
     * Guarda en JSON
     */
    @Override
    public void saveToJSON() {
        GestionFicheros.crearFichero(appConfig.ficheroLineaMatricula + ".json");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroLineaMatricula + ".json", true))) {

            bw.write(toJSON());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error JSON: " + e.getMessage());
        }
    }

    /**
     * Guarda en Binario
     */
    @Override
    public void saveToBinario() {
        GestionFicheros.crearFichero(appConfig.ficheroLineaMatricula + ".dat");

        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(appConfig.ficheroLineaMatricula + ".dat", true))) {

            dos.writeInt(codigo_matricula);
            dos.writeInt(codigo_modulo);
            dos.writeInt(repeticion);
            dos.writeDouble(calificacion_primera);
            dos.writeDouble(calificacion_segunda);

        } catch (IOException e) {
            System.out.println("Error BIN: " + e.getMessage());
        }
    }
    
    /**
     * Guarda en TXT
     */
    @Override
    public void saveToTXT() {
        GestionFicheros.crearFichero(appConfig.ficheroLineaMatricula + ".txt");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroLineaMatricula + ".txt", true))) {

            bw.write(toTXT());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error TXT: " + e.getMessage());
        }
    }

    // ============================================================================================================================================================================
    // ==================== LOAD ==================================================================================================================================================
    // ============================================================================================================================================================================
    
    /**
     * Construye desde CSV
     */
    @Override
    public void objFromCSV() {
    }

    /**
     * Construye desde JSON
     */
    @Override
    public void objFromJSON() {
    }

    /**
     * Construye desde Binario
     */
    @Override
    public void objFromBinario() {
    }

    /**
     * Construye desde TXT
     */
    @Override
    public void objFromTXT() {
    }

    // ============================================================================================================================================================================
    // ================= CONVERTIDORES ============================================================================================================================================
    // ============================================================================================================================================================================
    @Override
    public String toCSV() {
        return codigo_matricula + ";"
                + codigo_modulo + ";"
                + repeticion + ";"
                + calificacion_primera + ";"
                + calificacion_segunda;
    }

    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }
    
    public static LineaMatricula fromJson(String json) {
        return new Gson().fromJson(json, LineaMatricula.class);

    }

    @Override
    public String toTXT() {
        return toCSV();
    }

    // ============================================================================================================================================================================
    // ===================== SQL ==================================================================================================================================================
    // ============================================================================================================================================================================
    public static LineaMatricula SqlToObj(java.sql.ResultSet rs) throws java.sql.SQLException {

        return new LineaMatricula(
                rs.getInt("codigo_matricula"),
                rs.getInt("codigo_modulo"),
                rs.getInt("repeticion"),
                rs.getDouble("calificacion_primera"),
                rs.getDouble("calificacion_segunda")
        );
    }

    public void ObjToSql() {
        Insert.insertarLineaMatricula(this);
    }

    // ============================================================================================================================================================================
    // ===================== TO STRING ============================================================================================================================================
    // ============================================================================================================================================================================ 
    @Override
    public String toString() {
        return "LineaMatricula{"
                + "codMat=" + codigo_matricula
                + ", codMod=" + codigo_modulo
                + ", rep=" + repeticion
                + ", cal1=" + calificacion_primera
                + ", cal2=" + calificacion_segunda
                + '}';
    }
}
