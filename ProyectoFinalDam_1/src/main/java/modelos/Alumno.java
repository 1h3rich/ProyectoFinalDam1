/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.HashSet;
import servicios.GestionBaseDeDatos;
import servicios.GestionFicheros;
import Config.appConfig;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 *
 * @author isard
 */
public class Alumno implements interfaces.interpolaridadDeDatos {

    private final int codigo;
    private final String nombre;
    private final String fechaNacimiento;
    private final String domicilio;
    private final int telefono;
    private final String correo;
    private HashSet<Matricula> matriculas;

    /**
     * Creacion manual
     *
     * @param nombre
     * @param fechaNacimiento
     * @param domicilio
     * @param telefono
     * @param correo
     */
    public Alumno(String nombre, String fechaNacimiento, String domicilio, int telefono, String correo) {
        this.codigo = GestionBaseDeDatos.leerCodigoBDD("alumno");
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
        this.matriculas = new HashSet<>();
    }

    /**
     * Creacion desde la base de datos
     *
     * @param codigo
     * @param nombre
     * @param fechaNacimiento
     * @param domicilio
     * @param telefono
     * @param correo
     */
    public Alumno(int codigo, String nombre, String fechaNacimiento, String domicilio, int telefono, String correo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;
        this.matriculas = new HashSet<>();
    }

    /**
     * Guarda los alumnos en un archivo CSV
     */
    @Override
    public void saveToCSV() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".csv");

        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(appConfig.ficheroAlumno + ".csv", true))) {
                bw.write(toCSV());
                bw.newLine();
                bw.close();
            }

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error inesperado " + e);
        }
    }

    /**
     * Guarda los alumnos en un archivo JSON
     */
    @Override
    public void saveToJSON() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".json");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(appConfig.ficheroAlumno + ".json", true))) {

            bw.write(toJSON());
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    /**
     * Guarda los alumnos en un archivo binario
     */
    @Override
    public void saveToBinario() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".dat");
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(appConfig.ficheroAlumno + ".dat", true))) {

            dos.writeInt(codigo);
            dos.writeUTF(nombre);
            dos.writeUTF(fechaNacimiento);
            dos.writeUTF(domicilio);
            dos.writeInt(telefono);
            dos.writeUTF(correo);
            dos.close();

        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    /**
     * Guarda los alumnos en un archivo TXT
     */
    @Override
    public void saveToTXT() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".txt");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroAlumno + ".txt", true))) {

            bw.write(toTXT());
            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar TXT: " + e.getMessage());
        }

    }

    @Override
    public void objFromCSV() {

        if (Utils.Validadores.comprobarFichero(".csv")) {
            String linea;
            try {
                BufferedReader br = new BufferedReader(new FileReader(appConfig.ficheroAlumno + ".txt"));
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(";");

                    int tempCode = Integer.parseInt(datos[0]);
                    String tempName = datos[1];
                    String tempFechaNacimiento = datos[2];
                    String tempDomicilio = datos[3];
                    String tempTelefono = datos[4];
                    String tempCorreo = datos[5];

                    System.out.println("Codigo: " + tempCode);
                    System.out.println("Codigo: " + tempName);
                    System.out.println("Codigo: " + tempFechaNacimiento);
                    System.out.println("Codigo: " + tempDomicilio);
                    System.out.println("Codigo: " + tempTelefono);
                    System.out.println("Codigo: " + tempCorreo);
                    System.out.println("-----------------");

                    Alumno alumno = new Alumno(codigo, nombre, fechaNacimiento, domicilio, telefono, correo);
                    

                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo CSV");
            }

        }
    }

    //Override's
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

    /**
     * Convierte la informacion a un String csv
     *
     * @return
     */
    @Override
    public String toCSV() {
        return codigo + ";" + nombre + ";" + fechaNacimiento + ";" + domicilio + ";" + telefono + ";" + correo;
    }

    /**
     * Convierte la informacion a un String json
     *
     * @return
     */
    @Override
    public String toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    /**
     * Convierte la informacion a un String TXT
     *
     * @return
     */
    @Override
    public String toTXT() {
        return "Código: " + codigo + "\n"
                + "Nombre: " + nombre + "\n"
                + "Fecha nacimiento: " + fechaNacimiento + "\n"
                + "Domicilio: " + domicilio + "\n"
                + "Teléfono: " + telefono + "\n"
                + "Correo: " + correo + "\n"
                + "------------------------------\n";
    }

    //Metodos de la clase
    public void agregarMatriculas(Matricula matriculas) {
        this.matriculas.add(matriculas);
    }

    //Getters
    
    public HashSet<Matricula> getMatriculas() {
        return matriculas;
    }
    
    public int getCodigo() {
        return codigo;
    }

}
