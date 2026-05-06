package modelos;

import Config.appConfig;
import Utils.Validadores;
import com.google.gson.Gson;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import servicios.BaseDeDatos.ConsultasEspecificas;
import servicios.BaseDeDatos.Insert;
import servicios.Ficheros.GestionFicheros;

public class Alumno implements interfaces.interpolaridadDeDatos, Serializable {

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================
    private final int codigo;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String domicilio;
    private String telefono;
    private String correo;
    private HashSet<Matricula> matriculas;
    public static ArrayList<Alumno> lista = new ArrayList<>();

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================
    /**
     * Creación manual (nuevo alumno)
     *
     * @param nombre
     * @param fechaNacimiento
     * @param domicilio
     * @param telefono
     * @param correo
     */
    public Alumno(String nombre,
            LocalDate fechaNacimiento,
            String domicilio,
            String telefono,
            String correo) {

        this.codigo = ConsultasEspecificas.leerCodigoBDD("alumno");
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;

        this.matriculas = new HashSet<>();
    }

    /**
     * Creación desde base de datos
     * @param codigo
     * @param nombre
     * @param fechaNacimiento
     * @param domicilio
     * @param telefono
     * @param correo
     */
    public Alumno(int codigo,String nombre,LocalDate fechaNacimiento,String domicilio,String telefono,String correo) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;

        this.matriculas = new HashSet<>();
    }

    // =================================================================================================================================================================================================
    // ===================== GETTERS ===================================================================================================================================================================
    // =================================================================================================================================================================================================
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public HashSet<Matricula> getMatriculas() {
        return matriculas;
    }

    // ==========================================================================================================================================================================
    // ====SETTERS===============================================================================================================================================================
    // ==========================================================================================================================================================================

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    // ==========================================================================================================================================================================
    // ===METODOS================================================================================================================================================================
    // ==========================================================================================================================================================================
    
    public void agregarMatriculas(Matricula m) {
        this.matriculas.add(m);
    }

    public static Alumno obtenerLineas(String linea) {
        String[] partes = linea.split(";");

        int cod = Integer.parseInt(partes[0]);
        String tempNom = partes[1];
        LocalDate tempFn = LocalDate.parse(partes[2]);
        String temoDom = partes[3];
        String tempTelf = partes[4];
        String tempCo = partes[5];

        return new Alumno(cod, tempNom, tempFn, temoDom, tempTelf, tempCo);
    }

    private void cargarDesdeLineas(ArrayList<String> temp) {

        for (String linea : temp) {
            Alumno alumno = Alumno.obtenerLineas(linea);
            lista.add(alumno);
        }

        for (Alumno alumno : lista) {
            System.out.println(alumno);
        }
    }

    // ===============================================================================================================================================================
    // ===================== SAVE TO =================================================================================================================================
    // ===============================================================================================================================================================
    @Override
    public void saveToCSV() {
        if (Validadores.comprobarFicheroEscritura(appConfig.ficheroAlumno, ".csv")) {
            GestionFicheros.saveToTxtCsvJson(this.toCSV(), appConfig.ficheroAlumno, ".csv");
        }
    }

    @Override
    public void saveToJSON() {
        if (Validadores.comprobarFicheroEscritura(appConfig.ficheroAlumno, ".json")) {
            GestionFicheros.saveToTxtCsvJson(this.toJSON(), appConfig.ficheroAlumno, ".json");
        }
    }

    @Override
    public void saveToTXT() {
        if (Validadores.comprobarFicheroEscritura(appConfig.ficheroAlumno, ".txt")) {
            GestionFicheros.saveToTxtCsvJson(this.toTXT(), appConfig.ficheroAlumno, ".txt");
        }
    }

    @Override
    public void saveToBinario() {
        if (Validadores.comprobarFicheroEscritura(appConfig.ficheroAlumno, ".dat")) {
            GestionFicheros.saveToBinario(appConfig.ficheroAlumno, lista);
        }
    }

    // ===============================================================================================================================================================
    // =================== FROM FILES ================================================================================================================================
    // ===============================================================================================================================================================
    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(appConfig.ficheroAlumno, ".csv")) {

            ArrayList<String> temp = GestionFicheros.loadTxtCsv(appConfig.ficheroAlumno, ".csv");
            cargarDesdeLineas(temp);

        }
    }

    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(appConfig.ficheroAlumno, ".json")) {
            ArrayList<String> temp = GestionFicheros.loadJson(appConfig.ficheroAlumno);

            for (String string : temp) {
                Alumno alumno = Alumno.fromJson(string);

                lista.add(alumno);
            }

            for (Alumno string : lista) {
                System.out.println(string);
            }

        }
    }

    /**
     * Objeto desde binario...
     */
    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(appConfig.ficheroAlumno, ".dat")) {
            ArrayList<String> temp = GestionFicheros.loadBinario(appConfig.ficheroAlumno);
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(appConfig.ficheroAlumno, ".txt")) {
            ArrayList<String> temp = GestionFicheros.loadTxtCsv(appConfig.ficheroAlumno, ".txt");
            cargarDesdeLineas(temp);
        }
    }

    // ===============================================================================================================================================================
    // ===================== SQL =====================================================================================================================================
    // ===============================================================================================================================================================
    public static Alumno SqlToObj(ResultSet rs) throws SQLException {

        return new Alumno(
                rs.getInt("codigo"),
                rs.getString("nombre"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("domicilio"),
                rs.getString("telefono"),
                rs.getString("correo")
        );
    }

    public void ObjToSql() {
        Insert.insertarAlumno(this);
    }

    // ===============================================================================================================================================================
    // ================= CONVERTIDORES ===============================================================================================================================
    // ===============================================================================================================================================================
    @Override
    public String toCSV() {
        return codigo + ";" + nombre + ";" + fechaNacimiento + ";"
                + domicilio + ";" + telefono + ";" + correo;
    }

    @Override
    public String toJSON() {
        return new Gson().toJson(this);

    }

    public static Alumno fromJson(String json) {
        return new Gson().fromJson(json, Alumno.class);

    }

    @Override
    public String toTXT() {
        return toCSV();
    }

    // ===============================================================================================================================================================
    // ===================== TO STRING ===============================================================================================================================
    // ===============================================================================================================================================================
    @Override
    public String toString() {
        return "Alumno{"
                + "codigo=" + codigo
                + ", nombre='" + nombre + '\''
                + ", fechaNacimiento=" + fechaNacimiento
                + ", domicilio='" + domicilio + '\''
                + ", telefono='" + telefono + '\''
                + ", correo='" + correo + '\''
                + '}';
    }
}
