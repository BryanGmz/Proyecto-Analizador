/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.analizador.manejadores;

import proyectoanalizador.backed.objetos.analizador.sintactico.*;
import proyectoanalizador.backed.objetos.analizador.sintactico.NoTerminal;
import proyectoanalizador.backed.objetos.analizador.lexico.Nodo;
import proyectoanalizador.backed.objetos.analizador.lexico.Arbol;
import java.util.ArrayList;
import java.util.List;
import proyectoanalizador.backed.objetos.*;
import proyectoanalizador.backed.objetos.analizador.lexico.AnalizadorLexico;
import proyectoanalizador.backed.objetos.analizador.lexico.TablaTransiciones;
import proyectoanalizador.backed.objetos.analizador.sintactico.ReglasGramaticas;
import proyectoanalizador.gui.Frame;

/**
 *
 * @author bryan
 */
public class ManejadorSintactico {
    
    private static ManejadorSintactico manejadorSintactico;
    private static TablaSimbolo tablaSimbolos;
    private final String M_EDE = " Ya se encuentra definido en la estructura.";  //Constante para indicar que el token ya se definio en la estructura
    private final String E_N_E_C = " La estructura no esta completa.";           //Constante para indicar que no se encuentra completa.
    private Frame frame;
    private int cN;
    
    private ManejadorSintactico() {}
    
    public static ManejadorSintactico getInstacia(){
        if (manejadorSintactico == null) {
            manejadorSintactico = new ManejadorSintactico();
            tablaSimbolos = new TablaSimbolo(new ArrayList<>(), "");
        } return manejadorSintactico;
    }
    
    public void reset(Frame frame){
        this.frame = frame;
        ManejadorSintactico.tablaSimbolos.getSimbolos().clear();
        this.cN = 0;
    }
    
    public void aumentarCN(){
        this.cN++;
    }

    public int getcN() {
        return cN;
    }
    
    /* Errores */
    
    public void errorSintax(int left, int right, String value, String mensaje){
        if (mensaje.isEmpty()) {
            frame.addError(
            "\nError de Sintaxis: " 
            + "\n\tLinea #:                 << " + (right + 1) + " >>"
            + "\n\tColumna #                << " + (((left)/(right)) + 1) + " >>"
            + "\n\tToken NO Reconocido:     << " + (value) + " >>" );
        } else {
            frame.addError(
            "\nError de Sintaxis: " 
            + "\n\tLinea #:                 << " + (right + 1) + " >>"
            + "\n\tColumna #                << " + (((left)/(right)) + 1) + " >>"
            + "\n\tToken NO Reconocido:     << " + (value) + " >>" 
            + "\n\tMensaje (Informacion): "
            + "\n\t\t-> " + mensaje);
        }
    }
    
    public void errorSemantico(int left, int right, String valor, String mensaje){
        frame.addError(
            "\nError Semantico: " 
            + "\n\tLinea #:                 << " + (right + 1) + " >>"
            + "\n\tColumna #                << " + (((left)/(right)) + 1) + " >>"
            + "\n\tValor:                   << " + valor + " >>"
            + "\n\tMensaje (Informacion): "
            + "\n\t\t-> " + mensaje);
    }
    
    /* Tabla de Simbolos */
    
    public void clear(){
        if (tablaSimbolos != null) {
            tablaSimbolos.getSimbolos().clear();
        }
    }
    
    //Agregar por tipos a la tabla de simbolos
    public Object addTabla(Object object, int left, int right, String tipo){
        System.out.println("O: " + object + " T: " + tipo);
        if (tablaSimbolos.buscarPorTipo(tipo) == null && object != null) {
            tablaSimbolos.agregarTablaTemp(object, tipo);
            return object;
        } else {
            tablaSimbolos.removerPorTipo(tipo);
            errorSintax(left, right,  tipo, "<< " + tipo + ">> -> Valor: " + object + M_EDE);
            return null;
        }
    }
    
    /* Informacion de Lenguaje */
    
