/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.LineaMatricula;

/**
 * Se lanza cuando la matrícula asociada a una línea de matrícula es nula.
 *
 * @author isard
 */
public class MatriculaNotNullException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public MatriculaNotNullException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public MatriculaNotNullException(String message) {
        super(message);
    }
    
}
