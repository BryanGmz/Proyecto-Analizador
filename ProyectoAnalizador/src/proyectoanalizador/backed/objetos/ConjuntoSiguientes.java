/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bryan
 */
public class ConjuntoSiguientes implements Serializable{
    private char caracter;
    private int numeroConjunto;
    private final List<Integer> siguientes;
    private boolean aceptacion;
    private String id;
    
    public ConjuntoSiguientes(char caracter, int numeroConjunto, String id) {
        this.siguientes = new ArrayList<>();
        this.caracter = caracter;
        this.numeroConjunto = numeroConjunto;
        this.id = id;
    }

    public ConjuntoSiguientes(int numeroConjunto) {
        this.numeroConjunto = numeroConjunto;
        this.siguientes = new ArrayList<>();
        this.aceptacion = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getSiguientes() {
        return siguientes;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }

    public void addSiguiente(int siguiente){
        siguientes.add(siguiente);
    }
    
    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public int getNumeroConjunto() {
        return numeroConjunto;
    }

    public void setNumeroConjunto(int numeroConjunto) {
        this.numeroConjunto = numeroConjunto;
    }
    
    public String siguientes() {
        String retornar = "";
        retornar = siguientes.stream().map((siguiente) -> siguiente + ", ").reduce(retornar, String::concat);
        return retornar;
    }
}
