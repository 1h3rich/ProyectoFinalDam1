/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Rich
 */
public class Validadores {
    
    /**
     * Comprueba que el fichero se pueda leer, se le pasa la direccion y la extension
     * Si no, no permite leerlo.
     * @param type
     * @param temp
     * @return 
     */
         public static boolean comprobarFicheroLectura(String type, String temp) {
        File archivo = new File(type + temp);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe");
            return false;
        }

        if (archivo.length() == 0) {
            System.out.println("El archivo está vacío");
            return false;
        }

        return true;
    }

         /**
          * Compruba que se pueda escribir, sino, lo crea.
          * @param type
          * @param temp
          * @return 
          */
    public static boolean comprobarFicheroEscritura(String type, String temp) {
        File archivo = new File(type + temp);

        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            return true;

        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo: " + e.getMessage());
            return false;
        }
    }
}
     

