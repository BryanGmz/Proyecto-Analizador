
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
public class Shift  implements Serializable {
    
    private final Produccion produccion;
    private int shift;
    private final String idTerminal;
    private List<Integer> shifts;
    private int nivel;
    private boolean presedencia;
    
    public Shift(Produccion produccion, int shift, String idProduccion) {
        this.produccion = produccion;
        this.shift = shift;
        this.idTerminal = idProduccion;
        this.shifts = new ArrayList<>();
        addlvl();
    }

    public boolean isPresedencia() {
        return presedencia;
    }

    public void setPresedencia(boolean presedencia) {
        this.presedencia = presedencia;
    }
    
    public final void addlvl(){
        this.nivel = produccion.getNivel();
    }
    
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public List<Integer> getShifts() {
        return shifts;
    }
    
    public void setShift(int shift) {
        this.shift = shift;
    }

    public Produccion getProduccion() {
        return produccion;
    }

    public int getShift() {
        return shift;
    }

    public String getIdTerminal() {
        return idTerminal;
    }
    
    public boolean compararNiveles(int a) {
        return (a < nivel); //Mi nivel es mas alto en la precedencia
    }
}
