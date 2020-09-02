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
public class Review implements Serializable{
    
    private final Produccion produccionReview;
    private final int idProduccion;
    private int nivel;
    private boolean presedencia;
    
    public Review(Produccion produccionReview, int idProduccion) {
        this.produccionReview = produccionReview;
        this.idProduccion = idProduccion;
        addlvl();
    }

    public Review(Produccion produccionReview, int idProduccion, boolean presedencia) {
        this.produccionReview = produccionReview;
        this.idProduccion = idProduccion;
        this.presedencia = presedencia;
        addlvl();
    }

    public boolean isPresedencia() {
        return presedencia;
    }

    public void setPresedencia(boolean presedencia) {
        this.presedencia = presedencia;
    }
    
    public final void addlvl(){
        this.nivel = produccionReview.getNivel();
    }
    
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public Produccion getProduccionReview() {
        return produccionReview;
    }

    public int getIdProduccion() {
        return idProduccion;
    }
    
    public boolean compararNiveles(int a) {
        return (a < nivel); //Mi nivel es mas alto en la precedencia
    }
}
