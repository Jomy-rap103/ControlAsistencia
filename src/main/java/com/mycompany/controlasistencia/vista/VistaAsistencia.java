package com.mycompany.controlasistencia.vista;

public class VistaAsistencia extends javax.swing.table.DefaultTableCellRenderer {
  @Override
  public java.awt.Component getTableCellRendererComponent(
      javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

    var c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

    if (!isSelected) c.setForeground(java.awt.Color.BLACK);

    var model = (VistaAsistenciaTabla) table.getModel();
    var item  = model.getAt(row);

    boolean rojo = (col==2 && item.entradaFuera()) || (col==3 && item.salidaFuera());
    if (!isSelected && rojo) c.setForeground(java.awt.Color.RED);

    return c;
  }
}


