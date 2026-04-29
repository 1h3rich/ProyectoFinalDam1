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
public interface interpolaridadDeDatos {
    
    //Conversiones a distintos tipos de ficheros
    
    public void convertirToCSV();
    public void convertirToJSON();
    public void convertirtToBinario();
    public void convertirToTXT();
    
    //Conversion de fichero a objetos
    
    public void convertirFromCSV();
    public void convertirFromJSON();
    public void convertirFromBinario();
    public void convertirFromTXT();
    
    //Obtener datos de la BDD y convertir en TXT (Por ejemplo)
    
    public String SQLtoTXT();
    
    //Los datos a la BDD
    
    public String TXTtoSQL();
    
    
}
