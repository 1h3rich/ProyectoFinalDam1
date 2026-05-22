package modelos;

import Config.Config;
import Control.SesionDatos;
import Utils.Validadores;
import com.google.gson.Gson;
import excepciones.Ciclo.HorasMayor0Exception;
import excepciones.Alumno.CodigMayor0Exception;
import excepciones.Modulo.CreditosMayor0Exception;
import excepciones.Modulo.CursoVacioException;
import excepciones.Modulo.LineaInvalidaModuloException;
import excepciones.Modulo.NombreVacioException;
import java.io.Serializable;
import java.util.ArrayList;

import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.Ficheros.GestionFicheros;
import interfaces.InterpolaridadDeDatos;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa un módulo formativo perteneciente a un ciclo.
 *
 * @author isard
 */
public class Modulo implements InterpolaridadDeDatos, Serializable, Comparable<Modulo> {

    private static final long serialVersionUID = 1L; //Esto es para poder importar los datos de binario a base de datos sin problemas

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================
    private final int codigo;
    private int codigo_ciclo;
    private String nombre;
    private String curso;
    private double creditos_ects;
    private int horas;

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================
    /**
     * Creación manual de nuevo módulo.
     *
     * @param codigo_ciclo  Código del ciclo al que pertenece el módulo.
     * @param nombre        Nombre del módulo.
     * @param curso         Curso en que se imparte (p.ej. "primero", "segundo").
     * @param creditos_ects Créditos ECTS asignados al módulo.
     * @param horas         Horas lectivas del módulo.
     * @throws CodigMayor0Exception si el código generado automáticamente es 0 o negativo.
     */
    public Modulo(int codigo_ciclo,
            String nombre,
            String curso,
            double creditos_ects,
            int horas) throws CodigMayor0Exception {

        int codigoGenerado = GestionBaseDeDatos.obtenerUltimoCodigo("modulo") + 1;

        if (!Validadores.validarCodigoPositivo(codigoGenerado)) {
                throw new CodigMayor0Exception("El código generado del módulo debe ser mayor que 0");
        }

        try {
            validarDatos(
                    codigo_ciclo,
                    nombre,
                    curso,
                    creditos_ects,
                    horas
            );
        } catch (NombreVacioException ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CursoVacioException ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreditosMayor0Exception ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HorasMayor0Exception ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.codigo = codigoGenerado;
        this.codigo_ciclo = codigo_ciclo;
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
    }

    /**
     * Creación desde base de datos / fichero.
     *
     * @param codigo        Código único del módulo (clave primaria).
     * @param codigo_ciclo  Código del ciclo al que pertenece el módulo.
     * @param nombre        Nombre del módulo.
     * @param curso         Curso en que se imparte (p.ej. "primero", "segundo").
     * @param creditos_ects Créditos ECTS asignados al módulo.
     * @param horas         Horas lectivas del módulo.
     * @throws CodigMayor0Exception si el código es 0 o negativo.
     */
    public Modulo(int codigo,
            int codigo_ciclo,
            String nombre,
            String curso,
            double creditos_ects,
            int horas) throws CodigMayor0Exception {

        if (!Validadores.validarCodigoPositivo(codigo)) {
            throw new CodigMayor0Exception("El código del módulo debe ser mayor que 0");
        }

        try {
            validarDatos(
                    codigo_ciclo,
                    nombre,
                    curso,
                    creditos_ects,
                    horas
            );
        } catch (NombreVacioException ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CursoVacioException ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreditosMayor0Exception ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HorasMayor0Exception ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.codigo = codigo;
        this.codigo_ciclo = codigo_ciclo;
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
    }

    /**
     * Creación rápida desde un array de cadenas (tabla Swing, ResultSet ya convertido, etc.).
     * Orden esperado: [0]=codigo, [1]=codigo_ciclo, [2]=nombre, [3]=curso, [4]=creditos_ects, [5]=horas.
     * No realiza validaciones — úsalo solo con datos que ya vienen de la BD.
     *
     * @param cadena Array de cadenas con los datos del módulo.
     */
    public Modulo(String cadena[]) {
        this.codigo = Integer.parseInt(cadena[0]);
        this.codigo_ciclo = Integer.parseInt(cadena[1]);
        this.nombre = cadena[2];
        this.curso = cadena[3];
        this.creditos_ects = Double.parseDouble(cadena[4]);
        this.horas = Integer.parseInt(cadena[5]);

    }
    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================

    /**
     * Retorna el código único del módulo (clave primaria).
     *
     * @return Código único del módulo (clave primaria).
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna el código del ciclo al que pertenece el módulo.
     *
     * @return Código del ciclo al que pertenece el módulo (clave foránea).
     */
    public int getCodigo_ciclo() {
        return codigo_ciclo;
    }

    /**
     * Retorna el nombre del módulo.
     *
     * @return Nombre del módulo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el curso en que se imparte el módulo.
     *
     * @return Curso en que se imparte el módulo (primero o segundo).
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Retorna los créditos ECTS asignados al módulo.
     *
     * @return Créditos ECTS asignados al módulo.
     */
    public double getCreditos_ects() {
        return creditos_ects;
    }

    /**
     * Retorna el número de horas lectivas del módulo.
     *
     * @return Número de horas lectivas del módulo.
     */
    public int getHoras() {
        return horas;
    }

    // =========================================================
    // ===================== SETTERS ===========================
    // =========================================================

    /**
     * Cambia el ciclo al que pertenece el módulo, validando que el código sea positivo.
     *
     * @param codigo_ciclo Nuevo código del ciclo.
     * @throws CodigMayor0Exception si el código es 0 o negativo.
     */
    public void setCodigo_ciclo(int codigo_ciclo) throws CodigMayor0Exception {
        if (!Validadores.validarCodigoPositivo(codigo_ciclo)) {
            throw new CodigMayor0Exception("El código del ciclo debe ser mayor que 0");
        }

        this.codigo_ciclo = codigo_ciclo;
    }

    /**
     * Actualiza el nombre del módulo tras validar que no esté vacío.
     *
     * @param nombre Nuevo nombre del módulo.
     * @throws NombreVacioException si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) throws NombreVacioException {
        if (!Validadores.validarTextoNoVacio(nombre)) {
            throw new NombreVacioException("El nombre del módulo no puede estar vacío");
        }

        this.nombre = nombre;
    }

    /**
     * Actualiza el curso del módulo tras validar que no esté vacío.
     *
     * @param curso Nuevo curso (p.ej. "primero", "segundo").
     * @throws CursoVacioException si el curso es nulo o vacío.
     */
    public void setCurso(String curso) throws CursoVacioException {
        if (!Validadores.validarCurso(curso)) {
            throw new CursoVacioException("El curso no puede estar vacío");
        }

        this.curso = curso;
    }

    /**
     * Actualiza los créditos ECTS del módulo tras validar que sean positivos.
     *
     * @param creditos_ects Nuevos créditos ECTS.
     * @throws CreditosMayor0Exception si los créditos son 0 o negativos.
     */
    public void setCreditos_ects(double creditos_ects) throws CreditosMayor0Exception {
        if (!Validadores.validarCreditosEcts(creditos_ects)) {
            throw new CreditosMayor0Exception("Los créditos ECTS deben ser mayores que 0");
        }

        this.creditos_ects = creditos_ects;
    }

    /**
     * Actualiza las horas del módulo tras validar que sean estrictamente positivas.
     *
     * @param horas Nuevo número de horas lectivas.
     * @throws HorasMayor0Exception si las horas son 0 o negativas.
     */
    public void setHoras(int horas) throws HorasMayor0Exception {
        if (!Validadores.validarHorasModulo(horas)) {
            throw new HorasMayor0Exception("Las horas del módulo deben ser mayores que 0");
        }

        this.horas = horas;
    }

    // =========================================================
    // ==================== VALIDACIONES =======================
    // =========================================================

    /**
     * Valida todos los campos de un módulo antes de asignarlos.
     *
     * @param codigo_ciclo  Código del ciclo al que pertenece.
     * @param nombre        Nombre del módulo.
     * @param curso         Curso en que se imparte.
     * @param creditos_ects Créditos ECTS.
     * @param horas         Horas lectivas.
     * @throws IllegalArgumentException en el primer campo inválido encontrado.
     */
    private static void validarDatos(int codigo_ciclo,
            String nombre,
            String curso,
            double creditos_ects,
            int horas) throws CodigMayor0Exception, NombreVacioException, CursoVacioException, CreditosMayor0Exception, HorasMayor0Exception {

        if (!Validadores.validarCodigoPositivo(codigo_ciclo)) {
            throw new CodigMayor0Exception("El código del ciclo debe ser mayor que 0");
        }

        if (!Validadores.validarTextoNoVacio(nombre)) {
            throw new NombreVacioException("El nombre del módulo no puede estar vacío");
        }

        if (!Validadores.validarCurso(curso)) {
            throw new CursoVacioException("El curso no puede estar vacío");
        }

        if (!Validadores.validarCreditosEcts(creditos_ects)) {
            throw new CreditosMayor0Exception("Los créditos ECTS deben ser mayores que 0");
        }

        if (!Validadores.validarHorasModulo(horas)) {
            throw new HorasMayor0Exception("Las horas del módulo deben ser mayores que 0");
        }
    }

    /**
     * Valida todos los campos del objeto actual.
     * Se usa al deserializar desde JSON para garantizar que los datos siguen siendo correctos.
     *
     * @throws IllegalArgumentException si cualquier campo no supera su validación.
     */
    private void validarObjeto() throws CodigMayor0Exception {
        if (!Validadores.validarCodigoPositivo(this.codigo)) {
            throw new CodigMayor0Exception("El código del módulo debe ser mayor que 0");
        }

        try {
            validarDatos(
                    this.codigo_ciclo,
                    this.nombre,
                    this.curso,
                    this.creditos_ects,
                    this.horas
            );
        } catch (CodigMayor0Exception ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NombreVacioException ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CursoVacioException ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreditosMayor0Exception ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HorasMayor0Exception ex) {
            Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // =========================================================
    // ===================== MÉTODOS ===========================
    // =========================================================
    /**
     * Este metodo es para poder añadir datos al TreeSet, en este caso ordenados
     * por codigo
     *
     * @param otro Módulo con el que se compara.
     * @return Resultado negativo, cero o positivo según si este código es menor, igual o mayor.
     */
    @Override
    public int compareTo(Modulo otro) {
        return Integer.compare(this.codigo, otro.codigo);
    }

    /**
     * Parsea una línea CSV (separador ";") y devuelve el Módulo correspondiente.
     *
     * @param linea Cadena con 6 campos: codigo;codigo_ciclo;nombre;curso;creditos_ects;horas.
     * @return Módulo construido con los datos de la línea.
     * @throws CodigMayor0Exception si el código de la línea es 0 o negativo.
     * @throws LineaInvalidaModuloException si la línea no tiene exactamente 6 campos.
     */
    public static Modulo obtenerLineas(String linea) throws CodigMayor0Exception, LineaInvalidaModuloException {
        String[] partes = linea.split(";", -1);

        if (partes.length != 6) {
            throw new LineaInvalidaModuloException("Línea inválida para Modulo: " + linea);
        }

        int tempCodigo = Integer.parseInt(partes[0]);
        int tempCodigoCiclo = Integer.parseInt(partes[1]);
        String tempNombre = partes[2];
        String tempCurso = partes[3];
        double tempCreditosEcts = Double.parseDouble(partes[4]);
        int tempHoras = Integer.parseInt(partes[5]);

        return new Modulo(
                tempCodigo,
                tempCodigoCiclo,
                tempNombre,
                tempCurso,
                tempCreditosEcts,
                tempHoras
        );
    }

    /**
     * Reemplaza la lista de módulos en sesión con los objetos parseados desde las líneas de texto.
     *
     * @param temp Lista de cadenas, cada una con los datos de un módulo en formato CSV.
     */
    private void cargarDesdeLineas(ArrayList<String> temp) throws CodigMayor0Exception, LineaInvalidaModuloException {

        SesionDatos.getListaModulos().clear();

        for (String linea : temp) {
            if (!linea.trim().isEmpty()) {
                Modulo modulo = Modulo.obtenerLineas(linea);
                SesionDatos.getListaModulos().add(modulo);
            }
        }

      
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    /** Serializa este módulo en formato CSV y lo añade al fichero modulo.csv. */
    @Override
    public void loadToCsv() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".csv")) {
            GestionFicheros.guardarTxtCsvJson(this.toCSV(), Config.ficheroModulo, ".csv");
        }
    }

    /** Serializa este módulo en formato JSON y lo añade al fichero modulo.json. */
    @Override
    public void loadToJson() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".json")) {
            GestionFicheros.guardarTxtCsvJson(this.toJSON(), Config.ficheroModulo, ".json");
        }
    }

    /** Serializa este módulo en formato TXT (mismo separador que CSV) y lo añade al fichero modulo.txt. */
    @Override
    public void loadToTxt() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".txt")) {
            GestionFicheros.guardarTxtCsvJson(this.toTXT(), Config.ficheroModulo, ".txt");
        }
    }

    /** Guarda la lista completa de módulos en sesión como objeto serializado binario (modulo.dat). */
    @Override
    public void loadToBinario() {
        if (Validadores.comprobarFicheroEscritura(Config.ficheroModulo, ".dat")) {
            GestionFicheros.guardarToBinario(Config.ficheroModulo, SesionDatos.getListaModulos());
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    /** Lee modulo.csv y reemplaza la lista en sesión con los objetos deserializados. */
    @Override
    public void objFromCSV() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".csv")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroModulo, ".csv");
            try {
                cargarDesdeLineas(temp);
            } catch (CodigMayor0Exception ex) {
                Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineaInvalidaModuloException ex) {
                Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** Lee modulo.json, valida cada objeto y reemplaza la lista en sesión. */
    @Override
    public void objFromJSON() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".json")) {

            SesionDatos.getListaModulos().clear();

            ArrayList<String> temp = GestionFicheros.leerJson(Config.ficheroModulo);

            for (String string : temp) {
                if (!string.trim().isEmpty()) {
                    Modulo modulo = (Modulo) GestionFicheros.toJson(string, Modulo.class);

                    try {
                        modulo.validarObjeto();
                    } catch (CodigMayor0Exception ex) {
                        Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    SesionDatos.getListaModulos().add(modulo);
                }
            }

        }
    }

    /** Lee modulo.dat (binario serializado) y reemplaza la lista en sesión. */
    @Override
    public void objFromBinario() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".dat")) {
            ArrayList<String> temp = GestionFicheros.leerBinario(Config.ficheroModulo);
            try {
                cargarDesdeLineas(temp);
            } catch (CodigMayor0Exception ex) {
                Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineaInvalidaModuloException ex) {
                Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** Lee modulo.txt (formato CSV con ";") y reemplaza la lista en sesión. */
    @Override
    public void objFromTXT() {
        if (Validadores.comprobarFicheroLectura(Config.ficheroModulo, ".txt")) {
            ArrayList<String> temp = GestionFicheros.leerTxtCsv(Config.ficheroModulo, ".txt");
            try {
                cargarDesdeLineas(temp);
            } catch (CodigMayor0Exception ex) {
                Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineaInvalidaModuloException ex) {
                Logger.getLogger(Modulo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    /**
     * Serializa el módulo como línea CSV con separador ";".
     *
     * @return Cadena en formato: codigo;codigo_ciclo;nombre;curso;creditos_ects;horas.
     */
    @Override
    public String toCSV() {
        return codigo + ":"
                + codigo_ciclo + ":"
                + nombre + ":"
                + curso + ":"
                + creditos_ects + ":"
                + horas;
    }

    /**
     * Serializa el módulo en formato JSON.
     *
     * @return Cadena JSON con todos los campos del módulo.
     */
    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }

    /**
     * Serializa el módulo en formato TXT (mismo separador que CSV).
     *
     * @return Cadena idéntica a {@link #toCSV()}.
     */
    @Override
    public String toTXT() {
        return codigo + ";"
                + codigo_ciclo + ";"
                + nombre + ";"
                + curso + ";"
                + creditos_ects + ";"
                + horas;
    }

    // =========================================================
    // ===================== TO STRING =========================
    // =========================================================

    /**
     * Retorna una representación legible del módulo para depuración y logs.
     *
     * @return Representación legible del módulo.
     */
    @Override
    public String toString() {
        return "Modulo{"
                + "codigo=" + codigo
                + ", codigo_ciclo=" + codigo_ciclo
                + ", nombre='" + nombre + '\''
                + ", curso='" + curso + '\''
                + ", creditos_ects=" + creditos_ects
                + ", horas=" + horas
                + '}';
    }
}
