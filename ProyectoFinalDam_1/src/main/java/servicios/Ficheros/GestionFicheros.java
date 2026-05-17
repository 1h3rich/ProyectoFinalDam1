/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.Ficheros;

import Utils.GsonUtils;
import Utils.Validadores;
import interfaces.InterpolaridadDeDatos;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.*;

/**
 * Servicio de gestión de ficheros que encapsula la lectura y escritura en los formatos
 * admitidos por la aplicación: TXT, CSV, JSON y binario serializado.
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
     * Vacía el contenido de un fichero de texto sin borrarlo.
     * Llamar antes de un bucle de exportación para evitar duplicados en ficheros de sesiones anteriores.
     *
     * @param direccion ruta base del fichero (sin extensión)
     * @param terminacion extensión del fichero (.txt, .csv, .json)
     */
    public static void truncarFichero(String direccion, String terminacion) {
        try (FileWriter fw = new FileWriter(direccion + terminacion, false)) {
            // solo abre en modo sobrescritura para dejar el fichero vacío
        } catch (IOException e) {
            System.out.println("Error al truncar fichero: " + e.getMessage());
        }
    }

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
     * @param lista 
     */
    public static void guardarToBinario(String direccion, Collection<?> lista) { //He puesto un Collection<?> para poderle pasar tanto TreeSet como ArrayList de cualquier objeto, hacieno uso de polimorfismo

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
                try (BufferedReader br = new BufferedReader(new FileReader(direccion + terminacion))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        lineas.add(linea);
                    }
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
                try (BufferedReader br = new BufferedReader(new FileReader(direccion + ".json"))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        lineas.add(linea);
                    }
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
        if (!Validadores.comprobarFicheroLectura(direccion, ".dat")) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(direccion + ".dat"))) {
            Object obj = ois.readObject();
            // Collection cubre tanto ArrayList como TreeSet
            if (obj instanceof Collection<?> coleccion) {
                ArrayList<String> resultado = new ArrayList<>();
                for (Object elemento : coleccion) {
                    if (elemento instanceof InterpolaridadDeDatos id) {
                        resultado.add(id.toCSV());
                    }
                }
                return resultado;
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
     * Deserializa una cadena JSON al tipo indicado usando Gson.
     *
     * @param <T>   Tipo de objeto a devolver.
     * @param json  Cadena JSON a deserializar.
     * @param clase Clase del tipo destino (p.ej. {@code Alumno.class}).
     * @return Objeto del tipo {@code T} con los datos del JSON.
     */
    public static <T> T toJson(String json, Class<T> clase) {
        return GsonUtils.get().fromJson(json, clase);
    }

}
