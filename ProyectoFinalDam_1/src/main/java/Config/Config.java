/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 1DAM
 */
public class Config {
    // SQL

    public static final String urlSQL = "jdbc:mysql://127.0.0.1:3306/centro_formacion";
    public static final String nombreUsuarioSQL = "root";
    public static String[] contraseñaSQL = {"alumno", "root"};
    //public static  Map<String,String > contraseñaSQL = new HashMap<>();
   

    // Ficheros
    public static final String ficheroAlumno = "alumno";
    public static final String ficheroCiclo = "ciclo";
    public static final String ficheroModulo = "modulo";
    public static final String ficheroLineaMatricula = "linea_matricula";
    public static final String ficheroMatricula = "matricula";

}
