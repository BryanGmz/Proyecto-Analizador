/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalizador.backed.analizador.manejadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bryangmz
 * @param <T>
 */
public class ManejadorArchivosBinarios <T> {
    
    public static final String TIPO_ARCHIVO = "Lenguaje"; 
    public static final String EXTENSION_ARCHIVO = ".len"; 
    
    public  boolean crearArchivo(T tipoObjeto, String tipoArchivo,  String identificadorUnico, String extensionArchivo) {
        String ruta = "./Repositorio/" + tipoArchivo + "(" + identificadorUnico + ")" + extensionArchivo;
        File file = new File(ruta);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);) {
            outputStream.writeObject(tipoObjeto);
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
        return true;
    }
      
    public T leerArchivo(String pathInicial, String pathNombreArchivo, String tipoDeArchivoPath){
        String ruta = "./Repositorio/" + pathInicial + "(" + pathNombreArchivo + ")" + tipoDeArchivoPath;
        File file =  new File(ruta);
        try (FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);) {
            return (T) inputStream.readObject();
        } catch (IOException e) {
           System.out.println(pathNombreArchivo);
           System.out.println("Error de conexion con el archivo");
           return null;
        } catch (ClassNotFoundException e) {
            System.out.println("El objeto no tiene la forma de una pieza");
        }
        return null;
    }   

    public  List<T> leerListaArchivos(String extensionArchivo) {
        List<T> lista = new ArrayList<>();
        File folder = new File("./Repositorio/");
        if (folder.isDirectory()) {
            String[] files = folder.list();
            
            for (String fileName : files){
                if (fileName.endsWith(extensionArchivo)){      
                    File childFile = new File("./Repositorio/"+fileName);
                    
                    try(FileInputStream fileInputStream = new FileInputStream(childFile);
                        ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);){
                            lista.add((T) inputStream.readObject());
                        } 
                    catch (IOException e){
                        System.out.println(e.getMessage());;
                        e.printStackTrace();
                        System.out.println("Error de conexion con el archivo");
                        System.out.println("Error en leer el archivo");				
                    } catch (ClassNotFoundException e) {
                        System.out.println("El objeto no tiene la forma de un " + extensionArchivo );
                    }
                }
            }
        }
        return lista;
    }
    
}
