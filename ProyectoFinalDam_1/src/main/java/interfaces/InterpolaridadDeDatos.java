/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

/**
 * Contrato que deben cumplir las entidades del modelo para soportar persistencia en ficheros
 * (CSV, JSON, TXT, binario) e importación desde esos mismos formatos.
 *
 * @author Ricardo
 * @autor Luis
 * @autor David
 */
public abstract interface InterpolaridadDeDatos {

    /** Serializa el objeto y lo añade al fichero CSV correspondiente. */
    public void loadToCsv();

    /** Serializa el objeto y lo añade al fichero JSON correspondiente. */
    public void loadToJson();

    /** Guarda la lista completa de la sesión como fichero binario serializado. */
    public void loadToBinario();

    /** Serializa el objeto y lo añade al fichero TXT correspondiente. */
    public void loadToTxt();

    /** Lee el fichero CSV y reemplaza la lista de objetos en sesión. */
    public void objFromCSV();

    /** Lee el fichero JSON y reemplaza la lista de objetos en sesión. */
    public void objFromJSON();

    /** Lee el fichero binario y reemplaza la lista de objetos en sesión. */
    public void objFromBinario();

    /** Lee el fichero TXT y reemplaza la lista de objetos en sesión. */
    public void objFromTXT();

    /** @return Representación del objeto en formato CSV (separador ";"). */
    public String toCSV();

    /** @return Representación del objeto en formato JSON. */
    public String toJSON();

    /** @return Representación del objeto en formato TXT (equivalente a CSV). */
    public String toTXT();

}
