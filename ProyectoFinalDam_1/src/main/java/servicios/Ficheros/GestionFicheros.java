/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.Ficheros;

import Utils.Validadores;
import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;

/**
 *
 * @author isard
 */
public class GestionFicheros {

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

    //==========================================================================================================================================================
    //======SAVE================================================================================================================================================
    //==========================================================================================================================================================
    /**
     * Guarda un objeto a csv, txt o json
     *
     * @param cadena toCSV, toTXT, toJSON
     * @param direccion appConfig.*;
     * @param terminacion .txt, .csv, .json
     */
    public static void guardarTxtCsvJson(String cadena, String direccion, String terminacion) {

        if (!Validadores.comprobarFicheroEscritura(direccion, terminacion)) {
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(direccion + terminacion, true))) {

            bw.write(cadena);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Guarda un objeto en binario, se le ha de pasar la lista con el tipo
     *
     * @param direccion
     * @param lista Collection
     */
    public static void guardarToBinario(String direccion, ArrayList<?> lista) { //He puesto un Collection<?> para poderle pasar tanto TreeSet como ArrayList de cualquier objeto, hacieno uso de polimorfismo

        if (Validadores.comprobarFicheroEscritura(direccion, ".dat")) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(direccion + ".dat", false)); //Cambio el true por false, ya que si no al añadir un objeto se borran los anteriores
                oos.writeObject(lista);

            } catch (IOException e) {
                System.out.println("Error BIN: " + e.getMessage());
            }
        }
    }

    //=========================================================================================================================================================
    //=======LOAD==============================================================================================================================================
    //=========================================================================================================================================================
    /**
     * Carga desde TXT o CSV pasandole la direccion y la extension
     *
     * @param direccion
     * @param terminacion
     * @return
     */
    public static ArrayList<String> leerTxtCsv(String direccion, String terminacion) {
        ArrayList<String> lineas = new ArrayList<>();
        try {
            if (Validadores.comprobarFicheroLectura(direccion, terminacion)) {
                BufferedReader br = new BufferedReader(new FileReader(direccion + terminacion));
                String linea = "";
                while ((linea = br.readLine()) != null) {
                    lineas.add(linea);
                }
                return lineas;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionFicheros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Carga desde Json, solo hace falta pasarle la direccion
     *
     * @param direccion
     * @return
     */
    public static ArrayList<String> leerJson(String direccion) {
        ArrayList<String> lineas = new ArrayList<>();
        try {
            if (Validadores.comprobarFicheroLectura(direccion, ".json")) {
                BufferedReader br = new BufferedReader(new FileReader(direccion + ".json"));
                String linea = "";
                while ((linea = br.readLine()) != null) {
                    lineas.add(linea);
                }
                return lineas;

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionFicheros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Carga desde binario, solo hace falta pasarle la direccion
     *
     * @param direccion
     * @return
     */
    public static ArrayList<String> leerBinario(String direccion) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(direccion + ".dat"))) {
            if (Validadores.comprobarFicheroLectura(direccion, ".dat")) {

                Object obj = ois.readObject();

                if (obj instanceof ArrayList<?>) {
                    return (ArrayList<String>) obj;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el archivo: " + direccion + ".dat");
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo binario: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Clase no encontrada.");
        }

        return new ArrayList<>();
    }

    //=========================================================================================================================================================================
    //======================= FROM ============================================================================================================================================
    //=========================================================================================================================================================================
    /**
     * Convierte un string en json y devuelve el objeto. Los parametros son: la
     * cadena y el .class de la clase.
     *
     * @param json
     * @param clase
     * @return
     */
    public static Object toJson(String json, Class clase) {
        return new Gson().fromJson(json, clase);
    }

}
