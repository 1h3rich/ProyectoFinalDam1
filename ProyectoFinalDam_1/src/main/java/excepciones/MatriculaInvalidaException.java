/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Se lanza cuando los datos de una matrícula no superan la validación.
 *
 * @author isard
 */
public class MatriculaInvalidaException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public MatriculaInvalidaException() {
    }
    
    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public MatriculaInvalidaException(String message) {
        super(message);
    }
    
}
