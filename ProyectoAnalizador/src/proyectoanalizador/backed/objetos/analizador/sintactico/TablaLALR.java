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
public class TablaLALR implements Serializable {
    
    private List<String> terminalesNoTerminales;
    private List<Estado> estados;
    private List<DEstado> estadosDiagrama;
    private List<Uniones> estadosUnir;
    private List<Terminal> presedecia;
    private boolean conlfictos;
    
    public TablaLALR() {
        estadosUnir = new ArrayList<>();
    }

    public List<Terminal> getPresedecia() {
        return presedecia;
    }

    public void setPresedecia(List<Terminal> presedecia) {
        this.presedecia = presedecia;
    }

    public boolean isConlfictos() {
        return conlfictos;
    }

    public void setConlfictos(boolean conlfictos) {
        this.conlfictos = conlfictos;
    }
    
    public Terminal getTerminalPresedencia(String id){
        if (id.equals("$")) {
            return new Terminal("$", 0);
        }
        for (Terminal terminal : presedecia) {
            if (id.equals(terminal.getId())) {
                return terminal;
            }
        } return null;
    }
    
    public void agregarTNT(List<String> terminales, List<String> noTerminales) {
        estados = new ArrayList<>();
        terminalesNoTerminales = new ArrayList<>();
        for (String t : terminales) {
            terminalesNoTerminales.add(t);
        }
        terminalesNoTerminales.add("$");
        for (String t : noTerminales) {
            terminalesNoTerminales.add(t);
        }
    }

    public List<String> getTerminalesNoTerminales() {
        return terminalesNoTerminales;
    }
    
    public List<Estado> getEstados() {
        return estados;
    }

    public void agregarEstados(List<DEstado> est){
        estadosDiagrama = est;
        for (DEstado dEstado : est) {
            addEstado(dEstado);
        } print(estados); generarLALR();
    }
    
    private boolean comprobarSiExisteT(List<TNT> lista, String id /*, int caso*/){
        for (TNT tnt : lista) {
            if (tnt.getIdEstado().equals(id) && tnt.getReview() == null) {
                return true;
            }
        } return false;
    }
    
    
    private TNT comprobarSiExisteRS(List<TNT> lista, String id, int caso){
        switch (caso) {
            case 1: //Shift
                for (TNT tnt : lista) {
                    if (tnt.getIdEstado().equals(id) && tnt.getReview() != null){
                        return tnt;
                    }
                }
            case 2: //Review
                for (TNT tnt : lista) {
                    if (tnt.getIdEstado().equals(id) && tnt.getShift()!= null){
                        return tnt;
                    }
                }  
            
        } return null;
    }
    
    public void addEstado(DEstado estado) {
        Estado add = new Estado(estado.getId());
        add.setName(estado.getIdEstado());
        for (Tupla lt : estado.getListaTuplas()) {
            if (lt.getShift() != null) {
                if (!comprobarSiExisteT(add.getEstados(), lt.getShift().getIdTerminal())) {
                    TNT comparar = comprobarSiExisteRS(add.getEstados(), lt.getShift().getIdTerminal(), 1);
                    if (comparar != null) { //Hay conflicto con presedencia
                        if (comparar.getReview().compararNiveles(lt.getShift().getNivel())) {
                            add.addEstado(new TNT(lt.getShift().getIdTerminal(), 
                                new Shift(lt.getProduccion(), lt.getShift().getShift(), lt.getShift().getIdTerminal())));
                            add.getEstados().remove(comparar);
                        } else {
                            comparar.getReview().setPresedencia(true);
                        }
                    } else {
                        add.addEstado(new TNT(lt.getShift().getIdTerminal(), 
                        new Shift(lt.getProduccion(), lt.getShift().getShift(), lt.getShift().getIdTerminal())));
                    }
                } else {
                    conlfictos = true;
                }
            } else if(lt.getGoTo() != null) {
                if (!comprobarSiExisteT(add.getEstados(), lt.getGoTo().getIdProduccion())) {
                    add.addEstado(new TNT(lt.getGoTo().getIdProduccion(), 
                        new Goto(lt.getProduccion(), lt.getGoTo().getGoTo(), lt.getGoTo().getIdProduccion())));
                }
            } 
            else if(lt.getReview() != null){
                if (lt.getReview().getProduccionReview().getIdProduccion() == 0) {
                    add.addEstado(new TNT("$", true));
                } else {
                    for (String pre : lt.getPreanalisis()) {
                        TNT comparar = comprobarSiExisteRS(add.getEstados(), 
                                pre, 2);
                        if (comparar != null) {//hay conflicto con presedencia
                            if (comparar.getShift().compararNiveles(lt.getReview().getNivel())) {
                                add.addEstado(new TNT(pre, 
                                    new Review(lt.getReview().getProduccionReview(), lt.getReview().getIdProduccion(), true)));
                                add.getEstados().remove(comparar);
                            }
                        } else {
                            add.addEstado(new TNT(pre, 
                            new Review(lt.getReview().getProduccionReview(), lt.getReview().getIdProduccion())));
                        }
                    }
                }
            }
        }
        estados.add(add);
    }
    
