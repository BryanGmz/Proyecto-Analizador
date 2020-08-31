/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.objetos.analizador.sintactico;

import java.util.ArrayList;
import java.util.List;
import proyectoanalizador.backed.objetos.DiagramaAS;

/**
 *
 * @author bryan
 */
public class ReglasGramaticas {
    
    private final List<NoTerminal> reglasGramaticas;//
    private final List<Terminal> precedencia;
    private int contador;
    private int contadorEstados;
    private final List<String> terminales;
    private final List<String> noTerminales;
    private final List<Produccion> producciones;
    private final DiagramaAS diagramaAS;
    private TablaLALR tablaLALR = new TablaLALR();
    private String estadoInicial;
    
    public ReglasGramaticas() {
        this.terminales = new ArrayList<>();
        this.noTerminales = new ArrayList<>();
        this.reglasGramaticas = new ArrayList<>();
        this.producciones = new  ArrayList<>();
        this.contador = 0;
        this.contadorEstados = 1;
        this.diagramaAS = new DiagramaAS();
        this.precedencia = new ArrayList<>();
    }

    public List<Terminal> getPrecedencia() {
        return precedencia;
    }
    
    public String getEstadoInicial() {
        return estadoInicial;
    }

    public List<Produccion> getProducciones() {
        return producciones;
    }

    public TablaLALR getTablaLALR() {
        return tablaLALR;
    }
    
    private void addNivel(){
        for (Produccion p : producciones) {
            Object o = p.getProduccion();
            while (o != null) {
                if (o instanceof Terminal) {
                    p.setNivel(lvlT(((Terminal) o).getId()));
                    break;
                } else {
                    o = ((NoTerminal) o).getSiguiente();
                }
            }
        }
    }
    
    private int lvlT(String id){
        for (Terminal terminal : precedencia) {
            if (terminal.getId().equals(id)) {
                return terminal.getNivel();
            }
        } return -1;
    }
    
    public void addEstadoInicial(NoTerminal produccion){
        NoTerminal inicial = new NoTerminal("init", true);
        NoTerminal auxPI = new NoTerminal(produccion.getId(), false);
        NoTerminal aceptacion = new NoTerminal("", false);
        aceptacion.setAceptacion(true);
        auxPI.setSiguiente(aceptacion);
        inicial.addProduccion(auxPI);
        reglasGramaticas.add(0, inicial);
        estadoInicial = produccion.getId();
    }
    
    public List<NoTerminal> getReglasGramaticas() {
        return reglasGramaticas;
    }

    public void addReglaGramatica(NoTerminal reglasGramaticas) {
        this.reglasGramaticas.add(0, reglasGramaticas);
    }

    public List<String> getTerminales() {
        return terminales;
    }

    public void addtTerminales(String terminales) {
        this.terminales.add(terminales);
    }

    public List<String> getNoTerminales() {
        return noTerminales;
    }

    public void addNoTerminales(String noTerminales) {
        this.noTerminales.add(noTerminales);
    }
    
    public boolean contieneTerminal(String terminal){
        return terminales.stream().anyMatch((terminalC) -> (terminalC.equals(terminal)));
    }

    public boolean contieneNoTerminal(String noTerminal){
        return terminales.stream().anyMatch((noTerminalC) -> (noTerminalC.equals(noTerminal)));
    }
    
    public void generarProducciones(){
        for (NoTerminal reglasGramatica : reglasGramaticas) {
            reglasGramatica.getProducciones().stream().map((produccion) -> {
                Object rg = produccion; 
                return produccion;
            }).forEachOrdered((produccion) -> {
                producciones.add(new Produccion(contador, reglasGramatica, produccion));
                contador++;
            });
        }
    }
    
    public void printPro(){
        generarProducciones();
        producciones.stream().map((produccion) -> {
            System.out.print("\n"+ produccion.getIdProduccion() + "\t" + produccion.getNoTerminal().getId() + " -> " );
            return produccion;
        }).map((produccion) -> produccion.getProduccion()).map((rg) -> {
            while (rg != null) {
                if (rg instanceof Terminal) {
                    System.out.print(((Terminal) rg).getId() + " ");
                    rg = ((Terminal) rg).getSiguiente();
                } else {
                    if (((NoTerminal) rg).isAceptacion()) {
                        System.out.print(" $");
                    } else if (((NoTerminal) rg).isLambda())
                        System.out.print("");
                    else 
                        System.out.print(((NoTerminal) rg).getId() + " ");
                    rg = ((NoTerminal) rg).getSiguiente();
                }
            }
            return rg;
        }).forEachOrdered((_item) -> {
            System.out.println("");
        });
    }
    
