/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.analizador.lexico.AnalizadorLexico;
import proyectoanalizador.backed.objetos.analizador.sintactico.Pila;
import proyectoanalizador.backed.objetos.analizador.sintactico.TablaLALR;

/**
 *
 * @author bryan
 */
public class Lenguajes implements Serializable{
    private InfLenguaje informacionLenguaje;
    private AnalizadorLexico analizadorLexico;    
    private Pila pila;
    private TablaLALR tablaLALR;
    private String actionCode;
    
    public Lenguajes() {}

    public Lenguajes(InfLenguaje informacionLenguaje) {
        this.informacionLenguaje = informacionLenguaje;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode.replaceAll("%%", "");
    }
    
    public AnalizadorLexico getAnalizadorLexico() {
        return analizadorLexico;
    }

    public void setAnalizadorLexico(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    public Pila getPila() {
        return pila;
    }

    public void setPila(Pila pila) {
        this.pila = pila;
    }

    public TablaLALR getTablaLALR() {
        return tablaLALR;
    }

    public void setTablaLALR(TablaLALR tablaLALR) {
        this.tablaLALR = tablaLALR;
    }                       
    
    public InfLenguaje getInformacionLenguaje() {
        return informacionLenguaje;
    }

    public void setInformacionLenguaje(InfLenguaje informacionLenguaje) {
        this.informacionLenguaje = informacionLenguaje;
    }
}
