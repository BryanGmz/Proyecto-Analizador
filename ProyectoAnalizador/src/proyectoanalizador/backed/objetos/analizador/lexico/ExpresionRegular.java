/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.lexico;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.TablaSiguientes;

/**
 *
 * @author bryan
 */
public class ExpresionRegular implements Serializable{
    
    private String id;
    private Arbol arbol;
    private TablaSiguientes tablaSiguientes;
    private TablaTransiciones tablaTransiciones;
    private boolean ignorar;
    
    public ExpresionRegular(String id, Arbol arbol, TablaSiguientes tablaSiguientes, TablaTransiciones tablaTransiciones) {
        this.id = id;
        this.arbol = arbol;
        this.tablaSiguientes = tablaSiguientes;
        this.tablaTransiciones = tablaTransiciones;
    }

    public boolean isIgnorar() {
        return ignorar;
    }

    public void setIgnorar(boolean ignorar) {
        this.ignorar = ignorar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    public TablaSiguientes getTablaSiguientes() {
        return tablaSiguientes;
    }

    public void setTablaSiguientes(TablaSiguientes tablaSiguientes) {
        this.tablaSiguientes = tablaSiguientes;
    }

    public TablaTransiciones getTablaTransiciones() {
        return tablaTransiciones;
    }

    public void setTablaTransiciones(TablaTransiciones tablaTransiciones) {
        this.tablaTransiciones = tablaTransiciones;
    }
}
