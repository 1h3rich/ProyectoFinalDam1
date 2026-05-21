package modelos;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import com.google.gson.Gson;
import excepciones.Alumno.CodigMayor0Exception;
import excepciones.LineaMatricula.LineaInvalidaLineaMatriculaException;
import excepciones.LineaMatricula.MatriculaNotNullException;
import excepciones.LineaMatricula.ModuloNotNullException;
import excepciones.LineaMatricula.PrimeraCalifException;
import excepciones.LineaMatricula.RepeticionException;
import excepciones.LineaMatricula.SegundaCalifException;
import java.io.Serializable;
import java.util.ArrayList;
import servicios.Ficheros.GestionFicheros;
import interfaces.InterpolaridadDeDatos;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa una línea de matrícula que asocia un módulo concreto a una matrícula.
 *
 * <p>Una línea de matrícula actúa como tabla intermedia (relación N:M) entre
 * {@link Matricula} y {@link Modulo}: indica qué módulo está incluido en
 * una matrícula determinada, cuántas veces lo ha cursado el alumno
 * (repetición) y las calificaciones obtenidas en la primera y segunda
 * convocatoria.</p>
 *
 * <p>La clave compuesta está formada por {@code cod_matricula} + {@code cod_modulo}.
 * La clase implementa {@link InterpolaridadDeDatos} para la persistencia uniforme
 * en CSV, JSON, TXT y binario, y {@link Serializable} para la serialización binaria.</p>
 *
 * <p><b>Invariante:</b> ambos códigos son enteros positivos, la repetición es
 * no negativa y las calificaciones se encuentran en el rango [0.0, 10.0].</p>
 *
 * @author isard
 */
