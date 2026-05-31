/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepción genérica de la aplicación para errores de negocio no contemplados por excepciones específicas.
 *
 * @author isard
 */
public class ErrorGenerico extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public ErrorGenerico(){

    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public ErrorGenerico(String message) {
        super(message);
    }
    
    
    
}
