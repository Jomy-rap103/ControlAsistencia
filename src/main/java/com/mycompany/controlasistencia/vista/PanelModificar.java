
package com.mycompany.controlasistencia.vista;


import com.mycompany.controlasistencia.dao.RolesDAO;
import com.mycompany.controlasistencia.dao.UsuarioDAO;
import com.mycompany.controlasistencia.modelo.Roles;
import com.mycompany.controlasistencia.modelo.Usuario;
import com.mycompany.controlasistencia.vista.VistaAdmin; //borrar este
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;


public class PanelModificar extends javax.swing.JPanel {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final Usuario usuario;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private final RolesDAO rolesDAO = new RolesDAO();
    private List<Roles> rolesCatalogo = new ArrayList<>();
    private final Map<String, Integer> rolNombreToId = new HashMap<>();
    private static final java.util.regex.Pattern Solo_Letras = java.util.regex.Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]{2,40}$");
    private static String normalizarNombre(String s) {
    if (s == null) return "";
    return s.trim().replaceAll("\\s{2,}", " ");
}
    private static boolean esNombreValido (String s) {String t = normalizarNombre(s); return Solo_Letras.matcher(t).matches();} 
    private static String normalizaRut(String rut) {
    if (rut == null) return null;
    return rut.replaceAll("[-.\\s]", "").toUpperCase();
}
    
    
    public PanelModificar(Usuario usuario) {
        this.usuario = usuario;
        initComponents();                
        configurarTabla();               
        cargarUsuariosEnTabla();      
        cargarRolesEnCombos();    
        engancharEventos();    
        engancharCombosRoles();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tablaEditarUser = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtContra = new javax.swing.JPasswordField();
        txtRepetirContra = new javax.swing.JPasswordField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(153, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tablaEditarUser.setViewportView(jTable1);

        jPanel1.add(tablaEditarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 200));

        jLabel1.setText("Nombre");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel2.setText("Apellido");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel3.setText("Contraseña");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        jLabel4.setText("Repetir contraseña");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        jLabel5.setText("Roles");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, -1, -1));

        btnConfirmar.setText("Guardar cambios");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        jPanel1.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 350, 130, 30));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 120, -1));
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 120, -1));
        jPanel1.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 120, -1));
        jPanel1.add(txtRepetirContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, 120, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, -1, -1));

        jLabel6.setText("Segundo rol (opcional)");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, -1, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, -1));

        jLabel7.setText("¿Activo?");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, -1, -1));

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, -1, -1));

        jLabel8.setText("Rut");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));
        jPanel1.add(txtRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 120, -1));

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
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        int row = jTable1.getSelectedRow();
    if (row >= 0) {
        int modelRow = jTable1.convertRowIndexToModel(row);
        modelo.setValueAt(jCheckBox1.isSelected(), modelRow, 4);
    }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    
    private void configurarTabla() {
    String[] columnas = {"ID", "Nombre", "Apellido","Rut", "Activo"};
    modelo = new DefaultTableModel(columnas, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
        @Override public Class<?> getColumnClass(int col) {
            return switch (col) {
                case 0 -> Integer.class; // ID
                case 4 -> Boolean.class; // Activo
                default -> String.class; // Nombre, Apellido
            };
        }
    };
    jTable1.setModel(modelo);
    sorter = new TableRowSorter<>(modelo);
    jTable1.setRowSorter(sorter);
    jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}
    
    private void cargarUsuariosEnTabla() {
    modelo.setRowCount(0);
    List<Usuario> usuarios = usuarioDAO.listarTodos();
    for (Usuario u : usuarios) {
        modelo.addRow(new Object[]{
            u.getId_Usuario(),
            u.getNombre(),
            u.getApellido(),
            u.getRut(),
            u.isActivo()
        });
    }
}

   private void engancharEventos() {
    // Al seleccionar aqui se cargan los datos
    jTable1.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            cargarSeleccionEnCampos();
        }
    });
    btnConfirmar.addActionListener(e -> guardarCambios());
}

