/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Alumno;

/**
 * Se lanza cuando el correo electrónico no tiene un formato válido.
 *
 * @author isard
 */
public class CorreoNoValidoException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public CorreoNoValidoException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public CorreoNoValidoException(String message) {
        super(message);
    }
    
    
}
