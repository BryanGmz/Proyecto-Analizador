/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bryan
 */
public class EstadosFilas implements Serializable{
    
    private int idEstado;
    private final List<CaracteresColumnas> aDondeConCaracter;
    private final List<Integer> sigEstados;
    private boolean aceptacion;
    
    public EstadosFilas(int idEstado) {
        this.idEstado = idEstado;
        this.aDondeConCaracter = new ArrayList<>();
        this.sigEstados = new ArrayList<>();
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }
    
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public List<CaracteresColumnas> getaDondeConCaracter() {
        return aDondeConCaracter;
    }

    public void addSubConjunto(CaracteresColumnas aDondeConCaracter) {
        this.aDondeConCaracter.add(aDondeConCaracter);
    }

    public List<Integer> getSig() {
        return sigEstados;
    }

    public void addSig(int sig) {
        this.sigEstados.add(sig);
    }
    
    public boolean comprobarEstado(List<Integer> listaSiguientes){
        if (listaSiguientes.size() == sigEstados.size() && sigEstados.size() > 0) {
            boolean comprobacion = true;
            for (Integer aux : listaSiguientes) {
                if (comprobacion) {
                    comprobacion = sigEstados.contains(aux);
                } else {
                    return false;
                }
            }
            return comprobacion;
        } else {
            return false;    
        }
    }
    
    public int contiene(char caracter){
        for (CaracteresColumnas caracteresColumnas : aDondeConCaracter) {
            if (caracter == caracteresColumnas.getCaracater()) {
                return caracteresColumnas.getEstado();
            }
        } return -1;
    } 
    
}
