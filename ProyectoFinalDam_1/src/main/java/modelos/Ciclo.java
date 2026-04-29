/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import interfaces.persistenciaSQL;

/**
 *
 * @author isard
 */
public class Ciclo implements persistenciaSQL{
    // Aqui va la creacion del objeto Ciclo, el cual deberemos meter en la base de datos 
    
    private final int codigo;
    private final String nombre;
    private final String denominacion;
    private final String familiaProfesional;
    private final int horasCiclo;
    private final int añoCurricular;

    public Ciclo(int codigo, String nombre, String denominacion, String familiaProfesional, int horasCiclo, int añoCurricular) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.horasCiclo = horasCiclo;
        this.añoCurricular = añoCurricular;
    }

    @Override
    public int obtenerCodigoSQL() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }    
    
}
