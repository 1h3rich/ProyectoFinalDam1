/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Alumno;

/**
 *
 * @author isard
 */
public class FechaNoValidaException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public FechaNoValidaException() {
    }

     /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public FechaNoValidaException(String message) {
        super(message);
    }
    
    
}
