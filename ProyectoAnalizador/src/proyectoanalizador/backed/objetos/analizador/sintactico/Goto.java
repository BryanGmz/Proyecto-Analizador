/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bryan
 */
public class Goto implements Serializable{
    
    private final Produccion produccion;
    private int goTo;
    private final String idProduccion;
    private List<Integer> goTos;
    
    public Goto(Produccion produccionGoto, int goTo, String idProduccion) {
        this.produccion = produccionGoto;
        this.goTo = goTo;
        this.idProduccion = idProduccion;
        this.goTos = new ArrayList<>();
    }

    public List<Integer> getGoTos() {
        return goTos;
    }
    
    public Produccion getProduccion() {
        return produccion;
    }

    public void setGoTo(int goTo) {
        this.goTo = goTo;
    }

    public int getGoTo() {
        return goTo;
    }

    public String getIdProduccion() {
        return idProduccion;
    }
    
    
    
}
