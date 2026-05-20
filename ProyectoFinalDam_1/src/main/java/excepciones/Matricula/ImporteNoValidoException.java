/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Matricula;

/**
 *
 * @author isard
 */
public class ImporteNoValidoException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public ImporteNoValidoException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public ImporteNoValidoException(String message) {
        super(message);
    }
    
    
}
