/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import interfaces.persistenciaSQL;
import java.time.LocalDate;

/**
 *
 * @author isard
 */
public class Alumno implements persistenciaSQL{
    // Aqui va la creacion del objeto Alumno, el cual deberemos meter en la base de datos 
    
    private final int codigo;
    private final String nombre;
    private final LocalDate fecha_nacimiento;
    private final String domicilio;
    private final int telefono;
    private final String correo;

    public Alumno(String nombre, LocalDate fecha_nacimiento, String domicilio, int telefono, String correo) {
        this.codigo = obtenerCodigoSQL(); 
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    /**
     * Obtiene el codigo y devuelve el codigo para mantener los mismos codigos entre el programa y la BDD.
     * @return 
     */
    @Override
    public final int obtenerCodigoSQL() {
        int codigoSQL = 0;
        return codigoSQL;
    }
    
    
    
}
