/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas.Modificar;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.LineaMatricula;
import servicios.BaseDeDatos.ConsultasSQL;
import servicios.BaseDeDatos.GestionBaseDeDatos;

/**
 * Formulario Swing para consultar y modificar líneas de matrícula existentes.
 * Muestra todas las líneas en una tabla y permite editar la repetición y calificaciones.
 *
 * @author Rich
 */
public class ModificarLineaMatricula extends javax.swing.JFrame {

    private int selectedCodMatricula = -1;
    private int selectedCodModulo = -1;
    private DefaultTableModel modeloTabla;

    /**
     * Creates new form ModificarLineaMatricula
     */
    public ModificarLineaMatricula() {
        initComponents();
        configurarTabla();
        cargarLineasEnTabla();
    }

    /**
     * Abre el formulario con una línea de matrícula preseleccionada y sus campos rellenos.
     *
     * @param lineaMatricula Línea de matrícula a editar.
     */
    public ModificarLineaMatricula(LineaMatricula lineaMatricula) {
        initComponents();
        setLocationRelativeTo(null);
        configurarTabla();
        cargarLineasEnTabla();
        if (lineaMatricula != null) {
            selectedCodMatricula = lineaMatricula.getCod_matricula();
            selectedCodModulo = lineaMatricula.getCod_modulo();
            jTextFieldRepeticion.setText(String.valueOf(lineaMatricula.getRepeticion()));
            jTextFieldPrimeraCalificacion.setText(lineaMatricula.getCal_primera() == 0.0 ? "" : String.valueOf(lineaMatricula.getCal_primera()));
            jTextFieldSegundaCalificacion.setText(lineaMatricula.getCal_segunda() == 0.0 ? "" : String.valueOf(lineaMatricula.getCal_segunda()));
        }
    }

