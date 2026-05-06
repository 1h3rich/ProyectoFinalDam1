/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.Ficheros;

import Config.appConfig;
import Utils.Validadores;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Alumno;

/**
 *
 * @author isard
 */
public class GestionFicheros {
    // En esta clase se va a encontrar todos los metodos necesarios para la gestion de ficheros (csv, txt, json, etc)

    /**
     * Crea el fichero; se sabe que tipo de fichero es porque por parámetro le
     * pasamos la ruta.
     *
     * @param ruta
     */
    public static void crearFichero(String ruta) {
        File f = new File(ruta);
        try {
            if (f.createNewFile()) {
                System.out.println("Fichero creado correctamente");
            } else {
                System.out.println("Fichero ya existente");
            }

        } catch (IOException ex) {
            Logger.getLogger(GestionFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================
    public void saveToBinario(String cadena, String direccion) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(direccion + ".dat", true))) {

            dos.writeUTF(cadena);

        } catch (IOException e) {
            System.out.println("Error BIN: " + e.getMessage());
        }
    }

    public static void saveTo(String cadena, String direccion, String terminacion) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(direccion + terminacion, true))) {

            bw.write(cadena);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error JSON: " + e.getMessage());
        }

    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================
    
    public void objFromCSV() {

        if (Validadores.comprobarFichero(appConfig.ficheroAlumno, ".csv")) {

            try (BufferedReader br = new BufferedReader(
                    new FileReader(appConfig.ficheroAlumno + ".csv"))) {

                String linea;

                while ((linea = br.readLine()) != null) {

                    String[] d = linea.split(";");

                    Alumno a = new Alumno(
                            Integer.parseInt(d[0]),
                            d[1],
                            LocalDate.parse(d[2]),
                            d[3],
                            d[4],
                            d[5]
                    );

                    System.out.println(a);
                    System.out.println("-----------------");
                }

            } catch (IOException e) {
                System.out.println("Error CSV");
            }
        }
    }
}
