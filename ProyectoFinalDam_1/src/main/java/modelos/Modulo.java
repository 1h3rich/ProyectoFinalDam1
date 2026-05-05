/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import Config.appConfig;
import Utils.Validadores;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicios.BaseDeDatos.ConsultasEspecificas;
import servicios.BaseDeDatos.Multitable;
import servicios.Ficheros.GestionFicheros;
/**
 *
 * @author isard
 */
public class Modulo implements interfaces.interpolaridadDeDatos{
    // Aqui va la creacion del objeto Modulo, el cual deberemos meter en la base de datos 
    private int codigo;
    private int codigo_ciclo;
    private String nombre;
    private String curso;
    private int creditos_ects;
    private int horas;

    /**
     * Creacion manual de nuevos modulos desde el programa
     * @param nombre
     * @param curso
     * @param creditos_ects
     * @param horas
     */
    public Modulo(String nombre, String curso, int creditos_ects, int horas) {
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
        this.codigo = ConsultasEspecificas.leerCodigoBDD("modulo");
        this.codigo_ciclo = ConsultasEspecificas.leerCodigoBDD("ciclo");
    }    

    /**
     * Creacion automatica desde la base de datos
     * @param codigo
     * @param codigo_ciclo
     * @param nombre
     * @param curso
     * @param creditos_ects
     * @param horas 
     */
    public Modulo(int codigo, int codigo_ciclo, String nombre, String curso, int creditos_ects, int horas) {
        this.codigo = codigo;
        this.codigo_ciclo = codigo_ciclo;
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
    }
    
    
    // Getters

    public int getCodigo() {
        return codigo;
    }

    public int getCodigo_ciclo() {
        return codigo_ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public int getCreditos_ects() {
        return creditos_ects;
    }

    public int getHoras() {
        return horas;
    }

    public String getNombre() {
        return nombre;
    }
    
    //=============SaveTo================
    
    /*
    * Guarda los modulos en un archivo csv
    */
    @Override
    public void saveToCSV(){
        GestionFicheros.crearFichero(appConfig.ficheroModulo + ".csv");
        
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(appConfig.ficheroModulo + ".csv",true))) {
                bw.write(toCSV());
                bw.newLine();
                bw.close();
            }
                
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error inesperado " + e);
        }
    }
    
    /*
    * Guarda los modulos en un archivo JSON
    */
    
    @Override
    public void saveToJSON(){
        GestionFicheros.crearFichero(appConfig.ficheroModulo + ".json");
        
         try (BufferedWriter bw = new BufferedWriter(new FileWriter(appConfig.ficheroModulo + ".csv",true))) {
                
             bw.write(toJSON());
                bw.newLine();
                bw.close();
                
            } catch (IOException e) {
             System.out.println("Error al guardar JSON: " + e.getMessage());
        }      
    }
    
    @Override
    public void saveToBinario(){
         GestionFicheros.crearFichero(appConfig.ficheroModulo + ".dat");
         try(DataOutputStream  modulo = new DataOutputStream(new FileOutputStream(appConfig.ficheroModulo + ".dat",true))) {
             
             modulo.writeInt(codigo);
             modulo.writeInt(codigo_ciclo);
             modulo.writeUTF(nombre);
             modulo.writeUTF(curso);
             modulo.writeInt(creditos_ects);
             modulo.writeInt(horas);
             modulo.close();
             
         } catch (IOException e) {
             System.out.println("Error al guardar Binario: " + e.getMessage());
         }
    }
    
    /*
    * Guarda los modulos en un archivo TXT
    */
    
    @Override
    public void saveToTXT(){
         GestionFicheros.crearFichero(appConfig.ficheroModulo + ".txt");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroModulo + ".txt", true))) {

            bw.write(toTXT());
            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar TXT: " + e.getMessage());
        }
    }
    
    
    //===========FromX===============
    /*
    * Convierte de CSV a objeto
    */
    
    @Override
    public void objFromCSV(){
        if(Utils.Validadores.comprobarFichero(appConfig.ficheroModulo, ".csv")) {
            String linea;
            try {
                BufferedReader br = new BufferedReader(new FileReader(appConfig.ficheroModulo + ".csv"));
                while ((linea = br.readLine()) != null) {
                    String[]datos = linea.split(";");
                    
                    int code = Integer.parseInt(datos[0]);
                    int ciclo_code = Integer.parseInt(datos[1]);
                    String nom = datos[2];
                    String curso = datos[3];
                    int credits = Integer.parseInt(datos[4]);
                    int horas = Integer.parseInt(datos[5]);
                    
                    System.out.println("Codigo: " + code);
                    System.out.println("Codigo_Ciclo: " + ciclo_code);
                    System.out.println("Nombre: " + nom);
                    System.out.println("Curso: " + curso);
                    System.out.println("Creditos: " + credits);
                    System.out.println("Horas: " + horas);
                    System.out.println("-----------------");
                    
                    Modulo modulo = new Modulo(code, ciclo_code, nom, curso, credits, horas);
                } 
            } catch (IOException e) {
                System.out.println("Error al leer el archivo CSV");
            }
        }
    }
    
    /*
    * Convierte de JSON a objeto
    */
    
    @Override
    public void objFromJSON(){
        File f = new File(appConfig.ficheroModulo + ".json");
        Validadores.comprobarFichero(appConfig.ficheroModulo, ".json");
        
        
    }

    @Override
    public void objFromBinario() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void objFromTXT() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toJSON() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toTXT() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String SqlToObj() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String ObjToSql() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}    
    
         
