/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isard
 */
public class GestionFicheros {
    // En esta clase se va a encontrar todos los metodos necesarios para la gestion de ficheros (csv, txt, json, etc)
    
    /**
     * Crea el fichero; se sabe que tipo de fichero es porque por parámetro le pasamos la ruta.
     * @param ruta 
     */
    public static void crearFichero(String ruta){
        File f = new File(ruta);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(GestionFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
