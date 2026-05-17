package servicios.BaseDeDatos;

/**
 * Centraliza todas las sentencias SQL para las operaciones CRUD de cada tabla.
 * Cada constante es un array donde el índice 0 es la sentencia SQL y los
 * siguientes son los nombres de columna a mostrar.
 *
 * @author 1DAM
 */
public class ConsultasSQL {

    // =========================================================
    // ================= Creacion Base De Datos ================
    // =========================================================
    /** Script DDL completo para crear la base de datos {@code centro_formacion} con todas sus tablas y restricciones de clave foránea. */
    public static final String[] CREACION_BASE_DE_DATOS = {
        """
        
        CREATE DATABASE centro_formacion;
        USE centro_formacion;
        
        CREATE TABLE ciclo (
            codigo INT PRIMARY KEY AUTO_INCREMENT,
            denominacion VARCHAR(100) NOT NULL,
            familia_profesional VARCHAR(100) NOT NULL,
            nivel VARCHAR(50) NOT NULL,
            horas INT NOT NULL,
            anio_curriculo int NOT NULL
        );
        
        CREATE TABLE modulo (
            codigo INT PRIMARY KEY AUTO_INCREMENT,
            nombre VARCHAR(100) NOT NULL,
            curso INT NOT NULL,
            horas INT NOT NULL,
            creditos_ects DECIMAL(4,2) NOT NULL,
            codigo_ciclo INT NOT NULL,
        
            CONSTRAINT fk_modulo_ciclo
                FOREIGN KEY (codigo_ciclo)
                REFERENCES ciclo(codigo)
                ON DELETE CASCADE
                ON UPDATE CASCADE
        );
       
        CREATE TABLE alumno (
            codigo INT PRIMARY KEY AUTO_INCREMENT,
            nombre VARCHAR(100) NOT NULL,
            correo VARCHAR(150) UNIQUE NOT NULL,
            domicilio VARCHAR(150) NOT NULL,
            telefono VARCHAR(20) NOT NULL,
            fecha_nacimiento DATE NOT NULL
        );
        
        CREATE TABLE matricula (
            codigo INT PRIMARY KEY AUTO_INCREMENT,
            estado VARCHAR(50) NOT NULL,
            importe DECIMAL(8,2) NOT NULL,
            anio_academico INT NOT NULL,
            codigo_alumno INT NOT NULL,
        
            CONSTRAINT fk_matricula_alumno
                FOREIGN KEY (codigo_alumno)
                REFERENCES alumno(codigo)
                ON DELETE CASCADE
                ON UPDATE CASCADE
        );
        
        CREATE TABLE linea_matricula (
            codigo_matricula INT NOT NULL,
            codigo_modulo INT NOT NULL,
            calificacion_primera DECIMAL(4,2),
            calificacion_segunda DECIMAL(4,2),
            repeticion int NOT NULL,
        
            PRIMARY KEY (codigo_matricula, codigo_modulo),
        
            CONSTRAINT fk_linea_matricula_matricula
                FOREIGN KEY (codigo_matricula)
                REFERENCES matricula(codigo)
                ON DELETE CASCADE
                ON UPDATE CASCADE,
        
            CONSTRAINT fk_linea_matricula_modulo
                FOREIGN KEY (codigo_modulo)
                REFERENCES modulo(codigo)
                ON DELETE CASCADE
                ON UPDATE CASCADE
        );
        """
    };

