package servicios.BaseDeDatos;

/**
 * Centraliza todas las sentencias SQL para las operaciones CRUD de cada tabla.
 * Cada constante es un array donde el índice 0 es la sentencia SQL
 * y los siguientes son los nombres de columna a mostrar.
 *
 * @author 1DAM
 */
public class ConsultasSQL {

    // =========================================================
    // ======================== ALUMNO =========================
    // =========================================================

    /** Sentencia INSERT para la tabla alumno. */
    public static final String[] INSERT_ALUMNO = {
        "INSERT INTO alumno (nombre, fecha_nacimiento, domicilio, telefono, correo) VALUES (?, ?, ?, ?, ?)"
    };

    /** Sentencia UPDATE para la tabla alumno. */
    public static final String[] UPDATE_ALUMNO = {
        "UPDATE alumno SET nombre=?, fecha_nacimiento=?, domicilio=?, telefono=?, correo=? WHERE codigo=?"
    };

    /** Sentencia DELETE para la tabla alumno. */
    public static final String[] DELETE_ALUMNO = {
        "DELETE FROM alumno WHERE codigo=?"
    };

    /** Sentencia SELECT alumno por código y columnas a mostrar. */
    public static final String[] SELECT_ALUMNO_POR_CODIGO = {
        "SELECT codigo, nombre, fecha_nacimiento, domicilio, telefono, correo FROM alumno WHERE codigo=?",
        "codigo", "nombre", "fecha_nacimiento", "domicilio", "telefono", "correo"
    };

    /** Sentencia SELECT todos los alumnos ordenados por nombre ASC y columnas a mostrar. */
    public static final String[] SELECT_ALUMNO_TODOS = {
        "SELECT codigo, nombre, fecha_nacimiento, domicilio, telefono, correo FROM alumno ORDER BY nombre ASC",
        "codigo", "nombre", "fecha_nacimiento", "domicilio", "telefono", "correo"
    };

    // =========================================================
    // ======================== CICLO ==========================
    // =========================================================

    /** Sentencia INSERT para la tabla ciclo. */
    public static final String[] INSERT_CICLO = {
        "INSERT INTO ciclo (denominacion, familia_profesional, nivel, horas, año_curriculum) VALUES (?, ?, ?, ?, ?)"
    };

    /** Sentencia UPDATE para la tabla ciclo. */
    public static final String[] UPDATE_CICLO = {
        "UPDATE ciclo SET denominacion=?, familia_profesional=?, nivel=?, horas=?, año_curriculum=? WHERE codigo=?"
    };

    /** Sentencia DELETE para la tabla ciclo. */
    public static final String[] DELETE_CICLO = {
        "DELETE FROM ciclo WHERE codigo=?"
    };

    /** Sentencia SELECT ciclo por código y columnas a mostrar. */
    public static final String[] SELECT_CICLO_POR_CODIGO = {
        "SELECT codigo, denominacion, familia_profesional, nivel, horas, año_curriculum FROM ciclo WHERE codigo=?",
        "codigo", "denominacion", "familia_profesional", "nivel", "horas", "año_curriculum"
    };

    /** Sentencia SELECT todos los ciclos ordenados por denominacion ASC y columnas a mostrar. */
    public static final String[] SELECT_CICLO_TODOS = {
        "SELECT codigo, denominacion, familia_profesional, nivel, horas, año_curriculum FROM ciclo ORDER BY denominacion ASC",
        "codigo", "denominacion", "familia_profesional", "nivel", "horas", "año_curriculum"
    };

    // =========================================================
    // ======================== MODULO =========================
    // =========================================================

    /** Sentencia INSERT para la tabla modulo. */
    public static final String[] INSERT_MODULO = {
        "INSERT INTO modulo (codigo_ciclo, nombre, curso, creditos_ects, horas) VALUES (?, ?, ?, ?, ?)"
    };

    /** Sentencia UPDATE para la tabla modulo. */
    public static final String[] UPDATE_MODULO = {
        "UPDATE modulo SET codigo_ciclo=?, nombre=?, curso=?, creditos_ects=?, horas=? WHERE codigo=?"
    };

