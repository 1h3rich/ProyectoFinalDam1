/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.excepciones;

/**
 *
 * @author isard
 */
public class DomicilioVacioException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public DomicilioVacioException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public DomicilioVacioException(String message) {
        super(message);
    }
    
    
}
