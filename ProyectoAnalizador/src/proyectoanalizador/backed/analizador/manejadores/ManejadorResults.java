/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.analizador.manejadores;

import java.util.List;
import java.util.Stack;
import proyectoanalizador.backed.objetos.analizador.lexico.Token;
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
    private Stack<Token> tokenActualesTerminales;
    private Stack<Token> tokenActualesNoTerminales;
    
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
        this.tokenActualesNoTerminales = new Stack<>();
        this.tokenActualesTerminales = new Stack<>();
    }
    
    //Agrega lso token terminales a una pila para cuando se realiza un reduce lo despila
    public void addPilatokenTerminales(Token token){
        this.tokenActualesTerminales.push(token);
    }
    
    public void addPilatokenNoTerminales(Token token){
        this.tokenActualesNoTerminales.push(token);
    }
    
    public NoTerminal regresaNoTerminal(String id){
        for (NoTerminal tnt : listaNoTeminales) {
            if (tnt.getId().equals(id)) {
                return tnt;
            }
        } return null;
    }
    
    public Terminal regresarTerminal(String id){
        for (Terminal tnt : listaTeminales) {
            if (tnt.getId().equals(id)) {
                return tnt;
            }
        } return null;
    }
    
    public String generarCodigo(List<Object> listaTNT, String actionCode, String reglaSemantica){
        String codigoSalida = 
                "public class Reduce {";
        codigoSalida += "\n" + actionCode;
        codigoSalida += "\n\n\tpublic Object metodoAcciones() {";
        codigoSalida += "\n\t\tObject RESULT = null;";
        for (Object o : listaTNT) {
            if (o != null) {
                if (o instanceof Terminal) {
                    Terminal t = regresarTerminal(o.toString());
                    if (tokenActualesTerminales.empty()) {
                        System.out.println("Terminal " + t.getId() + " Valor: " + t.getValorDevuelto() +  " ");
                        codigoSalida += "\n\t\t" + getTipo(t.getTipo()) + " " + t.getValorDevuelto() + ";";
                    } else {
                        System.out.println("Terminal " + t.getId() + " Valor: " + t.getValorDevuelto() +  " - " + tokenActualesTerminales.pop());
                        codigoSalida += "\n\t\t" + getTipo(t.getTipo()) + " " + t.getValorDevuelto() + " = ("  + getTipo(t.getTipo())+ ")" + tokenActualesTerminales.pop() + ";";
                    }
                } else {
                    if (((NoTerminal) o ).isAceptacion() || ((NoTerminal) o).isLambda()) {
                        
                    } else {
                        NoTerminal nt = regresaNoTerminal(o.toString());
                        if (tokenActualesNoTerminales.empty()) {
                            System.out.println("NoTerminal " + nt.getId() + " Valor: " + nt.getValorDevuelto() + " - ");
                            codigoSalida += "\n\t\t" + getTipo(nt.getTipo()) + " " + nt.getValorDevuelto() + ";";
                        } else {
                            System.out.println("NoTerminal " + nt.getId() + " Valor: " + nt.getValorDevuelto() + " - " + tokenActualesNoTerminales.pop());
                            codigoSalida += "\n\t\t" + getTipo(nt.getTipo()) + " " + nt.getValorDevuelto() + " = ("  + getTipo(nt.getTipo())+ ")" + tokenActualesNoTerminales.pop() + ";";
                        }
                    }
                }
            }
        }
        codigoSalida += "\n" + reglaSemantica;
        codigoSalida += "\n\t\treturn = RESULT;";
        codigoSalida += "\n\t}";
        codigoSalida += "\n}";
        return codigoSalida;
    }
    
    
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