    private int cantidadDe(List<TNT> estados, String comprobar){
        int contador = 0;
        for (TNT e : estados) {
            if (e.getIdEstado().equals(comprobar)) {
                contador++;
            }
        } return contador;
    }
    
    public void print(List<Estado> estadosC){
        for (String tnt : terminalesNoTerminales) {
            System.out.print("\t\t" + tnt);
        }
        boolean bandera = false;
        System.out.println("");
        for (Estado estado : estadosC) {
            System.out.print("E " + estado.getIdEstado());
            for (int i = 0; i < terminalesNoTerminales.size(); i++) {
                int a = cantidadDe(estado.getEstados(), terminalesNoTerminales.get(i));
                if (cantidadDe(estado.getEstados(), terminalesNoTerminales.get(i)) > 1) {
                    System.out.print("\t\t");
                } 
                for (TNT tnt : estado.getEstados()) {
                    if (terminalesNoTerminales.get(i).equals(tnt.getIdEstado())) {
                        bandera = true;
                        if (cantidadDe(estado.getEstados(), terminalesNoTerminales.get(i)) <= 1) {
                            System.out.print("\t\t");
                        }
                        if (tnt.getGoTo() != null) {
                            System.out.print("G" + tnt.getGoTo().getGoTo() + " ");
                        } else if(tnt.getShift() != null) {
                            System.out.print("S" + tnt.getShift().getShift() + " ");
                        } else if (tnt.getReview() != null) {
                            System.out.print("R" + tnt.getReview().getIdProduccion() + " ");
                        } else if (tnt.isAceptar()){
                            System.out.print("A" + " ");
                        }
                    }
                }
                if (!bandera) {
                    System.out.print("\t\t-");
                } else {
                    bandera = false;
                }
            }
            System.out.println("");
        }
        System.out.println("");
        
    }
    
    public int getIdTNT(String id){
        return terminalesNoTerminales.indexOf(id);
    }
    
    
    //Con diagramas
    
    private void generarLALR(){
//        estadosLALR = new ArrayList<>();
        int contador = 0;
        for (DEstado e : estadosDiagrama) {
            if (contador > 1) {
                comprobarIgualdad(e, compararD(e, contador + 1));
                contador++;
            } else {
                 estadosUnir.add(new Uniones(e.getId(), e.getId(), e.getId()));
                 contador++;
            }
        }
        contador = 1;
        comprobarEstados();
        for (Uniones u : estadosUnir) {
            u.setEstadoActual(contador);
//            System.out.println("Union \tP: " + u.getPrimerEstado() + "\tS: " + u.getSegundoEstado() + "\tID: " + u.getEstadoActual());
            contador++;
        }
        modificarGSR();
    }
    
    private void comprobarEstados(){
        for (Estado estado : estados) {
            int a = 0;
            for (Uniones uniones : estadosUnir) {
                if (estado.getIdEstado() != uniones.getPrimerEstado() && estado.getIdEstado() != uniones.getSegundoEstado()) {
                    a++;
                }
            }
            if (a == estadosUnir.size()) {
                estadosUnir.add(new Uniones(estado.getIdEstado(), estado.getIdEstado(), estado.getIdEstado()));
            }
//            System.out.println("A_ " + a + " " +estadosUnir.size());
        }
    }
    
    private List<DEstado> compararD(DEstado e, int desde){
        List<DEstado> retornar = new ArrayList<>();
        for (int i = desde; i < estadosDiagrama.size(); i++) {
            if (e.getIdEstado().equals(estadosDiagrama.get(i).getIdEstado())) {
                retornar.add(estadosDiagrama.get(i));
            }
        } return retornar;
    }
    