    /** Script DML con datos de ejemplo para poblar las tablas de ciclos, módulos, alumnos, matrículas y líneas de matrícula. */
    public static final String[] INSERTAR_DATOS_POR_DEFECTO = {
        """

      
      INSERT INTO ciclo
      (denominacion, familia_profesional, nivel, horas, anio_curriculo)
      VALUES
      ('Desarrollo de Aplicaciones Multiplataforma', 'Inform\u00e1tica y Comunicaciones', 'Grado Superior', 2000, 2024),
      ('Administraci\u00f3n de Sistemas Inform\u00e1ticos en Red', 'Inform\u00e1tica y Comunicaciones', 'Grado Superior', 2000, 2024),
      ('Gesti\u00f3n Administrativa', 'Administraci\u00f3n y Gesti\u00f3n', 'Grado Medio', 2000, 2023);
      

      
      INSERT INTO modulo
      (nombre, curso, horas, creditos_ects, codigo_ciclo)
      VALUES
      ('Programaci\u00f3n', 1, 256, 14.00, 1),
      ('Bases de Datos', 1, 192, 11.00, 1),
      ('Lenguajes de Marcas', 1, 128, 7.00, 1),
      ('Acceso a Datos', 2, 120, 9.00, 1),
      ('Desarrollo de Interfaces', 2, 120, 9.00, 1),
      
      ('Implantaci\u00f3n de Sistemas Operativos', 1, 224, 12.00, 2),
      ('Redes Locales', 1, 192, 11.00, 2),
      ('Seguridad y Alta Disponibilidad', 2, 100, 6.00, 2),
      
      ('Comunicaci\u00f3n Empresarial', 1, 160, 13.66, 3),
      ('Operaciones Administrativas', 1, 190, 12.4, 3);
      
 
      
      INSERT INTO alumno
      (nombre, correo, domicilio, telefono, fecha_nacimiento)
      VALUES
      ('Ricardo', 'ricardo.garcia@email.com', 'Calle Mayor 12, Zaragoza, Aragón', '600111222', '2003-03-13'),
      ('Paula', 'paula.lopez@email.com', 'Avenida Central 5, Zaragoza, Aragón', '600333444', '2002-08-21'),
      ('Miguel',  'miguel.navarro@email.com', 'Calle Norte 8, Madrid, Madrid', '600555666', '2001-11-02'),
      ('Lucía',  'lucia.martinez@email.com', 'Calle Sur 22, Valencia, Comunidad Valenciana', '600777888', '2004-05-17');
      

      
      INSERT INTO matricula
      (estado, importe, anio_academico, codigo_alumno)
      VALUES
      ('Activa', 420.50, '2025', 1),
      ('Activa', 420.50, '2024', 2),
      ('Pendiente', 390.00, '2025', 3),
      ('Anulada', 0.00, '2024', 4);

      
      INSERT INTO linea_matricula
      (codigo_matricula, codigo_modulo, calificacion_primera, calificacion_segunda, repeticion)
      VALUES
      (1, 1, 7.50, NULL, 2),
      (1, 2, 8.25, NULL, 3),
      (1, 3, 6.75, NULL, 2),
      
      (2, 1, 5.00, NULL, 1),
      (2, 2, 4.20, 6.10, 2),
      (2, 3, 7.00, NULL, 0),
      
      (3, 6, 6.50, NULL, 0),
      (3, 7, 8.00, NULL, 0),
      
      (4, 9, NULL, NULL, 2),
      (4, 10, NULL, NULL, 0);
      
      
      -- 1. Creamos 4 alumnos nuevos
      INSERT INTO alumno (nombre, correo, domicilio, telefono, fecha_nacimiento) VALUES
      ('Juan',  'juan.perez@email.com', 'Calle Pez 3, Madrid, Madrid',                             '600100001', '2000-01-01'),
      ('Ana',   'ana.garcia@email.com', 'Calle Luna 7, Sevilla, Andalucía',                        '600200002', '2000-02-02'),
      ('Luis',  'luis.rodri@email.com', 'Avenida España 14, Valencia, Comunidad Valenciana',       '600300003', '2000-03-03'),
      ('Elena', 'elena.sanz@email.com', 'Calle Ronda 9, Barcelona, Cataluña',                     '600400004', '2000-04-04');
      
      -- 2. Creamos sus matrículas (asumiendo que toman los IDs de alumno del 5 al 8)
      INSERT INTO matricula (estado, importe, anio_academico, codigo_alumno) VALUES
      ('Activa', 420.50, '2025', 5),
      ('Activa', 420.50, '2024', 6),
      ('Activa', 420.50, '2025', 7),
      ('Activa', 420.50, '2024', 8);
      
      -- 3. Los matriculamos en el módulo 1 (Programación) con repeticion = TRUE
      INSERT INTO linea_matricula (codigo_matricula, codigo_modulo, repeticion) VALUES
      (5, 1, 3),
      (6, 1, 3),
      (7, 1, 3),
      (8, 1, 3);
      
      """
    };

