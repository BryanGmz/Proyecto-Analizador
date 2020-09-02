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
public class Estado implements Serializable{
    
    private int idEstado;
    private final List<TNT> estados;
    private final List<Integer> estadosLALR;
    private String name;
    private List<Integer> uniones;
    
    public Estado(int idEstado) {
        this.idEstado = idEstado;
        this.estados = new ArrayList<>();
        this.estadosLALR = new ArrayList<>();
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    
    public List<Integer> getEstadosLALR() {
        return estadosLALR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addEstado(TNT d){
        estados.add(d);
    }

    public int getIdEstado() {
        return idEstado;
    }

    public List<TNT> getEstados() {
        return estados;
    }
}
