/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.lexico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import proyectoanalizador.backed.objetos.analizador.sintactico.CaracteresColumnas;
import proyectoanalizador.backed.objetos.analizador.sintactico.EstadosFilas;

/**
 *
 * @author bryan
 */
public class AnalizadorLexico implements Serializable {
    
    private TablaTransiciones tt;
    private String entrada;
    private String auxEntrada = "";
    private final List<Token> lista;
    private final Stack<Token> pila;
    private final List<Character> listaAux;
    
    public AnalizadorLexico(TablaTransiciones tt) {
        this.lista = new ArrayList<>();
        this.listaAux = new ArrayList<>();
        this.pila = new Stack<>();
        this.tt = tt;
    }

    //inicar el anlizador lexico
    public void iniciarAnalizadorLexico(String entrada){
        this.entrada = entrada;
        init();
    }
    
    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }
    
    public void init(){
        char[] caracteres = entrada.toCharArray();
        for (char caractere : caracteres) {
            listaAux.add(caractere);
        }
        while (!listaAux.isEmpty()) { 
            Token tokenAux =  comprobarEstado(listaAux, 0);
            if (tokenAux != null) {
                if (!tokenAux.getId().equalsIgnoreCase("&")) {
                    lista.add(tokenAux);
                }
                auxEntrada = "";
                pila.clear();
            } else {
                reconstruirToken();
                auxEntrada = "";
            } 
        }
    }
    
    public String getEntrada() {
        return entrada;
    }
    
    public Token comprobarEstado(List<Character> caracter, int estadoSig){
        int estadoAux = 0;
        String comodin = "";
        CaracteresColumnas estadoAuxC = null;
        Token result = null;
        boolean perdido = false;
        while (!caracter.isEmpty()) {
            char charAux = caracter.remove(0);
            estadoAuxC = comprobarTabla(tt.getListaEstadosT().get(estadoAux), estadoAux, charAux);
            if (estadoAuxC == null) {
                if (!pila.empty()) {
                    caracter.add(0, charAux);
                    if (perdido) {
                        caracter.add(0, auxEntrada.charAt(auxEntrada.length() - 1));
                    }
                } else {
                    return new Token("ERROR", Character.toString(charAux));
                }
                return null;
            } else if (tt.getListaEstadosT().get(estadoAuxC.getEstado()).isAceptacion()) {
                perdido = false;
                if (!caracter.isEmpty()) {
                    char auxSig = caracter.get(0);
                    CaracteresColumnas eac = comprobarTabla(tt.getListaEstadosT().get(estadoAux), estadoAux, auxSig);
                    if (eac == null) {
                        if (estadoAuxC.getId() == null) {
                            return (new Token(estadoAuxC.getIds().get(0), auxEntrada + Character.toString(charAux)));
                        } else {
                            return (new Token(estadoAuxC.getId(), auxEntrada + Character.toString(charAux)));
                        }  
                    } else {
                        if (pila.empty()) {
                            if (estadoAuxC.getId() == null) {
                                pila.push(new Token(estadoAuxC.getIds().get(0), Character.toString(charAux)));
                                comodin = estadoAuxC.getIds().get(0);
                            } else {
                                pila.push(new Token(estadoAuxC.getId(), Character.toString(charAux)));
                                comodin = estadoAuxC.getId();
                            }
                        } else {
                            if (estadoAuxC.getId() == null) {
                                if (estadoAuxC.getId() == null) {
                                    if (comodin == null ? estadoAuxC.getIds().get(0) == null : comodin.equals(estadoAuxC.getIds().get(0))) {
                                         Token a = pila.pop();
                                        pila.push(new Token(estadoAuxC.getIds().get(0), a.getValor() + Character.toString(charAux)));
                                    } else {
                                        pila.push(new Token(estadoAuxC.getIds().get(0), Character.toString(charAux)));
                                    }
                                } else {
                                     if (comodin == null ? estadoAuxC.getId() == null : comodin.equals(estadoAuxC.getId())) {
                                         Token a = pila.pop();
                                        pila.push(new Token(estadoAuxC.getId(), a.getValor() + Character.toString(charAux)));
                                    } else {
                                        pila.push(new Token(estadoAuxC.getId(), Character.toString(charAux)));
                                    }
                                }
                            } else {
                                if (estadoAuxC.getId() == null) {
                                    if (comodin.equals(estadoAuxC.getIds().get(0))) {
                                        Token a = pila.pop();
                                        pila.push(new Token(estadoAuxC.getIds().get(0), a.getValor() + Character.toString(charAux)));
                                    } else {
                                        pila.push(new Token(estadoAuxC.getIds().get(0), Character.toString(charAux)));
                                    }
                                } else {
                                    if (comodin.equals(estadoAuxC.getId())) {
                                        Token a = pila.pop();
                                        pila.push(new Token(estadoAuxC.getId(), a.getValor() + Character.toString(charAux)));
                                    } else {
                                        pila.push(new Token(estadoAuxC.getId(), Character.toString(charAux)));
                                    }
                                }

                            }
                            
                        }
                    }
                }
                if (estadoAuxC.getId() == null) {
                    result = new Token(estadoAuxC.getIds().get(0), auxEntrada + Character.toString(charAux));
                } else {
                    result = new Token(estadoAuxC.getId(), auxEntrada + Character.toString(charAux));
                }
            } 
            if (!tt.getListaEstadosT().get(estadoAuxC.getEstado()).isAceptacion()) {
                perdido = true;
            }
            estadoAux = estadoAuxC.getEstado();
            auxEntrada += Character.toString(charAux);
        }
        return result;
    }
    
    private void reconstruirToken() {
        while (!pila.empty()) {
            if (!"&".equalsIgnoreCase(pila.elementAt(0).getId())) {
                lista.add(pila.remove(0));
            } else {
                pila.remove(0);
            }
        }
    }
    
    public CaracteresColumnas comprobarTabla(EstadosFilas ef, int estado, char caracter) {
        for (CaracteresColumnas caracteresColumnas : ef.getaDondeConCaracter()) {
            if (caracteresColumnas.getCaracater() == caracter) {
                return caracteresColumnas;
            }
        } return null;
    }
    
    public void print(){
        lista.forEach((token) -> {
            System.out.println("Token: ID: " + token.getId() + " Valor: " + token.getValor());
        });
    }

    public Token getToken(){
        if (lista.size() > 0) {
            return lista.remove(0);
        }
        return new Token("$", "");
    }
    
}
