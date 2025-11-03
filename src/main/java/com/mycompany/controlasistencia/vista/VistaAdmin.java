
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;


public class VistaAdmin extends javax.swing.JFrame {

    private Usuario usuario;

    public VistaAdmin(Usuario usuario) {
    this.usuario = usuario;
    initComponents();
    setLocationRelativeTo(null);
    
    // 1-. Aqui estoy usando JPaneles para desplazarme por los botones, por alguna razon no funciona como lo hacia antes
    PanelCrearUsuario panelCrearUsuario = new PanelCrearUsuario(usuario);
    panelCrearUsuario.setPreferredSize(new Dimension(570, 390));
    panelCrearUsuario.setLocation(0, 0);

    panelCrud.removeAll();
    panelCrud.setLayout(new BorderLayout()); // importantísimo
    panelCrud.add(panelCrearUsuario, BorderLayout.CENTER);
    panelCrud.revalidate();
    panelCrud.repaint();
    
    setTitle("Opciones de administrador");
    }
    
    //1-. Aqui termina el desplazamiento de JPaneles


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelCrud = new javax.swing.JPanel();
        btnPaginaP = new javax.swing.JButton();
        btnCrear = new javax.swing.JButton();
        btnModificarUser = new javax.swing.JButton();
        btnModificarES = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCrud.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout panelCrudLayout = new javax.swing.GroupLayout(panelCrud);
        panelCrud.setLayout(panelCrudLayout);
        panelCrudLayout.setHorizontalGroup(
            panelCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );
        panelCrudLayout.setVerticalGroup(
            panelCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        jPanel1.add(panelCrud, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 570, 390));

        btnPaginaP.setBackground(new java.awt.Color(204, 255, 204));
        btnPaginaP.setText("Pagina principal");
        btnPaginaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaPActionPerformed(evt);
            }
        });
        jPanel1.add(btnPaginaP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 287, 52));

        btnCrear.setText("Crear ususario");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 287, 52));

        btnModificarUser.setText("Modificar usuario");
        btnModificarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUserActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 287, 52));

        btnModificarES.setText("Modificar entradas y salidas");
        btnModificarES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarESActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificarES, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 287, 52));

        btnHistorial.setText("Historial");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });
        jPanel1.add(btnHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 287, 52));

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

    private void btnPaginaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaginaPActionPerformed
        VistaPrincipal vistaP = new VistaPrincipal(usuario);
        vistaP.setVisible(true);
        dispose();
        
    }//GEN-LAST:event_btnPaginaPActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
    PanelCrearUsuario panelCrearUsuario = new PanelCrearUsuario(usuario);
    panelCrearUsuario.setPreferredSize(new Dimension(570, 390));
    panelCrearUsuario.setLocation(0, 0);

    panelCrud.removeAll();
    panelCrud.setLayout(new BorderLayout()); 
    panelCrud.add(panelCrearUsuario, BorderLayout.CENTER);
    panelCrud.revalidate();
    panelCrud.repaint();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnModificarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUserActionPerformed
    PanelModificar panelModUsuario = new PanelModificar(usuario);
    panelModUsuario.setPreferredSize(new Dimension(570, 390));
    panelModUsuario.setLocation(0, 0);

    panelCrud.removeAll();
    panelCrud.setLayout(new BorderLayout()); 
    panelCrud.add(panelModUsuario, BorderLayout.CENTER);
    panelCrud.revalidate();
    panelCrud.repaint();
    }//GEN-LAST:event_btnModificarUserActionPerformed

    private void btnModificarESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarESActionPerformed
        PanelModificarHorarios panelModHora = new PanelModificarHorarios(usuario);
        panelModHora.setPreferredSize(new Dimension(570,390));
        panelModHora.setLocation(0,0);
        
        panelCrud.removeAll();
        panelCrud.setLayout(new BorderLayout()); 
        panelCrud.add(panelModHora, BorderLayout.CENTER);
        panelCrud.revalidate();
        panelCrud.repaint();
    }//GEN-LAST:event_btnModificarESActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        PanelHistorial panelH = new PanelHistorial();
        panelH.setPreferredSize(new Dimension(570,390));
        panelH.setLocation(0,0);
        
        panelCrud.removeAll();
        panelCrud.setLayout(new BorderLayout()); 
        panelCrud.add(panelH, BorderLayout.CENTER);
        panelCrud.revalidate();
        panelCrud.repaint();
        
        
    }//GEN-LAST:event_btnHistorialActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnModificarES;
    private javax.swing.JButton btnModificarUser;
    private javax.swing.JButton btnPaginaP;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelCrud;
    // End of variables declaration//GEN-END:variables
}
