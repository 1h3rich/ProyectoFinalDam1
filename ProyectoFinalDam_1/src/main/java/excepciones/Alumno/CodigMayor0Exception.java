/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Alumno;

/**
 * Se lanza cuando el código identificador es 0 o negativo.
 *
 * @author isard
 */
public class CodigMayor0Exception extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public CodigMayor0Exception() {
    }
    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public CodigMayor0Exception(String message) {
        super(message);
    }
    
}
