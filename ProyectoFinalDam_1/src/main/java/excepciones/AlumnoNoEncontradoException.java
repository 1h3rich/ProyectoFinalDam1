/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author isard
 */
public class AlumnoNoEncontradoException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public AlumnoNoEncontradoException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public AlumnoNoEncontradoException(String message) {
        super(message);
    }
    
}
