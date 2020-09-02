/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author bryan
 */
public class TablaSiguientes implements Serializable{
    
    private final List<ConjuntoSiguientes> conjuntoSiguientes;

    public TablaSiguientes() {
        conjuntoSiguientes = new ArrayList<>();
    }
    
    public void addConjuntoSiguiente(ConjuntoSiguientes cs){
        conjuntoSiguientes.add(cs);
    }
    
    public ConjuntoSiguientes retornarCS(int nConjunto){
        for (ConjuntoSiguientes cs : conjuntoSiguientes) {
            if (cs.getNumeroConjunto() == nConjunto) {
                return cs;
            }
        } return null;
    }

    public List<ConjuntoSiguientes> getConjuntoSiguientes() {
        return conjuntoSiguientes;
    }
    
    public void imprimirTabla(){
        System.out.println("C   - # - Siguientes");
        conjuntoSiguientes.forEach((cs) -> {
            System.out.println(cs.getCaracter() + " - " + cs.getNumeroConjunto() + " - " + cs.getId() + "     " + cs.siguientes());
        });
    }
}
