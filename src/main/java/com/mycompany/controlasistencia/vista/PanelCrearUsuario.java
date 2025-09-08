
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.dao.RolesDAO;
import com.mycompany.controlasistencia.dao.UsuarioDAO;
import com.mycompany.controlasistencia.modelo.Roles;
import com.mycompany.controlasistencia.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;


public class PanelCrearUsuario extends javax.swing.JPanel {

     private List<Roles> rolesCache = new ArrayList<>();
    
    public PanelCrearUsuario() {
        initComponents();
        cargarRolesEnCombo();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        boxRoles = new javax.swing.JComboBox<>();
        txtRepetirContra = new javax.swing.JPasswordField();
        txtContra = new javax.swing.JPasswordField();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nombre");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 31, -1, -1));

        jLabel2.setText("Apellido");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 80, -1, -1));

        jLabel3.setText("Contraseñsa");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 136, 71, -1));

        jLabel4.setText("Repetir contraseña");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 181, -1, -1));

        jLabel5.setText("Seleccionar rol");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 223, -1, -1));

        btnConfirmar.setBackground(new java.awt.Color(153, 255, 153));
        btnConfirmar.setText("Crear usuario");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        jPanel1.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 170, 40));

        boxRoles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxRolesActionPerformed(evt);
            }
        });
        jPanel1.add(boxRoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 223, 160, -1));
        jPanel1.add(txtRepetirContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 178, 160, 20));
        jPanel1.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 133, 160, 20));
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 77, 160, 20));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 28, 160, 20));

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

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        int idRol = getRolSeleccionadoId();
        
        if (idRol <= 0 ) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un rol");
            return;
        }
        
        //4.- Aqui paso los textos escritos a string para que java pueda leerlos
        
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String contra = new String(txtContra.getPassword());
        String repetirContra = new String(txtRepetirContra.getPassword());
        
        //Aqui termina el comentario 4
        
        if (!contra.equals(repetirContra)) {
            javax.swing.JOptionPane.showMessageDialog(this, "las contraseñas no coinciden");
            txtNombre.setText("");
            txtApellido.setText("");
            return;
        }
        
        if (nombre.isEmpty() || apellido.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Los campos no puede estar vacios");
            txtApellido.setText("");
            txtRepetirContra.setText("");
            return;
            //Agregar validaciones para que no puedan agregar numeros, espacios o caracteres especiales
            
        }
        
        String validacionContra = null;
        boolean Contenido = !contra.isEmpty() || !repetirContra.isEmpty();
        if (Contenido) {
            if (!contra.equals(repetirContra)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Las contraseña no coinciden");
                txtContra.setText("");
                txtRepetirContra.setText("");
                return; }
            
            if (contra.length() < 6) {
                javax.swing.JOptionPane.showMessageDialog(this, "La contraseña debe tener mas de 6 caracteres");
                return;
            }
        
            validacionContra = contra;
        }
        
        
        
        
        //5.- Llamo al DAO y fu funcion para crear usuarios
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.crearCuenta(nombre, apellido, contra);
        
        
        
        
        
        
        //Aqui termina el comentario 5
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void boxRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxRolesActionPerformed

    }//GEN-LAST:event_boxRolesActionPerformed

    
    private void cargarRolesEnCombo() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        try {
            rolesCache = new RolesDAO().listarRoles(); // trae (id, nombre)
            for (Roles r : rolesCache) {
                model.addElement(r.getNombre()); // el combo muestra solo el texto
            }
        } catch (Exception e) {
            e.printStackTrace();
            for (Roles r : rolesCache) model.addElement(r.getNombre());
        }
        boxRoles.setModel(model);
    }
    
    private int getRolSeleccionadoId() {
        int idx = boxRoles.getSelectedIndex();
        if (idx < 0 || idx >= rolesCache.size()) return -1;
        return rolesCache.get(idx).getId();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxRoles;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtRepetirContra;
    // End of variables declaration//GEN-END:variables
}
