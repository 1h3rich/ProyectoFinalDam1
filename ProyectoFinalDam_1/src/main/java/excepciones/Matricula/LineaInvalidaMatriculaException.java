/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Matricula;

/**
 * Se lanza cuando una línea CSV no tiene los campos necesarios para construir una Matrícula.
 *
 * @author isard
 */
public class LineaInvalidaMatriculaException extends Exception{

     /** Construye la excepción sin mensaje de detalle. */
    public LineaInvalidaMatriculaException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public LineaInvalidaMatriculaException(String message) {
        super(message);
    }
    
    
}
