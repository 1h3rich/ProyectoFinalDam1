/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Config.appConfig;
import java.io.File;

/**
 *
 * @author Rich
 */
public class Validadores {
    
     public static boolean comprobarFichero(String temp) {
        File archivo = new File(appConfig.ficheroAlumno + temp);

        if (archivo.exists() && archivo.length() == 0) {
            System.out.println("El archivo está vacio");
            return false;
        } else if (archivo.exists()) {
            return true;
        } else {
            System.out.println("El archivo no existe");
            return false;
        }
    }
     
}
