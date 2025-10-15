
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.dao.AsistenciaDAO;
import com.mycompany.controlasistencia.dao.RolesDAO;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import javax.swing.JTable;



public class VistaUsuario extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaUsuario.class.getName());
    private final RolesDAO rolesDAO = new RolesDAO();


    private final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
    private final VistaAsistenciaTabla modeloProfe = new VistaAsistenciaTabla();
    private final VistaAsistenciaTabla modeloAsistente = new VistaAsistenciaTabla();
    private final java.time.ZoneId Tz = java.time.ZoneId.of("America/Santiago");
    
    public VistaUsuario() {
        initComponents();
        tablaProfesores.setModel(modeloProfe);
        tablaAsistenteEducacion.setModel(modeloAsistente);
        
    var rend = new VistaAsistencia();
    tablaProfesores.getColumnModel().getColumn(2).setCellRenderer(rend);
    tablaProfesores.getColumnModel().getColumn(3).setCellRenderer(rend);
    tablaAsistenteEducacion.getColumnModel().getColumn(2).setCellRenderer(rend);
    tablaAsistenteEducacion.getColumnModel().getColumn(3).setCellRenderer(rend);

    tablaProfesores.setRowHeight(28);
    tablaAsistenteEducacion.setRowHeight(28);

    recargarHoy();
    setTitle("Ver entradas/salidas");
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProfesores = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaAsistenteEducacion = new javax.swing.JTable();
        btnImprimirAsistente = new javax.swing.JButton();
        btnImprimirProfe = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaProfesores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaProfesores);

        jScrollPane1.setViewportView(jScrollPane2);

        tablaAsistenteEducacion.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tablaAsistenteEducacion);

        btnImprimirAsistente.setText("Imprimir");
        btnImprimirAsistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirAsistenteActionPerformed(evt);
            }
        });

        btnImprimirProfe.setText("Imprimir");
        btnImprimirProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirProfeActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(btnImprimirProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnImprimirAsistente, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimirProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimirAsistente, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnImprimirProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirProfeActionPerformed
        imprimirProfesores();
    }//GEN-LAST:event_btnImprimirProfeActionPerformed

    private void btnImprimirAsistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirAsistenteActionPerformed
        imprimirAsistentes();
    }//GEN-LAST:event_btnImprimirAsistenteActionPerformed

    private void imprimirProfesores() {
        try {
            MessageFormat header = new MessageFormat( "Horarios del día - " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy HH:MM")) );
            MessageFormat footer = new MessageFormat("Página {0}");
            
            PrintRequestAttributeSet atts = new HashPrintRequestAttributeSet();
            atts.add(OrientationRequested.LANDSCAPE);
            
            boolean profesores = tablaProfesores.print(
                    JTable.PrintMode.FIT_WIDTH,
                    header,
                    footer,
                    true,
                    atts,
                    true
            );
            
            if (profesores) {
                javax.swing.JOptionPane.showMessageDialog(this, "Impresion completada");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Impresion cancelada");
            }
            
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo imprimir:\n" + ex.getMessage(),
                "Error de impresión", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    private void imprimirAsistentes() {
        try {
            
            MessageFormat header = new MessageFormat("Horarios del día - " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy HH/MM")));
            MessageFormat footer = new MessageFormat("Página {0}");
            
            PrintRequestAttributeSet atts = new HashPrintRequestAttributeSet();
            atts.add(OrientationRequested.LANDSCAPE);
            
            boolean asistente = tablaAsistenteEducacion.print(
                    JTable.PrintMode.FIT_WIDTH,
                    header,
                    footer,
                    true,
                    atts,
                    true
            );
            
            if (asistente) {
                javax.swing.JOptionPane.showMessageDialog(this, "Impresion completada");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Impresion cancelada");
            }
            
            
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo imprimir:\n" + ex.getMessage(),
                "Error de impresión", JOptionPane.ERROR_MESSAGE);
        } 
    
    }
    
    
    public void recargarHoy() {
    var hoy = java.time.LocalDate.now(Tz); // cambiar a hora dia la cual estoy haciendo el PDF / IMPREISION
    var listaProfes = asistenciaDAO.listarResumenProfesores(hoy);
    var listaAsist  = asistenciaDAO.listarResumenAsistentes(hoy);
    modeloProfe.setData(listaProfes);
    modeloAsistente.setData(listaAsist);
}
    



    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnImprimirAsistente;
    private javax.swing.JButton btnImprimirProfe;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaAsistenteEducacion;
    private javax.swing.JTable tablaProfesores;
    // End of variables declaration//GEN-END:variables
}
