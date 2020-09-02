/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.lexico;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author bryan
 */
public class Nodo implements Serializable{
    
    private final List<Integer> primeraPos;
    private final List<Integer> utlimaPos;
    private List<Integer> listaSiguientes;
    private String id;
    
    private char caracter;
    
    private boolean concat;
    private boolean o;
    
    private boolean almenosUnaVez;
    private boolean ceroOMuchasVeces;
    private boolean unaOMuchasVeces;
    
    private boolean anulable;
    
    private boolean comodin;
    private boolean ignorar;
    
    private Nodo izquierdo;
    private Nodo derecho;
    private Nodo padre;

    public Nodo() {
        this.primeraPos = new ArrayList<>();
        this.utlimaPos = new ArrayList<>();
    }

    public void setListaSiguientes(List<Integer> listaSiguientes) {
        this.listaSiguientes = listaSiguientes;
    }

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public boolean isConcat() {
        return concat;
    }

    public void setConcat(boolean concat) {
        this.concat = concat;
    }

    public boolean isO() {
        return o;
    }

    public void setO(boolean o) {
        this.o = o;
    }

    public boolean isAlmenosUnaVez() {
        return almenosUnaVez;
    }

    public void setAlmenosUnaVez(boolean unaVez) {
        this.almenosUnaVez = unaVez;
    }

    public boolean isCeroOMuchasVeces() {
        return ceroOMuchasVeces;
    }

    public void setCeroOMuchasVeces(boolean ceroOMuchasVeces) {
        this.ceroOMuchasVeces = ceroOMuchasVeces;
    }

    public boolean isUnaOMuchasVeces() {
        return unaOMuchasVeces;
    }

    public void setUnaOMuchasVeces(boolean unaOMuchasVeces) {
        this.unaOMuchasVeces = unaOMuchasVeces;
    }

    public boolean isAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
    
    public void addSiguiente(Integer siguiente){
        listaSiguientes.add(siguiente);
    }

    public void addPrimero(Integer primero){
        primeraPos.add(primero);
    }
    
    public void addUltima(Integer ultima){
        utlimaPos.add(ultima);
    }
    
    public boolean isComodin() {
        return comodin;
    }

    public void setComodin(boolean comodin) {
        this.comodin = comodin;
    }

    public List<Integer> getPrimeraPos() {
        return primeraPos;
    }

    public List<Integer> getUtlimaPos() {
        return utlimaPos;
    }

    public List<Integer> getListaSiguientes() {
        return listaSiguientes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIgnorar() {
        return ignorar;
    }

    public void setIgnorar(boolean ignorar) {
        this.ignorar = ignorar;
    }
}