    public void pint(){
        System.out.println("\nNo Terminal");
        noTerminales.forEach((noTerminal) -> {
            System.out.println(noTerminal);
        });
        System.out.println("\nTerminal");
        terminales.forEach((terminal) -> {
            System.out.println(terminal);
        });
    }
    
    //calcula los primeros
    public void addPrimeros(){
        for (NoTerminal nt : reglasGramaticas) {
            if (!nt.isLambda()) {
                recorrerProduccion(nt.getProducciones(), nt, new ArrayList<>());
            }
        }
    }
    
    private void recorrerProduccion(List<Object> producciones, NoTerminal produccion, List<String> recorrido){
        for (Object pro : producciones) {
            if (pro instanceof Terminal) {
                if (!produccion.getPrimeros().contains(((Terminal) pro).getId())) {
                    produccion.addPrimero(((Terminal) pro).getId());
                }
            } else {
                if (!recorrido.contains(((NoTerminal) pro).getId())) {
                    recorrido.add(((NoTerminal) pro).getId());
                    recorrerProduccion(obtenerGrama(((NoTerminal) pro).getId()), produccion, recorrido);
                } 
                
            }
        }
    }
    
    private Object obtenerP(Object p, int punto){
        int continuar = 0;
        Object tnt = p;
        while (continuar != punto && tnt != null) {
            continuar++;
            if (tnt instanceof Terminal) {
                tnt = ((Terminal) tnt).getSiguiente();
            } else {
                tnt = ((NoTerminal) tnt).getSiguiente();
            }
        }
        return tnt;
    }
    
