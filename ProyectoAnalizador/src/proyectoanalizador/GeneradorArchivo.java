/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador;;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author bryan
 */
public class GeneradorArchivo {
    
    private static GeneradorArchivo generadorArchivos;
    public static final String PARSER = "-parser";
    public static final String SYM = "sym.java";
    //Constantes para generar archivos del analizador lexico y sintactico de las estructuras del lenguaje
    public static final String PATH_FLEX = "src/proyectoanalizador/backed/analizador/Lexer.flex";
    public static final String PATH_CUP = "src/proyectoanalizador/backed/analizador/Sintax.cup";
    public static final String PATH_SYM = "src/proyectoanalizador/backed/analizador/sym.java";
    public static final String PATH_SINTAX = "src/proyectoanalizador/backed/analizador/Sintax.java";
    public static final String NOMBRE_SINTAX = "Sintax";
    public static final String NOMBRE_CLASE_SINTAX = "Sintax.java";

    private GeneradorArchivo() {}

    public static GeneradorArchivo getGeneradorArchivos() {
        if (generadorArchivos == null) {
            generadorArchivos = new GeneradorArchivo();
        } 
        return generadorArchivos;
    }
    
    private void gen(String rutaFlex, String rutaCup, String rutaSym, String rutaSintaxJava, String nombreSintax, String nombreClase) throws Exception {
        String[] rutaSintactico = {PARSER, nombreSintax, rutaCup};
        generador(rutaFlex, rutaSintactico, rutaSym, SYM, rutaSintaxJava, nombreClase);
    }
    
    private void generador(String rutaLexicoProyecto, String[] rutaSintax, String rutaSymTXT, String nombreSym, String rutaSint, String nombreSin) throws IOException, Exception{
        File archivo;
        archivo = new File(rutaLexicoProyecto);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaSintax);
        Path rutaSym = Paths.get(rutaSymTXT);
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get(nombreSym), 
                Paths.get(rutaSymTXT)
        );
        Path rutaSin = Paths.get(rutaSint);
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get(nombreSin), 
                Paths.get(rutaSint)
        );
    }
    
    public void generador() throws Exception{
        GeneradorArchivo gArchivos = getGeneradorArchivos();
        gArchivos.gen(GeneradorArchivo.PATH_FLEX, GeneradorArchivo.PATH_CUP, GeneradorArchivo.PATH_SYM, 
                GeneradorArchivo.PATH_SINTAX, GeneradorArchivo.NOMBRE_SINTAX, GeneradorArchivo.NOMBRE_CLASE_SINTAX);
    } 
}
