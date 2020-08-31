/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador;

import proyectoanalizador.backed.analizador.manejadores.ManejadorEntrada;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import proyectoanalizador.backed.analizador.*;

/**
 *
 * @author bryan
 */
public class ProyectoAnalizador {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        GeneradorArchivo archivos = GeneradorArchivo.getGeneradorArchivos();
        archivos.generador();
//////    | COMILLAS contenido_exp_llave:a COMILLAS cantidad_expresion:e              {:RESULT = parser.arbol.concatencacionExpresionesCant((Nodo) a, (int) e);:}
//        String entrada = "";
//        File archivo = new File("/home/bryan/Escritorio/entrada.txt");
//        FileReader entradaArchivo = new FileReader(archivo);
//        int e;
//        while ((e = entradaArchivo.read()) != -1) {
//            char caracter = (char) e;
//            if (caracter == '\n') {
////                System.out.println("Salto");
//            }
//            entrada += caracter;
//        }
//        ManejadorEntrada manejadorEntrada = ManejadorEntrada.getManejadorEntrada();
//        entrada = manejadorEntrada.entradaTexto(entrada);
//        System.out.println("Entrada: \n" + entrada);
//        Lexer lexer = new Lexer(new StringReader(entrada));
//        Sintax sintax = new Sintax(lexer);
//        sintax.resetRecursos();
//        try {
//            sintax.parse();
//            System.out.println("ACEPTADO");
//        } catch (Exception ex) {
////            ex.printStackTrace();
//            System.out.println("Revisa tu entrada");
//        }
    }
}
