/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package excepciones;

/**
 * Excepción que se lanza cuando se intenta importar datos que ya han sido cargados en la sesión actual.
 *
 * @author isard
 */
public class YaImportadoException extends Exception {

    /** Construye la excepción sin mensaje de detalle. */
    public YaImportadoException() {
    }

    /**
     * Construye la excepción con un mensaje descriptivo del error de importación.
     *
     * @param msg Descripción del conflicto de importación.
     */
    public YaImportadoException(String msg) {
        super(msg);
    }
    
}
