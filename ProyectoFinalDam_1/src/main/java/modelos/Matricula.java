/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import java.util.ArrayList;
import servicios.BaseDeDatos.ConsultasEspecificas;
import servicios.BaseDeDatos.GestionBaseDeDatos;
import servicios.BaseDeDatos.Multitable;
/**
 *
 * @author isard
 */
public class Matricula {
    // Aqui va la creacion del objeto Matricula, el cual deberemos meter en la base de datos 
    private int codigo;
    private int codigo_alumno ;
    private int año_academico;
    private String estado;
    private double importe;
    private ArrayList<LineaMatricula> lineasMatriculas;

    /**
     * Creador manual de nuevas matriculas desde el programa
     * @param año_academico
     * @param estado
     * @param importe
     */
    public Matricula(int año_academico, String estado, double importe) {
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
        this.codigo = ConsultasEspecificas.leerCodigoBDD("matricula");
        this.codigo_alumno = ConsultasEspecificas.leerCodigoBDD("alumno");
        this.lineasMatriculas = new ArrayList<>();
    }

    /**
     * Creacion automatica de matriculas registradas en la BDD
     * @param codigo
     * @param codigo_alumno
     * @param año_academico
     * @param estado
     * @param importe 
     */
    public Matricula(int codigo, int codigo_alumno, int año_academico, String estado, double importe) {
        this.codigo = codigo;
        this.codigo_alumno = codigo_alumno;
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
        this.lineasMatriculas = new ArrayList<>();
    }
    
    //Metodos
    
    public void agregarLineaMatriculas(LineaMatricula lineasMatriculas){
        this.lineasMatriculas.add(lineasMatriculas);
    }
    
    //Getters

    public int getCodigo() {
        return codigo;
    }

    public int getCodigo_alumno() {
        return codigo_alumno;
    }

    public int getAño_academico() {
        return año_academico;
    }

    public String getEstado() {
        return estado;
    }

    public double getImporte() {
        return importe;
    }
    
    
    
}
