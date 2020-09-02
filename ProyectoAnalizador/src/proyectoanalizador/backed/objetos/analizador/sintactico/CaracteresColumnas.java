/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author bryan
 */
public class CaracteresColumnas implements Serializable{
    
    private char caracater;
    private int estado;
    private String id;
    private List<String> ids;
    
    public CaracteresColumnas(char caracater, int estado, String id) {
        this.caracater = caracater;
        this.estado = estado;
        this.id = id;
    }

    public CaracteresColumnas(char caracater, int estado, List<String> ids) {
        this.caracater = caracater;
        this.estado = estado;
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getCaracater() {
        return caracater;
    }

    public void setCaracater(char caracater) {
        this.caracater = caracater;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
