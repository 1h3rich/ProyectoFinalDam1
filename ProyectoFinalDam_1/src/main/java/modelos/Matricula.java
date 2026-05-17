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

public class Matricula implements InterpolaridadDeDatos, Serializable, Comparable<Matricula> {

    private static final long serialVersionUID = 1L; //Esto es para poder importar los datos de binario a base de datos sin problemas

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

    /**
     * Creación rápida desde un array de cadenas (tabla Swing, ResultSet ya convertido, etc.).
     * Orden esperado: [0]=codigo, [1]=codigo_alumno, [2]=anio_academico, [3]=estado, [4]=importe.
     * No realiza validaciones — úsalo solo con datos que ya vienen de la BD.
     *
     * @param cadena Array de cadenas con los datos de la matrícula.
     */
    public Matricula(String cadena[]) {
        this.codigo = Integer.parseInt(cadena[0]);
        this.codigo_alumno = Integer.parseInt(cadena[1]);
        this.año_academico = Integer.parseInt(cadena[2]);
        this.estado = cadena[3];
        this.importe = Double.parseDouble(cadena[4]);

    }

    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================

    /** @return Código único de la matrícula (clave primaria). */
    public int getCodigo() {
        return codigo;
    }

    /** @return Código del alumno al que pertenece esta matrícula (clave foránea). */
    public int getCodigo_alumno() {
        return codigo_alumno;
    }

    /** @return Año académico de la matrícula (p.ej. 2024). */
    public int getAño_academico() {
        return año_academico;
    }

    /** @return Estado de la matrícula (parcial, completa o anulada). */
    public String getEstado() {
        return estado;
    }

    /** @return Importe económico de la matrícula. */
    public double getImporte() {
        return importe;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    /**
     * Cambia el alumno asociado a la matrícula, validando que el código sea positivo.
     *
     * @param codigo_alumno Nuevo código del alumno.
     * @throws IllegalArgumentException si el código es 0 o negativo.
     */
    public void setCodigo_alumno(int codigo_alumno) {
        if (!Validadores.validarCodigoPositivo(codigo_alumno)) {
            throw new IllegalArgumentException("El código del alumno debe ser mayor que 0");
        }

        this.codigo_alumno = codigo_alumno;
    }

    /**
     * Actualiza el año académico tras validar que esté en el rango [1900, 3000].
     *
     * @param año_academico Nuevo año académico.
     * @throws IllegalArgumentException si el año está fuera del rango válido.
     */
    public void setAño_academico(int año_academico) {
        if (!Validadores.validarAñoAcademico(año_academico)) {
            throw new IllegalArgumentException("El año académico no es válido");
        }

        this.año_academico = año_academico;
    }

    /**
     * Actualiza el estado de la matrícula tras validar que no esté vacío.
     *
     * @param estado Nuevo estado (parcial, completa o anulada).
     * @throws IllegalArgumentException si el estado es nulo o vacío.
     */
    public void setEstado(String estado) {
        if (!Validadores.validarTextoNoVacio(estado)) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }

        this.estado = estado;
    }

    /**
     * Actualiza el importe de la matrícula tras validar que no sea negativo.
     *
     * @param importe Nuevo importe.
     * @throws IllegalArgumentException si el importe es negativo.
     */
    public void setImporte(double importe) {
        if (!Validadores.validarImporte(importe)) {
            throw new IllegalArgumentException("El importe no puede ser negativo");
        }

        this.importe = importe;
    }

    // =========================================================
    // ==================== VALIDACIONES =======================
    // =========================================================

    /**
     * Valida todos los campos de una matrícula antes de asignarlos.
     *
     * @param codigo_alumno Código del alumno (debe ser positivo).
     * @param año_academico Año académico (rango 1900-3000).
     * @param estado        Estado de la matrícula (no vacío).
     * @param importe       Importe (no negativo).
     * @throws IllegalArgumentException en el primer campo inválido encontrado.
     */
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

    /**
     * Valida todos los campos del objeto actual.
     * Se usa al deserializar desde JSON para garantizar que los datos siguen siendo correctos.
     *
     * @throws IllegalArgumentException si cualquier campo no supera su validación.
     */
    private void validarObjeto() {
        validarDatos(this.codigo_alumno, this.año_academico, this.estado, this.importe);
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
    public int compareTo(Matricula otro) {
        return Integer.compare(this.codigo, otro.codigo);
    }

    /**
     * Parsea una línea CSV (separador ";") y devuelve la Matrícula correspondiente.
     *
     * @param linea Cadena con 5 campos: codigo;codigo_alumno;anio_academico;estado;importe.
     * @return Matrícula construida con los datos de la línea.
     * @throws IllegalArgumentException si la línea no tiene exactamente 5 campos.
     */
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

    /**
     * Reemplaza la lista de matrículas en sesión con los objetos parseados desde las líneas de texto.
     *
     * @param temp Lista de cadenas, cada una con los datos de una matrícula en formato CSV.
     */
    private void cargarDesdeLineas(ArrayList<String> temp) {

        SesionDatos.listaMatriculas.clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Matricula matricula = Matricula.obtenerLineas(linea);
                SesionDatos.listaMatriculas.add(matricula);
            }
        }

        for (Matricula matricula : SesionDatos.listaMatriculas) {
            System.out.println(matricula);
        }
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    /** Serializa esta matrícula en formato CSV y la añade al fichero matricula.csv. */
    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroMatricula, ".csv");
        }
    }

    /** Serializa esta matrícula en formato JSON y la añade al fichero matricula.json. */
    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroMatricula, ".json");
        }
    }

    /** Serializa esta matrícula en formato TXT (mismo separador que CSV) y la añade al fichero matricula.txt. */
    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroMatricula, ".txt");
        }
    }

    /** Guarda la lista completa de matrículas en sesión como objeto serializado binario (matricula.dat). */
    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroMatricula, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroMatricula, SesionDatos.listaMatriculas);
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    /** Lee matricula.csv y reemplaza la lista en sesión con los objetos deserializados. */
    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroMatricula, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroMatricula, ".csv");
            cargarDesdeLineas(temp);
        }
    }

    /** Lee matricula.json, valida cada objeto y reemplaza la lista en sesión. */
    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroMatricula, ".json")) {

            SesionDatos.listaMatriculas.clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroMatricula);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Matricula matricula = (Matricula) GestionFicheros.toJson(string, Matricula.class);

                    matricula.validarObjeto();

                    SesionDatos.listaMatriculas.add(matricula);
                }
            }

            for (Matricula matricula : SesionDatos.listaMatriculas) {
                System.out.println(matricula);
            }
        }
    }

    /** Lee matricula.dat (binario serializado) y reemplaza la lista en sesión. */
    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroMatricula, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroMatricula);
            cargarDesdeLineas(temp);
        }
    }

    /** Lee matricula.txt (formato CSV con ";") y reemplaza la lista en sesión. */
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

    /**
     * Serializa la matrícula como línea CSV con separador ";".
     *
     * @return Cadena en formato: codigo;codigo_alumno;anio_academico;estado;importe.
     */
    @Override
    public String toCSV() {
        return codigo + ";"
                + codigo_alumno + ";"
                + año_academico + ";"
                + estado + ";"
                + importe;
    }

    /** @return Cadena JSON con todos los campos de la matrícula. */
    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }

    /** @return Cadena idéntica a {@link #toCSV()}. */
    @Override
    public String toTXT() {
        return toCSV();
    }

    // =========================================================
    // ===================== TO STRING =========================
    // =========================================================

    /** @return Representación legible de la matrícula para depuración y logs. */
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
