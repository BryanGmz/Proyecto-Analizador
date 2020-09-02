/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.lexico;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.analizador.sintactico.CaracteresColumnas;
import proyectoanalizador.backed.objetos.analizador.sintactico.EstadosFilas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import proyectoanalizador.backed.objetos.ConjuntoSiguientes;
import proyectoanalizador.backed.objetos.TablaSiguientes;

/**
 *
 * @author bryan
 */
public class TablaTransiciones implements Serializable{
    
    private final TablaSiguientes siguientes;
    private int contadorEstados;
    private final List<EstadosFilas> listaEstadosT;
    private int contadorMarcar;
    private List<Character> caracteres;
    private final int aceptacion;
    private int matrizEstados[][];
    
    public TablaTransiciones(TablaSiguientes siguientes) {
        this.siguientes = siguientes;
        this.contadorEstados = 0;
        this.listaEstadosT = new ArrayList<>();
        this.caracteres = new ArrayList<>();
        this.aceptacion = this.siguientes.getConjuntoSiguientes().size();
        this.siguientes.getConjuntoSiguientes().remove(this.siguientes.getConjuntoSiguientes().size() - 1);
    }
    
    private List<Character> generarListaChars(List<Integer> sigEstados){
        List<Character> aux = new ArrayList<>();
        siguientes.getConjuntoSiguientes().forEach((cs) -> {
            sigEstados.stream().filter((sigEstado) -> (cs.getNumeroConjunto() == sigEstado)).forEachOrdered((_item) -> {
                if (!aux.contains(cs.getCaracter())) {
                    aux.add(cs.getCaracter());
                }
            });
        });
        return aux;
    }
    
    public void generarTablaTransiciones(List<Integer> primeros){
        EstadosFilas ef0 = new EstadosFilas(contadorEstados);
        primeros.forEach((integer) -> {
            ef0.addSig(integer);
        });
        ef0.setAceptacion(ef0.getSig().contains(aceptacion));
        caracteres = generarListaChars(ef0.getSig());
        contadorMarcar = 0;
        listaEstadosT.add(ef0);
        contadorEstados++;
        generarEstados(ef0, caracteres);
        contadorMarcar++;
        while (contadorMarcar != contadorEstados && contadorMarcar < contadorEstados) {
            EstadosFilas efi = listaEstadosT.get(contadorMarcar);
            caracteres = generarListaChars(efi.getSig());
            generarEstados(efi, caracteres);
            contadorMarcar++;
        }
    }
    
    private void generarEstados(EstadosFilas efI, List<Character> chars){
        for (Character caracter : chars) {
            List<Integer> auxN = filtrarListaN(efI.getSig(), caracter);
            List<Integer> auxSigEstado = obtenerSiguientes(auxN);
            EstadosFilas aux = null;
            for (EstadosFilas estadosFilas : listaEstadosT) {
                if (estadosFilas.comprobarEstado(auxSigEstado)) {
                    aux = estadosFilas;
                    break;
                }
            }
            if (aux == null) {
                EstadosFilas nuevoEstado = new EstadosFilas(contadorEstados);
                contadorEstados++;
                auxSigEstado.forEach((integer) -> {
                    nuevoEstado.addSig(integer);
                });
                List<String> auxId = null;
                nuevoEstado.setAceptacion(nuevoEstado.getSig().contains(aceptacion));
                listaEstadosT.add(nuevoEstado);
                auxId = idNodos(nuevoEstado.getSig(), caracter);
                
                if (auxId != null && !auxId.isEmpty()) {
                    if (auxId.size() > 1) {
                        efI.addSubConjunto(new CaracteresColumnas(caracter, nuevoEstado.getIdEstado(), auxId));
                    } else {
                        efI.addSubConjunto(new CaracteresColumnas(caracter, nuevoEstado.getIdEstado(), auxId.get(0)));
                    }
                } else {
                    efI.addSubConjunto(new CaracteresColumnas(caracter, nuevoEstado.getIdEstado(), "Error"));
                }
            } else {
                List<String> auxId = null; 
                auxId = idNodos(aux.getSig(), caracter);
                if (auxId != null && !auxId.isEmpty()) {
                    if (auxId.size() > 1) {
                        efI.addSubConjunto(new CaracteresColumnas(caracter, aux.getIdEstado(), auxId));
                    } else {
                        efI.addSubConjunto(new CaracteresColumnas(caracter, aux.getIdEstado(), auxId.get(0)));
                    }
                } else {
                    efI.addSubConjunto(new CaracteresColumnas(caracter, aux.getIdEstado(), "Error"));
                }
            }
        }
    }
    
