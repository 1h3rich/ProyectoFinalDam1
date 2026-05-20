/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.LineaMatricula;

/**
 *
 * @author isard
 */
public class LineaInvalidaLineaMatriculaException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public LineaInvalidaLineaMatriculaException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public LineaInvalidaLineaMatriculaException(String message) {
        super(message);
    }
    
}
