
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.modelo.Usuario;
import java.text.MessageFormat;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import java.awt.print.PrinterException;
import javax.swing.JTable;


public class PanelModificarHorarios extends javax.swing.JPanel {

    private final com.mycompany.controlasistencia.dao.HorarioDAO horarioDAO = new com.mycompany.controlasistencia.dao.HorarioDAO();
    private Integer usuarioActualId = null; //Se setea cuando el horario es elegido 
    private javax.swing.table.DefaultTableModel modelo;  //Inmutable para la tabla 
    private boolean editandoFila = false; //Flag que contrala el estado de edicion
    private String diaOriginal = null; //Validar cambios del dia
    private final Usuario usuario; // El usuario Logeado
    
    //Orden de los dias de la semana, coincide con el enum de la base de datos
    private static final java.util.List<String> ORDEN_DIAS =
        java.util.List.of("LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO");
    
    
    private static int ordenDia(String diaRaw){
    if (diaRaw == null) return Integer.MAX_VALUE;
    String d = java.text.Normalizer.normalize(diaRaw, java.text.Normalizer.Form.NFD)
            .replaceAll("\\p{M}+", "")      // quita tildes (MIÉRCOLES -> MIERCOLES)
            .trim().toUpperCase();
    int idx = ORDEN_DIAS.indexOf(d);
    return (idx >= 0) ? idx : Integer.MAX_VALUE;
}
    
    
    public PanelModificarHorarios(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
        // Tabla con las columnas no editables
    modelo = new javax.swing.table.DefaultTableModel(new String[]{"Día","Entrada","Salida"}, 0) {
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    jTable1.setModel(modelo);
    
    //Aqui cargo los dias desde la base de datos (enum) asi UI usará los mismos valores y orden
    var dias = horarioDAO.listarDiasSemana();
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(dias.toArray(String[]::new)));
    jComboBox1.setSelectedIndex(0); // Inicia con el primer dia (lunes)

    
    //AQUI SE CARGAN LOS DATOS DE LA FILA SELECCIONADA
    
    jTable1.getSelectionModel().addListSelectionListener(e -> {
    if (e.getValueIsAdjusting()) return; //Ignora eventos intermedios del arrastre
    int vr = jTable1.getSelectedRow();
    if (vr < 0) return;
    int mr = jTable1.convertRowIndexToModel(vr);
    String dia     = String.valueOf(modelo.getValueAt(mr, 0));
    String entrada = String.valueOf(modelo.getValueAt(mr, 1));
    String salida  = String.valueOf(modelo.getValueAt(mr, 2));
    jComboBox1.setSelectedItem(dia);
    txtHoraInicio.setText(entrada);
    txtHoraSalida.setText(salida);
    editandoFila = true; //El boton confirmar hace update
    diaOriginal  = dia; // Se guarda para detectar si el usuario cambia de dia 
});

    }
    
// Busca por el nombre y apellido y si existen carga sus horarios en la tabla       
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
        modelo.setRowCount(0); //Limpia la tabla
        return;
    }
    usuarioActualId = id;
    javax.swing.JOptionPane.showMessageDialog(this, "Usuario encontrado");
    recargarTabla(); //Trae horarios del usuario y los muestra 
    vaciarCampos(); //Limpia los campos 
}

// Vacia y recarga la tabla
private void recargarTabla() {
    modelo.setRowCount(0);

    // Trae y ordena por el índice definido arriba
    java.util.List<Object[]> filas = new java.util.ArrayList<>(horarioDAO.listarHorarios(usuarioActualId));
    filas.sort(java.util.Comparator.comparingInt(f -> ordenDia(String.valueOf(f[0]))));

    for (Object[] fila : filas) { //las filas serán Dia, entrada salida
        modelo.addRow(fila);
    }
    jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); // desactiva el auto resize para evitar que las columnas se compriman 
}

// Elimina el horario del dia seleccionado en la tabla
private void limpiarSeleccion() {
   
    if (usuarioActualId == null || jTable1.getSelectedColumn() == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Seleccioné un día para eliminar por favor");
        return;
    }
    
    int fila =jTable1.getSelectedRow();
    
    String dia = (String) jTable1.getValueAt(fila, 0); //Dia de la fila
    
    boolean ok = horarioDAO.borrarHorarioDia(usuarioActualId, dia);
    
    if (ok) {
    
        recargarTabla();
        vaciarCampos();
        javax.swing.JOptionPane.showMessageDialog(this, "Dia eliminado correctamente");
    }else {
        javax.swing.JOptionPane.showMessageDialog(this, "error al intentar eliminar un dia", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

//Limpiar campos 
private void vaciarCampos() {

    jTable1.clearSelection();
    jComboBox1.setSelectedIndex(-1);
    txtHoraInicio.setText("");
    txtHoraSalida.setText("");
    editandoFila = false;
    diaOriginal = null;
}


// Valida y guarda los insert/update del horario para el usuario actual
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
        
        // Regla para que la salida sea mayor que la entrada
        if (!fin.isAfter(ini)) {
            javax.swing.JOptionPane.showMessageDialog(this, "La hora de salida debe ser mayor que la de entrada.");
            return;
        }
        boolean ok;
        Integer actorId = (usuario != null ? usuario.getId_Usuario() : null);
        if (editandoFila && diaOriginal != null && !dia.equals(diaOriginal)) {
            // Borrar el dia anterior y luego upsert del nuevo dia
            ok = horarioDAO.borrarHorarioDia(usuarioActualId, diaOriginal)
                 && horarioDAO.upsertHorarioLog(actorId, usuarioActualId, dia, ini, fin);
        } else {
            //Inserta o actualiza el mismo dia
            ok = horarioDAO.upsertHorarioLog(actorId, usuarioActualId, dia, ini, fin);
        }
        if (ok) {
            recargarTabla();
            javax.swing.JOptionPane.showMessageDialog(this, "Horario guardado.");
            vaciarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudo guardar.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        //"Atrapa" formato invalido
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
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void imprimirTablaHorarios() {
    try {
        MessageFormat header = new MessageFormat(    "Horarios - " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
);
        MessageFormat footer = new MessageFormat("Página {0}");

        PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
        attrs.add(OrientationRequested.LANDSCAPE); //En horizontal 

        boolean hecho = jTable1.print(
                JTable.PrintMode.FIT_WIDTH, //Ajustar el ancho 
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
    

    
    //Borrar si no funciona
    
    
    
    //Borrar si no funciona

   
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
