package gnz.backend.tablaDeSimbolos;

import gnz.backend.manejoDeDatos.DatoNumerico;
import gnz.backend.manejoDeDatos.TipoDeDato;

/**
 *
 * @author jesfrin
 */
public class ElementoDeTablaDeSimbolos {

   
    private int posicion;
    private String nombre;
    private TipoDeDato tipoBase;
    private DatoNumerico tipoNumerico;
    private int dimension;
    private double minimo;
    private double maximo;
    private char ambito;
    private boolean esArreglo;

    public ElementoDeTablaDeSimbolos(int posicion, String nombre, TipoDeDato tipoBase, DatoNumerico tipoNumerico, int dimension, double minimo, double maximo, char ambito,boolean esArreglo) {
        this.posicion = posicion;
        this.nombre = nombre;
        this.tipoBase = tipoBase;
        this.tipoNumerico = tipoNumerico;
        this.dimension = dimension;
        this.minimo = minimo;
        this.maximo = maximo;
        this.ambito = ambito;
        this.esArreglo=esArreglo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDeDato getTipoBase() {
        return tipoBase;
    }

    public void setTipoBase(TipoDeDato tipoBase) {
        this.tipoBase = tipoBase;
    }

    public DatoNumerico getTipoNumerico() {
        return tipoNumerico;
    }

    public void setTipoNumerico(DatoNumerico tipoNumerico) {
        this.tipoNumerico = tipoNumerico;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public double getMinimo() {
        return minimo;
    }

    public void setMinimo(double minimo) {
        this.minimo = minimo;
    }

    public double getMaximo() {
        return maximo;
    }

    public void setMaximo(double maximo) {
        this.maximo = maximo;
    }

    public char getAmbito() {
        return ambito;
    }

    public void setAmbito(char ambito) {
        this.ambito = ambito;
    }

    public boolean isEsArreglo() {
        return esArreglo;
    }

    public void setEsArreglo(boolean esArreglo) {
        this.esArreglo = esArreglo;
    }

}
