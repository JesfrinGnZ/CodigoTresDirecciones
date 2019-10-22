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
public class ManejadorDeTablaDeSimbolos {

    private final LinkedList<ElementoDeTablaDeSimbolos> tabla;
    private int posicion;
    private final EditorDeTextoFrame editor;
    private final ManejadorDeCuartetos manCuartetos;

    public ManejadorDeTablaDeSimbolos(EditorDeTextoFrame editor, ManejadorDeCuartetos manCuarteto) {
        this.tabla = new LinkedList<>();
        this.posicion = 1;
        this.editor = editor;
        this.manCuartetos = manCuarteto;
    }

    public Dato[] transformarDato(String tipoDeDato) {
        System.out.println("VAMOS A TRANSFORMAR EL DATO");
        Dato[] datos = new Dato[2];
        if (tipoDeDato.equalsIgnoreCase("string")) {
            datos[0] = TipoDeDato.CADENA;
            datos[1] = null;
        } else if (tipoDeDato.equalsIgnoreCase(TipoDeDato.BOOLEANO.name())) {
            datos[0] = TipoDeDato.BOOLEANO;
            datos[1] = null;
        } else if (tipoDeDato.equalsIgnoreCase(DatoNumerico.DOUBLE.name())) {
            datos[0] = TipoDeDato.NUMERICO;
            datos[1] = DatoNumerico.DOUBLE;
        } else if (tipoDeDato.equalsIgnoreCase(DatoNumerico.FLOAT.name())) {
            datos[0] = TipoDeDato.NUMERICO;
            datos[1] = DatoNumerico.FLOAT;
        } else if (tipoDeDato.equalsIgnoreCase(DatoNumerico.LONG.name())) {
            datos[0] = TipoDeDato.NUMERICO;
            datos[1] = DatoNumerico.LONG;
        } else if (tipoDeDato.equalsIgnoreCase(DatoNumerico.INT.name())) {
            datos[0] = TipoDeDato.NUMERICO;
            datos[1] = DatoNumerico.INT;
        } else if (tipoDeDato.equalsIgnoreCase(DatoNumerico.BYTE.name())) {
            datos[0] = TipoDeDato.NUMERICO;
            datos[1] = DatoNumerico.BYTE;
        } else {//char
            datos[0] = TipoDeDato.NUMERICO;
            datos[1] = DatoNumerico.CHAR;
        }
        return datos;
    }

    public void crearElemento(int linea, int columna, String nombre, TipoDeDato tipoDeDato, DatoNumerico datoNum, String dimension,boolean esArreglo) {
        ElementoDeTablaDeSimbolos elemento;
            if (datoNum == null) {
                elemento = new ElementoDeTablaDeSimbolos(this.posicion, nombre, tipoDeDato, datoNum, dimension, 0,0, '0',esArreglo);
            } else {
                elemento = new ElementoDeTablaDeSimbolos(this.posicion, nombre, tipoDeDato, datoNum, dimension, datoNum.getLimiteInferior(), datoNum.getLimiteSuperior(), '0',esArreglo);
            }
            this.tabla.add(elemento);
            posicion++;
       
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
