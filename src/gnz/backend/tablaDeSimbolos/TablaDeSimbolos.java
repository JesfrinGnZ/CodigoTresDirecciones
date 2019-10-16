/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.tablaDeSimbolos;

import gnz.backend.cuarteto.ManejadorDeCuartetos;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.manejoDeDatos.Dato;
import gnz.backend.manejoDeDatos.DatoNumerico;
import gnz.backend.manejoDeDatos.TipoDeDato;
import gnz.frontend.editorDeTexto.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class TablaDeSimbolos {

    private final LinkedList<ElementoDeTablaDeSimbolos> tabla;
    private int posicion;
    private EditorDeTextoFrame editor;
    private ManejadorDeCuartetos manCuartetos;

    public TablaDeSimbolos(EditorDeTextoFrame editor,ManejadorDeCuartetos manCuarteto) {
        this.tabla = new LinkedList<>();
        this.posicion = 1;
        this.editor= editor;
        this.manCuartetos = manCuarteto;
    }

    public Dato[] transformarDato(String tipoDeDato){
        Dato[] datos = new Dato[2];
        if(tipoDeDato.equalsIgnoreCase("string")){
            datos[0]=TipoDeDato.CADENA;
            datos[1]=null;
        }else if(tipoDeDato.equalsIgnoreCase("boolean")){
            datos[0]=TipoDeDato.BOOLEANO;
            datos[1]=null;
        }else if(tipoDeDato.equalsIgnoreCase("double")){
            datos[0]=TipoDeDato.NUMERICO;
            datos[1]=DatoNumerico.DOUBLE;
        }else if(tipoDeDato.equalsIgnoreCase("float")){
            datos[0]=TipoDeDato.NUMERICO;
            datos[1]=DatoNumerico.FLOAT;
        }else if(tipoDeDato.equalsIgnoreCase("long")){
            datos[0]=TipoDeDato.NUMERICO;
            datos[1]=DatoNumerico.LONG;
        }else if(tipoDeDato.equalsIgnoreCase("int")){
            datos[0]=TipoDeDato.NUMERICO;
            datos[1]=DatoNumerico.INT;
        }else if(tipoDeDato.equalsIgnoreCase("byte")){
            datos[0]=TipoDeDato.NUMERICO;
            datos[1]=DatoNumerico.BYTE;
        }else{//char
            datos[0]=TipoDeDato.NUMERICO;
            datos[1]=DatoNumerico.CHAR;
        }
        return datos;
    }
    
    public void crearElemento(int linea,int columna,String nombre,Dato[] datos,int dimension) {
        double minimo;
        double maximo;
        TipoDeDato tipoBase=(TipoDeDato)datos[0];
        DatoNumerico datoNum=null;
        if (datos[1] == null) {
            minimo = 0;
            maximo = 0;
        } else {
            datoNum= (DatoNumerico)datos[1];
            minimo = datoNum.getLimiteInferior();
            maximo = datoNum.getLimiteSuperior();
        }

        if (buscarElemento(nombre) == null) {
            ElementoDeTablaDeSimbolos elemento = new ElementoDeTablaDeSimbolos(this.posicion, nombre, tipoBase, datoNum, dimension, minimo, maximo, '0');
            this.tabla.add(elemento);
            posicion++;
        } else {
            //Error semantico la variable ya ha sido declarada anteriormente
            String mensaje="La variable "+nombre+" ya ha sido declarada";
            ManejadorDeErrores.mostrarErrorSemantico(editor.getErroresTextArea(), mensaje, linea, columna);
            manCuartetos.setExistioErrorSemantico(true);
        }

    }

    public ElementoDeTablaDeSimbolos buscarElemento(String nombre) {
        for (ElementoDeTablaDeSimbolos elementoDeTabla : tabla) {
            if (elementoDeTabla.getNombre().equals(nombre)) {
                return elementoDeTabla;
            }
        }
        return null;
    }

    public static void actualizarElemento(String nombre, String valor) {

    }

    public static void eliminarElemento() {

    }

    public static void getTipoDelElemento(String nombre) {

    }

}
