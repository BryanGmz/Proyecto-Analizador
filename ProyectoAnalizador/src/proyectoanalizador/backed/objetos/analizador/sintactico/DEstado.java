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
public class DEstado implements Serializable{
    
    private int id;
    private String idEstado;
    private final List<Tupla> listaTuplasIniciales;
    private boolean noAdd;
    private String idPadreAnt;
    //Ir_A
    private final List<Tupla> listaTuplas;
    
    public DEstado() {
        this.listaTuplas = new ArrayList<>();
        this.listaTuplasIniciales = new ArrayList<>();
    }

    public String getIdPadreAnt() {
        return idPadreAnt;
    }

    public void setIdPadreAnt(String idPadreAnt) {
        this.idPadreAnt = idPadreAnt;
    }

    public boolean isNoAdd() {
        return noAdd;
    }

    public void setNoAdd(boolean noAdd) {
        this.noAdd = noAdd;
    }
    
    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }
    
    public List<Tupla> getListaTuplasIniciales() {
        return listaTuplasIniciales;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tupla> getListaTuplas() {
        return listaTuplas;
    }
    
    public void addTupla(Tupla t){
        this.listaTuplas.add(t);
    }
    
    public void addTuplaInicial(Tupla t){
        this.listaTuplasIniciales.add(t);
    }
    
    public void addPreanalisis(Produccion p, String preA){
        listaTuplas.stream().filter((lt) -> (lt.getProduccion().equals(p) && !lt.getPreanalisis().contains(preA))).forEachOrdered((lt) -> {
            lt.addPreanalisis(preA);
        });
    }
    
    public Tupla getTupla(Produccion produccion) {
        for (Tupla lt : listaTuplasIniciales) {
            if (lt.getProduccion().equals(produccion)) {
                return lt;
            }
        } return null;
    }
}
