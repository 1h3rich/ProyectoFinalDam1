/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Ciclo;

/**
 * Se lanza cuando la denominación del ciclo está vacía o es nula.
 *
 * @author isard
 */
public class DenominacionVaciaException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public DenominacionVaciaException() {
    }

     /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public DenominacionVaciaException(String message) {
        super(message);
    }
    
    
}
