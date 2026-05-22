/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Alumno;

/**
 * Se lanza cuando una línea CSV no tiene los 6 campos necesarios para construir un Alumno.
 *
 * @author isard
 */
public class LineaInvalidaAlumnoException extends Exception{

     /** Construye la excepción sin mensaje de detalle. */
    public LineaInvalidaAlumnoException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public LineaInvalidaAlumnoException(String message) {
        super(message);
    }
    
    
}
