/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Matricula;

/**
 * Se lanza cuando el estado de la matrícula no es uno de los valores permitidos.
 *
 * @author isard
 */
public class EstadoNoValidoException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public EstadoNoValidoException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public EstadoNoValidoException(String message) {
        super(message);
    }
    
    
}
