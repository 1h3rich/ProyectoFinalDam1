/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.BaseDeDatos;

/**
 *
 * @author isard
 */
public class Consultas {
    //Aqui van las consultas especificas de los informes multitabla

    /*
    Consultar la denominación, la familia profesional y el nivel de todos los ciclos,
    indicando para cada ciclo, los nombres y las horas de sus módulos.
    Este informe deberá estar ordenado por la denominación del ciclo de forma ascendente. 
     */
    public static String consulta1 = """
                                     Select c.denominacion, c.familia_profesional, c.nivel, m.nombre 
                                     as 'nombre modulo', m.horas as 'horas modulo'
                                     from ciclo c join modulo m on c.codigo= m.codigo_ciclo
                                     order by c.denominacion asc;
                                     """;

    /*
    Solicitará al usuario un nivel de ciclo y un curso de módulo. Consultar el nombre 
    y las horas de los módulos que pertenecen a un nivel de ciclo dado y se impartan 
    en un curso dado, indicando para cada módulo, la denominación del ciclo al que pertenece
    y el número de alumnos matriculados. Este informe deberá estar ordenado por el nombre del módulo de forma ascendente. 
     */
    public static String consulta2 = """
                                    Select m.nombre, m.horas , c.denominacion 
                                    as ' denominacion ciclo', count(a.codigo) as 'numero de alumnos matriculados'
                                    from modulo m join ciclo c on c.codigo=m.codigo_ciclo
                                    join  linea_matricula lm on lm.codigo_modulo = m.codigo
                                    join matricula ma on ma.codigo = lm.codigo_matricula
                                    join alumno a on a.codigo = ma.codigo_alumno
                                    where c.nivel = ? and m.curso = ?
                                    group by  m.nombre, m.horas , c.denominacion;
                                     """ // En esta consulta hay dos parametros que hay que pasarle
            ;

    /*
    Solicitará al usuario un año académico de matrícula. Consultar el nombre 
    y la fecha de nacimiento de todos los alumnos, indicando para cada alumno, 
    los nombres de los módulos en los que está matriculado en un año académico dado. 
    Este informe deberá estar ordenado por el nombre del alumno de forma ascendente. 
     */
    public static String consulta3 = """
                                    Select a.nombre, a.fecha_nacimiento , m.nombre 
                                    from modulo m join ciclo c on c.codigo=m.codigo_ciclo
                                    join  linea_matricula lm on lm.codigo_modulo = m.codigo
                                    join matricula ma on ma.codigo = lm.codigo_matricula
                                    join alumno a on a.codigo = ma.codigo_alumno
                                    where ma.anio_academico = ?
                                    order by a.nombre asc;
                                    """;

    /*
    Solicitará al usuario una denominación de ciclo, un curso de módulo 
    y un año académico de matrícula. Consultar el nombre y la fecha de nacimiento 
    de los alumnos matriculados en un curso dado de un ciclo dado para un año académico dado,
    indicando para cada alumno, la calificación primera y la calificación segunda de los módulos en los que está matriculado.
    Este informe deberá estar ordenado por el nombre del alumno de forma ascendente.
     */
    public static String consulta4 = """
                                    select a.nombre, a.fecha_nacimiento , lm.calificacion_primera, lm.calificacion_segunda
                                    from modulo m join ciclo c on c.codigo=m.codigo_ciclo
                                    join  linea_matricula lm on lm.codigo_modulo = m.codigo
                                    join matricula ma on ma.codigo = lm.codigo_matricula
                                    join alumno a on a.codigo = ma.codigo_alumno
                                    where c.denominacion = 'Gestion Administrativa' and m.curso = '1' 
                                    and ma.anio_academico = '2024/2025'
                                    order by a.nombre asc;
                                    """;

    /*
    Consultar la denominación, la familia profesional y el nivel de todos los ciclos, 
    indicando para cada ciclo, el importe total de las matrículas de cada año académico.
    Este informe deberá estar ordenado por la denominación del ciclo de forma ascendente.

     */
    public static String consulta5 = """
                                     Select c.denominacion, c.familia_profesional, c.nivel, 
                                     sum(ma.importe) as 'importe total de cada a\u00f1o academico'
                                     from modulo m join ciclo c on c.codigo=m.codigo_ciclo
                                     join  linea_matricula lm on lm.codigo_modulo = m.codigo
                                     join matricula ma on ma.codigo = lm.codigo_matricula
                                     group by c.denominacion, c.familia_profesional, c.nivel, ma.anio_academico
                                     order by c.denominacion asc;
                                     """;

    /*
    Consultar el nombre del alumno y el año académico, indicando el total de créditos ECTS 
    y el total de horas en las que se ha matriculado. Este informe deberá estar ordenado por
    el total de créditos de forma descendente.
     */
    public static String consulta6 = """
                                     select a.nombre, ma.anio_academico, sum(m.creditos_ects) as 'total de creditos',
                                     sum(c.horas) as 'total de horas matriculadas'
                                     from modulo m join ciclo c on c.codigo=m.codigo_ciclo
                                     join  linea_matricula lm on lm.codigo_modulo = m.codigo
                                     join matricula ma on ma.codigo = lm.codigo_matricula
                                     join alumno a on a.codigo = ma.codigo_alumno
                                     group by a.nombre, ma.anio_academico
                                     order by sum(m.creditos_ects) asc;
                                     """;

    /*
    Consultar el nombre del alumno, el nombre del módulo y el año académico de 
    aquellos registros donde la "calificación_primera" todavía sea nula. 
    Este informe deberá estar ordenado por el año académico de forma ascendente.
     */
    public static String consulta7 = """
                                     select a.nombre as 'nombre alumno', m.nombre as 'nombre moudlo'  , ma.anio_academico 
                                     from modulo m join ciclo c on c.codigo=m.codigo_ciclo
                                     join  linea_matricula lm on lm.codigo_modulo = m.codigo
                                     join matricula ma on ma.codigo = lm.codigo_matricula
                                     join alumno a on a.codigo = ma.codigo_alumno
                                     where lm.calificacion_primera is null
                                     order by ma.anio_academico asc;
                                     """;

    /*
    Consultar el nombre del módulo y la denominación del ciclo,
    indicando cuántos alumnos están matriculados con un número de "repetición" superior a 1.
    Solo se mostrarán los módulos que tengan más de 3 alumnos repetidores.
     */
    public static String consulta8 = """
                                     select 
                                     m.nombre as nombre_modulo, 
                                     c.denominacion as denominacion_ciclo, 
                                     count(lm.codigo_matricula) as alumnos_repetidores
                                     from modulo m
                                     join ciclo c on m.codigo_ciclo = c.codigo
                                     join linea_matricula lm on m.codigo = lm.codigo_modulo
                                     where lm.repeticion > 1
                                     group by  m.codigo, m.nombre, c.denominacion
                                     having count(lm.codigo_matricula) > 3;
                                     """;

    public static String consulta9 = """
                                     select distinct 
                                     a.nombre, 
                                     a.telefono, 
                                     a.correo
                                     from alumno a
                                     left join matricula mat on a.codigo = mat.codigo_alumno
                                     where mat.codigo is null 
                                     or mat.estado = 'anulada'
                                     order by a.nombre asc;
                                     """;

}
