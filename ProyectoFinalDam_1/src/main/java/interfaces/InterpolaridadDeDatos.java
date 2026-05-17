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
public abstract interface InterpolaridadDeDatos {
    
    //Guardar a distintos tipos de ficheros
    
    
    public void loadToCsv();
    public void loadToJson();
    public void loadToBinario();
    public void loadToTxt();
    
    //Conversion de fichero a objetos
    
    public void objFromCSV();
    public void objFromJSON();
    public void objFromBinario();
    public void objFromTXT();
    
    //Conversion de datos
    
    public String toCSV();
    public String toJSON();
    public String toTXT();
    
}
