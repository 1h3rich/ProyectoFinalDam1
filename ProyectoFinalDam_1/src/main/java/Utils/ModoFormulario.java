/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 * Indica el modo de operación en el que se abre un formulario de la aplicación.
 * Controla qué campos son editables y qué botones se habilitan.
 *
 * @author Rich
 */
public enum ModoFormulario {
    /** El formulario permite introducir y guardar un nuevo registro. */
    CREAR,
    /** El formulario carga un registro existente para editarlo. */
    MODIFICAR,
    /** El formulario muestra un registro para confirmar su eliminación. */
    ELIMINAR,
    /** El formulario exporta o persiste los datos actuales de sesión. */
    GUARDAR
}
