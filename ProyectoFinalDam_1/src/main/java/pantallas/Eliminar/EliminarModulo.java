package pantallas.Eliminar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import Utils.ItemCombo;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import servicios.BaseDeDatos.GestionBaseDeDatos;

/**
 * Formulario Swing para eliminar un módulo de un ciclo formativo.
 * Permite seleccionar el ciclo, luego el módulo, y elimina el módulo
 * junto con sus líneas de matrícula asociadas (CASCADE en BD).
 *
 * @author Rich
 */
public class EliminarModulo extends javax.swing.JFrame {

    private int idCicloSeleccionado = -1;
    private int idModuloSeleccionado = -1;

    private HashMap<String, Integer> mapaCiclos = new HashMap<>();
    private HashMap<String, Integer> mapaModulos = new HashMap<>();

    /**
     * Creates new form FormularioCiclo
     */
    public EliminarModulo() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        configurarVentana();

        configurarTabla(); // AÑADIR ESTO

        cargarCiclos();
    }

    /**
     * Configura título, textos de etiquetas, modo de selección de la tabla,
     * tamaño del scroll panel y listener del comboBox de ciclos.
     */
    private void configurarVentana() {
        setLocationRelativeTo(null);
        setTitle("Eliminar módulo");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Actualizar");

        jLabelTitulo.setText("ELIMINAR MÓDULO");
        jLabelEliminar.setText("Elimina el módulo seleccionado y sus líneas de matrícula asociadas.");
        jLabel2.setText("Selecciona el ciclo y luego el módulo de la tabla antes de eliminarlo.");
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Dimension tamañoTabla = new Dimension(948, 427);
        jScrollPane1.setPreferredSize(tamañoTabla);

        jComboBoxCiclos.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                cicloSeleccionado();
            }
        });

        pack();
    }

    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Módulo"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1.setModel(modelo);

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jTable1.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = jTable1.getSelectedRow();

                if (fila != -1) {

                    idModuloSeleccionado = Integer.parseInt(
                            jTable1.getValueAt(fila, 0).toString()
                    );

                    System.out.println("Modulo seleccionado: " + idModuloSeleccionado);
                }
            }
        });
    }

    private void cargarCiclos() {
        jComboBoxCiclos.removeAllItems();

        mapaCiclos.clear();
        mapaModulos.clear();

        jComboBoxCiclos.addItem("Selecciona un ciclo");

        for (ItemCombo ciclo : GestionBaseDeDatos.obtenerCiclosCombo()) {
            String texto = ciclo.toString();

            jComboBoxCiclos.addItem(texto);
            mapaCiclos.put(texto, ciclo.getId());
        }
    }

    private void cicloSeleccionado() {

        Object seleccionado = jComboBoxCiclos.getSelectedItem();

        if (seleccionado == null) {
            return;
        }

        String textoSeleccionado = seleccionado.toString();

        if (!mapaCiclos.containsKey(textoSeleccionado)) {

            idCicloSeleccionado = -1;
            idModuloSeleccionado = -1;

            return;
        }

        idCicloSeleccionado = mapaCiclos.get(textoSeleccionado);

        cargarModulosPorCiclo(idCicloSeleccionado); // AÑADIR
    }

    /**
     * Recarga el comboBox de ciclos desde la BD (llamado por el botón Actualizar).
     */
    private void cargarTodosLosCiclos() {
        cargarCiclos();
    }

    /**
     * Solicita confirmación y elimina de la BD el módulo seleccionado.
     * Las líneas de matrícula asociadas se borran automáticamente por CASCADE.
     */
    private void eliminarModulo() {
        if (idCicloSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un ciclo.");
            return;
        }

        if (idModuloSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un módulo de la tabla.");
            return;
        }

        int filaVista = jTable1.getSelectedRow();
        String nombreModulo = "(sin nombre)";
        if (filaVista != -1) {
            Object nombre = jTable1.getValueAt(filaVista, 1);
            if (nombre != null) {
                nombreModulo = nombre.toString();
            }
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres eliminar el módulo?\n\n"
                + "ID: " + idModuloSeleccionado + "\n"
                + "Nombre: " + nombreModulo + "\n\n"
                + "También se eliminarán sus líneas de matrícula asociadas.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean eliminado = GestionBaseDeDatos.eliminarModuloPorCodigo(idModuloSeleccionado);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Módulo eliminado correctamente.");
            idModuloSeleccionado = -1;
            cargarModulosPorCiclo(idCicloSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el módulo.");
        }
    }

    private void cargarModulosPorCiclo(int idCiclo) {

        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        modelo.setRowCount(0);

        mapaModulos.clear();

        /*
        EJEMPLO:
        Debes crear este método en GestionBaseDeDatos
        que devuelva una lista de ItemCombo
         */
        for (ItemCombo modulo : GestionBaseDeDatos.obtenerModulosPorCicloCombo(idCiclo)) {

            modelo.addRow(new Object[]{
                modulo.getId(),
                modulo.toString()
            });

            mapaModulos.put(modulo.toString(), modulo.getId());
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

        jButton1 = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonEliminar = new javax.swing.JButton();
        jLabelEliminar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxCiclos = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabelTitulo.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setFont(new java.awt.Font("NSimSun", 0, 36)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("ELIMINAR MODULO");

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButtonEliminar.setBackground(new java.awt.Color(75, 75, 75));
        jButtonEliminar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jLabelEliminar.setText("Eliminar el modulo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonEliminar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEliminar)
                    .addComponent(jLabelEliminar))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jComboBoxCiclos.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxCiclos.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxCiclos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(567, 567, 567)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxCiclos, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jComboBoxCiclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(93, 93, 93))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        eliminarModulo();
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cargarTodosLosCiclos();
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EliminarModulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
            new EliminarModulo().setVisible(true);
        });
    }
    //No

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JComboBox<String> jComboBoxCiclos;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelEliminar;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
