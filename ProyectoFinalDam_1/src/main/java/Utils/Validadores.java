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

    /**
     * Comprueba que el código sea un entero positivo (mayor que 0).
     *
     * @param codigo Código numérico a validar.
     * @return true si el código es mayor que 0.
     */
    public static boolean validarCodigoPositivo(int codigo) {
        return codigo > 0;
    }

    /**
     * Comprueba que el número de repetición sea un valor no negativo.
     *
     * @param repeticion Número de veces que el alumno cursa un módulo.
     * @return true si la repetición es 0 o mayor.
     */
    public static boolean validarRepeticion(int repeticion) {
        return repeticion >= 0;
    }

    /**
     * Comprueba que la calificación esté en el rango válido de 0 a 10 inclusive.
     *
     * @param calificacion Nota numérica a validar.
     * @return true si la calificación está entre 0.0 y 10.0.
     */
    public static boolean validarCalificacion(double calificacion) {
        return calificacion >= 0 && calificacion <= 10;
    }

    /**
     * Comprueba que el año académico sea un valor razonable entre 1900 y 3000.
     *
     * @param año_academico Año académico a validar.
     * @return true si el año está en el rango [1900, 3000].
     */
    public static boolean validarAñoAcademico(int año_academico) {
        return año_academico >= 1900 && año_academico <= 3000;
    }

    /**
     * Comprueba que el texto no sea nulo ni esté compuesto únicamente de espacios.
     *
     * @param texto Cadena de texto a validar.
     * @return true si el texto contiene al menos un carácter no vacío.
     */
    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Comprueba que el importe no sea negativo.
     *
     * @param importe Cantidad monetaria a validar.
     * @return true si el importe es 0.0 o mayor.
     */
    public static boolean validarImporte(double importe) {
        return importe >= 0;
    }

    /**
     * Comprueba que el estado de una matrícula sea exactamente "Activa" o "No activa".
     *
     * @param estado Estado a validar.
     * @return true si el estado es exactamente "Activa" o "No activa".
     */
    public static boolean validarEstado(String estado) {
        return "Parcial".equalsIgnoreCase(estado) || "Completa".equalsIgnoreCase(estado) || "Anulada".equalsIgnoreCase(estado);
    }

    /**
     * Comprueba que el nombre del curso no sea nulo ni esté vacío.
     *
     * @param curso Nombre del curso (p.ej. "primero", "segundo").
     * @return true si el curso es una cadena no vacía.
     */
    public static boolean validarCurso(String curso) {
        return curso != null && !curso.trim().isEmpty();
    }

    /**
     * Comprueba que los créditos ECTS sean un valor estrictamente positivo.
     *
     * @param creditos_ects Número de créditos ECTS a validar.
     * @return true si los créditos son mayores que 0.
     */
    public static boolean validarCreditosEcts(double creditos_ects) {
        return creditos_ects > 0;
    }

    /**
     * Comprueba que el número de horas de un módulo sea estrictamente positivo.
     *
     * @param horas Número de horas lectivas del módulo.
     * @return true si las horas son mayores que 0.
     */
    public static boolean validarHorasModulo(int horas) {
        return horas > 0;
    }

    /**
     * Comprueba que la fecha de nacimiento no sea nula ni posterior a la fecha actual.
     *
     * @param fechaNacimiento Fecha de nacimiento a validar.
     * @return true si la fecha es válida y no está en el futuro.
     */
    public static boolean validarFechaNacimiento(LocalDate fechaNacimiento) {
        return fechaNacimiento != null && !fechaNacimiento.isAfter(LocalDate.now());
    }

    /**
     * Comprueba que el teléfono sea exactamente 9 dígitos numéricos.
     *
     * @param telefono Número de teléfono a validar.
     * @return true si el teléfono coincide con el patrón de 9 dígitos.
     */
    public static boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{9}");
    }

    /**
     * Comprueba que el correo electrónico tenga un formato básico válido
     * (usuario@dominio.extensión).
     *
     * @param correo Dirección de correo a validar.
     * @return true si el correo coincide con el patrón de email.
     */
    public static boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Comprueba que el nivel del ciclo sea exactamente "Basico", "Medio" o "Superior".
     * Si el nivel no es válido, muestra el mensaje: "Nivel inválido. Debe ser Basico, Medio o Superior."
     *
     * @param nivel Nivel del ciclo a validar.
     * @return true si el nivel es exactamente "Basico", "Medio" o "Superior".
     */
    public static boolean validarNivel(String nivel) {
        return "Basico".equalsIgnoreCase(nivel) || "Medio".equalsIgnoreCase(nivel) || "Superior".equalsIgnoreCase(nivel);
    }

    /**
     * Comprueba que el número de horas de un ciclo sea estrictamente positivo.
     *
     * @param horas Número de horas totales del ciclo.
     * @return true si las horas son mayores que 0.
     */
    public static boolean validarHorasCiclo(int horas) {
        return horas > 0;
    }

    /**
     * Comprueba que el año del currículum sea un valor razonable entre 1900 y 3000.
     *
     * @param añoCurriculum Año de publicación del currículum del ciclo.
     * @return true si el año está en el rango [1900, 3000].
     */
    public static boolean validarAñoCurriculum(int añoCurriculum) {
        return añoCurriculum >= 1900 && añoCurriculum <= 3000;
    }

    /**
     * Comprueba que el domicilio tenga el formato obligatorio:
     * {@code TipoVía NombreVía Número, Localidad, Provincia}.
     * Los valores no tienen que ser reales, pero la estructura sí debe respetarse.
     * <p>Ejemplo válido: {@code Calle Mayor 5, Madrid, Madrid}</p>
     *
     * @param domicilio Dirección postal a validar.
     * @return {@code true} si el domicilio contiene tipo de vía, nombre de vía,
     *         número, localidad y provincia en el formato indicado.
     */
    public static boolean validarDireccion(String domicilio) {
        if (domicilio == null || domicilio.isBlank()) return false;

        String[] partes = domicilio.split(",");
        if (partes.length < 3) return false;

        // Primera parte: "TipoVía NombreVía Número" → mínimo 3 tokens
        String[] via = partes[0].trim().split("\\s+");
        if (via.length < 3) return false;

        // El último token debe ser un número (con posible letra de portal: 12, 3B, 4bis)
        if (!via[via.length - 1].matches("\\d+[A-Za-z]*")) return false;

        // Localidad y provincia no pueden estar vacías
        return !partes[1].trim().isBlank() && !partes[2].trim().isBlank();
    }

}