private void cargarSeleccionEnCampos() {
    int viewRow = jTable1.getSelectedRow();
    if (viewRow < 0) {
        limpiarCampos(); //Limpiar los elementos en el label
        return;
    }
    int modelRow = jTable1.convertRowIndexToModel(viewRow);

    String nombre = String.valueOf(modelo.getValueAt(modelRow, 1));
    String apellido = String.valueOf(modelo.getValueAt(modelRow, 2));
    String rut = String.valueOf(modelo.getValueAt(modelRow, 3));
    boolean activo = (boolean) modelo.getValueAt(modelRow, 4);
    

    txtNombre.setText(nombre);
    txtApellido.setText(apellido);
    txtRut.setText(rut);
    txtContra.setText("");
    txtRepetirContra.setText("");
    
    //Aqui estoy sincronizando el checkBox
    
    jCheckBox1.setSelected(activo);

    Integer idSel = getIdSeleccionado();
    if (idSel != null) {
        seleccionarRolesDeUsuario(idSel);
    }

}

private Integer getIdSeleccionado() {
    int viewRow = jTable1.getSelectedRow();
    if (viewRow < 0) return null;
    int modelRow = jTable1.convertRowIndexToModel(viewRow);
    Object val = modelo.getValueAt(modelRow, 0);
    return (val instanceof Integer) ? (Integer) val : Integer.valueOf(val.toString());
}

private boolean getActivoSeleccionadoDesdeTabla() {
    int viewRow = jTable1.getSelectedRow();
    if (viewRow < 0) return true; // por defecto
    int modelRow = jTable1.convertRowIndexToModel(viewRow);
    Object val = modelo.getValueAt(modelRow, 4);
    return (val instanceof Boolean) ? (Boolean) val : Boolean.parseBoolean(String.valueOf(val));
}

