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
public class TNT implements Serializable{
    
    private String  idEstado;
    private Shift shift;
    private Goto goTo;
    private Review review;
    private boolean aceptar;
    private boolean conflictos;
    
    public TNT(String idEstado, Shift shift) {
        this.idEstado = idEstado;
        this.shift = shift;
    }

    public TNT(String idEstado, Goto goTo) {
        this.idEstado = idEstado;
        this.goTo = goTo;
    }

    public TNT(String idEstado, Review review) {
        this.idEstado = idEstado;
        this.review = review;
    }

    public TNT(String idEstado, boolean aceptar) {
        this.idEstado = idEstado;
        this.aceptar = aceptar;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
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

    public boolean isConflictos() {
        if (review != null) {
            return review.isPresedencia();
        } else if (shift != null) {
            return review.isPresedencia();
        }
        return false;
    }

    public void setConflictos(boolean conflictos) {
        this.conflictos = conflictos;
    }
    
}
