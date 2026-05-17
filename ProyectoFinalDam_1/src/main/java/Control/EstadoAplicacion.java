/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

/**
 * Almacena el estado global de la aplicación durante su ejecución.
 * Actualmente sólo registra si la conexión con la base de datos se ha establecido.
 *
 * @author Rich
 */
public class EstadoAplicacion {

    private boolean baseDeDatosCargada = false;

    /**
     * Indica si la conexión con la base de datos se ha establecido correctamente.
     *
     * @return true si la BD está disponible y conectada.
     */
    public boolean isBaseDeDatosCargada() {
        return baseDeDatosCargada;
    }

    /**
     * Actualiza el estado de conexión con la base de datos.
     *
     * @param baseDeDatosCargada true si la conexión se acaba de establecer, false si se ha perdido.
     */
    public void setBaseDeDatosCargada(boolean baseDeDatosCargada) {
        this.baseDeDatosCargada = baseDeDatosCargada;
    }
}
