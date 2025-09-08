
package com.mycompany.controlasistencia.vista;

/*FALTA AGREGAR EL BOTON DE "Borrar usuario"
  Solo cambiara el valor de True a False
  Tambien falta agregar en la DB asistente de la educacion
*/

import com.mycompany.controlasistencia.dao.RolesDAO;
import com.mycompany.controlasistencia.dao.UsuarioDAO;
import com.mycompany.controlasistencia.modelo.Roles;
import com.mycompany.controlasistencia.modelo.Usuario;
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
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private final RolesDAO rolesDAO = new RolesDAO();
    private List<Roles> rolesCatalogo = new ArrayList<>();
    private final Map<String, Integer> rolNombreToId = new HashMap<>();

    
    
    public PanelModificar() {
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

        jPanel1.add(tablaEditarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 227));

        jLabel1.setText("Nombre");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        jLabel2.setText("Apellido");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabel3.setText("Contraseña");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        jLabel4.setText("Repetir contraseña");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        jLabel5.setText("Roles");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, -1, -1));

        btnConfirmar.setText("Guardar cambios");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        jPanel1.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 130, 30));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 120, -1));
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 120, -1));
        jPanel1.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 120, -1));
        jPanel1.add(txtRepetirContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 120, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, -1, -1));

        jLabel6.setText("Segundo rol (opcional)");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, -1, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, -1, -1));

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
        //guardarCambios();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    
    private void configurarTabla() {
    String[] columnas = {"ID", "Nombre", "Apellido", "Activo"};
    modelo = new DefaultTableModel(columnas, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
        @Override public Class<?> getColumnClass(int col) {
            return switch (col) {
                case 0 -> Integer.class; // ID
                case 3 -> Boolean.class; // Activo
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
        limpiarCampos();
        return;
    }
    int modelRow = jTable1.convertRowIndexToModel(viewRow);

    String nombre = String.valueOf(modelo.getValueAt(modelRow, 1));
    String apellido = String.valueOf(modelo.getValueAt(modelRow, 2));

    txtNombre.setText(nombre);
    txtApellido.setText(apellido);
    txtContra.setText("");
    txtRepetirContra.setText("");

    // Roles aún no implementados en BD, deshabilitamos el combo
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
    Object val = modelo.getValueAt(modelRow, 3);
    return (val instanceof Boolean) ? (Boolean) val : Boolean.parseBoolean(String.valueOf(val));
}

private void guardarCambios() {
    Integer id = getIdSeleccionado();
    if (id == null) {
        JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    String contra = new String(txtContra.getPassword());
    String repetirContra = new String(txtRepetirContra.getPassword());

    if (nombre.isBlank() || apellido.isBlank()) {
        JOptionPane.showMessageDialog(this, "Nombre y Apellido no pueden estar vacíos.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
        //ME FALTA AGREGAR PARA EVITAR QUE SE AGREGUEN DATOS COMO NUMEROS, ESPACIOS Y CARACTERES ESPECIALES
        return;
    }

    String nuevaContrasena = null; 
    if (!contra.isBlank() || !repetirContra.isBlank()) {
        if (!contra.equals(repetirContra)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (contra.length() < 6 ) {
            JOptionPane.showMessageDialog(this, "La contraseña debe ser mayor a 6 digitos");
            return;
        }
        nuevaContrasena = contra; 
    }

    boolean activo = getActivoSeleccionadoDesdeTabla();

    boolean ok = usuarioDAO.actualizarUsuario(id, nombre, apellido, nuevaContrasena, activo);
    if (ok) {
    List<Integer> idsRoles = getIdsRolesSeleccionados();
    boolean okRoles = rolesDAO.reemplazarRolesUsuario(id, idsRoles);

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
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane tablaEditarUser;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtRepetirContra;
    // End of variables declaration//GEN-END:variables
}
