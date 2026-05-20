/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Modulo;

/**
 *
 * @author isard
 */
public class LineaInvalidaModuloException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public LineaInvalidaModuloException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public LineaInvalidaModuloException(String message) {
        super(message);
    }
    
    
}