    // =========================================================
    // ======================== ALUMNO =========================
    // =========================================================
    /**
     * Sentencia INSERT para la tabla alumno.
     */
    public static final String[] INSERT_ALUMNO = {
        "Alumno",
        "INSERT INTO alumno (nombre, correo, domicilio, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)"
    };

    /**
     * Sentencia INSERT para la tabla alumno.
     */
    public static final String[] INSERT_ALUMNO_CON_CODIGO = {
        "Alumno",
        "INSERT INTO alumno (codigo ,nombre, fecha_nacimiento , domicilio, telefono, correo) VALUES (?, ?, ?, ?, ?, ?)"
    };

    /**
     * Sentencia UPDATE para la tabla alumno.
     */
    public static final String[] UPDATE_ALUMNO = {
        "UPDATE alumno SET nombre=?, fecha_nacimiento=?, domicilio=?, telefono=?, correo=? WHERE codigo=?"
    };

    /**
     * Sentencia DELETE para la tabla alumno.
     */
    public static final String[] DELETE_ALUMNO = {
        "DELETE FROM alumno WHERE codigo=?"
    };

    /**
     * SELECT alumno por código — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_ALUMNO_POR_CODIGO = {
        "SELECT codigo, nombre, fecha_nacimiento, domicilio, telefono, correo FROM alumno WHERE codigo=?",
        "codigo", "nombre", "fecha_nacimiento", "domicilio", "telefono", "correo"
    };

    /**
     * SELECT todos los alumnos — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_ALUMNO_TODOS = {
        "SELECT codigo, nombre, fecha_nacimiento, domicilio, telefono, correo FROM alumno ORDER BY nombre ASC",
        "codigo", "nombre", "fecha_nacimiento", "domicilio", "telefono", "correo"
    };

    /**
     * SELECT todos los alumnos — para cargar objetos en la lista
     * (guardarDatos=true). [0] tipo Java · [1] SQL · [2..n] columnas del
     * ResultSet → constructor Alumno(String[]).
     */
    public static final String[] SAVE_ALUMNO_TODOS = {
        "Alumno",
        "SELECT codigo, nombre, fecha_nacimiento, domicilio, telefono, correo FROM alumno ORDER BY nombre ASC",
        "codigo", "nombre", "fecha_nacimiento", "domicilio", "telefono", "correo"
    };

    // =========================================================
    // ======================== CICLO ==========================
    // =========================================================
    /**
     * Sentencia INSERT para la tabla ciclo.
     */
    public static final String[] INSERT_CICLO = {
        "Ciclo",
        "INSERT INTO ciclo (denominacion, familia_profesional, nivel, horas, anio_curriculo) VALUES (?, ?, ?, ?, ?)"
    };

    /**
     * Sentencia UPDATE para la tabla ciclo.
     */
    public static final String[] UPDATE_CICLO = {
        "UPDATE ciclo SET denominacion=?, familia_profesional=?, nivel=?, horas=?, anio_curriculo=? WHERE codigo=?"
    };

    /**
     * Sentencia DELETE para la tabla ciclo.
     */
    public static final String[] DELETE_CICLO = {
        "DELETE FROM ciclo WHERE codigo=?"
    };

    /**
     * SELECT ciclo por código — para mostrar por pantalla (guardarDatos=false).
     */
    public static final String[] SELECT_CICLO_POR_CODIGO = {
        "SELECT codigo, denominacion, familia_profesional, nivel, horas, anio_curriculo FROM ciclo WHERE denominacion like ?",
        "codigo", "denominacion", "familia_profesional", "nivel", "horas", "anio_curriculo"
    };

    /**
     * INSERT para ciclo incluyendo el código explícito (importación desde
     * fichero). Orden de entradas: codigo, denominacion, familia_profesional,
     * nivel, horas, anio_curriculo
     */
    public static final String[] INSERT_CICLO_CON_CODIGO = {
        "Ciclo",
        "INSERT INTO ciclo (codigo, denominacion, familia_profesional, nivel, horas, anio_curriculo) VALUES (?, ?, ?, ?, ?, ?)"
    };

    /**
     * SELECT todos los ciclos — para mostrar por pantalla (guardarDatos=false).
     */
    public static final String[] SELECT_CICLO_TODOS = {
        "SELECT codigo, denominacion, familia_profesional, nivel, horas, anio_curriculo FROM ciclo ORDER BY denominacion ASC",
        "codigo", "denominacion", "familia_profesional", "nivel", "horas", "anio_curriculo"
    };