    /** Inicializa el modelo de la tabla con columnas no editables y registra el listener de selección. */
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new Object[]{"Cód.Matrícula", "Cód.Módulo", "Repetición", "1ª Calificación", "2ª Calificación"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        jTable1.setModel(modeloTabla);
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarLineaSeleccionada();
        });
    }

    /** Consulta todas las líneas de matrícula de la BD y las muestra en la tabla. */
    private void cargarLineasEnTabla() {
        modeloTabla.setRowCount(0);
        String sql = "SELECT codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda FROM linea_matricula ORDER BY codigo_matricula ASC";
        DefaultTableModel temp = GestionBaseDeDatos.obtenerTableModel(sql, new String[0]);
        for (int i = 0; i < temp.getRowCount(); i++) {
            modeloTabla.addRow(new Object[]{
                temp.getValueAt(i, 0),
                temp.getValueAt(i, 1),
                temp.getValueAt(i, 2),
                temp.getValueAt(i, 3),
                temp.getValueAt(i, 4)
            });
        }
    }

    /** Carga los datos de la línea seleccionada en la tabla sobre los campos de edición del formulario. */
    private void cargarLineaSeleccionada() {
        int fila = jTable1.getSelectedRow();
        if (fila == -1) return;
        selectedCodMatricula = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        selectedCodModulo = Integer.parseInt(modeloTabla.getValueAt(fila, 1).toString());
        Object rep = modeloTabla.getValueAt(fila, 2);
        Object cal1 = modeloTabla.getValueAt(fila, 3);
        Object cal2 = modeloTabla.getValueAt(fila, 4);
        jTextFieldRepeticion.setText(rep == null ? "" : rep.toString());
        jTextFieldPrimeraCalificacion.setText(cal1 == null ? "" : cal1.toString());
        jTextFieldSegundaCalificacion.setText(cal2 == null ? "" : cal2.toString());
    }

    /** Valida los campos y actualiza la línea de matrícula seleccionada en la BD. */
    private void modificarLineaMatricula() {
        if (selectedCodMatricula == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una línea de la tabla primero.");
            return;
        }
        String repeticion = jTextFieldRepeticion.getText().trim();
        String cal1 = jTextFieldPrimeraCalificacion.getText().trim();
        String cal2 = jTextFieldSegundaCalificacion.getText().trim();
        if (repeticion.isBlank()) {
            JOptionPane.showMessageDialog(this, "La repetición es obligatoria.");
            return;
        }
        // UPDATE_LINEA_MATRICULA: repeticion, cal_primera, cal_segunda, cod_matricula, cod_modulo
        String[] entradas = {
            repeticion,
            cal1.isBlank() ? null : cal1,
            cal2.isBlank() ? null : cal2,
            String.valueOf(selectedCodMatricula),
            String.valueOf(selectedCodModulo)
        };
        GestionBaseDeDatos.actualizarFila(ConsultasSQL.UPDATE_LINEA_MATRICULA, entradas);
        JOptionPane.showMessageDialog(this, "Línea de matrícula actualizada correctamente.");

        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Deseas también modificar la matrícula asociada?",
            "Modificar matrícula", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            new ModificarMatricula().setVisible(true);
        }
        cargarLineasEnTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitulo = new javax.swing.JLabel();
        jLabelInfoPrimeraCalificacion = new javax.swing.JLabel();
        jTextFieldSegundaCalificacion = new javax.swing.JTextField();
        jTextFieldPrimeraCalificacion = new javax.swing.JTextField();
        jLabelInfoSegundaCalificacion = new javax.swing.JLabel();
        jButtonCancelar = new javax.swing.JButton();
        jTextFieldRepeticion = new javax.swing.JTextField();
        jLabelInfoRepeticion = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonGuardar = new javax.swing.JButton();
        jLabelTitulo1 = new javax.swing.JLabel();

        jLabelTitulo.setFont(new java.awt.Font("NSimSun", 0, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("RELLENAR MATRICULA");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelInfoPrimeraCalificacion.setText("1ª Calificacion: ");

        jTextFieldSegundaCalificacion.setPreferredSize(new java.awt.Dimension(64, 128));

        jTextFieldPrimeraCalificacion.setPreferredSize(new java.awt.Dimension(64, 128));
        jTextFieldPrimeraCalificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPrimeraCalificacionActionPerformed(evt);
            }
        });

        jLabelInfoSegundaCalificacion.setText("2ª Calificacion:");

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jTextFieldRepeticion.setPreferredSize(new java.awt.Dimension(64, 128));

        jLabelInfoRepeticion.setText("Repeticion:");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jLabelTitulo1.setFont(new java.awt.Font("NSimSun", 0, 36)); // NOI18N
        jLabelTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo1.setText("MODIFICAR LINEA MATRICULA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(411, Short.MAX_VALUE)
                .addComponent(jLabelTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227))
            .addGroup(layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelInfoRepeticion)
                    .addComponent(jLabelInfoSegundaCalificacion)
                    .addComponent(jLabelInfoPrimeraCalificacion))
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldPrimeraCalificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldSegundaCalificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldRepeticion, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(217, 217, 217)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jButtonCancelar)
                            .addGap(29, 29, 29)
                            .addComponent(jButtonGuardar)
                            .addContainerGap(768, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(287, 287, 287)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(218, Short.MAX_VALUE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelInfoRepeticion)
                    .addComponent(jTextFieldRepeticion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelInfoPrimeraCalificacion)
                    .addComponent(jTextFieldPrimeraCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelInfoSegundaCalificacion)
                    .addComponent(jTextFieldSegundaCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(478, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(120, 120, 120)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonGuardar)
                        .addComponent(jButtonCancelar))
                    .addContainerGap(120, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPrimeraCalificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPrimeraCalificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPrimeraCalificacionActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que quieres cancelar? Se perderá todo lo creado en este proceso.",
            "Cancelar creación",
            JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            GestionBaseDeDatos.cancelarTransaccion();
            this.dispose();
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        modificarLineaMatricula();
    }//GEN-LAST:event_jButtonGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(ModificarLineaMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarLineaMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarLineaMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarLineaMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            new ModificarLineaMatricula().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JLabel jLabelInfoPrimeraCalificacion;
    private javax.swing.JLabel jLabelInfoRepeticion;
    private javax.swing.JLabel jLabelInfoSegundaCalificacion;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelTitulo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldPrimeraCalificacion;
    private javax.swing.JTextField jTextFieldRepeticion;
    private javax.swing.JTextField jTextFieldSegundaCalificacion;
    // End of variables declaration//GEN-END:variables
}
