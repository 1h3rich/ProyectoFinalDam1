/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Matricula;

/**
 * Se lanza cuando el año académico de la matrícula no tiene un formato válido.
 *
 * @author isard
 */
public class AñoAcademicoException extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public AñoAcademicoException() {
    }

     /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public AñoAcademicoException(String message) {
        super(message);
    }
    
    
}
