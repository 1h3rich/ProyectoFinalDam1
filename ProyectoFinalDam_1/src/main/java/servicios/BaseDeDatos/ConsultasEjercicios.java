package servicios.BaseDeDatos;

public class ConsultasEjercicios {

    /**
     * Consultar la denominación, la familia profesional y el nivel de todos los
     * ciclos, indicando para cada ciclo, los nombres y las horas de sus
     * módulos. Este informe deberá estar ordenado por la denominación del ciclo
     * de forma ascendente.
     */
    public static final String[] datosConsulta1 = {
        """
        SELECT 
            c.denominacion,
            c.familia_profesional,
            c.nivel,
            m.nombre AS nombre_modulo,
            m.horas AS horas_modulo
        FROM ciclo c
        JOIN modulo m ON c.codigo = m.codigo_ciclo
        ORDER BY c.denominacion ASC
        """
    };

    /**
     * Solicitará al usuario un nivel de ciclo y un curso de módulo. Consultar
     * el nombre y las horas de los módulos que pertenecen a un nivel de ciclo
     * dado y se impartan en un curso dado, indicando para cada módulo, la
     * denominación del ciclo al que pertenece y el número de alumnos
     * matriculados. Este informe deberá estar ordenado por el nombre del módulo
     * de forma ascendente.
     */
    public static final String[] datosConsulta2 = {
        """
        SELECT 
            m.nombre,
            m.horas,
            c.denominacion AS denominacion_ciclo,
            COUNT(a.codigo) AS numero_alumnos_matriculados
        FROM modulo m
        JOIN ciclo c ON c.codigo = m.codigo_ciclo
        JOIN linea_matricula lm ON lm.codigo_modulo = m.codigo
        JOIN matricula ma ON ma.codigo = lm.codigo_matricula
        JOIN alumno a ON a.codigo = ma.codigo_alumno
        WHERE c.nivel = ? 
          AND m.curso = ?
        GROUP BY m.nombre, c.horas, c.denominacion
        ORDER BY m.nombre ASC
        """
    };

    /**
     * Solicitará al usuario un año académico de matrícula. Consultar el nombre
     * y la fecha de nacimiento de todos los alumnos, indicando para cada
     * alumno, los nombres de los módulos en los que está matriculado en un año
     * académico dado. Este informe deberá estar ordenado por el nombre del
     * alumno de forma ascendente.
     */
    public static final String[] datosConsulta3 = {
        """
        SELECT 
            a.nombre,
            a.fecha_nacimiento,
            m.nombre AS nombre_modulo
        FROM modulo m
        JOIN ciclo c ON c.codigo = m.codigo_ciclo
        JOIN linea_matricula lm ON lm.codigo_modulo = m.codigo
        JOIN matricula ma ON ma.codigo = lm.codigo_matricula
        JOIN alumno a ON a.codigo = ma.codigo_alumno
        WHERE ma.anio_academico = ?
        ORDER BY a.nombre ASC
        """
    };

    /**
     * Solicitará al usuario una denominación de ciclo, un curso de módulo y un
     * año académico de matrícula. Consultar el nombre y la fecha de nacimiento
     * de los alumnos matriculados en un curso dado de un ciclo dado para un año
     * académico dado, indicando para cada alumno, la calificación primera y la
     * calificación segunda de los módulos en los que está matriculado. Este
     * informe deberá estar ordenado por el nombre del alumno de forma
     * ascendente.
     */
    public static final String[] datosConsulta4 = {
        """
        SELECT 
            a.nombre,
            a.fecha_nacimiento,
            lm.calificacion_primera,
            lm.calificacion_segunda
        FROM modulo m
        JOIN ciclo c ON c.codigo = m.codigo_ciclo
        JOIN linea_matricula lm ON lm.codigo_modulo = m.codigo
        JOIN matricula ma ON ma.codigo = lm.codigo_matricula
        JOIN alumno a ON a.codigo = ma.codigo_alumno
        WHERE c.denominacion = ?
          AND m.curso = ?
          AND ma.anio_academico = ?
        ORDER BY a.nombre ASC
        """
    };

