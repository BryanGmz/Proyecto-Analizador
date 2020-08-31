/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.analizador.manejadores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import proyectoanalizador.backed.objetos.analizador.sintactico.Estado;
import proyectoanalizador.backed.objetos.analizador.sintactico.TNT;

/**
 *
 * @author bryan
 */
public class ManejadorGeneradorHTML {
    private List<String> terminalesNoTerminales;
    private final String INICIO_HTML = "<!DOCTYPE html>";
    private final String HTML = "\n<html>";
    private final String FIN_HTML = "\n</html>";
    private final String ENCABEZADO = "\n<meta charset=\"UTF-8\">\n" +
                                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                                        "        <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\">";
    private final String BODY = "\n<body>";
    private final String FIN_BODY = "\n</body>";
    private final String HEAD = "\n<head>";
    private final String FIN_HEAD = "\n</head>";
    private final String CENTER = "\n<center>";
    private final String FIN_CENTER = "\n</center>";

    public ManejadorGeneradorHTML(List<String> terminalesNoTerminales) {
        this.terminalesNoTerminales = terminalesNoTerminales;
    }  
    
    public void escribirArchivoSalida(String path, String textoSalida) throws IOException {
        File chooser = new File(path);
        try (FileOutputStream salida = new FileOutputStream(chooser)) {
            byte[] byteTxt = textoSalida.getBytes();
            salida.write(byteTxt);
        }
    }
    
    public List<String> getTerminalesNoTerminales() {
        return terminalesNoTerminales;
    }

    public void setTerminalesNoTerminales(List<String> terminalesNoTerminales) {
        this.terminalesNoTerminales = terminalesNoTerminales;
    }
    
    private String tituloTabla(String titulo){
        return "<center>\n"
                + "<h1 class=\"display-3\">"+ titulo + "</h1>\n"
                + "</center>";
    }
    
    private String tituloPag(String titulo){
        return "\n<title>"+ titulo + "</title>";
    }
    
    public String construirTabla(List<Estado> estadosC){
        String html = INICIO_HTML;
        html += HTML;
        html += HEAD;
        html += tituloPag("TABLA LALR");
        html += ENCABEZADO;
        html += BODY;
        html += "\n<body style=\"background-color:#CC3300;\"></body>";
        html += tituloTabla("Tabla LALR");
        html += CENTER;
        html += "\n"+tablaLALR(estadosC);
        html += FIN_CENTER;
        html += FIN_BODY;
        html += FIN_HEAD;
        html += FIN_HTML;
        return html;
    }
    
    public String construirPila(List<String> registroEstados, List<String> registroSimbolos, List<String> registroAcciones){
        String html = INICIO_HTML;
        html += HTML;
        html += HEAD;
        html += tituloPag("PILA");
        html += ENCABEZADO;
        html += BODY;
        html += "\n<body style=\"background-color:#000099;\"></body>";
        html += tituloTabla("Pila");
        html += CENTER;
        html += "\n" + tablaPila(registroEstados, registroSimbolos, registroAcciones);
        html += FIN_CENTER;
        html += FIN_BODY;
        html += FIN_HEAD;
        html += FIN_HTML;
        return html;
    }
    
    public String tablaPila(List<String> registroEstados, List<String> registroSimbolos, List<String> registroAcciones){
        int mayor = registroEstados.size();
        if(registroSimbolos.size() > mayor){
            mayor = registroSimbolos.size();
            if(mayor < registroAcciones.size()) {
                mayor = registroAcciones.size();
            }
        } else {
            if(mayor < registroAcciones.size()) {
                mayor = registroAcciones.size();
            }
        }
        String tabla = " <table class=\"table table-striped table-dark\">\n" +
                        "   <thead>\n" +
                        "    <tr>";
        tabla += "\n<th scope=\"col\">Estados</th>";
        tabla += "\n<th scope=\"col\">Simbolos</th>";
        tabla += "\n<th scope=\"col\">Acciones</th>";
        tabla += "</tr>\n" +
                    "  </thead>\n" +
                    "  <tbody>\n";
        for(int i = 0;  i < mayor; i++) {
            tabla += "<tr>\n";
            if (i < registroEstados.size()){
                tabla += "\n<td>"+ (registroEstados.get(i)) + "</td>";
            }
            if (i < registroSimbolos.size()){
                tabla += "\n<td>"+ (registroSimbolos.get(i)) + "</td>";
            }
            if (i < registroAcciones.size()){
                tabla += "\n<td>"+ (registroAcciones.get(i)) + "</td>";
            }
            tabla += "</tr>\n";
        }
        tabla += "\n </tbody>\n" +
                    "</table>";
        return tabla;
    }
    
    private String tablaLALR(List<Estado> estadosC){
        boolean bandera = false;
        String tabla = " <table class=\"table table-striped table-dark\">\n" +
                        "   <thead>\n" +
                        "    <tr>";
        tabla += "\n<th scope=\"col\">#Estado</th>";
        for (String tnt : terminalesNoTerminales) {
            tabla += "\n<th scope=\"col\">" + tnt + "</th>";
        }
        tabla += "</tr>\n" +
                    "  </thead>\n" +
                    "  <tbody>\n";
        for (Estado estado : estadosC) {
            tabla += "<tr>\n" +
                     "      <th scope=\"row\">" + estado.getIdEstado() + "</th>\n";
            for (String tnt : terminalesNoTerminales) {
                for (TNT tntE : estado.getEstados()) {
                    if (tnt.equals(tntE.getIdEstado())) {
                        bandera = true;
                        if (tntE.getGoTo() != null) {
                            tabla += "\n<td>"+ ("G" + tntE.getGoTo().getGoTo() + "</td>");
                        } else if(tntE.getShift() != null) {
                            tabla += "\n<td>"+ ("S" + tntE.getShift().getShift() + "</td>");
                        } else if (tntE.getReview() != null) {
                            tabla += "\n<td>"+ ("R" + tntE.getReview().getIdProduccion() + "</td>");
                        } else if (tntE.isAceptar()){
                            tabla += "\n<td> A </td>";
                        }
                    }
                }
                if (!bandera) {
                    tabla += ("\n<td>---</td>");
                } else {
                    bandera = false;
                }
            }
            tabla += "\n</tr>";
        }
        tabla += "\n </tbody>\n" +
                    "</table>";
        return tabla;
    }

}