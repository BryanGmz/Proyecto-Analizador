/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.analizador.manejadores;

import java.util.List;
import proyectoanalizador.backed.objetos.analizador.sintactico.NoTerminal;
import proyectoanalizador.backed.objetos.analizador.sintactico.Produccion;
import proyectoanalizador.backed.objetos.analizador.sintactico.Terminal;

/**
 *
 * @author bryangmz
 */
public class ManejadorResults {
    public static final String ENTERO = "Integer";
    public static final String CADENA = "String";
    public static final String REAL = "Double";
    public static final String OBJECT = "Object";
    public static final String RESULT = "Object RESULT = null;";
    public static final String CORCHETE_A = "{";
    public static final String CORCHETE_C = "}";
    private static ManejadorResults mr;
    private List<Terminal> listaTeminales;
    private List<NoTerminal> listaNoTeminales;
    private List<Produccion> producciones;
    private Object aux;
    
    private ManejadorResults(){} 
    
    public static ManejadorResults getInstancia(){
        if (mr == null) {
            mr = new ManejadorResults();
        } return mr;
    }
    
    public void constructor(List<Terminal> listaTerminales, List<NoTerminal> listaNoTerminales, List<Produccion> producciones){
        this.listaNoTeminales = listaNoTerminales;
        this.listaTeminales = listaTerminales;
        this.producciones = producciones;
    }
//    
//    public String returnClase(String actionCode){
//        
//    }
    
    
    public String getTipo(String id){
        for (Terminal lt : listaTeminales) {
            if (lt.getId().equals(id)) {
                switch (lt.getTipo()) {
                    case "entero":
                        return ENTERO;
                    case "real":
                        return REAL;
                    case "cadena":
                        return CADENA;
                    default:
                        return OBJECT;
                }
            }
        }
        for (NoTerminal lnt : listaNoTeminales) {
            if (lnt.getId().equals(id)) {
                switch (lnt.getTipo()) {
                    case "entero":
                        return ENTERO;
                    case "real":
                        return REAL;
                    case "cadena":
                        return CADENA;
                    default:
                        return OBJECT;
                }
            }
        } 
        return OBJECT;
    }
    
//    public String getMetodo(Produccion produccion){
//        String codigo = "";
//        
//    }
    
}