    /**
     * Consultar la denominación, la familia profesional y el nivel de todos los
     * ciclos, indicando para cada ciclo, el importe total de las matrículas de
     * cada año académico. Este informe deberá estar ordenado por la
     * denominación del ciclo de forma ascendente.
     */
    public static final String[] datosConsulta5 = {
        """
        SELECT 
            c.denominacion,
            c.familia_profesional,
            c.nivel,
            ma.anio_academico,
            SUM(ma.importe) AS importe_total
        FROM modulo m
        JOIN ciclo c ON c.codigo = m.codigo_ciclo
        JOIN linea_matricula lm ON lm.codigo_modulo = m.codigo
        JOIN matricula ma ON ma.codigo = lm.codigo_matricula
        GROUP BY c.denominacion, c.familia_profesional, c.nivel, ma.anio_academico
        ORDER BY c.denominacion ASC
        """
    };

    /**
     * Consultar el nombre del alumno y el año académico, indicando el total de
     * créditos ECTS y el total de horas en las que se ha matriculado. Este
     * informe deberá estar ordenado por el total de créditos de forma
     * descendente.
     */
    public static final String[] datosConsulta6 = {
        """
        SELECT 
            a.nombre,
            ma.anio_academico,
            SUM(m.creditos_ects) AS total_creditos,
            SUM(m.horas) AS total_horas_matriculadas
        FROM modulo m
        JOIN ciclo c ON c.codigo = m.codigo_ciclo
        JOIN linea_matricula lm ON lm.codigo_modulo = m.codigo
        JOIN matricula ma ON ma.codigo = lm.codigo_matricula
        JOIN alumno a ON a.codigo = ma.codigo_alumno
        GROUP BY a.nombre, ma.anio_academico
        ORDER BY SUM(m.creditos_ects) DESC
        """
    };

    /**
     * Consultar el nombre del alumno, el nombre del módulo y el año académico
     * de aquellos registros donde la "calificación_primera" todavía sea nula.
     * Este informe deberá estar ordenado por el año académico de forma
     * ascendente.
     */
    public static final String[] datosConsulta7 = {
        """
        SELECT 
            a.nombre AS nombre_alumno,
            m.nombre AS nombre_modulo,
            ma.anio_academico
        FROM modulo m
        JOIN ciclo c ON c.codigo = m.codigo_ciclo
        JOIN linea_matricula lm ON lm.codigo_modulo = m.codigo
        JOIN matricula ma ON ma.codigo = lm.codigo_matricula
        JOIN alumno a ON a.codigo = ma.codigo_alumno
        WHERE lm.calificacion_primera IS NULL
        ORDER BY ma.anio_academico ASC
        """
    };

    /**
     * Consultar el nombre del módulo y la denominación del ciclo, indicando
     * cuántos alumnos están matriculados con un número de "repetición" superior
     * a 1. Solo se mostrarán los módulos que tengan más de 3 alumnos
     * repetidores.
     */
    public static final String[] datosConsulta8 = {
        """
        SELECT m.nombre, c.denominacion, COUNT(lm.codigo_matricula) AS num_repetidores
        FROM modulo m
        JOIN ciclo c ON m.codigo_ciclo = c.codigo
        JOIN linea_matricula lm ON m.codigo = lm.codigo_modulo
        WHERE lm.repeticion > 1
        GROUP BY m.codigo, c.denominacion
        HAVING COUNT(lm.codigo_matricula) > 3;
        """
    };

    /**
     * Consultar el nombre, teléfono y correo de aquellos alumnos registrados
     * que no tengan ninguna matrícula asociada en el sistema o cuya matrícula
     * esté en estado "anulada". Este informe deberá estar ordenado por el
     * nombre del alumno de forma ascendente.
     */
    public static final String[] datosConsulta9 = {
        """
        SELECT DISTINCT
            a.nombre,
            a.telefono,
            a.correo
        FROM alumno a
        LEFT JOIN matricula mat ON a.codigo = mat.codigo_alumno
        WHERE mat.codigo IS NULL
           OR mat.estado = 'Anulada'
        ORDER BY a.nombre ASC
        """
    };
}
