/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.analizador.sintactico.DEstado;
import java.util.*;

/**
 *
 * @author bryan
 */
public class DiagramaAS implements Serializable{
 
    private final List<DEstado> estados;
//    private int contador;
    
    public DiagramaAS() {
        this.estados = new ArrayList<>();
//        this.contador = 0;
    }

    public List<DEstado> getEstados() {
        return estados;
    }
    
    public void addEstado(DEstado de){
//        de.setId(contador);
//        contador++;
        this.estados.add(de);
    }
}
