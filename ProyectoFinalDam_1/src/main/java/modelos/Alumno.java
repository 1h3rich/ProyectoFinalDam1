package modelos;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import com.google.gson.Gson;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;
import interfaces.InterpolaridadDeDatos;

public class Alumno implements InterpolaridadDeDatos, Serializable, Comparable<Alumno> {

    private static final long serialVersionUID = 1L; //Esto es para poder importar los datos de binario a base de datos sin problemas

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================

    private final int codigo;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String domicilio;
    private String telefono;
    private String correo;

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================

    /**
     * Creación manual de nuevo alumno.
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

        int codigoGenerado = GestionBaseDeDatos.obtenerUltimoCodigo("alumno") + 1;

        if (!Validadores.validarCodigoPositivo(codigoGenerado)) {
            throw new IllegalArgumentException("El código generado del alumno debe ser mayor que 0");
        }

        validarDatos(nombre, fechaNacimiento, domicilio, telefono, correo);

        this.codigo = codigoGenerado;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
    }

    /**
     * Creación desde base de datos / fichero.
     *
     * @param codigo
     * @param nombre
     * @param fechaNacimiento
     * @param domicilio
     * @param telefono
     * @param correo
     */
    public Alumno(int codigo,
                  String nombre,
                  LocalDate fechaNacimiento,
                  String domicilio,
                  String telefono,
                  String correo) {

        if (!Validadores.validarCodigoPositivo(codigo)) {
            throw new IllegalArgumentException("El código del alumno debe ser mayor que 0");
        }

        validarDatos(nombre, fechaNacimiento, domicilio, telefono, correo);

        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
    }

    
    public Alumno(String cadena[]){
        this.codigo = Integer.parseInt(cadena[0]) ;
        this.nombre = cadena[1];
        this.fechaNacimiento = LocalDate.parse(cadena[2]);
        this.domicilio = cadena[3];
        this.telefono = cadena[4];
        this.correo = cadena[5];
        
        
        
        
        
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

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    public void setNombre(String nombre) {
        if (!Validadores.validarTextoNoVacio(nombre)) {
            throw new IllegalArgumentException("El nombre del alumno no puede estar vacío");
        }

        this.nombre = nombre;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (!Validadores.validarFechaNacimiento(fechaNacimiento)) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }

        this.fechaNacimiento = fechaNacimiento;
    }

    public void setDomicilio(String domicilio) {
        if (!Validadores.validarTextoNoVacio(domicilio)) {
            throw new IllegalArgumentException("El domicilio no puede estar vacío");
        }

        this.domicilio = domicilio;
    }

    public void setTelefono(String telefono) {
        if (!Validadores.validarTelefono(telefono)) {
            throw new IllegalArgumentException("El teléfono no es válido");
        }

        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        if (!Validadores.validarCorreo(correo)) {
            throw new IllegalArgumentException("El correo no es válido");
        }

        this.correo = correo;
    }

    // =========================================================
    // ==================== VALIDACIONES =======================
    // =========================================================
    
    /**
     * Valida que los datos estén introducidos correctamente.
     * @param nombre
     * @param fechaNacimiento
     * @param domicilio
     * @param telefono
     * @param correo 
     */
    private static void validarDatos(String nombre,
                                     LocalDate fechaNacimiento,
                                     String domicilio,
                                     String telefono,
                                     String correo) {

        if (!Validadores.validarTextoNoVacio(nombre)) {
            throw new IllegalArgumentException("El nombre del alumno no puede estar vacío");
        }

        if (!Validadores.validarFechaNacimiento(fechaNacimiento)) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }

        if (!Validadores.validarTextoNoVacio(domicilio)) {
            throw new IllegalArgumentException("El domicilio no puede estar vacío");
        }

        if (!Validadores.validarTelefono(telefono)) {
            throw new IllegalArgumentException("El teléfono no es válido");
        }

        if (!Validadores.validarCorreo(correo)) {
            throw new IllegalArgumentException("El correo no es válido");
        }
    }

    private void validarObjeto() {
        if (!Validadores.validarCodigoPositivo(this.codigo)) {
            throw new IllegalArgumentException("El código del alumno debe ser mayor que 0");
        }

        validarDatos(
                this.nombre,
                this.fechaNacimiento,
                this.domicilio,
                this.telefono,
                this.correo
        );
    }

    // =========================================================
    // ===================== MÉTODOS ===========================
    // =========================================================

    /**
     * Este metodo es para poder añadir datos al TreeSet, en este caso ordenados por codigo
     * @param otro
     * @return
     */
    @Override
    public int compareTo(Alumno otro){
        return Integer.compare(this.codigo, otro.codigo);
    }
    
    
    
    public static Alumno obtenerLineas(String linea) {
        String[] partes = linea.split(";", -1);

        if (partes.length != 6) {
            throw new IllegalArgumentException("Línea inválida para Alumno: " + linea);
        }

        int tempCodigo = Integer.parseInt(partes[0]);
        String tempNombre = partes[1];
        LocalDate tempFechaNacimiento = LocalDate.parse(partes[2]);
        String tempDomicilio = partes[3];
        String tempTelefono = partes[4];
        String tempCorreo = partes[5];

        return new Alumno(
                tempCodigo,
                tempNombre,
                tempFechaNacimiento,
                tempDomicilio,
                tempTelefono,
                tempCorreo
        );
    }

    private void cargarDesdeLineas(ArrayList<String> temp) {

       SesionDatos.listaAlumnos.clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Alumno alumno = Alumno.obtenerLineas(linea);
                SesionDatos.listaAlumnos.add(alumno);
            }
        }

        for (Alumno alumno : SesionDatos.listaAlumnos) {
            System.out.println(alumno);
        }
    }

    /**
     * Devuelve las matrículas que pertenecen a este alumno.La relación correcta está en Matricula.codigo_alumno.
     *
     * @return
     */
    public ArrayList<Matricula> obtenerMatriculasDelAlumno() {
        ArrayList<Matricula> matriculasDelAlumno = new ArrayList<>();

        for (Matricula matricula : SesionDatos.listaMatriculas) {
            if (matricula.getCodigo_alumno() == this.codigo) {
                matriculasDelAlumno.add(matricula);
            }
        }

        return matriculasDelAlumno;
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroAlumno, ".csv");
        }
    }

    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroAlumno, ".json");
        }
    }

    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroAlumno, ".txt");
        }
    }

    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroAlumno,SesionDatos.listaAlumnos);
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroAlumno, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroAlumno, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroAlumno, ".json")) {

            SesionDatos.listaAlumnos.clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroAlumno);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Alumno alumno = (Alumno) GestionFicheros.toJson(string, Alumno.class);

                    alumno.validarObjeto();

                   SesionDatos.listaAlumnos.add(alumno);
                }
            }

            for (Alumno alumno : SesionDatos.listaAlumnos) {
                System.out.println(alumno);
            }
        }
    }

    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroAlumno, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroAlumno);
            cargarDesdeLineas(temp);
        }
    }

    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroAlumno, ".txt")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroAlumno, ".txt");
            cargarDesdeLineas(temp);
        }
    }

    

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    @Override
    public String toCSV() {
        return codigo + ";"
                + nombre + ";"
                + fechaNacimiento + ";"
                + domicilio + ";"
                + telefono + ";"
                + correo;
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
