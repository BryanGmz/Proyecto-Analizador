/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.analizador.manejadores;

/**
 *
 * @author bryan
 */
public class ManejadorEntrada {
    private static ManejadorEntrada manejadorEntrada;
    
    private ManejadorEntrada(){}
    
    public static ManejadorEntrada getManejadorEntrada(){
        if (manejadorEntrada == null) {
            manejadorEntrada = new ManejadorEntrada();
        } return manejadorEntrada;
    }
    
    public String entradaTexto(String entrada){
        String [] split = entrada.split("%%");
        if (split.length == 5) { 
            entrada = "";
            for (int i = 0; i < 5; i++) {
                if (i == 1) {
                    entrada += "\n%%\n" + split[1] + "%%\n\n";
                } else {
                    entrada += split[i];
                }   
            }
        } else {
            entrada = entrada.replaceAll("%%", "");
        }
        return entrada;
    }
}
