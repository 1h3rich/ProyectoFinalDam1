/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author Rich
 */
public class Validadores {

    /**
     * Comprueba que el fichero se pueda leer, se le pasa la direccion y la
     * extension Si no, no permite leerlo.
     *
     * @param type
     * @param temp
     * @return
     */
    public static boolean comprobarFicheroLectura(String type, String temp) {
        File archivo = new File(type + temp);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe");
            return false;
        }

        if (archivo.length() == 0) {
            System.out.println("El archivo está vacío");
            return false;
        }

        return true;
    }

    /**
     * Compruba que se pueda escribir, sino, lo crea.
     *
     * @param type
     * @param temp
     * @return
     */
    public static boolean comprobarFicheroEscritura(String type, String temp) {
        File archivo = new File(type + temp);

        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            return true;

        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo: " + e.getMessage());
            return false;
        }
    }

    // =========================================================
    // ===================== VALIDADORES DE CLASES =============
    // =========================================================
    public static boolean validarCodigoPositivo(int codigo) {
        return codigo > 0;
    }

    public static boolean validarRepeticion(int repeticion) {
        return repeticion == 1 || repeticion == 2;
    }

    public static boolean validarCalificacion(double calificacion) {
        return calificacion >= 0 && calificacion <= 10;
    }

    public static boolean validarAñoAcademico(int año_academico) {
        return año_academico >= 1900 && año_academico <= 3000;
    }

    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarImporte(double importe) {
        return importe >= 0;
    }

    public static boolean validarCurso(String curso) {
        return curso != null && !curso.trim().isEmpty();
    }

    public static boolean validarCreditosEcts(int creditos_ects) {
        return creditos_ects > 0;
    }

    public static boolean validarHorasModulo(int horas) {
        return horas > 0;
    }

    public static boolean validarFechaNacimiento(LocalDate fechaNacimiento) {
        return fechaNacimiento != null && !fechaNacimiento.isAfter(LocalDate.now());
    }

    public static boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{9}");
    }

    public static boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean validarNivel(String nivel) {
        return nivel != null && !nivel.trim().isEmpty();
    }

    public static boolean validarHorasCiclo(int horas) {
        return horas > 0;
    }

    public static boolean validarAñoCurriculum(int añoCurriculum) {
        return añoCurriculum >= 1900 && añoCurriculum <= 3000;
    }

}
