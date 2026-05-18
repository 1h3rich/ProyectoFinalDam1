/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author isard
 */
public class CicloExistenteException extends Exception{
    
    /** Construye la excepción sin mensaje de detalle. */
    public CicloExistenteException() {
    }
    
    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public CicloExistenteException(String message) {
        super(message);
    }
    
    
}
