/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeDatos;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.ManejadorDeCuartetos;

/**
 *
 * @author jesfrin
 */
public class DatoCodigo {

    private int numeroDeLinea;
    private int numeroDeColumna;
    private TipoDeDato dato;
    private DatoNumerico datoNumerico;
    private String valor;
    private String temporal;

    public DatoCodigo(int numeroDeLinea, int numeroDeColumna, TipoDeDato dato, DatoNumerico datoNumerico, String valor) {
        this.numeroDeLinea = numeroDeLinea;
        this.numeroDeColumna = numeroDeColumna;
        this.dato = dato;
        this.datoNumerico = datoNumerico;
        this.valor = valor;
        this.temporal = null;
    }

    public DatoCodigo(int numeroDeLinea, int numeroDeColumna, TipoDeDato dato, DatoNumerico datoNumerico, String valor, String temporal) {
        this.numeroDeLinea = numeroDeLinea;
        this.numeroDeColumna = numeroDeColumna;
        this.dato = dato;
        this.datoNumerico = datoNumerico;
        this.valor = valor;
        this.temporal = temporal;
    }

   
    
    public String getTemporal() {
        return temporal;
    }

    public void setTemporal(String temporal) {
        this.temporal = temporal;
    }

    public int getNumeroDeLinea() {
        return numeroDeLinea;
    }

    public void setNumeroDeLinea(int numeroDeLinea) {
        this.numeroDeLinea = numeroDeLinea;
    }

    public int getNumeroDeColumna() {
        return numeroDeColumna;
    }

    public void setNumeroDeColumna(int numeroDeColumna) {
        this.numeroDeColumna = numeroDeColumna;
    }

    public TipoDeDato getDato() {
        return dato;
    }

    public void setDato(TipoDeDato dato) {
        this.dato = dato;
    }

    public DatoNumerico getDatoNumerico() {
        return datoNumerico;
    }

    public void setDatoNumerico(DatoNumerico datoNumerico) {
        this.datoNumerico = datoNumerico;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
}
