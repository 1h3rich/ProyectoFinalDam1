/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.LineaMatricula;

/**
 * Se lanza cuando la primera calificación tiene un valor fuera del rango [0, 10].
 *
 * @author isard
 */
public class PrimeraCalifException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public PrimeraCalifException() {
    }

     /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public PrimeraCalifException(String message) {
        super(message);
    }
    
}