    private void comprobarIgualdad(DEstado e1, List<DEstado> estados){//comprobar diagramas
        boolean bandera = false;
        for (DEstado dEstado : estados) {
            if (dEstado.getIdEstado().equals(e1.getIdEstado())) {
                if (dEstado.getListaTuplas().size() == e1.getListaTuplas().size()) {
                    int contador = 0;
                    for (Tupla lt : e1.getListaTuplas()) {
                        for (Tupla lt1 : dEstado.getListaTuplas()) {
                            if (lt.getProduccion().getIdProduccion() == lt1.getProduccion().getIdProduccion()
                                    && lt.getPunto() == lt1.getPunto()) {
                                contador++;
                                break;
                            }
                        }
                    } 
                    if (contador ==  dEstado.getListaTuplas().size() && e1.getId() != dEstado.getId()) {
                        if (e1.getId() < dEstado.getId()) {
                            estadosUnir.add(new Uniones(e1.getId(), dEstado.getId(), e1.getId()));
                        } else {
                            estadosUnir.add(new Uniones(dEstado.getId(), e1.getId(), dEstado.getId()));
                        }
                        bandera = true;
                    }
                }
            }
        } 
    }
    
    public void modificarGSR(){
        List<Estado> estadosLALRs = new ArrayList<>();
        for (Uniones uniones : estadosUnir) {
            Estado estadoLALR;
            Estado primerE = getEstado(uniones.getPrimerEstado());
            Estado segundoE = getEstado(uniones.getSegundoEstado());
            if (primerE.getIdEstado() == segundoE.getIdEstado()) {
                estadoLALR = new Estado(uniones.getEstadoActual());
                estadoLALR.setName(primerE.getName());
                List<TNT> add = realizarCambio(primerE.getEstados(), estadoLALR.getEstados());
                estadoLALR.getEstados().addAll(add);
                estadosLALRs.add(estadoLALR);
            } else {
                estadoLALR = new Estado(uniones.getEstadoActual());
                estadoLALR.setName(primerE.getName());
                List<TNT> add = realizarCambio(primerE.getEstados(), estadoLALR.getEstados());
                estadoLALR.getEstados().addAll(add);
                add = realizarCambio(segundoE.getEstados(), estadoLALR.getEstados());
                estadoLALR.getEstados().addAll(add);
                estadosLALRs.add(estadoLALR);
            }
        }
        this.estados = estadosLALRs;
        print(estadosLALRs);
    }
    
    private List<TNT> realizarCambio(List<TNT> listaAdd, List<TNT> estado){
        List<TNT> nuevo = new ArrayList<>();
        for (TNT tnt : listaAdd) {
            if (tnt.getShift() != null) {
                if (!comprobarSiEstadoGRS(1, estado, tnt.getIdEstado())) {
                    tnt.setShift(new Shift(tnt.getShift().getProduccion(), getUnion(tnt.getShift().getShift()).getEstadoActual(), tnt.getShift().getIdTerminal()));
                    nuevo.add(tnt);
                }                
            } else if (tnt.getGoTo() != null) {
                if (!comprobarSiEstadoGRS(2, estado, tnt.getIdEstado())) {
                    tnt.setGoTo(new Goto(tnt.getGoTo().getProduccion(), getUnion(tnt.getGoTo().getGoTo()).getEstadoActual(), tnt.getGoTo().getIdProduccion()));
                    nuevo.add(tnt);
                }
            } else if (tnt.getReview() != null) {
                if (!comprobarSiEstadoGRS(3, estado, tnt.getIdEstado())) {
                    tnt.setReview(tnt.getReview());
                    nuevo.add(tnt);
                }
            } else if (tnt.isAceptar()) {
                    nuevo.add(new TNT("$", true));
                }
        } return nuevo;
    }
    
    private boolean comprobarSiEstadoGRS(int caso, List<TNT> grs, String id){
        switch (caso) {
            case 1: //Shift
                for (TNT tnt : grs) {
                    if (tnt.getShift() != null && tnt.getIdEstado().equals(id)) {
                        return true;
                    }
                }
            case 2: //Shift
                for (TNT tnt : grs) {
                    if (tnt.getGoTo()!= null && tnt.getIdEstado().equals(id)) {
                        return true;
                    }
                }
            case 3: //Shift
                for (TNT tnt : grs) {
                    if (tnt.getReview()!= null && tnt.getIdEstado().equals(id)) {
                        return true;
                    }
                }
        } return false;
    }
    
    private Uniones getUnion(int estado){
        for (Uniones uniones : estadosUnir) {
            if (uniones.getPrimerEstado() == estado || uniones.getSegundoEstado() == estado) {
                return uniones;
            }
        } return null;
    }
    
    private Estado getEstado(int idEstado){
        for (Estado estado : estados) {
            if (estado.getIdEstado()== idEstado) {
                return estado;
            }
        } return null;
    }
    
}
