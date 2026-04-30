/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import servicios.GestionBaseDeDatos;
/**
 *
 * @author isard
 */
public class Ciclo{
    // Aqui va la creacion del objeto Ciclo, el cual deberemos meter en la base de datos 
    
    private final int codigo;
    private final String nombre;
    private final String denominacion;
    private final String familiaProfesional;
    private final int horasCiclo;
    private final int añoCurricular;

    /**
     * Aqui habra que igualar el codigo al de la base de datos con el metodo 
     * @param nombre
     * @param denominacion
     * @param familiaProfesional
     * @param horasCiclo
     * @param añoCurricular
     */
    public Ciclo(String nombre, String denominacion, String familiaProfesional, int horasCiclo, int añoCurricular) {
        this.codigo = GestionBaseDeDatos.leerCodigoBDD("ciclo");
        this.nombre = nombre;
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.horasCiclo = horasCiclo;
        this.añoCurricular = añoCurricular;
    }
}
