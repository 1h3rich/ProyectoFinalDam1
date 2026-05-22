/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Ciclo;

/**
 * Se lanza cuando el año del currículum está fuera del rango válido [1900, 3000].
 *
 * @author isard
 */
public class AñoNoValidoException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public AñoNoValidoException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public AñoNoValidoException(String message) {
        super(message);
    }
    
}
