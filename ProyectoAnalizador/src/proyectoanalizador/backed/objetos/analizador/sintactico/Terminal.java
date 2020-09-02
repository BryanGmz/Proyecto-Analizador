/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.analizador.lexico.Token;

/**
 *
 * @author bryan
 */
public class Terminal implements Serializable{
    
    private final String id;
    private Object siguiente;
    private Object anterior;
    private final String primero;
    private String valorDevuelto;
    private Token token;
    private int nivel;
    private String tipo;
    
    public Terminal(String id) {
        this.id = id;
        this.primero = id;
    }

    public Terminal(String id, int nivel) {
        this.id = id;
        this.nivel = nivel;
        this.primero = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getId() {
        return id;
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

    public String getPrimero() {
        return primero;
    }

    public String getValorDevuelto() {
        return valorDevuelto;
    }

    public void setValorDevuelto(String valorDevuelto) {
        this.valorDevuelto = valorDevuelto;
    }
    
    @Override
    public String toString(){
        return id;
    }
}
