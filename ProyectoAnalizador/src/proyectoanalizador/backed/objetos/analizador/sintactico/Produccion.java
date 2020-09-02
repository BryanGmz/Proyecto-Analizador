/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;

/**
 *
 * @author bryan
 */
public class Produccion implements Serializable{
    
    private int idProduccion;
    private NoTerminal noTerminal;
    private Object produccion;
    private String primero;
    private int nivel;
    
    public Produccion(int idProduccion, NoTerminal noTerminal, Object produccion) {
        this.idProduccion = idProduccion;
        this.noTerminal = noTerminal;
        this.produccion = produccion;
    }

    public int getIdProduccion() {
        return idProduccion;
    }

    public void setIdProduccion(int idProduccion) {
        this.idProduccion = idProduccion;
    }

    public NoTerminal getNoTerminal() {
        return noTerminal;
    }

    public void setNoTerminal(NoTerminal noTerminal) {
        this.noTerminal = noTerminal;
    }

    public Object getProduccion() {
        return produccion;
    }

    public void setProduccion(Object produccion) {
        this.produccion = produccion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public String produccion(){
        Object p = produccion;
        String regresar =  "#" + idProduccion  + " - " + noTerminal.getId() + " -> ";
        while (p != null) {
            regresar += p.toString() + " ";
            if (p instanceof Terminal) {
                p = ((Terminal) p).getSiguiente();
            } else {
                p = ((NoTerminal) p).getSiguiente();
            }
        } return regresar;
    }
}