    public InfLenguaje comprobarInformacion(int aleft, int aright, int eleft, int eright, int ileft, int iright, int oleft, int oright, int uleft, int uright,
            Object nombre, Object version, Object lanzamiento, Object autor, Object extension){
        if (nombre != null && tablaSimbolos.buscarPorTipo("Nombre") != null) {
            InfLenguaje infLenguaje = new InfLenguaje((String) tablaSimbolos.buscarPorTipo("Nombre"));
            tablaSimbolos.removerPorTipo("Nombre");
            if (version != null) {
                infLenguaje.setVersion((String) tablaSimbolos.buscarPorTipo("Version"));
                tablaSimbolos.removerPorTipo("Version");
                System.out.println("Version");
            }
            if (autor != null) {
                infLenguaje.setAutor((String) tablaSimbolos.buscarPorTipo("Autor"));
                tablaSimbolos.removerPorTipo("Autor");
                System.out.println("Autor");
            }
            if (lanzamiento != null) {
                infLenguaje.setLanzamiento((String) tablaSimbolos.buscarPorTipo("Lanzamiento"));
                tablaSimbolos.removerPorTipo("Lanzamiento");
                System.out.println("Lanzamiento");
            }
            if (extension != null) {
                infLenguaje.setExtension((String) tablaSimbolos.buscarPorTipo("Extension"));
                tablaSimbolos.removerPorTipo("Extension");
                System.out.println("Extension");
            }
            return infLenguaje;
        } else {
            errorSemantico(aleft, aright, "Nombre", "El atributo nombre no se encuentra en la estructura de información del lenguaje.");
            return null;
        }
    }
    
    
    /* Analizador Lexico */
    
    public Lenguajes generadorLenguaje(Arbol arbol, Nodo add, ReglasGramaticas rg, InfLenguaje inf, String actionCode){
        Nodo aux = arbol.concatComodin(add); 
        arbol.addNombreHojas(aux, null);
        arbol.postorden(aux);
        arbol.agregarSiguientes(aux); 
        TablaTransiciones tt = new TablaTransiciones(arbol.getTabla());
        tt.generarTablaTransiciones(aux.getPrimeraPos());
        tt.crearMatriz();
        AnalizadorLexico al = new AnalizadorLexico(tt);
        rg.addEstadoInicial(rg.getReglasGramaticas().get(rg.getReglasGramaticas().size() - 1));
        rg.printPro();
        rg.addPrimeros();
        rg.imprimirPrimeros();
        rg.initDiagram();
        rg.impDiagram();
        Pila pila = new Pila(al, rg.getTablaLALR().getEstados());
        pila.setListaNoTerminales(rg.getReglasGramaticas());
        pila.setListaTerminales(rg.getPrecedencia());
        pila.setProduccionInical(rg.getEstadoInicial());
        Lenguajes lenguaje = new Lenguajes();
        lenguaje.setAnalizadorLexico(al);
        lenguaje.setInformacionLenguaje(inf);
        lenguaje.setTablaLALR(rg.getTablaLALR());
        lenguaje.setPila(pila);
        lenguaje.setActionCode(actionCode);
        return lenguaje;
    }
    
    public void addTipo(Object e){
        Object c =  tablaSimbolos.buscarPorTipo("-var");
        if (c == null) {
            tablaSimbolos.agregarTablaTemp(e, "-var");
        } else {
            tablaSimbolos.removerPorTipo("-var");
            tablaSimbolos.agregarTablaTemp(e, "-var");
        }
    }
    
    /* Agregar Tipo de terminales y no terminales */
   
    public void addTipoTNT(List<NoTerminal> listaNT, List<Terminal> listaT, Object tipo){
//        List<String> listaObjectsTNT = (List<String>) tablaSimbolos.buscarPorTipo("ListaTNT");
//        System.out.println("Tipo " + tipo + " " + listaObjectsTNT.size() + " " + listaObjectsTNT);
//        if (tipo != null) {
//            for (NoTerminal noTerminal : listaNT) {
//                if (listaObjectsTNT.contains(noTerminal.getId())) {
//                    noTerminal.setTipo((String) tipo);
//                }
//            }
//            for (Terminal terminal : listaT) {
//                if (listaObjectsTNT.contains(terminal.getId())) {
//                    terminal.setTipo((String) tipo);
//                }
//            }
//        } 
//        tablaSimbolos.removerPorTipo("ListaTNT");
//        tablaSimbolos.agregarTablaTemp(new ArrayList<>(),"ListaTNT");
    }
    
