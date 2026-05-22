/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Modulo;

/**
 * Se lanza cuando el nombre del módulo está vacío o es nulo.
 *
 * @author isard
 */
public class NombreVacioException extends Exception{
    
    /** Construye la excepción sin mensaje de detalle. */
    public NombreVacioException() {
    }
    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public NombreVacioException(String message) {
        super(message);
    }
    
}
