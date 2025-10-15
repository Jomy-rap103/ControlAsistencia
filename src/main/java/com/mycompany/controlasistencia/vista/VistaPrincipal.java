
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.modelo.Usuario;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.mycompany.controlasistencia.dao.RolesDAO;
import java.awt.BorderLayout;
//IMPORTS para poder leer el QR
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.*;
//
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatClientProperties;

import com.mycompany.controlasistencia.dao.UsuarioDAO;
import javax.swing.UIManager;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class VistaPrincipal extends javax.swing.JFrame {
    
    private final com.mycompany.controlasistencia.dao.UsuarioDAO usuarioDAO = new com.mycompany.controlasistencia.dao.UsuarioDAO();
    private final com.mycompany.controlasistencia.dao.AsistenciaDAO asistenciaDAO = new com.mycompany.controlasistencia.dao.AsistenciaDAO();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaPrincipal.class.getName());
    private final VistaAsistenciaTabla asistenciaModel = new VistaAsistenciaTabla();
    private static final java.time.ZoneId TZ = java.time.ZoneId.of("America/Santiago");
    private VistaUsuario ventanaUsuarios;
    //  
    private final StringBuilder scanBuf = new StringBuilder();
    private KeyEventDispatcher scannerDisp;
    private Timer scanTimeOut;
             


   private ScheduledExecutorService scanExec;
   private volatile boolean escaneando = false;
   
   private final java.util.concurrent.atomic.AtomicBoolean procesando = new java.util.concurrent.atomic.AtomicBoolean(false);
   private volatile String ultimoQR = null;
   private volatile long tUltimoQR = 0L;

   private enum ModoAsistencia { 
       ENTRADA, SALIDA }
    // pistola
   private int RutAsistencia;
   // pistola
   private volatile ModoAsistencia modoActual = null;
   private final java.time.format.DateTimeFormatter DF = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
    
    //Privates nuevos para la activacion de la camara
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    // Aqui termina
    private final RolesDAO rolesDAO = new RolesDAO();
    private Usuario usuario;

    public VistaPrincipal(Usuario usuario ) {
        this.usuario = usuario;
        //Borrar si no funciona
         getRootPane().setDefaultButton(null);            // NO default button

    for (var b : new javax.swing.JButton[]{
            btnPDF, btnAdmin, btnMostrarUsuarios, btnActivarEntrada, btnActivarSalida, btnApagarCamara
    }) {
        if (b != null) b.setFocusable(false);        // evita que ENTER los dispare
    }

    // 3) Dispatcher global para “comer” ENTER si no hay modo activo
    java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(e -> {
            if (e.getID() == java.awt.event.KeyEvent.KEY_PRESSED
                    && e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER
                    && modoActual == null) {
                return true;
            }
            return false;
        });
        //Borrar si no funciona
        initComponents();
        tablaIniciar();
        hookComboFechas();
        cargarFechas();
        recargarHoy();
        this.setLocationRelativeTo(null);
        panelCamara.setLayout(new BorderLayout());
        
        boolean esAdmin = usuario != null && rolesDAO.esAdmin(usuario.getId_Usuario());
        btnAdmin.setVisible(esAdmin);
        setTitle("Control Asistencia");
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new VistaPrincipal.FondoPanel()
        ;
        verPanelUser = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAsistencia = new javax.swing.JTable();
        btnAdmin = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        btnMostrarUsuarios = new javax.swing.JButton();
        btnActivarSalida = new javax.swing.JButton();
        panelCamara = new javax.swing.JPanel();
        btnActivarEntrada = new javax.swing.JButton();
        btnApagarCamara = new javax.swing.JButton();
        boxFechas = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(255, 255, 204));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verPanelUser.setBackground(new java.awt.Color(204, 204, 255));

        jTableAsistencia.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableAsistencia);

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
        btnAdmin.putClientProperty("JButton.buttonType", "roundRect");
        btnAdmin.putClientProperty("FlatLaf.style",
            "arc:999;" +
            "focusWidth:0;" +
            "innerFocusWidth:0;" +
            "borderWidth:0;" +
            "minimumWidth:0;"
            +   "background:#0EA5E9;"
        );

        btnAdmin.setPreferredSize(new java.awt.Dimension(72, 72));
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        background.add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 200, 70));

        btnPDF.setText("Generar PDF");
        btnPDF.putClientProperty("JButton.buttonType", "roundRect");
        btnPDF.putClientProperty("FlatLaf.style",
            "arc:999;" +
            "focusWidth:0;" +
            "innerFocusWidth:0;" +
            "borderWidth:0;" +
            "minimumWidth:0;"
            +   "background:#0EA5E9"
        );

        btnPDF.setPreferredSize(new java.awt.Dimension(72, 72));
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });
        background.add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 70));

        btnMostrarUsuarios.setText("Mostrar a los usuarios");
        btnMostrarUsuarios.putClientProperty("JButton.buttonType", "roundRect");
        btnMostrarUsuarios.putClientProperty("FlatLaf.style",
            "arc:999;" +
            "focusWidth:0;" +
            "innerFocusWidth:0;" +
            "borderWidth:0;" +
            "minimumWidth:0;"
            +   "background:#0EA5E9"
        );
        // tamaño cuadrado
        btnMostrarUsuarios.setPreferredSize(new java.awt.Dimension(72, 72));
        btnMostrarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarUsuariosActionPerformed(evt);
            }
        });
        background.add(btnMostrarUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 200, 70));

        btnActivarSalida.setText("Activar Sallida");
        btnActivarSalida.putClientProperty("JButton.buttonType", "roundRect");
        btnActivarSalida.putClientProperty("FlatLaf.style",
            "arc:999;" +
            "focusWidth:0;" +
            "innerFocusWidth:0;" +
            "borderWidth:0;" +
            "minimumWidth:0;"
            +   "background:#0EA5E9;"
        );
        // tamaño cuadrado
        btnActivarSalida.setPreferredSize(new java.awt.Dimension(72, 72));
        btnActivarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarSalidaActionPerformed(evt);
            }
        });
        background.add(btnActivarSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 200, 70));

        javax.swing.GroupLayout panelCamaraLayout = new javax.swing.GroupLayout(panelCamara);
        panelCamara.setLayout(panelCamaraLayout);
        panelCamaraLayout.setHorizontalGroup(
            panelCamaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        panelCamaraLayout.setVerticalGroup(
            panelCamaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        background.add(panelCamara, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 450, 260));

        btnActivarEntrada.setText("Activar Entrada");
        btnActivarEntrada.putClientProperty("JButton.buttonType", "roundRect");
        btnActivarEntrada.putClientProperty("FlatLaf.style",
            "arc:999;" +
            "focusWidth:0;" +
            "innerFocusWidth:0;" +
            "borderWidth:0;" +
            "minimumWidth:0;"
            +   "background:#0EA5E9;"
        );
        // tamaño cuadrado
        btnActivarEntrada.setPreferredSize(new java.awt.Dimension(72, 72));
        btnActivarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarEntradaActionPerformed(evt);
            }
        });
        background.add(btnActivarEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 200, 70));

        btnApagarCamara.setText("Apagar camara");
        btnApagarCamara.putClientProperty("JButton.buttonType", "roundRect");
        btnApagarCamara.putClientProperty("FlatLaf.style",
            "arc:999;" +
            "focusWidth:0;" +
            "innerFocusWidth:0;" +
            "borderWidth:0;" +
            "minimumWidth:0;"
            +   "background:#0EA5E9"
        );
        // tamaño cuadrado
        btnApagarCamara.setPreferredSize(new java.awt.Dimension(72, 72));
        btnApagarCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarCamaraActionPerformed(evt);
            }
        });
        background.add(btnApagarCamara, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 300, 200, 70));

        boxFechas.setModel(new javax.swing.DefaultComboBoxModel<>());
        background.add(boxFechas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 190, -1));

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
       
       apagarCamara();
       desactivarLectorHID();
       //2-.//
    }//GEN-LAST:event_btnAdminActionPerformed

    private void btnMostrarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarUsuariosActionPerformed
        if (ventanaUsuarios == null || !ventanaUsuarios.isDisplayable()) {
        ventanaUsuarios = new VistaUsuario();
        ventanaUsuarios.setLocationRelativeTo(this);
        // limpia la referencia cuando se cierre
        ventanaUsuarios.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosed(java.awt.event.WindowEvent e) {
                ventanaUsuarios = null;
            }
        });
    }
        ventanaUsuarios.setVisible(true);
        ventanaUsuarios.toFront();
        ventanaUsuarios.recargarHoy();
    }//GEN-LAST:event_btnMostrarUsuariosActionPerformed

    private void btnActivarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarEntradaActionPerformed
        modoActual = ModoAsistencia.ENTRADA;
        prenderCamara();
        activarLectorHID();
        leerQR(200);
        JOptionPane.showMessageDialog(null, "Entrada activada");
    }//GEN-LAST:event_btnActivarEntradaActionPerformed

    private void btnApagarCamaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarCamaraActionPerformed
        apagarCamara();
        desactivarLectorHID();
    }//GEN-LAST:event_btnApagarCamaraActionPerformed

    private void btnActivarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarSalidaActionPerformed
        modoActual = ModoAsistencia.SALIDA;
        prenderCamara();
        activarLectorHID();
        leerQR(200);
        JOptionPane.showMessageDialog(null, "Salida activada");
    }//GEN-LAST:event_btnActivarSalidaActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        int viewRow = jTableAsistencia.getSelectedRow();
        if (viewRow < 0) { javax.swing.JOptionPane.showMessageDialog(this,"Seleccione a alguien en la tabla."); return; }

        int modelRow = jTableAsistencia.convertRowIndexToModel(viewRow); 
        var sel = asistenciaModel.getAt(modelRow); 

        
        VistaGenerarPDF v = new VistaGenerarPDF(usuario, sel.id_usuario(), sel.nombre(), sel.apellido());
        v.setVisible(true);
        dispose();
        
        apagarCamara();
        desactivarLectorHID();
    }//GEN-LAST:event_btnPDFActionPerformed
    
    private void refrescarPantallas() {
    recargarHoy(); // refresca la tabla de VistaPrincipal
    if (ventanaUsuarios != null && ventanaUsuarios.isDisplayable()) {
        ventanaUsuarios.recargarHoy(); // refresca la ventana que ve la gente
    }
}

    
    
    //Aqui estoy apagando la camara
    public void apagarCamara() {
        try {
            // Detén el escaneo
            escaneando = false;
            if (scanExec != null && !scanExec.isShutdown()) {
                scanExec.shutdownNow();
                scanExec = null;
            }

            if (webcamPanel != null) {
                panelCamara.remove(webcamPanel);
                panelCamara.revalidate();
                panelCamara.repaint();
                webcamPanel = null;
            }
            if (webcam != null) {
                if (webcam.isOpen()) webcam.close();
                webcam = null;
            }
        } catch (Exception e) {
            logger.warning("Error al apagar la camara: " + e.getMessage()); //Mensaje de error por posibles errores
        }
    }

   
    
    //AQUI ESTOY HABILITANDO LA CAMARA
    public void prenderCamara() {
        try {
            if (webcam != null && webcam.isOpen()) {
                return; 
            }


            webcam = Webcam.getDefault();
            if (webcam == null) {
                JOptionPane.showMessageDialog(null, "Cámara no encontrada", "error de cámara", JOptionPane.ERROR_MESSAGE);
                logger.warning("No se encontró ninguna cámara.");
                return;
            }
            new Thread(() -> {
                try {
                    webcam.open(); 
                    webcamPanel = new WebcamPanel(webcam);
                    webcamPanel.setFPSDisplayed(true);
                    webcamPanel.setFillArea(true);

                    
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        panelCamara.removeAll();
                        panelCamara.add(webcamPanel, BorderLayout.CENTER);
                        panelCamara.revalidate();
                        panelCamara.repaint();
                    });
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al abrir la cámara", "error de cámara", JOptionPane.ERROR_MESSAGE);
                    logger.severe("Error al abrir la cámara: " + ex.getMessage());
                }
            }, "cam-open-thread").start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al prender la cámara", "error de cámara", JOptionPane.ERROR_MESSAGE);
            logger.severe("Error en prenderCamara: " + e.getMessage());
        }
    }

    
    private String decodificarQR(BufferedImage img) {
    try {
        LuminanceSource source = new BufferedImageLuminanceSource(img);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.of(BarcodeFormat.QR_CODE)); 

        Result result = new MultiFormatReader().decode(bitmap, hints);
        return (result != null) ? result.getText() : null;
    } catch (NotFoundException nf) {
        return null; 
    } catch (Exception e) {
        logger.severe("decodificarQR error: " + e.getMessage());
        return null;
    }
}
    
    public void leerQR(int periodoMs) {
    if (escaneando) return;
    escaneando = true;

    if (scanExec == null || scanExec.isShutdown()) {
        scanExec = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "qr-scan-loop");
            t.setDaemon(true);
            return t;
        });
    }

    scanExec.scheduleAtFixedRate(() -> {
        if (!escaneando) return;
        try {
            if (webcam == null || !webcam.isOpen()) return;

            BufferedImage img = webcam.getImage();
            if (img == null) return;

            String texto = decodificarQR(img);
            if (texto == null || texto.isBlank()) return;

            // --- Antirrebote por contenido (1.2s) ---
            long now = System.currentTimeMillis();
            if (texto.equals(ultimoQR) && (now - tUltimoQR) < 1200) {
                return;
            }
            ultimoQR = texto;
            tUltimoQR = now;

            // --- Exclusión: si ya estamos procesando otro frame, salimos ---
            if (procesando.getAndSet(true)) {
                return;
            }

            // Procesar en EDT la parte de UI/DAO y SIEMPRE liberar el candado
            javax.swing.SwingUtilities.invokeLater(() -> {
                try {
                    System.out.println("[QR] Leído: " + texto);
                    registrarAsistencia(texto, modoActual);
                } finally {
                    // Pequeño cooldown para no tragar el mismo frame repetido
                    scanExec.schedule(() -> procesando.set(false), 300, TimeUnit.MILLISECONDS);
                }
            });

        } catch (Exception ex) {
            logger.severe("Error en lectura continua: " + ex.getMessage());
        }
    }, 0, periodoMs, TimeUnit.MILLISECONDS);
}



    private String[] parseNombreApellido(String textoQR) {
        if (textoQR == null) return null;
        String t = textoQR.trim();
        if (t.isEmpty()) return null;
        if (t.contains(";")) {
            String[] p = t.split(";", 2);
            return new String[]{ p[0].trim(), p[1].trim() };
        } else {
            String[] p = t.split("\\s+", 2);
            if (p.length == 2) return new String[]{ p[0].trim(), p[1].trim() };
            return null;
        }
    }

   
    private void registrarAsistencia(String textoQR, ModoAsistencia modo) {
        
    String[] datos = parseNombreApellido(textoQR);
    if (datos == null) {
        JOptionPane.showMessageDialog(null, "QR no reconocido","error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String nombre = datos[0], apellido = datos[1];

    Usuario u = usuarioDAO.buscarPorNombreApellido(nombre, apellido);
    if (u == null) {
        JOptionPane.showMessageDialog(null, "Usuario no encontrado", "error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    

    var tz    = java.time.ZoneId.of("America/Santiago");
    var ahora = java.time.LocalDateTime.now(tz);
    var hoy   = ahora.toLocalDate();

   switch (modo) {
    case ENTRADA -> {
        boolean yaE = asistenciaDAO.existeEntradaHoy(u.getId_Usuario(), hoy);
        if (yaE) {
            java.awt.Toolkit.getDefaultToolkit().beep();
            javax.swing.SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Este usuario ya fue registrado", "entrada registrada", JOptionPane.INFORMATION_MESSAGE)
            );
            return;
        }
        boolean ok  = !yaE && asistenciaDAO.registrarEntradaSiNoExiste(u.getId_Usuario(), ahora);
        if (ok) {
            javax.swing.SwingUtilities.invokeLater(this::refrescarPantallas);
        }
    }
    case SALIDA -> {
        boolean yaE = asistenciaDAO.existeEntradaHoy(u.getId_Usuario(), hoy);
        if (!yaE) {
            java.awt.Toolkit.getDefaultToolkit().beep();
            javax.swing.SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "No existe entrada para hoy", "Falta entrada", JOptionPane.WARNING_MESSAGE)
            );
            return;
        }
        boolean yaS = asistenciaDAO.existeSalidaHoy(u.getId_Usuario(), hoy);
        if (yaS) {
            java.awt.Toolkit.getDefaultToolkit().beep();
            javax.swing.SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Este usuario ya fue registrado", "Salida registrada", JOptionPane.INFORMATION_MESSAGE)
            );
        }
        boolean ok  = yaE && !yaS && asistenciaDAO.registrarSalidaSiNoExiste(u.getId_Usuario(), ahora);
        if (ok) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                refrescarPantallas();
                if (jTableAsistencia.getRowCount() > 0) {
                    jTableAsistencia.clearSelection();
                    jTableAsistencia.setRowSelectionInterval(0, 0);
                }
            });
        }
    }
}


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
                var url = getClass().getResource("/imagenes/BackGround_principal.jpg");
                imagen = new ImageIcon(url).getImage();
            }
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    
   private void tablaIniciar() {
   
        jTableAsistencia.setModel(asistenciaModel); 
        var rend = new VistaAsistencia();
        jTableAsistencia.getColumnModel().getColumn(2).setCellRenderer(rend); 
        jTableAsistencia.getColumnModel().getColumn(3).setCellRenderer(rend); 
        jTableAsistencia.setRowHeight(28);
       
   }
   
   private void recargarHoy() {
        var lista = asistenciaDAO.listarResumenFecha(java.time.LocalDate.now(TZ));
        asistenciaModel.setData(lista);
   }
   
   private static class FechaItem {
       final java.time.LocalDate fecha;
       final String label;
       
       FechaItem(java.time.LocalDate f, String lbl){this.fecha = f; this.label = lbl;}
       @Override public String toString(){ return label; }
   }
   
   private void cargarFechas (){
       boxFechas.removeAllItems();
       
       var hoy = java.time.LocalDate.now(TZ);
       
       boxFechas.addItem(new FechaItem(hoy, "Hoy (" + DF.format(hoy) + ")"));
       
       for (var f : asistenciaDAO.listarFechasDisponibles(30)) {
           if (!f.equals(hoy)) {
               boxFechas.addItem(new FechaItem(f, DF.format(f)));
           }
       }
       boxFechas.setSelectedIndex(0);
   }
   
   private void hookComboFechas() {
    boxFechas.addActionListener(e -> {
        var item = (FechaItem) boxFechas.getSelectedItem();
        if (item == null) return;
        asistenciaModel.setData(asistenciaDAO.listarResumenFecha(item.fecha));
        if (jTableAsistencia.getRowCount() > 0) {
            jTableAsistencia.scrollRectToVisible(jTableAsistencia.getCellRect(0, 0, true));
        }
    });
}
   
   
private void activarLectorHID() {
    if (scannerDisp != null) return;

    scanTimeOut = new Timer(120, e -> finalizarLectura());
    scanTimeOut.setRepeats(false);

    scannerDisp = e -> {
        if (e.getID() != KeyEvent.KEY_PRESSED) return false;

        char ch = e.getKeyChar();

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            finalizarLectura();
            return true; 
        }

        if (Character.isISOControl(ch)) return false;

        scanBuf.append(ch);
        scanTimeOut.restart();
        return true; 
    };

    KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(scannerDisp);
}

