/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas.Crear;

import Utils.ItemCombo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servicios.BaseDeDatos.*;
import java.util.HashMap;

/**
 * Formulario Swing para gestionar los módulos asociados a un ciclo durante su creación.
 * Permite añadir módulos existentes o crear nuevos, muestra un contador de módulos
 * añadidos y habilita el botón Finalizar cuando se alcanzan los cinco mínimos requeridos.
 *
 * @author Rich
 */
public class GestionarModulosCiclo extends javax.swing.JFrame {

    private int idCiclo;
    private HashMap<String, Integer> mapaModulos = new HashMap<>();

    /**
     * Creates new form GestionarModulosCiclo
     *
     * @param idCiclo
     */
    public GestionarModulosCiclo(int idCiclo) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        this.idCiclo = idCiclo;

        configurarVentana();
        cargarModulosExistentes();
        actualizarContador();
    }

    /** Abre el formulario CrearModulo vinculado al ciclo actual y cierra esta ventana. */
    private void crearNuevoModulo() {
        new CrearModulo(idCiclo).setVisible(true);
        this.dispose();
    }

    /** Configura el título, el cierre con confirmación, el texto de los botones y deshabilita Finalizar hasta alcanzar 5 módulos. */
    private void configurarVentana() {
        setLocationRelativeTo(null);
        setTitle("Gestionar módulos del ciclo");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                cancelar();
            }
        });

        jButtonAñadirModuloExistente.setText("Añadir módulo existente");
        jButtonCrearModulo.setText("Crear nuevo módulo");
        jButtonFinalizar.setText("Finalizar");
        jButtonCancelar.setText("Cancelar");

        jButtonFinalizar.setEnabled(false);
    }

    /** Carga en el combo todos los módulos de la BD disponibles para asignar a este ciclo. */
    private void cargarModulosExistentes() {
        jComboBoxModulos.removeAllItems();
        mapaModulos.clear();

        jComboBoxModulos.addItem("Selecciona un módulo");

        DefaultTableModel tm = GestionBaseDeDatos.obtenerTableModel(
                "SELECT codigo, nombre FROM modulo ORDER BY nombre ASC", new String[0]);

        for (int i = 0; i < tm.getRowCount(); i++) {
            int id = Integer.parseInt(tm.getValueAt(i, 0).toString());
            String nombre = tm.getValueAt(i, 1).toString();
            jComboBoxModulos.addItem(nombre);
            mapaModulos.put(nombre, id);
        }
    }

    /** Consulta el número de módulos del ciclo en la BD, actualiza la etiqueta contador y habilita Finalizar si ya hay 5 o más. */
    private void actualizarContador() {
        int total = GestionBaseDeDatos.contarModulosPorCiclo(idCiclo);

        jLabelContador.setText("Módulos añadidos: " + total + "/5");

        jButtonFinalizar.setEnabled(total >= 5);
    }

    /** Asigna al ciclo el módulo seleccionado en el combo, actualizando la tabla de módulos y el contador. */
    private void añadirModuloExistente() {
        Object seleccionado = jComboBoxModulos.getSelectedItem();

        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un módulo.");
            return;
        }

        String nombreModulo = seleccionado.toString();

        if (nombreModulo.equals("Selecciona un módulo")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un módulo válido.");
            return;
        }

        if (!mapaModulos.containsKey(nombreModulo)) {
            JOptionPane.showMessageDialog(this, "No se ha encontrado el ID del módulo seleccionado.");
            return;
        }

        int idModulo = mapaModulos.get(nombreModulo);

        DefaultTableModel check = GestionBaseDeDatos.obtenerTableModel(
                "SELECT codigo FROM modulo WHERE codigo = ? AND codigo_ciclo = ?",
                new String[]{String.valueOf(idModulo), String.valueOf(idCiclo)}
        );

        if (check.getRowCount() > 0) {
            JOptionPane.showMessageDialog(this, "Módulo ya añadido");
            return;
        }

        String[] entradas = {
            String.valueOf(idCiclo),
            String.valueOf(idModulo)
        };

        boolean asignado = GestionBaseDeDatos.actualizarFila(
                ConsultasSQL.ASIGNAR_MODULO_A_CICLO,
                entradas
        );

        if (asignado) {
            JOptionPane.showMessageDialog(this, "Módulo añadido");
            cargarModulosExistentes();
            actualizarContador();
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el módulo al ciclo.");
        }
    }

    /** Verifica que el ciclo tenga al menos 5 módulos, confirma la transacción y cierra la ventana. */
    private void finalizar() {
        int total = GestionBaseDeDatos.contarModulosPorCiclo(idCiclo);

        if (total < 5) {
            JOptionPane.showMessageDialog(this, "Debes añadir al menos 5 módulos antes de finalizar.");
            return;
        }

        GestionBaseDeDatos.confirmarTransaccion();

        JOptionPane.showMessageDialog(this, "Ciclo creado correctamente con sus módulos.");
        this.dispose();
    }

    /** Pide confirmación y, si el usuario acepta, cancela la transacción (elimina el ciclo y sus módulos) y cierra la ventana. */
    private void cancelar() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres cancelar? Se eliminará el ciclo y los módulos creados en este proceso.",
                "Cancelar creación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            GestionBaseDeDatos.cancelarTransaccion();
            this.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxModulos = new javax.swing.JComboBox<>();
        jButtonAñadirModuloExistente = new javax.swing.JButton();
        jButtonCrearModulo = new javax.swing.JButton();
        jButtonFinalizar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jLabelContador = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBoxModulos.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxModulos.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxModulos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxModulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxModulosActionPerformed(evt);
            }
        });

        jButtonAñadirModuloExistente.setBackground(new java.awt.Color(255, 255, 255));
        jButtonAñadirModuloExistente.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAñadirModuloExistente.setText("Añador Modulo Existente");
        jButtonAñadirModuloExistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAñadirModuloExistenteActionPerformed(evt);
            }
        });

        jButtonCrearModulo.setBackground(new java.awt.Color(255, 255, 255));
        jButtonCrearModulo.setForeground(new java.awt.Color(0, 0, 0));
        jButtonCrearModulo.setText("Crear Nuevo Modulo");
        jButtonCrearModulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearModuloActionPerformed(evt);
            }
        });

        jButtonFinalizar.setBackground(new java.awt.Color(75, 75, 75));
        jButtonFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonFinalizar.setText("Finalizar");
        jButtonFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFinalizarActionPerformed(evt);
            }
        });

        jButtonCancelar.setBackground(new java.awt.Color(75, 75, 75));
        jButtonCancelar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jLabelContador.setBackground(new java.awt.Color(255, 255, 255));
        jLabelContador.setForeground(new java.awt.Color(0, 0, 0));
        jLabelContador.setText("0/5");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("NSimSun", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Gestion de Modulos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonAñadirModuloExistente, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jButtonCrearModulo)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonCancelar)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonFinalizar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabelContador))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jComboBoxModulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxModulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAñadirModuloExistente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCrearModulo)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonFinalizar))
                .addGap(18, 18, 18)
                .addComponent(jLabelContador)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxModulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxModulosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxModulosActionPerformed

    private void jButtonAñadirModuloExistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAñadirModuloExistenteActionPerformed
        añadirModuloExistente();
    }//GEN-LAST:event_jButtonAñadirModuloExistenteActionPerformed

    private void jButtonCrearModuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearModuloActionPerformed
        crearNuevoModulo();
    }//GEN-LAST:event_jButtonCrearModuloActionPerformed

    private void jButtonFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFinalizarActionPerformed
        finalizar();
    }//GEN-LAST:event_jButtonFinalizarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionarModulosCiclo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionarModulosCiclo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionarModulosCiclo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionarModulosCiclo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GestionarModulosCiclo(1).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAñadirModuloExistente;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonCrearModulo;
    private javax.swing.JButton jButtonFinalizar;
    private javax.swing.JComboBox<String> jComboBoxModulos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelContador;
    // End of variables declaration//GEN-END:variables
}