    public void initDiagram(){
        addNivel();
        DEstado inicial = new DEstado();
        inicial.setIdEstado("");
        inicial.setId(contadorEstados);
        contadorEstados++;
        Produccion pInicial = producciones.get(0);
        inicial.addTupla(new Tupla(pInicial, 0));
        inicial.addTuplaInicial(new Tupla(pInicial, 0));
        Tupla t = inicial.getListaTuplas().get(0);
        t.addPreanalisis("?");
        Object tnt = t.getProduccion().getProduccion();
        //Inicial no tomar en cuenta
        int cont = 0;
        while (inicial.getListaTuplas().size() != cont) {
            cont++;
            System.out.println("Tupla P - " + t.getProduccion().getIdProduccion() + " " + tnt);
            if (tnt instanceof NoTerminal) {
                if (((NoTerminal) tnt).getSiguiente() != null) {
                    if (((NoTerminal) tnt).getSiguiente() instanceof Terminal) {
//                        System.out.println("1");
                        addPreAnalisis(((Terminal) ((NoTerminal) tnt).getSiguiente()).getId(), inicial, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), 0);
                    } else {
//                        System.out.println("2");
                        for (String pre : ((NoTerminal) (((NoTerminal) tnt).getSiguiente())).getPrimeros()) {
                            addPreAnalisis(pre, inicial, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), 0);
                        }
                    }
                } else {
//                    System.out.println("3");
                    for (String object : t.getPreanalisis()) {
                        addPreAnalisis(object, inicial, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), 0);
                    }
                }
            }
            if (cont < inicial.getListaTuplas().size()) {
                t  = inicial.getListaTuplas().get(cont);
                tnt = obtenerP(inicial.getListaTuplas().get(cont).getProduccion().getProduccion(), inicial.getListaTuplas().get(cont).getPunto());
            } else {
                cont = inicial.getListaTuplas().size(); 
            }
        }
        
        diagramaAS.addEstado(inicial);
        generarEstados(inicial);
    }
    
    private void generarPreanalisis(DEstado dEstado){
        DEstado estado = dEstado;
        Tupla t = estado.getListaTuplas().get(0);
        Object tnt = obtenerP((t.getProduccion().getProduccion()), t.getPunto());
        int cont = 0;
        while (estado.getListaTuplas().size() != cont) {
            cont++;
//            System.out.println("Size  " + estado.getListaTuplas().size() + "#" + estado.getId());
            if (tnt instanceof NoTerminal) {
                if (((NoTerminal) tnt).getSiguiente() != null) {
                    if (((NoTerminal) tnt).getSiguiente() instanceof Terminal) {
                        addPreAnalisis(((Terminal) ((NoTerminal) tnt).getSiguiente()).getId(), estado, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), t.getPunto());
                    } else {
                        boolean bandera = false;
                        for (String pre : ((NoTerminal) (((NoTerminal) tnt).getSiguiente())).getPrimeros()) {
//                            System.out.println("PRE " + ((NoTerminal) tnt).getId());
                            if (bandera) {
                                addPreAnalisis(pre, estado, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), 0);
                            } else {
                                addPreAnalisis(pre, estado, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), t.getPunto());
                            }
                            bandera = true;
                        }
                    }
                } else {
                    boolean bandera = false;
//                    System.out.println("E " + estado.getIdEstado());
                    for (String object : t.getPreanalisis()) {
//                        System.out.println("E " + estado.getId()+" -> " +estado.getIdEstado() + " PRE NULL " + object + " pre: " + ((NoTerminal) tnt).getId() + " punto: " + t.getPunto() + " p: " + t.getProduccion().getIdProduccion());
                        if (bandera) {
                            addPreAnalisis(object, estado, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), 0);
                        } else {
                            addPreAnalisis(object, estado, obtenerProduccion(((NoTerminal) tnt).getId()), new ArrayList<>(), 0);
                        }
                        bandera = true;
                    }
                }
            }
            if (cont < estado.getListaTuplas().size()) {
                t  = estado.getListaTuplas().get(cont);
                tnt = obtenerP(estado.getListaTuplas().get(cont).getProduccion().getProduccion(), estado.getListaTuplas().get(cont).getPunto());
            } else {
                cont = estado.getListaTuplas().size(); 
            }
        }
    }
    
        private void generarEstados(DEstado inicial){
        int contEstados = 0;
        DEstado estado = inicial;
        generarGRS(estado);
        while (contEstados != diagramaAS.getEstados().size()) {
            if (contEstados == 0) {
                contEstados++;
            }
            if (contEstados < diagramaAS.getEstados().size()) {
                estado = diagramaAS.getEstados().get(contEstados);
            } else {
                contEstados = diagramaAS.getEstados().size();
                break;
            }
            generarPreanalisis(estado);
            generarGRS(estado);
            contEstados++;
        }
    }
    
    
    private void generarGRS(DEstado estado){
        int conta = 0;
        System.out.println("Estado: " + estado.getId());
        while (conta < estado.getListaTuplas().size()) {
            Tupla tupla = estado.getListaTuplas().get(conta);
            Object tnt = obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto());
            if (tnt == null) { //Review
                tupla.setReview(new Review(getProduccion(tupla.getProduccion().getIdProduccion()), tupla.getProduccion().getIdProduccion()));
            } else { //Goto o Shift
                if (tnt instanceof Terminal) {
                    DEstado aux = comprobarIniciales(tuplasETNT(obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto()), estado.getListaTuplas()), 
                            obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto()));
                    if (aux == null) {
                        aux = new DEstado();
                        aux.setId(contadorEstados);
                        aux.setIdEstado(((Terminal) tnt).getId());
                        aux.setIdPadreAnt(estado.getIdEstado());
                        aux.setNoAdd(true);
                        contadorEstados++;
                        diagramaAS.getEstados().add(aux);
                        List<Tupla> add = tuplasETNT(obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto()), estado.getListaTuplas());
                        for (Tupla t : add) {
                            Tupla auxTupla = new Tupla(t.getProduccion(), t.getPunto() + 1);
                            auxTupla.copiarPreanalisis(t.getPreanalisis());
                            aux.addTupla(auxTupla);
                            aux.addTuplaInicial(auxTupla);
                        }
                    } 
                    if (!comprobarTupla(aux, tupla, tupla.getPunto() + 1)) {
                        Tupla auxTupla = new Tupla(tupla.getProduccion(), tupla.getPunto() + 1);
                        auxTupla.copiarPreanalisis(tupla.getPreanalisis());
                        aux.addTupla(auxTupla);
                        aux.addTuplaInicial(auxTupla);
                    }
                    tupla.setShift(new Shift(getProduccion(tupla.getProduccion().getIdProduccion()), aux.getId(), ((Terminal) tnt).getId()));
                } else {
                    if (((NoTerminal) tnt).isAceptacion()) {
                        tupla.setReview(new Review(getProduccion(tupla.getProduccion().getIdProduccion()), tupla.getProduccion().getIdProduccion()));
                    } else {
                        DEstado aux = comprobarIniciales(tuplasETNT(obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto()), estado.getListaTuplas()), 
                                obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto()));
                        if (aux == null) {
                            aux = new DEstado();
                            aux.setId(contadorEstados);
                            contadorEstados++;    
                            aux.setIdEstado(((NoTerminal) tnt).getId());
                            aux.setIdPadreAnt(estado.getIdEstado());
                            aux.setNoAdd(true);
                            diagramaAS.getEstados().add(aux);
                            List<Tupla> add = tuplasETNT(obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto()), estado.getListaTuplas());
                            for (Tupla t : add) {
                                Tupla auxTupla = new Tupla(t.getProduccion(), t.getPunto() + 1);
                                auxTupla.copiarPreanalisis(t.getPreanalisis());
                                aux.addTupla(auxTupla);
                                aux.addTuplaInicial(auxTupla);
                            }
                        } 
                        if (!comprobarTupla(aux, tupla, tupla.getPunto() + 1)) {
                            Tupla auxTupla = new Tupla(tupla.getProduccion(), tupla.getPunto() + 1);
                            auxTupla.copiarPreanalisis(tupla.getPreanalisis());
                            aux.addTupla(auxTupla);
                            aux.addTuplaInicial(auxTupla);
                        }
                        tupla.setGoTo(new Goto(getProduccion(tupla.getProduccion().getIdProduccion()), aux.getId(), ((NoTerminal) tnt).getId()));
                    }
                }
            }
            conta++;
        }
        estado.setNoAdd(false);
    }
    
    private List<Tupla> tuplasETNT(Object d, List<Tupla> lista){
        String id = "";
        if (d instanceof Terminal) {
            id = ((Terminal) d).getId();
        } else if(d != null){
            id = ((NoTerminal) d).getId();
        }
        List<Tupla> tuplas = new ArrayList<>();
        for (Tupla tupla : lista) {
            Object o = obtenerP(tupla.getProduccion().getProduccion(), tupla.getPunto());
            if (o instanceof Terminal) {
                if (((Terminal) o).getId().equals(id)) {
                    tuplas.add(tupla);
                }
            } else if (o instanceof NoTerminal){
                if (((NoTerminal) o).getId().equals(id)) {
                    tuplas.add(tupla);
                }
            }
        } return tuplas;
    }
    
    private DEstado comprobarIniciales(List<Tupla> listaIniciales, Object d){
        String idTNT = "";
        if (d instanceof Terminal) {
            idTNT = ((Terminal) d).getId();
        } else if(d != null){
            idTNT = ((NoTerminal) d).getId();
        }
        for (DEstado estado : diagramaAS.getEstados()) {
            if (idTNT.equals(estado.getIdEstado())) {
                if (estado.getListaTuplasIniciales().size() == listaIniciales.size()) {//Tamaño igual
                    int contador1 = 0;
                    for (Tupla lt : estado.getListaTuplasIniciales()) {
                        for (Tupla ltI : listaIniciales) {
                            if (lt.getProduccion().getIdProduccion() == ltI.getProduccion().getIdProduccion()
                                    && lt.getPunto() == ltI.getPunto() + 1
                                    && lt.getPreanalisis().size() == (ltI.getPreanalisis().size()) 
                                    && lt.getPreanalisis().containsAll(ltI.getPreanalisis())) {
                                contador1++;
                                break;
                            }
                        }
                        if (contador1 == listaIniciales.size()) {
                            contador1 = 0;
                            return estado;
                        }
                    }
                }
            }  
        } return null;
    }
    
    private boolean comprobarTupla(DEstado estado, Tupla t, int punto){
        for (Tupla lt : estado.getListaTuplas()) {
            if (t.getProduccion().getIdProduccion() == lt.getProduccion().getIdProduccion() 
                            && punto == lt.getPunto() && comprobarPreanalisis(t, lt)) {
                return true;
            }
        } return false;
    }
    
    private boolean comprobarPreanalisis(Tupla t1, Tupla t2){
        if (t1.getPreanalisis().size() == t2.getPreanalisis().size()) {
            return t1.getPreanalisis().containsAll(t2.getPreanalisis()) && t2.getPreanalisis().containsAll(t1.getPreanalisis());
        } 
        return false;
    }
    
    private void comprobarSiExisteTupla(List<Tupla> lista, String comparar, int punto, List<Produccion> produccion){
        for (Produccion p : produccion) {
            for (Tupla tupla : lista) {
                if (tupla.getProduccion().getIdProduccion() == p.getIdProduccion()
                        && tupla.getPunto() == punto) {
                    if (!tupla.getPreanalisis().contains(comparar)) {
                        tupla.addPreanalisis(comparar);
                    }
                }
            }
        }
    }
    
    private void addPreAnalisis(String preanalisis, DEstado estado, List<Produccion> producciones, List<String> lista, int punto){
        if (!lista.contains(preanalisis)) {
            lista.add(preanalisis);
            for (Produccion produccion : producciones) {
                if (estado.getListaTuplas().isEmpty()) {
                    Tupla add = new Tupla(produccion, 0);
                    add.addPreanalisis(preanalisis);
                    estado.addTupla(add);
                    lista.add(preanalisis);
                } else {
                    boolean bandera = false;
                    for (Tupla listaTupla : estado.getListaTuplas()) {
                        if (listaTupla.getProduccion().getIdProduccion() == produccion.getIdProduccion() && 
                                listaTupla.getPunto() == punto) {
                            Object c = obtenerP(listaTupla.getProduccion().getProduccion(), 0) ;
                            bandera = true;
                            if (!listaTupla.getPreanalisis().contains(preanalisis)) {
                                listaTupla.getPreanalisis().add(preanalisis);
                            }
                            if (c instanceof NoTerminal) {
                                if (((NoTerminal) c).getSiguiente() == null) {
                                    comprobarSiExisteTupla(estado.getListaTuplas(), preanalisis, punto, obtenerProduccion(c.toString()));
                                }
                            }
                            break;
                        }  
                    }
                    if (!bandera) {
                        Tupla add = new Tupla(produccion, 0);
                        add.addPreanalisis(preanalisis);
                        estado.addTupla(add);
                        lista.add(preanalisis);
                    }
                }
            }
        }
    }
    
    public void impDiagram(){
        for (DEstado estado : diagramaAS.getEstados()) {
            System.out.println("\nEstado: " + estado.getId());
            for (Tupla t : estado.getListaTuplas()) {
                System.out.print(t.getProduccion().getIdProduccion() + "  - ");
                for (String preanalisi : t.getPreanalisis()) {
                    System.out.print(" - " + preanalisi);
                    
                }
                t.printGRS();
                System.out.println("");
            }
            System.out.println("");
        }
        tablaLALR.setPresedecia(precedencia);
        tablaLALR.agregarTNT(terminales, noTerminales);
        tablaLALR.agregarEstados(diagramaAS.getEstados());
    }
    
    
    public List<Produccion> obtenerProduccion(String id){
        List<Produccion> listaAux = new ArrayList<>();
        for (Produccion produccion : producciones) {
            if (produccion.getNoTerminal().getId().equals(id)) {
                listaAux.add(produccion);
            }
        } return listaAux;
    }
    
    
    
    private List<Object> obtenerGrama(String id){
        for (NoTerminal rg : reglasGramaticas) {
            if (rg.getId().equals(id)) {
                return rg.getProducciones();
            }
        } return new ArrayList<>();
    }
    
    public void imprimirPrimeros(){
        reglasGramaticas.stream().map((nt) -> {
            System.out.print(nt.getId() + " -> ");
            return nt;
        }).map((nt) -> {
            nt.getPrimeros().forEach((primero) -> {
                System.out.print(primero + " ");
            });
            return nt;
        }).forEachOrdered((_item) -> {
            System.out.println("");
        });
    }
    
    private Produccion getProduccion(int id){
        for (Produccion p : producciones) {
            if (p.getIdProduccion() == id) {
                return p;
            }
        } return null;
    }
}
