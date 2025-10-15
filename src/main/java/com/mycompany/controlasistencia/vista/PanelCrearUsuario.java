
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.dao.RolesDAO;
import com.mycompany.controlasistencia.dao.UsuarioDAO;
import com.mycompany.controlasistencia.modelo.Roles;
import com.mycompany.controlasistencia.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;



public class PanelCrearUsuario extends javax.swing.JPanel {

     private List<Roles> rolesCache = new ArrayList<>(); //Listar los roles
     private final Usuario actor; //
     private final RolesDAO rolesDAO = new RolesDAO(); 
     private static final java.util.regex.Pattern SOLO_LETRAS =
        java.util.regex.Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]{2,40}$");
     private static String normalizarNombre(String s) {
    if (s == null) return "";
    return s.trim().replaceAll("\\s{2,}", " ");
}
    private static boolean esNombreValido(String s) {
    String t = normalizarNombre(s);
    return SOLO_LETRAS.matcher(t).matches();
}
    public PanelCrearUsuario(Usuario actor) {
         this.actor = actor;
        initComponents();
        cargarRolesEnCombo();
    }
    
    private static String normalizaRut(String rut) {
    if (rut == null) return null;
    return rut.replaceAll("[\\.\\-\\s]", "").toUpperCase();
}

private static boolean rutValido(String rut) {
    String s = normalizaRut(rut);
    if (s == null || !s.matches("^[0-9]{7,8}[0-9K]$")) return false;

    String cuerpo = s.substring(0, s.length()-1);
    char dvIn = s.charAt(s.length()-1);

    int suma = 0, mult = 2;
    for (int i = cuerpo.length()-1; i >= 0; i--) {
        int d = Character.digit(cuerpo.charAt(i), 10);
        suma += d * mult;
        mult = (mult == 7) ? 2 : mult + 1;
    }
    int rest = 11 - (suma % 11);
    char dvCalc = (rest == 11) ? '0' : (rest == 10) ? 'K' : Character.forDigit(rest, 10);
    return dvCalc == dvIn;
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
        jLabel6 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nombre");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, -1));

        jLabel2.setText("Apellido");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        jLabel3.setText("Contraseña");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 71, -1));

        jLabel4.setText("Repetir contraseña");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, -1, -1));

        jLabel5.setText("Seleccionar rol");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, -1, -1));

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
        jPanel1.add(boxRoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 160, -1));
        jPanel1.add(txtRepetirContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 160, 20));
        jPanel1.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 160, 20));
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 160, 20));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 160, 20));

        jLabel6.setText("Rut");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, -1, -1));
        jPanel1.add(txtRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 160, -1));

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
    if (idRol <= 0) { //En caso de que alguna manera no haya un rol seleccionado
        javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un rol");
        return;
    }

    String nombre = normalizarNombre(txtNombre.getText());
    String apellido = normalizarNombre(txtApellido.getText());
    String contra = new String(txtContra.getPassword());
    String repetir = new String(txtRepetirContra.getPassword());
    String rutInput = txtRut.getText();
    String rutNorm = normalizaRut(rutInput);
    
    if (rutNorm == null || rutNorm.isBlank() || !rutValido(rutNorm)) {
    javax.swing.JOptionPane.showMessageDialog(this,
        "RUT inválido. Ingrésalo sin guion, por ejemplo: 215204804"); //Ejemplo de rut funcionando sin guión
    return;
}

    if (nombre.isEmpty() || apellido.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Nombre y Apellido no pueden estar vacíos");
        return;
    }
    
    if (!esNombreValido(nombre) || !esNombreValido(apellido)) {
        javax.swing.JOptionPane.showMessageDialog(this, "Nombre y apellido solo pueden contener letras y espacios");
        return;
    }
    
    // password opcional pero, si la ingresan, debe validar
    String pass = null;
    if (!contra.isBlank() || !repetir.isBlank()) {
        if (!contra.equals(repetir)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
            return;
        }
        if (contra.length() < 6) {
            javax.swing.JOptionPane.showMessageDialog(this, "La contraseña debe tener 6+ caracteres");
            return;
        }
        pass = contra;
    }

    UsuarioDAO dao = new UsuarioDAO();
    Usuario nuevo;
    
    Usuario existente = dao.buscarPorRutFlexible(rutNorm); //Buscar el rut existente
    if (existente != null) {
    javax.swing.JOptionPane.showMessageDialog(this, "Ese RUT ya está registrado.");
    return;
    }

    if (actor != null) {
        nuevo = dao.crearCuentaConLog(actor.getId_Usuario(), nombre, apellido,rutNorm, pass);
    } else {
        nuevo = dao.crearCuenta(nombre, apellido,rutNorm ,pass);
    }

    if (nuevo == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "No se pudo crear el usuario", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // asignar el rol seleccionado
    java.util.List<Integer> roles = java.util.Collections.singletonList(idRol);
    boolean okRoles = rolesDAO.reemplazarRolesUsuario(nuevo.getId_Usuario(), roles);

    javax.swing.JOptionPane.showMessageDialog(
        this,
        okRoles ? "Usuario creado y rol asignado." : "Usuario creado, pero no se pudo asignar el rol."
    );

    // limpiar formulario
    txtNombre.setText("");
    txtApellido.setText("");
    txtContra.setText("");
    txtRepetirContra.setText("");
    txtRut.setText("");
    boxRoles.setSelectedIndex(-1);
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void boxRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxRolesActionPerformed

    }//GEN-LAST:event_boxRolesActionPerformed

    //Agregar los roles en el comboBox
    private void cargarRolesEnCombo() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        try {
            rolesCache = new RolesDAO().listarRoles(); 
            for (Roles r : rolesCache) {
                model.addElement(r.getNombre());
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtRepetirContra;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
