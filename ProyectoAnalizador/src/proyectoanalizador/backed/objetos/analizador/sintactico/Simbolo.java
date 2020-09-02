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
public class Simbolo implements Serializable{
    private String tipo;
    private Object valor;
    private String id;
    
    public Simbolo(String tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public Simbolo(String tipo, Object valor, String id) {
        this.tipo = tipo;
        this.valor = valor;
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
