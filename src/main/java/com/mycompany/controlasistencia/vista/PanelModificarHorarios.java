
package com.mycompany.controlasistencia.vista;

import java.text.MessageFormat;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import java.awt.print.PrinterException;
import javax.swing.JTable;


public class PanelModificarHorarios extends javax.swing.JPanel {

    private final com.mycompany.controlasistencia.dao.HorarioDAO horarioDAO = new com.mycompany.controlasistencia.dao.HorarioDAO();
    private Integer usuarioActualId = null;
    private javax.swing.table.DefaultTableModel modelo; 
    private boolean editandoFila = false;
    private String diaOriginal = null;
    
    
    public PanelModificarHorarios() {
        initComponents();
        // Tabla
    modelo = new javax.swing.table.DefaultTableModel(new String[]{"Día","Entrada","Salida"}, 0) {
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    jTable1.setModel(modelo);

    var dias = horarioDAO.listarDiasSemana();
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(dias.toArray(String[]::new)));
    jComboBox1.setSelectedIndex(-1);

    
    //AQUI SE CARGAN LOS DATOS DE LA FILA SELECCIONADA
    
    jTable1.getSelectionModel().addListSelectionListener(e -> {
    if (e.getValueIsAdjusting()) return;
    int vr = jTable1.getSelectedRow();
    if (vr < 0) return;
    int mr = jTable1.convertRowIndexToModel(vr);
    String dia     = String.valueOf(modelo.getValueAt(mr, 0));
    String entrada = String.valueOf(modelo.getValueAt(mr, 1));
    String salida  = String.valueOf(modelo.getValueAt(mr, 2));
    jComboBox1.setSelectedItem(dia);
    txtHoraInicio.setText(entrada);
    txtHoraSalida.setText(salida);
    editandoFila = true;
    diaOriginal  = dia;
});

    }
    
    private void buscarUsuarioYListar() {
    String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    if (nombre.isBlank() || apellido.isBlank()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Escriba nombre y apellido.");
        return;
    }
    Integer id = horarioDAO.buscarUsuarioId(nombre, apellido);
    if (id == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "No se encontró el usuario.");
        usuarioActualId = null;
        modelo.setRowCount(0);
        return;
    }
    usuarioActualId = id;
    recargarTabla();
    limpiarSeleccion();
}

private void recargarTabla() {
    modelo.setRowCount(0);
    for (Object[] fila : horarioDAO.listarHorarios(usuarioActualId)) {
        modelo.addRow(fila);
    }
}

private void limpiarSeleccion() {
    jTable1.clearSelection();
    jComboBox1.setSelectedIndex(-1);
    txtHoraInicio.setText("");
    txtHoraSalida.setText("");
    editandoFila = false;
    diaOriginal = null;
}

private void guardarHorario() {
    if (usuarioActualId == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Primero busca y selecciona al usuario.");
        return;
    }
    String dia = (String) jComboBox1.getSelectedItem();
    String sIni = txtHoraInicio.getText().trim();
    String sFin = txtHoraSalida.getText().trim();
    if (dia == null || sIni.isBlank() || sFin.isBlank()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecciona día y completa horas.");
        return;
    }
    //5.- Aqui sirve para rescatar la fecha actual y los parametros que le paso son los de Hora/Minuto
    var fmt = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
    //5.- Aqui termina
    try {
        var ini = java.time.LocalTime.parse(sIni, fmt);
        var fin = java.time.LocalTime.parse(sFin, fmt);
        if (!fin.isAfter(ini)) {
            javax.swing.JOptionPane.showMessageDialog(this, "La hora de salida debe ser mayor que la de entrada.");
            return;
        }
        boolean ok;
        if (editandoFila && diaOriginal != null && !dia.equals(diaOriginal)) {
            ok = horarioDAO.borrarHorarioDia(usuarioActualId, diaOriginal)
                 && horarioDAO.upsertHorario(usuarioActualId, dia, ini, fin);
        } else {
            ok = horarioDAO.upsertHorario(usuarioActualId, dia, ini, fin);
        }
        if (ok) {
            recargarTabla();
            javax.swing.JOptionPane.showMessageDialog(this, "Horario guardado.");
            limpiarSeleccion();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudo guardar.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Formato de hora invalido. Usa HH:MM (ej. 08:15).");
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtHoraInicio = new javax.swing.JTextField();
        txtHoraSalida = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Nombre");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 110, -1));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 90, 40));

        jLabel3.setText("Apellido");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 110, -1));

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

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 570, 220));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 110, -1));
        jPanel2.add(txtHoraInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 120, -1));
        jPanel2.add(txtHoraSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 140, 20));

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        jPanel2.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 90, 30));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel2.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 90, 30));

        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 320, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        guardarHorario();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarSeleccion();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
       imprimirTablaHorarios();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarUsuarioYListar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void imprimirTablaHorarios() {
    try {
        MessageFormat header = new MessageFormat(    "Horarios - " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
);
        MessageFormat footer = new MessageFormat("Página {0}");

        PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
        attrs.add(OrientationRequested.LANDSCAPE);

        boolean hecho = jTable1.print(
                JTable.PrintMode.FIT_WIDTH,
                header,
                footer,
                true,   // diálogo de impresión
                attrs,  // landscape
                true    // interactivo
        );

        if (hecho) {
            JOptionPane.showMessageDialog(this, "Impresión completada ✅");
        } else {
            JOptionPane.showMessageDialog(this, "Impresión cancelada.");
        }
    } catch (PrinterException ex) {
        JOptionPane.showMessageDialog(this, "No se pudo imprimir:\n" + ex.getMessage(),
                "Error de impresión", JOptionPane.ERROR_MESSAGE);
    }
}

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtHoraInicio;
    private javax.swing.JTextField txtHoraSalida;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
