/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import proyectoanalizador.gui.Frame;
import proyectoanalizador.NumeroLinea;

/**
 *
 * @author bryan
 */
public class Pestaña {
    private String nombre;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private NumeroLinea numeroLinea;
    private String path;
    private final Frame frame;
    
    public Pestaña(String nombre, JTextArea textArea, JScrollPane scrollPane, String path, Frame frame) {
        this.nombre = nombre;
        this.textArea = textArea;
        this.scrollPane = scrollPane;
        this.path = path;
        this.frame = frame;
        setNumeroLinea();
        addEscucha();
        addCaret();
    }

    @Override
    public Pestaña clone() throws CloneNotSupportedException{
        return new Pestaña(nombre, textArea, scrollPane, path, frame);
    }
    
    private void addEscucha(){
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                frame.setPestañaActual(nombre);
            }
        });
    }
    
    private void addCaret(){
        textArea.addCaretListener(new javax.swing.event.CaretListener() {
            @Override
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                JTextArea editArea = (javax.swing.JTextArea) evt.getSource();
                int linea = 1;
                int columna = 1;
                try {
                    int caretPos = editArea.getCaretPosition();
                    linea = editArea.getLineOfOffset(caretPos);
                    columna = caretPos - editArea.getLineStartOffset(linea);
                    linea += 1;
                    columna += 1;
                } catch (Exception e) {
                }
                frame.setLblCursor("Linea: " + linea + " - Columna: " + columna);
            }
        });
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    private void setNumeroLinea() {
        this.numeroLinea = new NumeroLinea(textArea);
        this.scrollPane.setRowHeaderView(numeroLinea);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
}
