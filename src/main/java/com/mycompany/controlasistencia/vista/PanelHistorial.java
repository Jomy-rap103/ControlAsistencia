
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.dao.HistorialDAO;
import java.util.List;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;

public class PanelHistorial extends javax.swing.JPanel {
    private final HistorialDAO dao = new HistorialDAO();
    private final DateTimeFormatter fFecha = DateTimeFormatter.ofPattern("yy-MM-dd");
    private final DateTimeFormatter fHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final javax.swing.Timer autoRefresco =
        new javax.swing.Timer(5000, e -> cargar());
    
    

    public PanelHistorial() {
        initComponents();
        
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{}, new String[]{"ID","Fecha","Hora","Actor","Acción","Objeto","Objetivo","Detalles"}
        ){
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) { return c==0 ? Long.class : String.class; }
        });
        jTable1.setAutoCreateRowSorter(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        
        cargar();
        autoRefresco.start();
    }
    
    @Override public void removeNotify() {
        autoRefresco.stop();
        super.removeNotify();
    }
    @Override public void addNotify() {
        super.addNotify();
        autoRefresco.start();
    }
    

        private void cargar() {
        List<HistorialDAO.Item> items = dao.listarUltimos(200); 

        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
        m.setRowCount(0); // limpiar

        for (var it : items) {
            var dt = it.fechaHora();
            m.addRow(new Object[]{
                it.id(),
                dt.format(fFecha),
                dt.format(fHora),
                it.actorNombre(),
                it.accion(),
                it.objeto(),
                it.objetivoNombre(),
                it.detalles()
            });
        }

        var col = jTable1.getColumnModel();
        if (col.getColumnCount() >= 8) {
            col.getColumn(0).setPreferredWidth(50);
            col.getColumn(1).setPreferredWidth(90);
            col.getColumn(2).setPreferredWidth(75);
            col.getColumn(3).setPreferredWidth(160);
            col.getColumn(4).setPreferredWidth(120);
            col.getColumn(5).setPreferredWidth(90);
            col.getColumn(6).setPreferredWidth(160);
            col.getColumn(7).setPreferredWidth(400);
        }
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