    /* Reglas Terminales */
    
    public void agregarTNT(List<String> lista, boolean terminal, String aux, int left, int right, List<NoTerminal> listaNT, List<Terminal> listaT, int contadorLVL){
        if (lista.contains(aux)) {
            if (terminal) {
                errorSemantico(left, right, "Terminal", "Error, ya existe una terminal con este ID.");
            } else {
                errorSemantico(left, right, "No Terminal", "Error, ya existe una terminal con este ID.");
            }
        } else {
            if (terminal) {
                lista.add(aux);
                Terminal add = new Terminal(aux, contadorLVL);
                if (tablaSimbolos.buscarPorTipo("-var") != null) {
                     add.setTipo((String) tablaSimbolos.buscarPorTipo("-var"));
                }
                listaT.add(add);
            } else {
                NoTerminal add = new NoTerminal(aux, true);
                if (tablaSimbolos.buscarPorTipo("-var") != null) {
                     add.setTipo((String) tablaSimbolos.buscarPorTipo("-var"));
                }
                listaNT.add(add);
                lista.add(aux);
            }
        }
    }
    
    //Reglas Semanticas
    
    public Object retornarTNT(List<String> comprobar, boolean terminal, String aux, int left, int right){
        if (comprobar.contains(aux)) {
            if (terminal) {
                return new Terminal(aux);
            } else {
                return new NoTerminal(aux, false);
            }
        }
        if (terminal) {
            errorSemantico(left, right, "Terminal", "La terminal: " + aux + " no se a definido en el apartado de Terminales.");
        } else {
            errorSemantico(left, right, "No Terminal", "La no terminal: " + aux + " no se a definido en el apartado de No Terminales.");
        }
        return null;
    }
    
    public Object construirSigPro(Object i, String valorDeVuelto, Object d, int left, int right){
        if (i != null && d != null) {
            if (i instanceof NoTerminal) {
                ((NoTerminal) i).setSiguiente(d);
                if (valorDeVuelto != null) {
                    ((NoTerminal) i).setValorDevuelto(valorDeVuelto);
                }
            } else {
                ((Terminal) i).setSiguiente(d);
                if (valorDeVuelto != null) {
                    ((Terminal) i).setValorDevuelto(valorDeVuelto);
                }
            }
            return  i;
        } else if ((i != null)){
            if (valorDeVuelto != null) {
                if (i instanceof NoTerminal) {
                    ((NoTerminal) i).setValorDevuelto(valorDeVuelto);
                } else {
                     ((Terminal) i).setValorDevuelto(valorDeVuelto);
                }
                
            }
            return  i;
        } else {
            errorSemantico(left, right, "\"Producción\"", "No se puede construir la producción debido a que existen problemas con las terminales y no terminales. \n\t\tNo se encuentran definido");
        }
        /* Aun falta arreglar el control de errores */
        return null;
    }
    
    public void addRG(Object noTerminal, List<NoTerminal> lnt, String ntS, int left, int right, String rs){
        boolean bandera = false;
        if (noTerminal == null) {
             errorSemantico(left, right, "Produccion", "Debido a que existen problemas en la produccion no se puede agregar.: " + ntS);
        } else {
             for (NoTerminal nt : lnt) {
                if (nt.getId().equals(ntS)) {
                    if (noTerminal instanceof NoTerminal && !nt.isLambda()) {
                        nt.setLambda(((NoTerminal) noTerminal).isLambda());
                    }
                    nt.addProduccion(noTerminal);
                    nt.setReglaSemantica(rs);
                    bandera = true;
                    break;
                }
            }
            if (!bandera) {
                errorSemantico(left, right, "Produccion", "No se encuentra definido una produccion con el id: " + ntS);
            }
        }
    }
}
