package modelos;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import Utils.GsonUtils;
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

    
    /**
     * Creación rápida desde un array de cadenas (tabla Swing, ResultSet ya convertido, etc.).
     * Orden esperado: [0]=codigo, [1]=nombre, [2]=fecha_nacimiento, [3]=domicilio, [4]=telefono, [5]=correo.
     * No realiza validaciones — úsalo solo con datos que ya vienen de la BD.
     *
     * @param cadena Array de cadenas con los datos del alumno.
     */
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

    /** @return Código único del alumno (clave primaria). */
    public int getCodigo() {
        return codigo;
    }

    /** @return Nombre completo del alumno. */
    public String getNombre() {
        return nombre;
    }

    /** @return Fecha de nacimiento del alumno. */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /** @return Domicilio postal del alumno. */
    public String getDomicilio() {
        return domicilio;
    }

    /** @return Teléfono de contacto (9 dígitos). */
    public String getTelefono() {
        return telefono;
    }

    /** @return Correo electrónico del alumno. */
    public String getCorreo() {
        return correo;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    /**
     * Actualiza el nombre del alumno tras validar que no esté vacío.
     *
     * @param nombre Nuevo nombre del alumno.
     * @throws IllegalArgumentException si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (!Validadores.validarTextoNoVacio(nombre)) {
            throw new IllegalArgumentException("El nombre del alumno no puede estar vacío");
        }

        this.nombre = nombre;
    }

    /**
     * Actualiza la fecha de nacimiento tras comprobar que no es nula ni futura.
     *
     * @param fechaNacimiento Nueva fecha de nacimiento.
     * @throws IllegalArgumentException si la fecha es nula o posterior a hoy.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (!Validadores.validarFechaNacimiento(fechaNacimiento)) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }

        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Actualiza el domicilio tras validar que no esté vacío.
     *
     * @param domicilio Nuevo domicilio del alumno.
     * @throws IllegalArgumentException si el domicilio es nulo o vacío.
     */
    public void setDomicilio(String domicilio) {
        if (!Validadores.validarTextoNoVacio(domicilio)) {
            throw new IllegalArgumentException("El domicilio no puede estar vacío");
        }

        this.domicilio = domicilio;
    }

    /**
     * Actualiza el teléfono tras validar que tenga exactamente 9 dígitos.
     *
     * @param telefono Nuevo teléfono del alumno.
     * @throws IllegalArgumentException si el teléfono no cumple el formato de 9 dígitos.
     */
    public void setTelefono(String telefono) {
        if (!Validadores.validarTelefono(telefono)) {
            throw new IllegalArgumentException("El teléfono no es válido");
        }

        this.telefono = telefono;
    }

    /**
     * Actualiza el correo electrónico tras validar su formato básico.
     *
     * @param correo Nuevo correo electrónico del alumno.
     * @throws IllegalArgumentException si el correo no tiene formato válido.
     */
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

    /**
     * Valida todos los campos del objeto actual.
     * Se usa al deserializar desde JSON para garantizar que los datos siguen siendo correctos.
     *
     * @throws IllegalArgumentException si cualquier campo no supera su validación.
     */
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
    
    
    
    /**
     * Parsea una línea de texto con formato CSV (separador ";") y devuelve el Alumno correspondiente.
     *
     * @param linea Cadena con 6 campos separados por ";": codigo;nombre;fecha;domicilio;telefono;correo.
     * @return Alumno construido con los datos de la línea.
     * @throws IllegalArgumentException si la línea no tiene exactamente 6 campos.
     */
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

    /**
     * Reemplaza la lista en sesión con los alumnos parseados desde las líneas de texto.
     * Imprime cada alumno en consola tras la carga (útil para depuración).
     *
     * @param temp Lista de cadenas, cada una con los datos de un alumno en formato CSV.
     */
    private void cargarDesdeLineas(ArrayList<String> temp) {

       SesionDatos.getListaAlumnos().clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Alumno alumno = Alumno.obtenerLineas(linea);
                SesionDatos.getListaAlumnos().add(alumno);
            }
        }

        for (Alumno alumno : SesionDatos.getListaAlumnos()) {
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

        for (Matricula matricula : SesionDatos.getListaMatriculas()) {
            if (matricula.getCodigo_alumno() == this.codigo) {
                matriculasDelAlumno.add(matricula);
            }
        }

        return matriculasDelAlumno;
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    /** Serializa este alumno en formato CSV y lo añade al fichero alumno.csv. */
    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroAlumno, ".csv");
        }
    }

    /** Serializa este alumno en formato JSON y lo añade al fichero alumno.json. */
    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroAlumno, ".json");
        }
    }

    /** Serializa este alumno en formato TXT (mismo separador que CSV) y lo añade al fichero alumno.txt. */
    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroAlumno, ".txt");
        }
    }

    /** Guarda la lista completa de alumnos en sesión como objeto serializado binario (alumno.dat). */
    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroAlumno, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroAlumno,SesionDatos.getListaAlumnos());
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    /** Lee alumno.csv y reemplaza la lista en sesión con los objetos deserializados. */
    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroAlumno, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroAlumno, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    /** Lee alumno.json, valida cada objeto y reemplaza la lista en sesión. */
    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroAlumno, ".json")) {

            SesionDatos.getListaAlumnos().clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroAlumno);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Alumno alumno = (Alumno) GestionFicheros.toJson(string, Alumno.class);

                    alumno.validarObjeto();

                   SesionDatos.getListaAlumnos().add(alumno);
                }
            }

            for (Alumno alumno : SesionDatos.getListaAlumnos()) {
                System.out.println(alumno);
            }
        }
    }

    /** Lee alumno.dat (binario serializado) y reemplaza la lista en sesión. */
    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroAlumno, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroAlumno);
            cargarDesdeLineas(temp);
        }
    }

    /** Lee alumno.txt (formato CSV con ";") y reemplaza la lista en sesión. */
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

    /**
     * Serializa el alumno como línea CSV con separador ";".
     *
     * @return Cadena en formato: codigo;nombre;fecha_nacimiento;domicilio;telefono;correo.
     */
    @Override
    public String toCSV() {
        return codigo + ":"
                + nombre + ":"
                + fechaNacimiento + ":"
                + domicilio + ":"
                + telefono + ":"
                + correo;
    }

    /**
     * Serializa el alumno como objeto JSON usando Gson.
     *
     * @return Cadena JSON con todos los campos del alumno.
     */
    @Override
    public String toJSON() {
        return GsonUtils.get().toJson(this);
    }

    /**
     * Serializa el alumno en formato texto plano (mismo formato que CSV con ";").
     *
     * @return Cadena idéntica a {@link #toCSV()}.
     */
    @Override
    public String toTXT() {
         return codigo + ";"
                + nombre + ";"
                + fechaNacimiento + ";"
                + domicilio + ";"
                + telefono + ";"
                + correo;
    }

    // =========================================================
    // ===================== TO STRING =========================
    // =========================================================

    /**
     * Representación legible del alumno para depuración y logs.
     *
     * @return Cadena con todos los campos del alumno.
     */
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