    /** Sentencia DELETE para la tabla modulo. */
    public static final String[] DELETE_MODULO = {
        "DELETE FROM modulo WHERE codigo=?"
    };

    /** Sentencia SELECT modulo por código y columnas a mostrar. */
    public static final String[] SELECT_MODULO_POR_CODIGO = {
        "SELECT codigo, codigo_ciclo, nombre, curso, creditos_ects, horas FROM modulo WHERE codigo=?",
        "codigo", "codigo_ciclo", "nombre", "curso", "creditos_ects", "horas"
    };

    /** Sentencia SELECT todos los módulos ordenados por nombre ASC y columnas a mostrar. */
    public static final String[] SELECT_MODULO_TODOS = {
        "SELECT codigo, codigo_ciclo, nombre, curso, creditos_ects, horas FROM modulo ORDER BY nombre ASC",
        "codigo", "codigo_ciclo", "nombre", "curso", "creditos_ects", "horas"
    };

    // =========================================================
    // ====================== MATRICULA ========================
    // =========================================================

    /** Sentencia INSERT para la tabla matricula. */
    public static final String[] INSERT_MATRICULA = {
        "INSERT INTO matricula (codigo_alumno, anio_academico, estado, importe) VALUES (?, ?, ?, ?)"
    };

    /** Sentencia UPDATE para la tabla matricula. */
    public static final String[] UPDATE_MATRICULA = {
        "UPDATE matricula SET codigo_alumno=?, anio_academico=?, estado=?, importe=? WHERE codigo=?"
    };

    /** Sentencia DELETE para la tabla matricula. */
    public static final String[] DELETE_MATRICULA = {
        "DELETE FROM matricula WHERE codigo=?"
    };

    /** Sentencia SELECT matricula por código y columnas a mostrar. */
    public static final String[] SELECT_MATRICULA_POR_CODIGO = {
        "SELECT codigo, codigo_alumno, anio_academico, estado, importe FROM matricula WHERE codigo=?",
        "codigo", "codigo_alumno", "anio_academico", "estado", "importe"
    };

    /** Sentencia SELECT todas las matrículas ordenadas por estado ASC y columnas a mostrar. */
    public static final String[] SELECT_MATRICULA_TODOS = {
        "SELECT codigo, codigo_alumno, anio_academico, estado, importe FROM matricula ORDER BY estado ASC",
        "codigo", "codigo_alumno", "anio_academico", "estado", "importe"
    };

    // =========================================================
    // =================== LINEA_MATRICULA =====================
    // =========================================================

    /** Sentencia INSERT para la tabla linea_matricula. */
    public static final String[] INSERT_LINEA_MATRICULA = {
        "INSERT INTO linea_matricula (codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda) VALUES (?, ?, ?, ?, ?)"
    };

    /**
     * Sentencia UPDATE para la tabla linea_matricula.
     * La PK compuesta es (codigo_matricula, codigo_modulo).
     */
    public static final String[] UPDATE_LINEA_MATRICULA = {
        "UPDATE linea_matricula SET repeticion=?, calificacion_primera=?, calificacion_segunda=? WHERE codigo_matricula=? AND codigo_modulo=?"
    };

    /** Sentencia DELETE para la tabla linea_matricula por su clave primaria compuesta. */
    public static final String[] DELETE_LINEA_MATRICULA = {
        "DELETE FROM linea_matricula WHERE codigo_matricula=? AND codigo_modulo=?"
    };

    /** Sentencia SELECT linea_matricula por su clave compuesta y columnas a mostrar. */
    public static final String[] SELECT_LINEA_MATRICULA_POR_CODIGO = {
        "SELECT codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda FROM linea_matricula WHERE codigo_matricula=? AND codigo_modulo=?",
        "codigo_matricula", "codigo_modulo", "repeticion", "calificacion_primera", "calificacion_segunda"
    };

    /** Sentencia SELECT todas las líneas de matrícula ordenadas por código de matrícula ASC y columnas. */
    public static final String[] SELECT_LINEA_MATRICULA_TODOS = {
        "SELECT codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda FROM linea_matricula ORDER BY codigo_matricula ASC",
        "codigo_matricula", "codigo_modulo", "repeticion", "calificacion_primera", "calificacion_segunda"
    };
}
