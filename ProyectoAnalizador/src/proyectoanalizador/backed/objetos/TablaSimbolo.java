/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.analizador.sintactico.Simbolo;
import java.util.List;

/**
 *
 * @author bryan
 */
public class TablaSimbolo implements Serializable{
    private List<Simbolo> simbolos;
    private String id;
    
    public TablaSimbolo() {}

    public TablaSimbolo(List<Simbolo> simbolos, String id) {
        this.simbolos = simbolos;
        this.id = id;
    }

    public List<Simbolo> getSimbolos() {
        return simbolos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSimbolos(List<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }
    
    public void removerPorTipo(String removerTipo) {
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getTipo().equalsIgnoreCase(removerTipo)) {
                simbolos.remove(simbolo);
                break;
            }
        }
    }
    
    public void removerPorIdTipo(String tipo, String id){
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getId() != null && simbolo.getTipo().equals(tipo) && simbolo.getId().equals(id)) {
                simbolos.remove(simbolo);
                break;
            }
        }
    }
    
    public void removerPorId(String removerId) {
        simbolos.stream().filter((simbolo) -> (simbolo.getTipo().equalsIgnoreCase(removerId))).forEachOrdered((simbolo) -> {
            simbolos.remove(simbolo);
        });
    }
    
    public Object buscarPorTipo(String tipo) {
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getTipo().equalsIgnoreCase(tipo)) {
                return simbolo.getValor();
            }
        } return null;
    }
    
    public Object buscarPorId(String id) { 
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getId() != null && simbolo.getId().equalsIgnoreCase(id)) {
                return simbolo;
            }
        } return null;
    }
    
    public void agregarTablaTemp(Object object, String tipo){
        simbolos.add(new Simbolo(tipo, object));
    }
    
    public void agregarTabla(Object object, String tipo, String id){
        simbolos.add(new Simbolo(tipo, object, id));
    }
}
