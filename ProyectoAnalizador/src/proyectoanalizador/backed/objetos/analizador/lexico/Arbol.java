/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.lexico;

import java.io.Serializable;
import proyectoanalizador.backed.objetos.ConjuntoSiguientes;
import proyectoanalizador.backed.objetos.TablaSiguientes;

/**
 *
 * @author bryan
 */
public class Arbol implements Serializable{
    
    private int contador;
    private final TablaSiguientes tabla;
    private String id; 
    
    public Arbol() {
        this.contador = 1;
        this.tabla = new TablaSiguientes();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TablaSiguientes getTabla() {
        return tabla;
    }
    
    public Nodo insertarCaracter(char caracter){
        Nodo nodo = new Nodo();
        nodo.setCaracter(caracter);
        nodo.setAnulable(false);
        return nodo;
    }
    
    
    public Nodo insertarNodoMasAstIntC(Nodo nodo, int caso) {
        Nodo masAstIntC = new Nodo();
        switch (caso) {
            case 1://Interrogación Cerrado
                masAstIntC.setAlmenosUnaVez(true);
                masAstIntC.setIzquierdo(nodo);
                masAstIntC.setAnulable(true);
                return masAstIntC;
            case 2://Mas
                masAstIntC.setUnaOMuchasVeces(true);
                masAstIntC.setIzquierdo(nodo);
                masAstIntC.setAnulable(nodo.isAnulable());
                return masAstIntC;
            case 3://Asterisco
                masAstIntC.setCeroOMuchasVeces(true);
                masAstIntC.setIzquierdo(nodo);
                masAstIntC.setAnulable(true);
                return masAstIntC;
        } return null;
    }
    
    //Para la uniones de nodo con caracter
    
    public Nodo insertarNodoYNodo(Nodo concatOI, Nodo concatOD, boolean concat){
//        System.out.println("OI " + concatOI.getId());
//        System.out.println("OD " + concatOD.getId());
        if (concat) {
            Nodo concatenar = new Nodo();
            concatenar.setIzquierdo(concatOI);
            concatenar.setDerecho(concatOD);
            concatenar.setConcat(true);
            if (concatOI.isAnulable() && concatOD.isAnulable()) {
                concatenar.setAnulable(true);
            } else {
                concatenar.setAnulable(false);
            }
            return concatenar;
        } else {
            Nodo o = new Nodo();
            o.setIzquierdo(concatOI);
            o.setDerecho(concatOD);
            o.setO(true);
            if (concatOI.isAnulable() || concatOD.isAnulable()) {
                o.setAnulable(true);
            } else {
                o.setAnulable(false);
            }
            return o;
        }
        
    }
    
    public Nodo insertarPalabra(String palabra){
        switch (palabra.length()) {
            case 1:
//                System.out.println("Aqui 1 " + palabra.charAt(0));
                Nodo caracter = new Nodo();
                caracter.setCaracter(palabra.charAt(0));
                caracter.setAnulable(false);
                return caracter;
            case 2:
            {
//                System.out.println("Aqui 2 ");
                Nodo nodoConcat = new Nodo();
                nodoConcat.setConcat(true);
                Nodo izq = new Nodo();
                izq.setCaracter(palabra.charAt(0));
                Nodo der = new Nodo();
                der.setCaracter(palabra.charAt(1));
                nodoConcat.setIzquierdo(izq);
                nodoConcat.setDerecho(der);
                nodoConcat.setAnulable(false);
                izq.setAnulable(false);
                der.setAnulable(false);
                return nodoConcat;
            }
            default:
            {
                Nodo izq = new Nodo();
                izq.setCaracter(palabra.charAt(0));
                Nodo aux = insertarPalabra(palabra.substring(1, palabra.length()));
                Nodo nodoConcat = new Nodo();
                nodoConcat.setIzquierdo(izq);
                nodoConcat.setDerecho(aux);
                nodoConcat.setConcat(true);
                nodoConcat.setAnulable(false);
                izq.setAnulable(false);
                aux.setAnulable(false);
                return nodoConcat;
            }
        }
    }
    
    /* Para las Uniones con Llaves */
    
    //Para las expresiones con normales concatenadas ej. [0123459]
    public Nodo insertarUniones(char primerChar, char segundoChar){
        Nodo nodoO = new Nodo();
        nodoO.setO(true);
        Nodo izq = new Nodo();
        izq.setCaracter(primerChar);
        Nodo der = new Nodo();
        der.setCaracter(segundoChar);
        nodoO.setIzquierdo(izq);
        nodoO.setDerecho(der);
        nodoO.setAnulable(false);
        izq.setAnulable(false);
        der.setAnulable(false);
        return nodoO;
    }
    
    //Para las expresiones con llaves, susecivamente ej. [0-9]
    public Nodo crearUnionCaracteres(char start, char end, int contI, int contD){
        if (((int) start) < ((int) end)) {
            if (contI + 1 == contD) {
                Nodo nodoO = new Nodo();
                nodoO.setO(true);
                Nodo izq = new Nodo();
                izq.setCaracter((char) contI);
                Nodo der = new Nodo();
                der.setCaracter((char) contD);
                nodoO.setIzquierdo(izq);
                nodoO.setDerecho(der);
                nodoO.setAnulable(false);
                izq.setAnulable(false);
                der.setAnulable(false);
                return nodoO;
            } else {
                Nodo nodoO = new Nodo();
                nodoO.setO(true);
                Nodo izq = crearUnionCaracteres(start, end, contI, contD-1);
                Nodo der = new Nodo();
                der.setCaracter((char) contD);
                nodoO.setIzquierdo(izq);
                nodoO.setDerecho(der);
                if (izq.isAnulable() || der.isAnulable()) {
                    nodoO.setAnulable(true);
                } else {
                    nodoO.setAnulable(false);
                }
                return nodoO;
            }
        } else if (((int) start) == ((int) end)) {
            Nodo caracter = new Nodo();
            caracter.setCaracter(start);
            caracter.setAnulable(false);
            return caracter;
        } else {
            Nodo nodoO = new Nodo();
            nodoO.setO(true);
            Nodo izq = new Nodo();
            izq.setCaracter((char) contI);
            Nodo der = new Nodo();
            der.setCaracter((char) contD);
            nodoO.setIzquierdo(izq);
            nodoO.setDerecho(der);
            nodoO.setAnulable(false);
            izq.setAnulable(false);
            der.setAnulable(false);
            return nodoO;
        }
    }
    
    public Nodo unionDePalabras(String palabra){
        switch (palabra.length()) {
            case 1:
                return insertarCaracter(palabra.charAt(0));
            case 2:
                return insertarNodoYNodo(insertarCaracter(palabra.charAt(0)), insertarCaracter(palabra.charAt(1)), true);
            default:
                String aux = palabra.substring(1, palabra.length() - 1);
                return insertarNodoYNodo(insertarCaracter(palabra.charAt(0)), unionDePalabras(aux), true);
        }
    }
    
    public Nodo concatencacionExpresionesCant(Nodo nodo, int caso){
         switch (caso) {
            case 0:
                return nodo;
            case 1:
                return insertarNodoMasAstIntC(nodo, 1);
            case 2:
                return insertarNodoMasAstIntC(nodo, 3);
            case 3:
                return insertarNodoMasAstIntC(nodo, 2);
        } return null;
    }
    
    public Nodo concatComodin(Nodo nodo){
        Nodo comodin = new Nodo();
        comodin.setAnulable(false);
        comodin.setComodin(true);
        return insertarNodoYNodo(nodo, comodin, true);
    }
    
    public String pup(Nodo nodo){
        String salida = "P -> ";
        salida = nodo.getPrimeraPos().stream().map((pP) -> " " + pP).reduce(salida, String::concat);
        salida += " U -> ";
        salida = nodo.getUtlimaPos().stream().map((uP) -> " " + uP).reduce(salida, String::concat);
        return salida;
    }
    
    public void verArbol(Nodo recorrer, int n){
    if(recorrer == null) {
        return;
    }
    verArbol(recorrer.getDerecho(), n + 1);
    for(int i = 0; i < n; i++){
        System.out.print("\t");
    }
     if (recorrer.isAlmenosUnaVez()) {
        System.out.print(" ? " + recorrer.isAnulable() + " " + recorrer.getId() + " " + pup(recorrer) + "\n");
    } else if (recorrer.isCeroOMuchasVeces()){
        System.out.print(" * " + recorrer.isAnulable() + " " + recorrer.getId() + " " + pup(recorrer) + "\n");
    } else if (recorrer.isUnaOMuchasVeces()){
        System.out.print(" + " + recorrer.isAnulable() + " " + recorrer.getId() + " " +  pup(recorrer) + "\n");
    } else if (recorrer.isConcat()){
        System.out.print(" . " + recorrer.isAnulable() + " " + recorrer.getId() + " " +  pup(recorrer) + "\n");
    } else if (recorrer.isO()){
        System.out.print(" | " + recorrer.isAnulable() + " " + recorrer.getId() + " " +  pup(recorrer) + "\n");
    } else if (recorrer.isComodin()){
        System.out.print(" # " + recorrer.isAnulable() + " " + recorrer.getId() + " " +  pup(recorrer) + "\n");
    } else {
        System.out.print(recorrer.getCaracter() + " " + recorrer.isAnulable() + " " + recorrer.getId() + " " +  pup(recorrer) + "\n");
    }
    verArbol(recorrer.getIzquierdo(), n + 1);
    }
    
    public void addNombreHojas(Nodo recorrer, String idH){
        if (recorrer  != null) {
            if (idH == null) {
                addNombreHojas(recorrer.getIzquierdo(), recorrer.getId());
                addNombreHojas(recorrer.getDerecho(), recorrer.getId());
            } else {
                if (recorrer.getId() == null) {
                    recorrer.setId(idH);
                }
                addNombreHojas(recorrer.getIzquierdo(), recorrer.getId());
                addNombreHojas(recorrer.getDerecho(), recorrer.getId());         
            }
            
            if (recorrer.getIzquierdo() != null && recorrer.getId() != null) {
                recorrer.getIzquierdo().setId(recorrer.getId());
            }
            if (recorrer.getDerecho()!= null && recorrer.getId() != null) {
                recorrer.getDerecho().setId(recorrer.getId());
            }
        }
    }
    
    public void agregarSiguientes(Nodo recorrer){
        if (recorrer != null) {
            agregarSiguientes(recorrer.getIzquierdo());
            agregarSiguientes(recorrer.getDerecho());
            if (recorrer.getIzquierdo() == null && recorrer.getDerecho() == null) {
                
            } else {
                if (recorrer.isUnaOMuchasVeces() || recorrer.isCeroOMuchasVeces()) {
                    recorrer.getPrimeraPos().stream().map((primeraPos) -> tabla.retornarCS(primeraPos)).forEachOrdered((cs) -> {
                        recorrer.getUtlimaPos().stream().filter((utlimaPos) -> (!cs.getSiguientes().contains(utlimaPos))).forEachOrdered((utlimaPos) -> {
                            cs.addSiguiente(utlimaPos);
                        });
                    });
                } else if (recorrer.isConcat()){
                    recorrer.getIzquierdo().getUtlimaPos().forEach((utlimaPos) -> {
                        recorrer.getDerecho().getPrimeraPos().forEach((primeraPos) -> {
                            ConjuntoSiguientes cs = tabla.retornarCS(utlimaPos);
                            if (!cs.getSiguientes().contains(primeraPos)) {
                                cs.addSiguiente(primeraPos);
                            }
                        });
                    });
                }
            }
            
        }
    }
    
    //Recorrido postOrden
    public void postorden(Nodo recorrer){
        if (recorrer != null) {
            postorden(recorrer.getIzquierdo());
            postorden(recorrer.getDerecho());
            if (recorrer.getIzquierdo() == null && recorrer.getDerecho() == null) {
                if (recorrer.isComodin()) {
                    tabla.addConjuntoSiguiente(new ConjuntoSiguientes(contador));
//                    System.out.println("Valor: " + "#");
                } else {
                    tabla.addConjuntoSiguiente(new ConjuntoSiguientes(recorrer.getCaracter(), contador, recorrer.getId()));
//                    System.out.println("Valor: " + recorrer.getCaracter());
                }
                recorrer.addPrimero(contador);
                recorrer.addUltima(contador);
                contador++;
            } else {
                if (recorrer.isAlmenosUnaVez() || recorrer.isCeroOMuchasVeces() || recorrer.isUnaOMuchasVeces()) {
                    recorrer.getIzquierdo().getPrimeraPos().forEach((primeraPosicion) -> {
                        recorrer.addPrimero(primeraPosicion);
                    });
                    recorrer.getIzquierdo().getUtlimaPos().forEach((ultimaPosicion) -> {
                        recorrer.addUltima(ultimaPosicion);
                    });
                } else {
                    if (recorrer.isConcat()) {
                        if (recorrer.getIzquierdo().isAnulable()) {
                            recorrer.getIzquierdo().getPrimeraPos().forEach((primeraPos) -> {
                                recorrer.addPrimero(primeraPos);
                            });
                            recorrer.getDerecho().getPrimeraPos().forEach((primeraPos) -> {
                                recorrer.addPrimero(primeraPos);
                            });

                        } else {
                            recorrer.getIzquierdo().getPrimeraPos().forEach((primeraPos) -> {
                                recorrer.addPrimero(primeraPos);
                            });
                        }
                        if (recorrer.getDerecho().isAnulable()) {
                            recorrer.getIzquierdo().getUtlimaPos().forEach((ultimaPos) -> {
                                recorrer.addUltima(ultimaPos);
                            });
                            recorrer.getDerecho().getUtlimaPos().forEach((ultimaPos) -> {
                                recorrer.addUltima(ultimaPos);
                            });
                        } else {
                            recorrer.getDerecho().getUtlimaPos().forEach((ultimaPos) -> {
                                recorrer.addUltima(ultimaPos);
                            });
                        }
                    } else {
                        recorrer.getIzquierdo().getPrimeraPos().forEach((primeraPos) -> {
                            recorrer.addPrimero(primeraPos);
                        });
                        recorrer.getDerecho().getPrimeraPos().forEach((primeraPos) -> {
                            recorrer.addPrimero(primeraPos);
                        });
                        recorrer.getIzquierdo().getUtlimaPos().forEach((ultimaPos) -> {
                            recorrer.addUltima(ultimaPos);
                        });
                        recorrer.getDerecho().getUtlimaPos().forEach((ultimaPos) -> {
                            recorrer.addUltima(ultimaPos);
                        });
                    }
                }
            }
        }
    }
}
