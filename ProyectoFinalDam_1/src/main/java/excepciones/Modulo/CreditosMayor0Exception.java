/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones.Modulo;

/**
 *
 * @author isard
 */
public class CreditosMayor0Exception extends Exception{

    /** Construye la excepción sin mensaje de detalle. */
    public CreditosMayor0Exception() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param message Descripción del error.
     */
    public CreditosMayor0Exception(String message) {
        super(message);
    }
    
}
