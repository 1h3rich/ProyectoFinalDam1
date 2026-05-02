/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

/**
 * 
 * @author Ricardo
 * @autor Luis
 * @autor David
 */
public abstract interface interpolaridadDeDatos {
    
    //Guardar a distintos tipos de ficheros
    
    
    public void saveToCSV();
    public void saveToJSON();
    public void saveToBinario();
    public void saveToTXT();
    
    //Conversion de fichero a objetos
    
    public void objFromCSV();
    public void objFromJSON();
    public void objFromBinario();
    public void objFromTXT();
    
    //Conversion de datos
    
    public String toCSV();
    public String toJSON();
    public String toTXT();
    
    
    //Obtener datos de la BDD y convertir en TXT (Por ejemplo)
    
    public String SQLtoTXT();
    
    //Los datos a la BDD
    
    public String TXTtoSQL();
    
    
}
