/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 * Elemento para poblar un JComboBox asociando un identificador numérico con el texto visible.
 *
 * @author Rich
 */
public class ItemCombo {

    private int id;
    private String texto;

    /**
     * Crea un elemento de combo con su identificador y su etiqueta visible.
     *
     * @param id    Identificador numérico del elemento (p.ej. código de BD).
     * @param texto Texto que se mostrará en el desplegable.
     */
    public ItemCombo(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    /** @return Identificador numérico asociado a este elemento. */
    public int getId() {
        return id;
    }

    /** @return Texto visible del elemento, usado por Swing para renderizar el combo. */
    @Override
    public String toString() {
        return texto;
    }
}