private void desactivarLectorHID() {
    if (scannerDisp != null) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .removeKeyEventDispatcher(scannerDisp);
        scannerDisp = null;
    }
    if (scanTimeOut != null) { scanTimeOut.stop(); scanTimeOut = null; }
    scanBuf.setLength(0);
}

private void finalizarLectura() {
    if (scanTimeOut != null) scanTimeOut.stop();

    String raw = scanBuf.toString();
    scanBuf.setLength(0);

    String texto = raw.replace("\r", "").replace("\n", "").trim();
    if (texto.isEmpty()) return;

    if (modoActual == null) {
        System.out.println("[HID] Ignorado (sin modo activo): " + texto);
        return;
    }

    String rut = normalizarRut(texto);
    if (rut == null || !esRutValido(rut)) {
        JOptionPane.showMessageDialog(null, "Rut invalido", "error", JOptionPane.ERROR_MESSAGE);
        System.out.println("[HID] RUT inválido: " + texto);
        return;
    }

   // Aqui confirmo que lei el rut System.out.println("[HID] RUT leido: " + formatearRutBonito(rut));
    
    try {
    String cuerpo = rut.split("-")[0];     
    Usuario u = usuarioDAO.buscarPorRutFlexible(cuerpo);
    if (u == null || !u.isActivo()) {
        JOptionPane.showMessageDialog(null, "RUT no encontrado", "error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (modoActual == null) {
        JOptionPane.showMessageDialog(null, "Seleccione ENTRADA o SALIDA");
        return;
    }

    var tz    = java.time.ZoneId.of("America/Santiago");
    var ahora = java.time.LocalDateTime.now(tz);
    var hoy   = ahora.toLocalDate();

    switch (modoActual) {
        case ENTRADA -> {
            boolean yaE = asistenciaDAO.existeEntradaHoy(u.getId_Usuario(), hoy);
            if (yaE) {
                java.awt.Toolkit.getDefaultToolkit().beep();
                javax.swing.SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(VistaPrincipal.this, "Este usuario ya esta registrado", "Entrada registrada", JOptionPane.INFORMATION_MESSAGE)
                );
            }
            boolean ok  = !yaE && asistenciaDAO.registrarEntradaSiNoExiste(u.getId_Usuario(), ahora);
            if (ok) javax.swing.SwingUtilities.invokeLater(this::refrescarPantallas);
        }
        case SALIDA -> {
            boolean yaE = asistenciaDAO.existeEntradaHoy(u.getId_Usuario(), hoy);
            if(!yaE) {
                java.awt.Toolkit.getDefaultToolkit().beep();
                javax.swing.SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(VistaPrincipal.this, "No existe entrada para hoy", "Falta entrada", JOptionPane.WARNING_MESSAGE)
                );
            }
            boolean yaS = asistenciaDAO.existeSalidaHoy(u.getId_Usuario(), hoy);
            if(yaS) {
                java.awt.Toolkit.getDefaultToolkit().beep();
                javax.swing.SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(VistaPrincipal.this, "Este usuario ya esta registrado", "Salida registrada", JOptionPane.INFORMATION_MESSAGE)
                );
            }
            boolean ok  = yaE && !yaS && asistenciaDAO.registrarSalidaSiNoExiste(u.getId_Usuario(), ahora);
            if (ok) javax.swing.SwingUtilities.invokeLater(() -> {
                refrescarPantallas();
                if (jTableAsistencia.getRowCount() > 0) {
                    jTableAsistencia.clearSelection();
                    jTableAsistencia.setRowSelectionInterval(0, 0);
                }
            });
        }
    }
} catch (Exception ex) {
    logger.severe("[HID] Error al registrar asistencia: " + ex.getMessage());
}
}

