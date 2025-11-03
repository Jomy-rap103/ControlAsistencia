
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.dao.UsuarioDAO;
import com.mycompany.controlasistencia.modelo.Usuario;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;                                                                     

public class LoginVentana extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginVentana.class.getName());
    private static String normalizarNombre (String s) { 
        if ( s == null) return "";
        return s.trim().replaceAll("\\s+", "");
    }
    
    
    public LoginVentana() {
    com.formdev.flatlaf.FlatLightLaf.setup();

    initComponents();
    this.setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new LoginVentana.FondoPanel();
        jLabel1 = new javax.swing.JLabel();
        txtContra = new javax.swing.JPasswordField();
        btnConfirmar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lock_icon_24x24.png"))); // NOI18N
        jLabel1.setText("Contraseña");
        jLabel1.putClientProperty("FlatLaf.style", "font: 14 $semibold.font; foreground: #263238");

        txtContra.putClientProperty("JTextField.placeholderText", "Tu contraseña");
        txtContra.putClientProperty("FlatLaf.style", "arc:14; borderWidth:1");
        txtContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraActionPerformed(evt);
            }
        });

        btnConfirmar.setBackground(new java.awt.Color(0, 204, 255));
        btnConfirmar.setText("Iniciar sesion");
        btnConfirmar.putClientProperty("JButton.buttonType", "roundRect");
        btnConfirmar.putClientProperty("FlatLaf.style",
            "arc:999;" +
            "background:#2196F3;" +
            "foreground:#FFFFFF;" +
            "hoverBackground:#1E88E5;" +
            "pressedBackground:#1976D2"
        );
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user_icon_24x24_no_bg.png"))); // NOI18N
        jLabel5.setText("Rut");
        jLabel5.putClientProperty("FlatLaf.style", "font: 14 $semibold.font; foreground: #263238");

        txtRut.putClientProperty("JTextField.placeholderText", "Tu rut");
        txtRut.putClientProperty("FlatLaf.style", "arc:14; borderWidth:1");
        txtRut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))
                        .addGap(66, 66, 66)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtContra, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtRut)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
    String rut = txtRut.getText().trim();
    String pass = new String(txtContra.getPassword());

    if (rut.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe ingresar RUT y contraseña.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    UsuarioDAO dao = new UsuarioDAO();
    Usuario usuario = dao.iniciarSesion(rut, pass);

    if (usuario == null) {
        JOptionPane.showMessageDialog(this, "RUT o contraseña inválidos.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        return;
    }

    new VistaPrincipal(usuario).setVisible(true);
    this.dispose();
    setTitle("Inicio de sesión");
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraActionPerformed
        
    }//GEN-LAST:event_txtContraActionPerformed

    private void txtRutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutActionPerformed

    public static void main(String args[]) {
   
        java.awt.EventQueue.invokeLater(() -> new LoginVentana().setVisible(true));
    }
    
    private void reemplazarBackgroundPorFondo() {
    // nuevo panel con imagen
    FondoPanel nuevo = new FondoPanel();

    // Copiamos layout y movemos hijos
    nuevo.setLayout(background.getLayout());
    java.awt.Component[] hijos = background.getComponents();
    background.removeAll();
    for (java.awt.Component c : hijos) {
        nuevo.add(c);
    }

    // Reemplazo en el content pane
    java.awt.Container cp = getContentPane();
    cp.remove(background);
    background = nuevo;
    cp.add(background);

    cp.revalidate();
    cp.repaint();
}
    class FondoPanel extends javax.swing.JPanel {
        private Image imagen;
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen == null) {
                var url = getClass().getResource("/imagenes/gradient_background.jpg");
                imagen = new ImageIcon(url).getImage();
            }
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
    
}
