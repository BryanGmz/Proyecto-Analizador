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
public class NoTerminal implements Serializable{
    
    private String id;
    private boolean aceptacion;
    private String valorDevuelto;
    private String reglaSemantica;
    private boolean lambda;
    private List<String> primeros;
    private String tipo;
    
    //Cuando es una produccion
    private List<Object>  producciones;
    private List<String> simboloPreAnalisis;
    
    //Cuando no es una produccion        
    private Object siguiente;
    private Object anterior;

    public NoTerminal(String id, boolean produccion) {
        this.id = id;
        this.primeros = new ArrayList<>();
        if (produccion) {
            this.producciones = new ArrayList<>();
            this.simboloPreAnalisis = new ArrayList<>();
        }
    }

    public NoTerminal(boolean lambda) {
        this.lambda = lambda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public boolean isLambda() {
        return lambda;
    }

    public void setLambda(boolean lambda) {
        this.lambda = lambda;
    }

    public String getReglaSemantica() {
        return reglaSemantica;
    }

    public void setReglaSemantica(String reglaSemantica) {
        this.reglaSemantica = reglaSemantica;
    }
    
    public String getValorDevuelto() {
        return valorDevuelto;
    }

    public void setValorDevuelto(String valorDevuelto) {
        this.valorDevuelto = valorDevuelto;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
        this.primeros.add("$");
    }

    public List<String> getSimboloPreAnalisis() {
        return simboloPreAnalisis;
    }

    public void addSimboloPreAnalisis(String caracterSig) {
        this.simboloPreAnalisis.add(caracterSig);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Object> getProducciones() {
        return producciones;
    }

    public void addProduccion(Object produccion) {
        this.producciones.add(produccion);
    }

    public Object getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Object siguiente) {
        this.siguiente = siguiente;
    }

    public Object getAnterior() {
        return anterior;
    }

    public void setAnterior(Object anterior) {
        this.anterior = anterior;
    }
    
    public void primero(){
        if (producciones == null) {//Quiere decir que no es una sola produccion
            
        } 
    }

    public List<String> getPrimeros() {
        return primeros;
    }
    
    public void addPrimero(String add){
        this.primeros.add(add);
    }
    
    @Override
    public String toString(){
        if (lambda) {
            return "";
        }
        return id;
    }
}
