/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Ciclo;

/**
 * Se lanza cuando una línea CSV no tiene los 6 campos necesarios para construir un Ciclo.
 *
 * @author isard
 */
public class LineaInvalidaCicloException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public LineaInvalidaCicloException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public LineaInvalidaCicloException(String message) {
        super(message);
    }
    
}
