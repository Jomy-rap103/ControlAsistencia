
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.modelo.Usuario;


public class VistaPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaPrincipal.class.getName());

    private Usuario usuario;

    public VistaPrincipal(Usuario usuario ) {
        this.usuario = usuario;
        initComponents();
        this.setLocationRelativeTo(this);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        verPanelUser = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdmin = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnActivarSalida = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnActivarEntrada = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(255, 255, 204));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verPanelUser.setBackground(new java.awt.Color(204, 204, 255));

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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout verPanelUserLayout = new javax.swing.GroupLayout(verPanelUser);
        verPanelUser.setLayout(verPanelUserLayout);
        verPanelUserLayout.setHorizontalGroup(
            verPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1011, Short.MAX_VALUE)
        );
        verPanelUserLayout.setVerticalGroup(
            verPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
        );

        background.add(verPanelUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, -1, -1));

        btnAdmin.setText("Opciones administrador");
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        background.add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 200, 70));

        btnPDF.setText("Generar PDF");
        background.add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 70));

        jButton1.setText("Mostrar a los usuarios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        background.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 200, 70));

        btnActivarSalida.setText("Activar Sallida");
        background.add(btnActivarSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 200, 70));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 450, 260));

        btnActivarEntrada.setText("Activar Entrada");
        background.add(btnActivarEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 200, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        //2-. Recordar pasarle siempre pasarle el objeto usuario para poder cambiar de JFrame
       VistaAdmin vistAD = new VistaAdmin(usuario);
       vistAD.setVisible(true);
       dispose();
       //2-.//
    }//GEN-LAST:event_btnAdminActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        VistaUsuario vistaU = new VistaUsuario();
        vistaU.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    public static void main(String args[]) {
        //java.awt.EventQueue.invokeLater(() -> new VistaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btnActivarEntrada;
    private javax.swing.JButton btnActivarSalida;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel verPanelUser;
    // End of variables declaration//GEN-END:variables
}