private void guardarCambios() {
    Integer id = getIdSeleccionado();
    if (id == null) {
        JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String nombre = normalizarNombre(txtNombre.getText());
    String apellido = normalizarNombre(txtApellido.getText());
    String contra = new String(txtContra.getPassword());
    String repetirContra = new String(txtRepetirContra.getPassword());
    String rutInput = txtRut.getText();
    String rutNorm  = normalizaRut(rutInput);

    if (nombre.isBlank() || apellido.isBlank()) {
        JOptionPane.showMessageDialog(this, "Nombre y Apellido no pueden estar vacíos.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (!esNombreValido(nombre) || !esNombreValido(apellido)) {
        JOptionPane.showMessageDialog(this, "Nombre y apellido solo pueden contener letras");
        return;
    }

    String nuevaContrasena = null; 
    if (!contra.isBlank() || !repetirContra.isBlank()) {
        //Comparar contraseñas
        if (!contra.equals(repetirContra)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Limitar caracteres
        if (contra.length() < 6 ) {
            JOptionPane.showMessageDialog(this, "La contraseña debe ser mayor a 6 digitos");
            return;
        }
        nuevaContrasena = contra; 
    }
        //true si el checkbox se encuentra seleccionado
        boolean activo = jCheckBox1.isSelected();

    Integer actorId = (usuario != null) ? usuario.getId_Usuario() : null;

    boolean ok = usuarioDAO.actualizarUsuarioConLog(
        actorId,
        id,
        nombre,
        apellido,
        rutNorm,
        nuevaContrasena,  // puede ser null
        activo
    );

    if (ok) {
        
        //Guardar en el historial los cambios realizados 
        var rolesAntes = rolesDAO.listarPorUsuario(id);

        List<Integer> idsRoles = getIdsRolesSeleccionados();
        boolean okRoles = rolesDAO.reemplazarRolesUsuario(id, idsRoles);

        var rolesDespues = rolesDAO.listarPorUsuario(id);

        if (okRoles && actorId != null) {
            String antes = rolesAntes.stream().map(Roles::getNombre).sorted()
                             .reduce((a,b)->a+", "+b).orElse("(sin roles)");
            String despues = rolesDespues.stream().map(Roles::getNombre).sorted()
                               .reduce((a,b)->a+", "+b).orElse("(sin roles)");
            new com.mycompany.controlasistencia.dao.HistorialDAO().log(
                actorId, "EDITAR_ROLES", "USUARIO", id,
                "antes: " + antes + " | despues: " + despues
            );
        }

        JOptionPane.showMessageDialog(this,
            okRoles ? "Usuario y roles actualizados." : "Usuario actualizado, pero no se guardaron los roles.");

        int idReSeleccion = id;
        cargarUsuariosEnTabla();
        reseleccionarPorId(idReSeleccion);
        txtContra.setText("");
        txtRepetirContra.setText("");
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
    }  
}

private void reseleccionarPorId(int id) {
    for (int i = 0; i < modelo.getRowCount(); i++) {
        Object val = modelo.getValueAt(i, 0);
        int rowId = (val instanceof Integer) ? (Integer) val : Integer.parseInt(String.valueOf(val));
        if (rowId == id) {
            int viewRow = jTable1.convertRowIndexToView(i);
            jTable1.getSelectionModel().setSelectionInterval(viewRow, viewRow);
            jTable1.scrollRectToVisible(jTable1.getCellRect(viewRow, 0, true));
            break;
        }
    }
}

private void limpiarCampos() {
    txtNombre.setText("");
    txtApellido.setText("");
    txtContra.setText("");
    txtRepetirContra.setText("");
}

// Cargar catálogo en ambos combos
private void cargarRolesEnCombos() {
    rolesCatalogo = rolesDAO.listarTodos();
    rolNombreToId.clear();
    for (Roles r : rolesCatalogo) {
        rolNombreToId.put(r.getNombre(), r.getId());
    }
    String[] nombres = rolesCatalogo.stream().map(Roles::getNombre).toArray(String[]::new);
    jComboBox1.setModel(new DefaultComboBoxModel<>(nombres));
    jComboBox2.setModel(new DefaultComboBoxModel<>(nombres));
    jComboBox1.setSelectedIndex(-1);
    jComboBox2.setSelectedIndex(-1);
}

// Evitar duplicar el mismo rol en Rol 2
private void actualizarModeloRol2() {
    String sel1 = (String) jComboBox1.getSelectedItem();
    List<String> nombres = new ArrayList<>();
    for (Roles r : rolesCatalogo) {
        if (sel1 == null || !r.getNombre().equals(sel1)) {
            nombres.add(r.getNombre());
        }
    }
    jComboBox2.setModel(new DefaultComboBoxModel<>(nombres.toArray(new String[0])));
    jComboBox2.setSelectedIndex(-1);
}

// Precargar hasta 2 roles del usuario
private void seleccionarRolesDeUsuario(int idUsuario) {
    List<Roles> delUsuario = rolesDAO.listarPorUsuario(idUsuario);
    delUsuario.sort(Comparator.comparing(Roles::getNombre));

    String r1 = (delUsuario.size() >= 1) ? delUsuario.get(0).getNombre() : null;
    String r2 = (delUsuario.size() >= 2) ? delUsuario.get(1).getNombre() : null;

    // Set Rol 1
    if (r1 == null) jComboBox1.setSelectedIndex(-1);
    else jComboBox1.setSelectedItem(r1);

    // Actualizar Rol 2 (filtrado por selección de Rol 1)
    actualizarModeloRol2();

    if (r2 != null) jComboBox2.setSelectedItem(r2);
}


private List<Integer> getIdsRolesSeleccionados() {
    Set<Integer> ids = new LinkedHashSet<>();
    String s1 = (String) jComboBox1.getSelectedItem();
    String s2 = (String) jComboBox2.getSelectedItem();
    if (s1 != null && rolNombreToId.containsKey(s1)) ids.add(rolNombreToId.get(s1));
    if (s2 != null && rolNombreToId.containsKey(s2)) ids.add(rolNombreToId.get(s2));
    return new ArrayList<>(ids);
}

// Aqui es para recalcular los roles de Combox2
private void engancharCombosRoles() {
    jComboBox1.addActionListener(e -> actualizarModeloRol2());
}

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane tablaEditarUser;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtRepetirContra;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
