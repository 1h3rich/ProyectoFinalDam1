/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.LineaMatricula;

/**
 *
 * @author isard
 */
public class SegundaCalifException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public SegundaCalifException() {
    }

     /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public SegundaCalifException(String message) {
        super(message);
    }
    
}