//Rut

private static String normalizarRut(String s) {
    if (s == null) return null;
    String t = s.toUpperCase().replaceAll("[^0-9K]", "");
    if (t.length() < 2) return null; 

    String cuerpo = t.substring(0, t.length() - 1).replaceFirst("^0+(?!$)", "");
    String dv = t.substring(t.length() - 1);
    return cuerpo + "-" + dv; 
}


private static boolean esRutValido (String rutCanon) {
    if (rutCanon == null) return false;
    if (!rutCanon.matches("^\\d+-[0-9K]$")) return false; // <-- ¡el ! es clave!
    String[] p = rutCanon.split("-");
    return calcularDV(p[0]).equals(p[1]);
}


private static String calcularDV (String cuerpoNum) {
    
    int suma = 0, mul = 2;
    for (int i = cuerpoNum.length() - 1; i >= 0; i--) {
        int d = cuerpoNum.charAt(i) - '0';
        suma += d * mul;
        mul = (mul == 7) ? 2 : (mul + 1);
    }
    int resto = 11 - (suma % 11);
    if (resto == 11) return "0";
    if (resto == 10) return "K";
    return String.valueOf(resto);
}
   
private static String formatearRutBonito(String rutCanonico) {
    if (rutCanonico == null) return null;
    String[] p = rutCanonico.split("-");
    String num = p[0], dv = p[1];
    StringBuilder sb = new StringBuilder();
    int c = 0;
    for (int i = num.length() - 1; i >= 0; i--) {
        sb.append(num.charAt(i));
        c++;
        if (c == 3 && i > 0) { sb.append('.'); c = 0; }
    }
    return sb.reverse().append('-').append(dv).toString();
}
   
   
    
    
    public static void main(String args[]) {
        //java.awt.EventQueue.invokeLater(() -> inew VistaPrincipal().setVisible(true));
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JComboBox<FechaItem> boxFechas;
    private javax.swing.JButton btnActivarEntrada;
    private javax.swing.JButton btnActivarSalida;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnApagarCamara;
    private javax.swing.JButton btnMostrarUsuarios;
    private javax.swing.JButton btnPDF;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableAsistencia;
    private javax.swing.JPanel panelCamara;
    private javax.swing.JPanel verPanelUser;
    // End of variables declaration//GEN-END:variables
}