public class LineaMatricula implements InterpolaridadDeDatos, Serializable {

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
            double cal_segunda) throws MatriculaNotNullException, ModuloNotNullException {

        if (matricula == null) {
            throw new MatriculaNotNullException("La matrícula no puede ser null");
        }

        if (modulo == null) {
            throw new ModuloNotNullException("El módulo no puede ser null");
        }

        try {
            validarDatos(
                    matricula.getCodigo(),
                    modulo.getCodigo(),
                    repeticion,
                    cal_primera,
                    cal_segunda
            );
        } catch (CodigMayor0Exception ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepeticionException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrimeraCalifException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SegundaCalifException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        try {
            validarDatos(
                    cod_matricula,
                    cod_modulo,
                    repeticion,
                    cal_primera,
                    cal_segunda
            );
        } catch (CodigMayor0Exception ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepeticionException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrimeraCalifException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SegundaCalifException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.cod_matricula = cod_matricula;
        this.cod_modulo = cod_modulo;
        this.repeticion = repeticion;
        this.cal_primera = cal_primera;
        this.cal_segunda = cal_segunda;
    }

    /**
     * Creación rápida desde un array de cadenas (tabla Swing, ResultSet ya convertido, etc.).
     * Orden esperado: [0]=cod_matricula, [1]=cod_modulo, [2]=repeticion, [3]=cal_primera (null ok), [4]=cal_segunda (null ok).
     * No realiza validaciones — úsalo solo con datos que ya vienen de la BD.
     *
     * @param cadena Array de cadenas con los datos de la línea de matrícula.
     */
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

    /** @return Código de la matrícula a la que pertenece esta línea (parte de la clave compuesta). */
    public int getCod_matricula() {
        return cod_matricula;
    }

    /** @return Código del módulo al que corresponde esta línea (parte de la clave compuesta). */
    public int getCod_modulo() {
        return cod_modulo;
    }

    /** @return Número de veces que el alumno ha cursado el módulo (0 = primera vez). */
    public int getRepeticion() {
        return repeticion;
    }

    /** @return Calificación de la primera convocatoria (0.0–10.0; 0 si aún no evaluado). */
    public double getCal_primera() {
        return cal_primera;
    }

    /** @return Calificación de la segunda convocatoria (0.0–10.0; 0 si aún no evaluado). */
    public double getCal_segunda() {
        return cal_segunda;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    /**
     * Cambia el código de matrícula tras validar que sea positivo.
     *
     * @param cod_matricula Nuevo código de matrícula.
     * @throws IllegalArgumentException si el código es 0 o negativo.
     */
    public void setCod_matricula(int cod_matricula) throws CodigMayor0Exception {
        if (!Validadores.validarCodigoPositivo(cod_matricula)) {
            throw new CodigMayor0Exception("El código de matrícula debe ser mayor que 0");
        }

        this.cod_matricula = cod_matricula;
    }

    /**
     * Cambia el código de módulo tras validar que sea positivo.
     *
     * @param cod_modulo Nuevo código de módulo.
     * @throws IllegalArgumentException si el código es 0 o negativo.
     */
    public void setCod_modulo(int cod_modulo) throws CodigMayor0Exception {
        if (!Validadores.validarCodigoPositivo(cod_modulo)) {
            throw new CodigMayor0Exception("El código del módulo debe ser mayor que 0");
        }

        this.cod_modulo = cod_modulo;
    }

    /**
     * Actualiza el número de repetición tras validar que sea no negativo.
     *
     * @param repeticion Nuevo número de repetición.
     * @throws IllegalArgumentException si la repetición es negativa.
     */
    public void setRepeticion(int repeticion) throws RepeticionException {
        if (!Validadores.validarRepeticion(repeticion)) {
            throw new RepeticionException("La repetición debe ser 1 o 2");
        }

        this.repeticion = repeticion;
    }

    /**
     * Actualiza la calificación de primera convocatoria tras validar el rango [0, 10].
     *
     * @param cal_primera Nueva calificación de primera convocatoria.
     * @throws IllegalArgumentException si la calificación está fuera del rango válido.
     */
    public void setCal_primera(double cal_primera) throws PrimeraCalifException {
        if (!Validadores.validarCalificacion(cal_primera)) {
            throw new PrimeraCalifException("La primera calificación debe estar entre 0 y 10");
        }

        this.cal_primera = cal_primera;
    }

    /**
     * Actualiza la calificación de segunda convocatoria tras validar el rango [0, 10].
     *
     * @param cal_segunda Nueva calificación de segunda convocatoria.
     * @throws IllegalArgumentException si la calificación está fuera del rango válido.
     */
    public void setCal_segunda(double cal_segunda) throws SegundaCalifException {
        if (!Validadores.validarCalificacion(cal_segunda)) {
            throw new SegundaCalifException("La segunda calificación debe estar entre 0 y 10");
        }

        this.cal_segunda = cal_segunda;
    }

    // =========================================================
    // ==================== VALIDAR OBJETO =======================
    // =========================================================

    /**
     * Valida todos los campos de una línea de matrícula antes de asignarlos.
     *
     * @param cod_matricula Código de matrícula (positivo).
     * @param cod_modulo    Código de módulo (positivo).
     * @param repeticion    Número de repetición (no negativo).
     * @param cal_primera   Calificación primera convocatoria ([0, 10]).
     * @param cal_segunda   Calificación segunda convocatoria ([0, 10]).
     * @throws IllegalArgumentException en el primer campo inválido encontrado.
     */
    private static void validarDatos(int cod_matricula,
            int cod_modulo,
            int repeticion,
            double cal_primera,
            double cal_segunda) throws CodigMayor0Exception, RepeticionException, PrimeraCalifException, SegundaCalifException {

        if (!Validadores.validarCodigoPositivo(cod_matricula)) {
            throw new CodigMayor0Exception("El código de matrícula debe ser mayor que 0");
        }

        if (!Validadores.validarCodigoPositivo(cod_modulo)) {
            throw new CodigMayor0Exception("El código del módulo debe ser mayor que 0");
        }

        if (!Validadores.validarRepeticion(repeticion)) {
            throw new RepeticionException("La repetición debe ser 1 o 2");
        }

        if (!Validadores.validarCalificacion(cal_primera)) {
            throw new PrimeraCalifException("La primera calificación debe estar entre 0 y 10");
        }

        if (!Validadores.validarCalificacion(cal_segunda)) {
            throw new SegundaCalifException("La segunda calificación debe estar entre 0 y 10");
        }
    }

    /**
     * Valida todos los campos del objeto actual.
     * Se usa al deserializar desde JSON para garantizar que los datos siguen siendo correctos.
     *
     * @throws IllegalArgumentException si cualquier campo no supera su validación.
     */
    private void validarObjeto() {
        try {
            validarDatos(
                    this.cod_matricula,
                    this.cod_modulo,
                    this.repeticion,
                    this.cal_primera,
                    this.cal_segunda
            );
        } catch (CodigMayor0Exception ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepeticionException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrimeraCalifException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SegundaCalifException ex) {
            Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // =========================================================
    // ===================== MÉTODOS ===========================
    // =========================================================

    /**
     * Parsea una línea CSV (separador ";") y devuelve la LineaMatricula correspondiente.
     *
     * @param linea Cadena con 5 campos: cod_matricula;cod_modulo;repeticion;cal_primera;cal_segunda.
     * @return LineaMatricula construida con los datos de la línea.
     * @throws IllegalArgumentException si la línea no tiene exactamente 5 campos.
     */
    public static LineaMatricula obtenerLineas(String linea) throws LineaInvalidaLineaMatriculaException {
        String[] partes = linea.split(";", -1);

        if (partes.length != 5) {
            throw new LineaInvalidaLineaMatriculaException("Línea inválida para LineaMatricula: " + linea);
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

    /**
     * Reemplaza la lista de líneas de matrícula en sesión con los objetos parseados desde las líneas de texto.
     *
     * @param temp Lista de cadenas, cada una con los datos de una línea de matrícula en formato CSV.
     */
    private void cargarDesdeLineas(ArrayList<String> temp) throws LineaInvalidaLineaMatriculaException {

        SesionDatos.getListaLineasMatricula().clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                LineaMatricula lineaMatricula = LineaMatricula.obtenerLineas(linea);
                SesionDatos.getListaLineasMatricula().add(lineaMatricula);
            }
        }

       
    }

    // =========================================================
    // ===================== LOAD TO ===========================
    // =========================================================

    /** Serializa esta línea en formato CSV y la añade al fichero linea_matricula.csv. */
    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroLineaMatricula, ".csv");
        }
    }

    /** Serializa esta línea en formato JSON y la añade al fichero linea_matricula.json. */
    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroLineaMatricula, ".json");
        }
    }

    /** Serializa esta línea en formato TXT (mismo separador que CSV) y la añade al fichero linea_matricula.txt. */
    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroLineaMatricula, ".txt");
        }
    }

    /** Guarda la lista completa de líneas en sesión como objeto serializado binario (linea_matricula.dat). */
    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroLineaMatricula, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroLineaMatricula, SesionDatos.getListaLineasMatricula());
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    /** Lee linea_matricula.csv y reemplaza la lista en sesión. */
    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroLineaMatricula, ".csv");
            try {
                cargarDesdeLineas(temp);
            } catch (LineaInvalidaLineaMatriculaException ex) {
                Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** Lee linea_matricula.json, valida cada objeto y reemplaza la lista en sesión. */
    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".json")) {

            SesionDatos.getListaLineasMatricula().clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroLineaMatricula);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    LineaMatricula lineaMatricula
                            = (LineaMatricula) GestionFicheros.toJson(string, LineaMatricula.class);

                    lineaMatricula.validarObjeto();

                    SesionDatos.getListaLineasMatricula().add(lineaMatricula);
                }
            }

           
        }
    }

    /** Lee linea_matricula.dat (binario serializado) y reemplaza la lista en sesión. */
    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroLineaMatricula);
            try {
                cargarDesdeLineas(temp);
            } catch (LineaInvalidaLineaMatriculaException ex) {
                Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** Lee linea_matricula.txt (formato CSV con ";") y reemplaza la lista en sesión. */
    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroLineaMatricula, ".txt")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroLineaMatricula, ".txt");
            try {
                cargarDesdeLineas(temp);
            } catch (LineaInvalidaLineaMatriculaException ex) {
                Logger.getLogger(LineaMatricula.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    /**
     * Serializa la línea de matrícula como cadena CSV con separador ";".
     *
     * @return Cadena en formato: cod_matricula;cod_modulo;repeticion;cal_primera;cal_segunda.
     */
    @Override
    public String toCSV() {
        return cod_matricula + ":"
                + cod_modulo + ":"
                + repeticion + ":"
                + cal_primera + ":"
                + cal_segunda;
    }

    /** @return Cadena JSON con todos los campos de la línea de matrícula. */
    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }

    /** @return Cadena idéntica a {@link #toCSV()}. */
    @Override
    public String toTXT() {
         return cod_matricula + ";"
                + cod_modulo + ";"
                + repeticion + ";"
                + cal_primera + ";"
                + cal_segunda;
    }

    // =========================================================
    // ===================== TO STRING =========================
    // =========================================================

    /** @return Representación legible de la línea de matrícula para depuración y logs. */
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
