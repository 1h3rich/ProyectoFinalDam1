/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Ciclo;

/**
 *
 * @author isard
 */
public class HorasMayor0Exception extends Exception{

    
    /** Construye la excepción sin mensaje de detalle. */
    public HorasMayor0Exception() {
    }

     /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public HorasMayor0Exception(String message) {
        super(message);
    }
    
}