    private List<Integer> filtrarListaN(List<Integer> lista, char caracter){
        List<Integer> aux = new ArrayList<>();
        siguientes.getConjuntoSiguientes().forEach((siguiente) -> {
            lista.stream().filter((integer) -> (integer == siguiente.getNumeroConjunto() && siguiente.getCaracter() == caracter)).forEachOrdered((integer) -> {
                aux.add(integer);
            });
        });
        return aux;
    }
    
    private List<Integer> obtenerSiguientes(List<Integer> lista){
        List<Integer> aux = new ArrayList<>();
        siguientes.getConjuntoSiguientes().forEach((cs) -> {
            lista.stream().filter((integer) -> (integer == cs.getNumeroConjunto())).forEachOrdered((_item) -> {
                cs.getSiguientes().stream().filter((iAux) -> (!aux.contains(iAux))).forEachOrdered((iAux) -> {
                    aux.add(iAux);
                });
            });
        });
        return aux;
    }
    
    public List<Character> obtenerCaracteres(){
        List<Character> aux = new ArrayList<>();
        siguientes.getConjuntoSiguientes().stream().filter((cs) -> (!aux.contains(cs.getCaracter()))).forEachOrdered((cs) -> {
            aux.add(cs.getCaracter());
        });
        return aux;
    }
    
    public void crearMatriz(){
        List<Character> aux = obtenerCaracteres();
        matrizEstados = new int[listaEstadosT.size()][aux.size()];
        for (int i = 0; i < listaEstadosT.size(); i++) {
            for (int j = 0; j < aux.size(); j++) {
                int auxEstado = listaEstadosT.get(i).contiene(aux.get(j));
                if (auxEstado != -1) {
                    matrizEstados[i][j] = auxEstado;
                } else {
                    matrizEstados[i][j] = -1;
                }
            }
        } 
//        pintarTabla(matrizEstados, listaEstadosT.size(), aux.size(), aux);
//        printEstadosFinales();
    }
    
    public void pintarTabla(int matriz [][], int i, int j, List<Character> aux){
        for (int k = 0; k < i; k++) {
            System.out.print("E " + k + "\t");
            for (int l = 0; l < j; l++) {
                System.out.print(matriz[k][l] + "\t");
            } System.out.println("");
        }
    }
    
    public void printEstadosFinales(){
        System.out.println("\n//ESTADOS FINALES: \n");
        listaEstadosT.stream().filter((estadosFilas) -> (estadosFilas.isAceptacion())).map((estadosFilas) -> {
            System.out.println("ACEPTACION ESTADO: " + estadosFilas.getIdEstado());
            return estadosFilas;
        }).map((estadosFilas) -> {
            System.out.println("E -> " + estadosFilas.getIdEstado() + " " + Arrays.toString(estadosFilas.getSig().toArray()));
            return estadosFilas;
        }).forEachOrdered((_item) -> {
            System.out.println("");
        });
    }

    public int getAceptacion() {
        return aceptacion;
    }

    public List<EstadosFilas> getListaEstadosT() {
        return listaEstadosT;
    }

    public int[][] getMatrizEstados() {
        return matrizEstados;
    }

    public void setMatrizEstados(int[][] matrizEstados) {
        this.matrizEstados = matrizEstados;
    }
    
    public String idNodo(List<Nodo> lista){
        for (Nodo nodo : lista) {
            if (nodo.getId() != null) {
                return nodo.getId();
            }
        } return null;
    }
   
    
    public List<String> idNodos(List<Integer> listaSiguientes, char c){
        List<String> auxIdNodo = new ArrayList<>();
        if (listaSiguientes.size() == 1 && listaSiguientes.get(0) == aceptacion) {
            for (ConjuntoSiguientes cs : siguientes.getConjuntoSiguientes()) {
                if (cs.getCaracter() == c) {
                    auxIdNodo.add(cs.getId());
                    return auxIdNodo;
                }
            }
            return auxIdNodo;
        }
        for (Integer lInt : listaSiguientes) {
             for (ConjuntoSiguientes cs : siguientes.getConjuntoSiguientes()) {
                if (cs.getNumeroConjunto() == lInt  && !auxIdNodo.contains(cs.getId())) {
                    auxIdNodo.add(cs.getId());
                }
            }
        }
        return auxIdNodo;
    }
}
