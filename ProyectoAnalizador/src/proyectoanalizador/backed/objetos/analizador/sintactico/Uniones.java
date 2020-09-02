/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;

/**
 *
 * @author bryan
 */
public class Uniones implements Serializable{
    
    private int primerEstado;
    private int segundoEstado;
    private int estadoActual;

    public Uniones(int primerEstado, int segundoEstado, int estadoActuaal) {
        this.primerEstado = primerEstado;
        this.segundoEstado = segundoEstado;
        this.estadoActual = estadoActuaal;
    }

    public int getPrimerEstado() {
        return primerEstado;
    }

    public void setPrimerEstado(int primerEstado) {
        this.primerEstado = primerEstado;
    }

    public int getSegundoEstado() {
        return segundoEstado;
    }

    public void setSegundoEstado(int segundoEstado) {
        this.segundoEstado = segundoEstado;
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(int estadoActuaal) {
        this.estadoActual = estadoActuaal;
    }
     
}