    /**
     * SELECT todos los ciclos — para cargar objetos en la lista
     * (guardarDatos=true). [0] tipo Java · [1] SQL · [2..n] columnas del
     * ResultSet → constructor Ciclo(String[]).
     */
    public static final String[] SAVE_CICLO_TODOS = {
        "Ciclo",
        "SELECT codigo, denominacion, familia_profesional, nivel, horas, anio_curriculo FROM ciclo ORDER BY denominacion ASC",
        "codigo", "denominacion", "familia_profesional", "nivel", "horas", "anio_curriculo"
    };

    // =========================================================
    // ======================== MODULO =========================
    // =========================================================
    /**
     * Sentencia INSERT para la tabla modulo.
     */
    public static final String[] INSERT_MODULO = {
        "Modulo",
        "INSERT INTO modulo (nombre, curso, creditos_ects, horas, codigo_ciclo) VALUES (?,?,?,?,?)"
    };

    /**
     * INSERT para modulo incluyendo el código explícito (importación desde
     * fichero). Orden de entradas: codigo, codigo_ciclo, nombre, curso,
     * creditos_ects, horas
     */
    public static final String[] INSERT_MODULO_CON_CODIGO = {
        "Modulo",
        "INSERT INTO modulo (codigo, codigo_ciclo, nombre, curso, creditos_ects, horas) VALUES (?, ?, ?, ?, ?, ?)"
    };

    /**
     * Sentencia UPDATE para la tabla modulo.
     */
    public static final String[] UPDATE_MODULO = {
        "UPDATE modulo SET codigo_ciclo=?, nombre=?, curso=?, creditos_ects=?, horas=? WHERE codigo=?"
    };

    /**
     * Sentencia DELETE para la tabla modulo.
     */
    public static final String[] DELETE_MODULO = {
        "DELETE FROM modulo WHERE codigo=?"
    };

    /**
     * SELECT modulo por código — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_MODULO_POR_CODIGO = {
        "SELECT codigo, codigo_ciclo, nombre, curso, creditos_ects, horas FROM modulo WHERE nombre like ?",
        "codigo", "codigo_ciclo", "nombre", "curso", "creditos_ects", "horas"
    };

    /**
     * SELECT todos los módulos — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_MODULO_TODOS = {
        "SELECT codigo, codigo_ciclo, nombre, curso, creditos_ects, horas FROM modulo ORDER BY nombre ASC",
        "codigo", "codigo_ciclo", "nombre", "curso", "creditos_ects", "horas"
    };

    /**
     * SELECT todos los módulos — para cargar objetos en la lista
     * (guardarDatos=true). [0] tipo Java · [1] SQL · [2..n] columnas del
     * ResultSet → constructor Modulo(String[]).
     */
    public static final String[] SAVE_MODULO_TODOS = {
        "Modulo",
        "SELECT codigo, codigo_ciclo, nombre, curso, creditos_ects, horas FROM modulo ORDER BY nombre ASC",
        "codigo", "codigo_ciclo", "nombre", "curso", "creditos_ects", "horas"
    };

    // =========================================================
    // ====================== MATRICULA ========================
    // =========================================================
    /**
     * Sentencia INSERT para la tabla matricula.
     */
    public static final String[] INSERT_MATRICULA = {
        "Matricula",
        "INSERT INTO matricula (estado, importe, anio_academico, codigo_alumno) VALUES (?, ?, ?, ?)"
    };

    // ====================== MATRICULA ========================
    /**
     * INSERT para matricula incluyendo el código explícito (importación desde
     * fichero). Orden de entradas: codigo, codigo_alumno, anio_academico,
     * estado, importe
     */
    public static final String[] INSERT_MATRICULA_CON_CODIGO = {
        "Matricula",
        "INSERT INTO matricula (codigo, codigo_alumno, anio_academico, estado, importe) VALUES (?, ?, ?, ?, ?)"
    };

    /**
     * Sentencia UPDATE para la tabla matricula.
     */
    public static final String[] UPDATE_MATRICULA = {
        "UPDATE matricula SET codigo_alumno=?, anio_academico=?, estado=?, importe=? WHERE codigo=?"
    };

