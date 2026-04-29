/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author Rich
 */
public interface persistenciaSQL {

    /**
     * Consulta en la base de datos el código correspondiente y lo devuelve,
     * así mantemeos la persistencia de la información en la app y en la SQL.
     * @return
     */
    public int obtenerCodigoSQL();
}
