/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.LineaMatricula;

/**
 *
 * @author isard
 */
public class ModuloNotNullException extends Exception{
    
    /** Construye la excepción sin mensaje de detalle. */
    public ModuloNotNullException() {
    }
 
    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public ModuloNotNullException(String message) {
        super(message);
    }
    
    
}
