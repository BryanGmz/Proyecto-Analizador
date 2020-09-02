/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.analizador.lexico.AnalizadorLexico;
import proyectoanalizador.backed.objetos.analizador.lexico.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author bryan
 */
public class Pila implements Serializable{
     
    private final AnalizadorLexico analizadorLexico;
    private final Stack<Integer> pilaEstados;
    private final Stack<String> pilaSimbolos;
    private final List<Estado> listaEstado;
    private final List<String> registroEstados;
    private final List<String> registroSimbolos;
    private final List<String> registroAcciones;
    private String produccionInical;
    private int conflicto;
    private List<Terminal> listaTerminales;
    private List<NoTerminal> listaNoTerminales;
    private String actionCode;
    
    public Pila(AnalizadorLexico analizadorLexico, List<Estado> estado) {
        this.analizadorLexico = analizadorLexico;
        this.pilaEstados = new Stack<>();
        this.pilaSimbolos = new Stack<>();
        this.registroAcciones = new ArrayList<>();
        this.registroEstados = new ArrayList<>();
        this.registroSimbolos = new ArrayList<>();
        this.listaEstado = estado;
        this.conflicto = -1;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public List<String> getRegistroEstados() {
        return registroEstados;
    }

    public List<String> getRegistroSimbolos() {
        return registroSimbolos;
    }

    public List<String> getRegistroAcciones() {
        return registroAcciones;
    }

    public void setProduccionInical(String produccionInical) {
        this.produccionInical = produccionInical;
    }

    public List<Terminal> getListaTerminales() {
        return listaTerminales;
    }

    public void setListaTerminales(List<Terminal> listaTerminales) {
        this.listaTerminales = listaTerminales;
    }

    public List<NoTerminal> getListaNoTerminales() {
        return listaNoTerminales;
    }

    public void setListaNoTerminales(List<NoTerminal> listaNoTerminales) {
        this.listaNoTerminales = listaNoTerminales;
    }
    
    public void pilas(){
        registroAcciones.clear();
        registroEstados.clear();
        registroSimbolos.clear();
        pilaEstados.clear();
        pilaSimbolos.clear();
        int estadoActual = 1;
        boolean fin = true;
        pilaEstados.push(estadoActual);
        Token enTurno = analizadorLexico.getToken();
        TNT gtr = retornarInterseccion (getEstado(estadoActual), enTurno);
        registroEstados.add(getPilaEstados());
        registroSimbolos.add(getPilaSimbolos());
        while(fin){
            if(gtr == null && enTurno.getId().equals("$")) {
                fin = false;
                break;
            }
            if (gtr != null) {
                if(gtr.getShift() != null) {
                    pilaEstados.push(gtr.getShift().getShift());
                    pilaSimbolos.push(gtr.getIdEstado());
                    registroEstados.add(getPilaEstados());
                    System.out.println(getPilaEstados());
                    registroSimbolos.add(getPilaSimbolos());
                    registroAcciones.add("Shift ( " + estadoActual + ", " + enTurno.getId() + ")");
                    enTurno = analizadorLexico.getToken();
                    estadoActual = pilaEstados.peek();
                    gtr = retornarInterseccion(getEstado(pilaEstados.peek()), enTurno);
                } else if(gtr.getGoTo() != null) {
                    pilaEstados.push(gtr.getGoTo().getGoTo());
                    registroEstados.add(getPilaEstados());
                    System.out.println(getPilaEstados());
                    registroSimbolos.add(getPilaSimbolos());
                    registroAcciones.add("Goto ( " + estadoActual + ", " + pilaSimbolos.peek() + ")");
//                    System.out.println("Goto ( " + estadoActual + ", " + pilaSimbolos.peek() + ")");
                    estadoActual = pilaEstados.peek();
                    gtr = retornarInterseccion(getEstado(pilaEstados.peek()), enTurno);
                } else if(gtr.getReview() != null) {
                    if (gtr.getReview().getProduccionReview().getNoTerminal().isLambda() && gtr.getReview().getProduccionReview().getProduccion() instanceof NoTerminal && 
                            ((NoTerminal) gtr.getReview().getProduccionReview().getProduccion()).isLambda()) {
//                        pilaSimbolos.pop();
                        pilaSimbolos.push(gtr.getReview().getProduccionReview().getNoTerminal().getId());
                        registroEstados.add(getPilaEstados());
                        registroSimbolos.add(getPilaSimbolos());
                        registroAcciones.add("Review ( " + estadoActual + ", " + enTurno.getId() + ") " + gtr.getReview().getProduccionReview().produccion());
                    } else {
                        review(gtr);
                        registroEstados.add(getPilaEstados());
                        registroSimbolos.add(getPilaSimbolos());
                        registroAcciones.add("Review ( " + estadoActual + ", " + enTurno.getId() + ") " + gtr.getReview().getProduccionReview().produccion());
                    }
                        estadoActual = pilaEstados.peek();
                        gtr = retornarInterseccion(getEstado(pilaEstados.peek()), new Token(pilaSimbolos.peek(), ""));
                } else if(gtr.isAceptar()) {
                    registroEstados.add(getPilaEstados());
                    registroSimbolos.add(getPilaSimbolos());
                    registroAcciones.add("Aceptado");
                    fin = false;
                    pilaEstados.removeAllElements();
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        if(enTurno.getId().equals("$") && pilaEstados.empty()){
            System.out.println("Aceptado");
     
        } else {
            System.out.println("No aceptado");
        }
        print();
    }
    private void review(TNT tnt) {
        Object p = tnt.getReview().getProduccionReview().getProduccion();
        Stack<String> pR = new Stack<>();
        while(p != null){
            pR.push(p.toString());
            if (p instanceof Terminal) {
                p = ((Terminal) p).getSiguiente();
            } else {
                p = ((NoTerminal) p).getSiguiente();   
            }
        }
        while(!pR.isEmpty() && !pilaSimbolos.isEmpty()) {
            pilaSimbolos.pop();
            pR.pop();
            pilaEstados.pop();
        }
        pilaSimbolos.push(tnt.getReview().getProduccionReview().getNoTerminal().getId());
    }

    private TNT retornarInterseccion(Estado estado, Token token){
        for(TNT tnt : estado.getEstados()){
            if(token == null) {
                if (tnt.getIdEstado().equals("$")) {
                    return tnt;
                }
            } else {
                if (tnt.getIdEstado().equals(token.getId())) {
                    return tnt;
                }
            }
        } return null;
    }

    private String getPilaSimbolos(){
        String aux = "";
        for(int i = 0; i < pilaSimbolos.size(); i++){
            aux += pilaSimbolos.get(i) + " ";
        } return aux;
    }

    private String getPilaEstados(){
        String aux = "";
        for(int i = 0; i < pilaEstados.size(); i++){
            aux += Integer.toString(pilaEstados.get(i)) + " ";
        } return aux;
    }
    
    private Estado getEstado(int id){
        for(Estado e : listaEstado){
            if(e.getIdEstado() == id) {
                return e;
            }
        } return null;
    }

    private void print() {
        int mayor = registroEstados.size();
        if(registroSimbolos.size() > mayor){
            mayor = registroSimbolos.size();
            if(mayor < registroAcciones.size()) {
                mayor = registroAcciones.size();
            }
        } else {
            if(mayor < registroAcciones.size()) {
                mayor = registroAcciones.size();
            }
        }
        System.out.print("Estados\t\t\t\t");
        System.out.print("Simbolo\t\t\t\t");
        System.out.print("Acción\t\t\t\t\n");
        for(int i = 0;  i < mayor; i++) {
            if (i < registroEstados.size()){
                System.out.print(registroEstados.get(i) + "\t\t\t\t");
            }
            if (i < registroSimbolos.size()){
                System.out.print(registroSimbolos.get(i) + "\t\t\t\t");
            }
            if (i < registroAcciones.size()){
                System.out.print(registroAcciones.get(i) + "\t\t\t\t");
            }
            System.out.println("");
        }
    }
}
