/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import java.time.LocalDate;
import java.util.HashSet;
import servicios.GestionBaseDeDatos;

/**
 *
 * @author isard
 */
public class Alumno{
    
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
    
    
    
}
