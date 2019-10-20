/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.cuarteto;

import gnz.backend.manejoDeDatos.DatoCodigo;
import gnz.backend.manejoDeDatos.Operacion;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeCuartetos {

    private ArrayList<Cuarteto> listaDeCuartetos;
    private boolean existioError;
    private int numeroDeTemporal;
    private int numeroDeEtiqueta;
    private int inicioDeExpresion;
    private Cuarteto ultimoGoto;
    private Cuarteto ultimoGotoParaIf;

    public ManejadorDeCuartetos() {
        this.listaDeCuartetos = new ArrayList<>();
        this.existioError = false;
        this.numeroDeTemporal = 1;
        this.numeroDeEtiqueta = 1;
        this.inicioDeExpresion = 0;
    }

    public String crearCuarteto(Operacion operando, String operador1, String operador2) {
        String temporal = "t" + this.numeroDeTemporal;
        numeroDeTemporal++;
        Cuarteto cuarteto = new Cuarteto(operando, operador1, operador2, temporal, TipoDeCuarteto.EXPRESION);
        this.listaDeCuartetos.add(cuarteto);
        return temporal;
    }
    
    public void crearCuartetoParaTrue(){
        
    }
    
    public void crearCuartetoParaFalse(){
        
    }
    

    public void crearCuartetoPrimeroParaComparacion(Operacion op, DatoCodigo t1, DatoCodigo t2) {
        if(op==null){//Viene un true o false
            boolean valor=true;
            if(valor){
                
            }else{
                
            }
        }else{
            Cuarteto c = crearCuartetoIf(op, t1, t2, "L" + numeroDeEtiqueta);//>>>>>>>>>>>>>>>>>>1
        numeroDeEtiqueta++;
        Cuarteto nuevoGoto = new Cuarteto(null, "goto", null, "L" + numeroDeEtiqueta, TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2
        this.ultimoGoto = nuevoGoto;
        this.ultimoGotoParaIf = c;
        numeroDeEtiqueta++;
        this.listaDeCuartetos.add(c);
        this.listaDeCuartetos.add(nuevoGoto);
        }
        
    }

    public int[] generarGotoAnd() {
        String numero = this.ultimoGoto.getResultado().substring(1, this.ultimoGoto.getResultado().length());
        String numero2 = this.ultimoGotoParaIf.getResultado().substring(1, this.ultimoGotoParaIf.getResultado().length());
        int arr[] = {Integer.valueOf(numero), Integer.valueOf(numero2)};
        return arr;
    }

    public void crearSegundoCuartetoParaAnd(Operacion op, DatoCodigo t1, DatoCodigo t2) {
        int numeroDeLabelAlInicio = generarGotoAnd()[1];
        int numeroDeLableFInal = generarGotoAnd()[0];
        //int numeroDeLabelAlInicio = numeroUltimoGotoIf;
        Cuarteto cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
        Cuarteto c = crearCuartetoIf(op, t1, t2, "L" + generarEtiqueta());//>>>>>>>>>>>>>>>>>>>2 
        // int numeroDeLableFInal = numeroDeUltimoGoto;
        Cuarteto nuevoGoto = new Cuarteto(null, "goto", null, "L" + numeroDeLableFInal, TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>3
        this.ultimoGoto = nuevoGoto;
        this.ultimoGotoParaIf = c;
        //numeroDeEtiqueta++;
        this.listaDeCuartetos.add(this.inicioDeExpresion, cuartetoLabel);
        this.listaDeCuartetos.add(c);
        this.listaDeCuartetos.add(nuevoGoto);
    }

    public void crearSegundoCuartetoParaOr(Operacion op, DatoCodigo t1, DatoCodigo t2) {
        int numeroDeLabelAlInicio = generarGotoAnd()[0];
        int labelDeIf = generarGotoAnd()[1];
        Cuarteto cuartetoLabel = new Cuarteto(null, null, null, "L" + numeroDeLabelAlInicio, TipoDeCuarteto.ETIQUETA);//>>>>>>>>>>>>>>>>1
        Cuarteto c = crearCuartetoIf(op, t1, t2, "L" + labelDeIf);//>>>>>>>>>>>>>>>>>>>2 
        Cuarteto nuevoGoto = new Cuarteto(null, "goto", null, "L" + generarEtiqueta(), TipoDeCuarteto.GOTOETIQUETA);//>>>>>>>>>>>>>>>>>>>>>>3
        this.ultimoGoto = nuevoGoto;
        this.ultimoGotoParaIf = c;
        this.listaDeCuartetos.add(this.inicioDeExpresion, cuartetoLabel);
        this.listaDeCuartetos.add(c);
        this.listaDeCuartetos.add(nuevoGoto);
    }

    private Cuarteto crearCuartetoIf(Operacion op, DatoCodigo t1, DatoCodigo t2, String numParaLabel) {
        Cuarteto c;
        if (t1.getTemporal() == null && t2.getTemporal() == null) {
            c = new Cuarteto(op, t1.getValor(), t2.getValor(), numParaLabel, TipoDeCuarteto.IF);//if(t1 op t2) goto 
        } else if (t1.getTemporal() == null) {//Si su operando es del tipo >,<,>=,<= etc, se generara el if
            c = new Cuarteto(op, t1.getValor(), t2.getTemporal(), numParaLabel, TipoDeCuarteto.IF);//if(t1 op t2) goto 
        } else if (t2.getTemporal() == null) {
            c = new Cuarteto(op, t1.getTemporal(), t2.getValor(), numParaLabel, TipoDeCuarteto.IF);
        } else {
            c = new Cuarteto(op, t1.getTemporal(), t2.getTemporal(), numParaLabel, TipoDeCuarteto.IF);
        }
        return c;
    }

    public void escribirCuartetos(JTextArea txt) {
        for (Cuarteto cuarteto : listaDeCuartetos) {
            if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.EXPRESION) {
                if (cuarteto.getOperando() == null && cuarteto.getOperador1() == null) {
                    txt.append(cuarteto.getResultado() + "=" + cuarteto.getOperador2() + "\n");
                } else {
                    txt.append(cuarteto.getResultado() + "=" + cuarteto.getOperador1() + " " + cuarteto.getOperando().getSigno() + " " + cuarteto.getOperador2() + "\n");
                }
            } else if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.IF) {
                txt.append("IF (" + cuarteto.getOperador1() + " " + cuarteto.getOperando().getSigno() + " " + cuarteto.getOperador2() + ") " + "goto" + " " + cuarteto.getResultado() + "\n");
            } else if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.GOTOETIQUETA) {//Cuarteto ETIQUETA
                txt.append("goto" + " " + cuarteto.getResultado() + "\n");
            } else {//Solo es Etiqueta
                txt.append("  " + cuarteto.getResultado() + ":" + "\n");
            }

        }
        this.listaDeCuartetos.clear();
        this.numeroDeTemporal = 1;
        this.numeroDeEtiqueta = 1;
    }

    public int generarTemporal() {
        return numeroDeTemporal++;
    }

    public int generarEtiqueta() {
        return numeroDeEtiqueta++;
    }

    public ArrayList<Cuarteto> getListaDeCuartetos() {
        return listaDeCuartetos;
    }

    public void setListaDeCuartetos(ArrayList<Cuarteto> listaDeCuartetos) {
        this.listaDeCuartetos = listaDeCuartetos;
    }

    public boolean existioError() {
        return existioError;
    }

    public void setExistioError(boolean existioErrorSemantico) {
        this.existioError = existioErrorSemantico;
    }

    public int getNumeroDeTemporal() {
        return numeroDeTemporal;
    }

    public void setNumeroDeTemporal(int numeroDeTemporal) {
        this.numeroDeTemporal = numeroDeTemporal;
    }

    public int getNumeroDeEtiqueta() {
        return numeroDeEtiqueta;
    }

    public void setNumeroDeEtiqueta(int numeroDeEtiqueta) {
        this.numeroDeEtiqueta = numeroDeEtiqueta;
    }

    public int getInicioDeExpresion() {
        return inicioDeExpresion;
    }

    public void setInicioDeExpresion() {
        this.inicioDeExpresion = this.listaDeCuartetos.size() - 1;
    }

}
