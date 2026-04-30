/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import java.time.LocalDate;
import servicios.GestionBaseDeDatos;

/**
 *
 * @author isard
 */
public class Alumno{
    // Aqui va la creacion del objeto Alumno, el cual deberemos meter en la base de datos 
    
    private final int codigo;
    private final String nombre;
    private final LocalDate fecha_nacimiento;
    private final String domicilio;
    private final int telefono;
    private final String correo;

    public Alumno(String nombre, LocalDate fecha_nacimiento, String domicilio, int telefono, String correo) {
        this.codigo = GestionBaseDeDatos.leerCodigoBDD("alumno");
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    
    
}
