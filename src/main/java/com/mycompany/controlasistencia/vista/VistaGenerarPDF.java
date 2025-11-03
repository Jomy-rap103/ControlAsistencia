
package com.mycompany.controlasistencia.vista;

import com.mycompany.controlasistencia.modelo.Usuario;
import java.awt.print.PrinterJob;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;


public class VistaGenerarPDF extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaGenerarPDF.class.getName());
    private final java.time.ZoneId TZ = java.time.ZoneId.of("America/Santiago");
    private final java.time.format.DateTimeFormatter TS = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
     private final com.mycompany.controlasistencia.dao.AsistenciaDAO asistenciaDAO = new com.mycompany.controlasistencia.dao.AsistenciaDAO();
    
    
    private Usuario usuario;
    private int userId;
    private String nombre;
    private String apellido;
    
    public VistaGenerarPDF(Usuario usuarioSesion, int userId, String nombre, String apellido) {
    initComponents();
    this.usuario = usuarioSesion;
    this.userId = userId;
    this.nombre = nombre;
    this.apellido = apellido;
    setTitle("Reporte - " + nombre + ", " + apellido);
    lblTitulo.setText("Reporte para: " + nombre + " " + apellido);
    agregarAutorActual();
    }
    
    private void agregarAutorActual() {
        String quien = autorActual();
        String cuando = TS.format(java.time.LocalDateTime.now(TZ));
        lblAutor.setText("Generado por: " + quien + " " + cuando);
    }
    
    private String autorActual() {
        
    String n = usuario != null && usuario.getNombre() != null ? usuario.getNombre() : "";
    String a = usuario != null && usuario.getApellido() != null ? usuario.getApellido() : "";
    String full = (n + " " + a).trim();
    return full.isEmpty() ? "Usuario desconocido" : full;
    } 
    
    private static String safe(String s) { return s == null ? "" : s; }
    
    private void agregarTexto(String texto) {
        textPersonalizado.setText(texto);
        textPersonalizado.setCaretPosition(0);
    }
    
    private String firma() {
        
        return "\n— " + autorActual() + " — " + TS.format(java.time.LocalDateTime.now(TZ));
    
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnPDF = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPersonalizado = new javax.swing.JTextArea();
        lblAutor = new javax.swing.JLabel();
        btnDia = new javax.swing.JButton();
        btnMes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Generar reporte");

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        btnPDF.setText("Imprimir / PDF");
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblTitulo.setText("jLabel2");

        textPersonalizado.setEditable(false);
        textPersonalizado.setColumns(20);
        textPersonalizado.setRows(5);
        textPersonalizado.setWrapStyleWord(true);
        textPersonalizado.setBorder(null);
        textPersonalizado.setOpaque(false);
        jScrollPane1.setViewportView(textPersonalizado);

        lblAutor.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAutor)
                .addGap(62, 62, 62))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblAutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPDF)
                    .addComponent(btnRegresar))
                .addContainerGap())
        );

        btnDia.setText("Dia");
        btnDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiaActionPerformed(evt);
            }
        });

        btnMes.setText("Mes");
        btnMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addComponent(btnDia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addComponent(btnMes)
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnDia)
                    .addComponent(btnMes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        VistaPrincipal vistaP = new VistaPrincipal(usuario);
        vistaP.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiaActionPerformed
        var hoy = java.time.LocalDate.now(TZ);
        var reporte = asistenciaDAO.generarReporteDia(userId, hoy);
        
        var fmtH = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
        //entrada programada = eProg | entrada real = eReal
        String eProg = reporte.entradaProg() == null ? "-" : fmtH.format(reporte.entradaProg());
        String eReal = reporte.entradaReal() == null ? "-" : fmtH.format(reporte.entradaReal().toLocalTime());
        //Salida programada = sProg | salida real = eReal
        String sProg = reporte.salidaProg() == null ? "-" : fmtH.format(reporte.salidaProg());
        String sReal = reporte.salidaReal() == null ? "-" : fmtH.format(reporte.salidaReal().toLocalTime()); 
        // Aqui van los datos del DAO
        
        String mensaje = """
                         [ Reporte diario ]
                         
                         Persona: %s %s 
                         Fecha: %s
                         
                         Motivo: %s
                         
                         Entrada programada: %s | Registrada: %s | Tardanza: %d min
                         Salida programada : %s | Registrada: %s | Anticipo: %d min
                         """.formatted(nombre, apellido, hoy.toString(), reporte.motivo(), 
                                        eProg, eReal, reporte.minutosTarde(),
                                        sProg, sReal, reporte.minutosAnticipo()
                                        ) + firma();
        agregarTexto(mensaje);
        
        
    }//GEN-LAST:event_btnDiaActionPerformed

    private void btnMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesActionPerformed
        var mes = java.time.YearMonth.now(TZ);
        var reporte = asistenciaDAO.generarReporteMes(userId, mes);

        String mensaje = """
                         [ Reporte mensual ]
                         
                         Persona: %s %s
                         Periodo: %s
                         
                         Entradas acumuladas: %S min
                         Salidas acumuladas: %s min
                         """.formatted(nombre, apellido, mes, 
                                 reporte.minutosTardeTotal(), reporte.minutosAnticipoTotal()
                                 ) + firma();
        
        agregarTexto(mensaje);
    }//GEN-LAST:event_btnMesActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        imprimirPanelCompleto();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void imprimirPanelCompleto() {
     try {
        agregarAutorActual();

        boolean v1 = btnPDF.isVisible();
        boolean v2 = btnRegresar.isVisible();
        btnPDF.setVisible(false);
        btnRegresar.setVisible(false);
        jPanel2.revalidate();
        jPanel2.repaint();

        java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
        job.setPrintable((g, pf, pageIndex) -> {
            if (pageIndex > 0) return java.awt.print.Printable.NO_SUCH_PAGE;
            var g2 = (java.awt.Graphics2D) g;
            g2.translate(pf.getImageableX(), pf.getImageableY());

            double sw = pf.getImageableWidth()  / jPanel2.getWidth();
            double sh = pf.getImageableHeight() / jPanel2.getHeight();
            double s  = Math.min(sw, sh);
            if (s < 1.0) g2.scale(s, s);

            jPanel2.printAll(g2);
            return java.awt.print.Printable.PAGE_EXISTS;
        });

        if (job.printDialog()) job.print();

        
        btnPDF.setVisible(v1);
        btnRegresar.setVisible(v2);
        jPanel2.revalidate();
        jPanel2.repaint();

    } catch (java.awt.print.PrinterException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "No se pudo imprimir:\n" + ex.getMessage(),
            "Error de impresión", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
    
    public static void main(String args[]) {

       // java.awt.EventQueue.invokeLater(() -> new VistaGenerarPDF().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDia;
    private javax.swing.JButton btnMes;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextArea textPersonalizado;
    // End of variables declaration//GEN-END:variables
}