    /**
     * Sentencia DELETE para la tabla matricula.
     */
    public static final String[] DELETE_MATRICULA = {
        "DELETE FROM matricula WHERE codigo=?"
    };

    /**
     * SELECT matricula por código — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_MATRICULA_POR_CODIGO = {
        "SELECT codigo, codigo_alumno, anio_academico, estado, importe FROM matricula WHERE codigo = (select codigo from alumno where telefono = ? )",
        "codigo", "codigo_alumno", "anio_academico", "estado", "importe"
    };

    /**
     * SELECT todas las matrículas — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_MATRICULA_TODOS = {
        "SELECT codigo, codigo_alumno, anio_academico, estado, importe FROM matricula ORDER BY estado ASC",
        "codigo", "codigo_alumno", "anio_academico", "estado", "importe"
    };

    /**
     * SELECT todas las matrículas — para cargar objetos en la lista
     * (guardarDatos=true). [0] tipo Java · [1] SQL · [2..n] columnas del
     * ResultSet → constructor Matricula(String[]).
     */
    public static final String[] SAVE_MATRICULA_TODOS = {
        "Matricula",
        "SELECT codigo, codigo_alumno, anio_academico, estado, importe FROM matricula ORDER BY estado ASC",
        "codigo", "codigo_alumno", "anio_academico", "estado", "importe"
    };

    // =========================================================
    // =================== LINEA_MATRICULA =====================
    // =========================================================
    /**
     * Sentencia INSERT para la tabla linea_matricula. Se puede utilizar este para los imports
     */
    public static final String[] INSERT_LINEA_MATRICULA = {
        "LineaMatricula",
        "INSERT INTO linea_matricula (codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda) VALUES (?, ?, ?, ?, ?)"
    };

    /**
     * Sentencia UPDATE para la tabla linea_matricula. La PK compuesta es
     * (codigo_matricula, codigo_modulo).
     */
    public static final String[] UPDATE_LINEA_MATRICULA = {
        "UPDATE linea_matricula SET repeticion=?, calificacion_primera=?, calificacion_segunda=? WHERE codigo_matricula=? AND codigo_modulo=?"
    };

    /**
     * Sentencia DELETE para la tabla linea_matricula por su clave primaria
     * compuesta.
     */
    public static final String[] DELETE_LINEA_MATRICULA = {
        "DELETE FROM linea_matricula WHERE codigo_matricula=? AND codigo_modulo=?"
    };

    /**
     * SELECT linea_matricula por clave compuesta — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_LINEA_MATRICULA_POR_CODIGO = {
        "SELECT codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda FROM linea_matricula "
        + "WHERE codigo_matricula = "
        + "(select codigo from matricula where codigo_alumno = "
        + "(select codigo from alumno where telefono = ?))",
        "codigo_matricula", "codigo_modulo", "repeticion", "calificacion_primera", "calificacion_segunda"
    };

    /**
     * SELECT todas las líneas de matrícula — para mostrar por pantalla
     * (guardarDatos=false).
     */
    public static final String[] SELECT_LINEA_MATRICULA_TODOS = {
        "SELECT codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda FROM linea_matricula ORDER BY codigo_matricula ASC",
        "codigo_matricula", "codigo_modulo", "repeticion", "calificacion_primera", "calificacion_segunda"
    };

    /**
     * SELECT todas las líneas de matrícula — para cargar objetos en la lista
     * (guardarDatos=true). [0] tipo Java · [1] SQL · [2..n] columnas →
     * constructor LineaMatricula(String[]).
     */
    public static final String[] SAVE_LINEA_MATRICULA_TODOS = {
        "LineaMatricula",
        "SELECT codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda FROM linea_matricula ORDER BY codigo_matricula ASC",
        "codigo_matricula", "codigo_modulo", "repeticion", "calificacion_primera", "calificacion_segunda"
    };

    // =========================================================
    // ===================  CONSULTAS EXTRA ====================
    // =========================================================
    /** Sentencia UPDATE para reasignar un módulo a un ciclo distinto dado su código. */
    public static final String[] ASIGNAR_MODULO_A_CICLO = {
        "UPDATE modulo SET codigo_ciclo = ? WHERE codigo = ?",};

}
