/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Ciclo;

/**
 *
 * @author isard
 */
public class NivelVacioException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */

    public NivelVacioException() {
    }

     /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public NivelVacioException(String message) {
        super(message);
    }
    
}
