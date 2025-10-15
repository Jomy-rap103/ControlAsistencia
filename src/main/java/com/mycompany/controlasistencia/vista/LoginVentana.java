
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
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtContra = new javax.swing.JPasswordField();
        btnConfirmar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Nombre");
        jLabel2.putClientProperty("FlatLaf.style", "font: 14 $semibold.font; foreground: #263238");

        txtNombre.putClientProperty("JTextField.placeholderText", "Tu nombre");
        txtNombre.putClientProperty("FlatLaf.style", "arc:14; borderWidth:1");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lock_icon_24x24.png"))); // NOI18N
        jLabel1.setText("Contraseña");
        jLabel1.putClientProperty("FlatLaf.style", "font: 14 $semibold.font; foreground: #263238");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user_icon_24x24_no_bg.png"))); // NOI18N
        jLabel3.setText("Apellido");
        jLabel3.putClientProperty("FlatLaf.style", "font: 14 $semibold.font; foreground: #263238");

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

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user_icon_24x24_no_bg.png"))); // NOI18N

        txtApellido.putClientProperty("JTextField.placeholderText", "Tu apellido");
        txtApellido.putClientProperty("FlatLaf.style", "arc:14; borderWidth:1");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(backgroundLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel2))
                                .addComponent(jLabel3))
                            .addComponent(jLabel1))
                        .addGap(68, 68, 68)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtContra, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtApellido))))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(184, 184, 184)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
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
        

        String nombre = normalizarNombre(txtNombre.getText());
        String apellido = normalizarNombre(txtApellido.getText());
        String pass   = new String(txtContra.getPassword());

        if (nombre.isEmpty() || apellido.isEmpty()||pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa usuario y contraseña.");
            txtNombre.setText("");
            return;
        }
        
        var dao = new UsuarioDAO();
        Usuario u = dao.iniciarSesion(nombre, apellido, pass);
        
        if (u == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario invalido");
            return;
        } 
        
        new VistaPrincipal(u).setVisible(true);
        this.dispose();
        setTitle("Iniciar sesión");
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraActionPerformed
        
    }//GEN-LAST:event_txtContraActionPerformed

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
    
}
