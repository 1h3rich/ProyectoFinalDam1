/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 * Identifica el tipo de entidad del modelo sobre el que opera una pantalla o servicio.
 * Se usa para parametrizar operaciones genéricas de importación, exportación y visualización.
 *
 * @author Rich
 */
public enum TipoDato {
    /** Entidad alumno. */
    ALUMNO,
    /** Entidad módulo formativo. */
    MODULO,
    /** Entidad ciclo formativo. */
    CICLO,
    /** Entidad matrícula. */
    MATRICULA,
    /** Entidad línea de matrícula (módulo matriculado). */
    LINEA_MATRICULA
}
