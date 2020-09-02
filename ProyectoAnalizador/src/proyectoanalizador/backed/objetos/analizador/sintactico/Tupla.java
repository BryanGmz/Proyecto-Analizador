/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author bryan
 */
public class Tupla implements Serializable{
    
    private final List<String> preanalisis;
    private final Produccion produccion;
    private int punto;
    private Shift shift;
    private Goto goTo;
    private Review review;
    
    public Tupla(Produccion produccion, int punto) {
        this.punto = punto;
        this.produccion = produccion;
        this.preanalisis = new ArrayList<>();
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Goto getGoTo() {
        return goTo;
    }

    public void setGoTo(Goto goTo) {
        this.goTo = goTo;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
    
    public int getPunto() {
        return punto;
    }

    public void setPunto(int punto) {
        this.punto = punto;
    }

    public List<String> getPreanalisis() {
        return preanalisis;
    }

    public Produccion getProduccion() {
        return produccion;
    }
    
    public void addPreanalisis(String s) {
        this.preanalisis.add(s);
    }
    
    public void copiarPreanalisis(List<String> lista){
        this.preanalisis.addAll(lista);
    }
    
    public void printGRS() {
        if (goTo != null) {
            System.out.print("\tGoto: " + goTo.getGoTo() + " Terminal: " + goTo.getIdProduccion());
        } else if (review != null) {
            System.out.print("\tReview - Produccion: " + review.getIdProduccion());
        } else if (shift != null) {
            System.out.print("\tShift: " + shift.getShift()+ " NoTerminal: " + shift.getIdTerminal());
        } System.out.println("\tPunto: " + punto);
    }
}
