/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import java.time.LocalDate;
import java.util.HashSet;
import servicios.GestionBaseDeDatos;
import servicios.GestionFicheros;
import Utils.Configuracion;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author isard
 */
public class Alumno implements interfaces.interpolaridadDeDatos{
    
    private final int codigo;
    private final String nombre;
    private final LocalDate fecha_nacimiento;
    private final String domicilio;
    private final int telefono;
    private final String correo;
    private HashSet<Matricula> matriculas;

    /**
     * Creacion automatica desde la base de datos
     * @param nombre
     * @param fecha_nacimiento
     * @param domicilio
     * @param telefono
     * @param correo 
     */
    public Alumno(String nombre, LocalDate fecha_nacimiento, String domicilio, int telefono, String correo) {
        this.codigo = GestionBaseDeDatos.leerCodigoBDD("alumno");
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
    }

    /**
     * Creacion automatica desde la base de datos
     * @param codigo
     * @param nombre
     * @param fecha_nacimiento
     * @param domicilio
     * @param telefono
     * @param correo 
     */
    public Alumno(int codigo, String nombre, LocalDate fecha_nacimiento, String domicilio, int telefono, String correo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
    }

    @Override
    public void saveToCSV() {
        GestionFicheros.crearFichero(Configuracion.ficheroAlumno + ".csv");
        
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(Configuracion.ficheroAlumno + ".csv", true))) {
                bw.write(toCSV());
                bw.newLine();
            }
            
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error inesperado " + e);
        }
    }

    @Override
    public void saveToJSON() {
        GestionFicheros.crearFichero(Configuracion.ficheroAlumno + ".json");
    }

    @Override
    public void saveToBinario() {
        GestionFicheros.crearFichero(Configuracion.ficheroAlumno + ".dat");
    }

    @Override
    public void saveToTXT() {
        GestionFicheros.crearFichero(Configuracion.ficheroAlumno + ".txt");
    }

    @Override
    public void objFromCSV() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void objFromJSON() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public String SQLtoTXT() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String TXTtoSQL() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toCSV() {
        return codigo + ";" + nombre + ";" + fecha_nacimiento + ";" + domicilio + ";" + telefono + ";" + correo;
    }

    @Override
    public String toJSON() {
         throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toTXT() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
