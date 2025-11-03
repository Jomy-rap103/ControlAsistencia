package com.mycompany.controlasistencia.vista;

public class VistaAsistenciaTabla extends javax.swing.table.AbstractTableModel {
  private final java.util.List<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia> data = new java.util.ArrayList<>();
  private final String[] cols = {"Nombre","Apellido","Entrada","Salida"};
  private final java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

  public void setData(java.util.List<com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia> nueva){
    data.clear(); if(nueva!=null) data.addAll(nueva); fireTableDataChanged();
  }
  public com.mycompany.controlasistencia.modelo.Asistencia.AgregarAsistencia getAt(int row){ return data.get(row); }

  @Override public int getRowCount(){ return data.size(); }
  @Override public int getColumnCount(){ return cols.length; }
  @Override public String getColumnName(int c){ return cols[c]; }
  @Override public boolean isCellEditable(int r,int c){ return false; }
  @Override public Class<?> getColumnClass(int c){ return String.class; }

  @Override public Object getValueAt(int r,int c){
    var a = data.get(r);
    return switch(c){
      case 0 -> a.nombre();
      case 1 -> a.apellido();
      case 2 -> a.entrada()==null? "" : fmt.format(a.entrada());
      case 3 -> a.salida()==null? "" : fmt.format(a.salida());
      default -> "";
    };
  }
}

  

